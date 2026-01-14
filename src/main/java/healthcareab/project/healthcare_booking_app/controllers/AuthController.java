package healthcareab.project.healthcare_booking_app.controllers;

import healthcareab.project.healthcare_booking_app.dto.AuthRequest;
import healthcareab.project.healthcare_booking_app.dto.AuthResponse;
import healthcareab.project.healthcare_booking_app.dto.PatientRegisterRequest;
import healthcareab.project.healthcare_booking_app.dto.RegisterResponse;
import healthcareab.project.healthcare_booking_app.models.Patient;
import healthcareab.project.healthcare_booking_app.models.enums.Role;
import healthcareab.project.healthcare_booking_app.repository.PatientRepository;
import healthcareab.project.healthcare_booking_app.services.AuthService;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AuthService authService;
    private final PatientRepository patientRepository;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          AuthService authService,
                          PatientRepository patientRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.authService = authService;
        this.patientRepository = patientRepository;
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

        authService.registerPatient(patient);

        RegisterResponse response = new RegisterResponse(
                "Patient registered successfully",
                patient.getUsername(),
                patient.getRole()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // =========================
    // LOGIN
    // =========================
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest authRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String jwt = jwtUtil.generateToken(userDetails);

            ResponseCookie jwtCookie = ResponseCookie.from("jwt", jwt)
                    .httpOnly(true)
                    .secure(false) // IMPORTANT: true in production (HTTPS)
                    .path("/")
                    .maxAge(10 * 60 * 60)
                    .sameSite("Lax")
                    .build();

            // Fetch patient to include patient fields
            Patient patient = patientRepository.findByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("Patient not found"));

            // NOTE: do NOT return personalIdentityNumber (sensitive)
            AuthResponse authResponse = new AuthResponse(
                    jwt,
                    patient.getUsername(),
                    patient.getRole(),
                    patient.getUsername(),   // email
                    patient.getFirstName(),
                    patient.getLastName(),
                    patient.getPhoneNumber(),
                    patient.getAddress(),
                    "Login successful"
            );

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .body(authResponse);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
        }
    }

    // =========================
    // LOGOUT
    // =========================
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        ResponseCookie jwtCookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(false) // IMPORTANT: true in production (HTTPS)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();

        SecurityContextHolder.clearContext();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body("Logout successful!");
    }

    // =========================
    // CHECK AUTH (ME)
    // =========================
    @GetMapping("/check")
    public ResponseEntity<?> checkAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated!");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // get full patient info
        Patient patient = patientRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Patient not found"));

        AuthResponse response = new AuthResponse(
                null, // since token is in cookie, you can keep this null (or remove jwt from response)
                patient.getUsername(),
                patient.getRole(),
                patient.getUsername(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getPhoneNumber(),
                patient.getAddress(),
                "Authenticated"
        );

        return ResponseEntity.ok(response);
    }
}
