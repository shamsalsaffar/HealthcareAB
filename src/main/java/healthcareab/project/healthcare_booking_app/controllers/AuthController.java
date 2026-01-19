package healthcareab.project.healthcare_booking_app.controllers;

import healthcareab.project.healthcare_booking_app.dto.AuthRequest;
import healthcareab.project.healthcare_booking_app.dto.AuthResponse;
import healthcareab.project.healthcare_booking_app.dto.PatientRegisterRequest;
import healthcareab.project.healthcare_booking_app.dto.RegisterResponse;
import healthcareab.project.healthcare_booking_app.models.Patient;
import healthcareab.project.healthcare_booking_app.models.User;
import healthcareab.project.healthcare_booking_app.models.enums.EmailVerificationToken;
import healthcareab.project.healthcare_booking_app.models.enums.Role;
import healthcareab.project.healthcare_booking_app.repository.EmailVerificationTokenRepository;
import healthcareab.project.healthcare_booking_app.repository.PatientRepository;
import healthcareab.project.healthcare_booking_app.repository.UserRepository;
import healthcareab.project.healthcare_booking_app.services.AuthService;
import healthcareab.project.healthcare_booking_app.services.MailService;
import healthcareab.project.healthcare_booking_app.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AuthService authService;
    private final PatientRepository patientRepository;
    private final MailService mailService;
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, AuthService authService,
            PatientRepository patientRepository, MailService mailService,
            EmailVerificationTokenRepository emailVerificationTokenRepository, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.authService = authService;
        this.patientRepository = patientRepository;
        this.mailService = mailService;
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
        this.userRepository = userRepository;
    }

    // =========================
    // REGISTER PATIENT
    // =========================
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody PatientRegisterRequest req) {

        // Email uniqueness
        if (patientRepository.existsByUsername(req.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists.");
        }

        // PIN uniqueness
        if (patientRepository.existsByPersonalIdentityNumber(req.getPersonalIdentityNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Personal identity number already exists.");
        }

        Patient patient = new Patient();
        patient.setUsername(req.getUsername());
        patient.setPassword(req.getPassword()); // will be encoded in AuthService
        patient.setFirstName(req.getFirstName());
        patient.setLastName(req.getLastName());

        patient.setAddress(req.getAddress());
        patient.setPhoneNumber(req.getPhoneNumber());
        patient.setPersonalIdentityNumber(req.getPersonalIdentityNumber());

        patient.setRole(Role.USER);
        patient.setEnabled(false);

        Patient saved = authService.registerPatient(patient);

        // create token
        String token = UUID.randomUUID().toString().replace("-", "");
        EmailVerificationToken emailVerificationToken = new EmailVerificationToken();
        emailVerificationToken.setToken(token);
        emailVerificationToken.setUser(saved);
        emailVerificationToken.setExpiresAt(LocalDateTime.now().plusHours(24));
        emailVerificationTokenRepository.findByUserId(saved.getId())
                .ifPresent(emailVerificationTokenRepository::delete);

        emailVerificationTokenRepository.save(emailVerificationToken);

        // send email
        String link = "http://localhost:8080/auth/verify?token=" + token;
        mailService.sendVerificationEmail(saved.getUsername(), link);

        RegisterResponse response = new RegisterResponse("Registered successfully. Please verify your email.",
                saved.getUsername(), saved.getRole());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /*
     * authService.registerPatient(patient); RegisterResponse response = new RegisterResponse(
     * "Patient registered successfully", patient.getUsername(), patient.getRole() ); return
     * ResponseEntity.status(HttpStatus.CREATED).body(response); }
     */

    // =========================
    // VERIFY
    // =========================
    @GetMapping("/verify")
    public ResponseEntity<?> verifyEmail(@RequestParam String token) {
        EmailVerificationToken emailVerificationToken = emailVerificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid token"));
        if (emailVerificationToken.isUsed()) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token already used.");
        }
        if (emailVerificationToken.isExpired()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token has expired.");
        }
        User user = emailVerificationToken.getUser();
        user.setEnabled(true);
        user.setEmailVerifiedAt(LocalDateTime.now());
        userRepository.save(user);

        emailVerificationToken.setUsedAt(LocalDateTime.now());
        emailVerificationTokenRepository.save(emailVerificationToken);

        return ResponseEntity
                .ok(new RegisterResponse(" Email verified successfully!+ \n + ", user.getUsername(), user.getRole()));

    }

    // =========================
    // LOGIN
    // =========================
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest authRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String jwt = jwtUtil.generateToken(userDetails);

            ResponseCookie jwtCookie = ResponseCookie.from("jwt", jwt).httpOnly(true).secure(false) // IMPORTANT: true
                                                                                                    // in production
                                                                                                    // (HTTPS)
                    .path("/").maxAge(10 * 60 * 60).sameSite("Lax").build();

            // Fetch patient to include patient fields
            Patient patient = patientRepository.findByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("Patient not found"));

            // NOTE: do NOT return personalIdentityNumber (sensitive)
            AuthResponse authResponse = new AuthResponse(jwt, patient.getUsername(), patient.getRole(),
                    patient.getUsername(), // email
                    patient.getFirstName(), patient.getLastName(), patient.getPhoneNumber(), patient.getAddress(),
                    "Login successful");

            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(authResponse);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
        }
    }

    // =========================
    // LOGOUT
    // =========================
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        ResponseCookie jwtCookie = ResponseCookie.from("jwt", "").httpOnly(true).secure(false) // IMPORTANT: true in
                                                                                               // production (HTTPS)
                .path("/").maxAge(0).sameSite("Strict").build();

        SecurityContextHolder.clearContext();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body("Logout successful!");
    }

    // =========================
    // CHECK AUTH (ME)
    // =========================
    @GetMapping("/check")
    public ResponseEntity<?> checkAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated!");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // get full patient info
        Patient patient = patientRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Patient not found"));

        AuthResponse response = new AuthResponse(null, // since token is in cookie, you can keep this null (or remove
                                                       // jwt from response)
                patient.getUsername(), patient.getRole(), patient.getUsername(), patient.getFirstName(),
                patient.getLastName(), patient.getPhoneNumber(), patient.getAddress(), "Authenticated");

        return ResponseEntity.ok(response);
    }

}
