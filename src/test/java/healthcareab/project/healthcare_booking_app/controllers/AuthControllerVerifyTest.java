package healthcareab.project.healthcare_booking_app.controllers;

import healthcareab.project.healthcare_booking_app.models.User;
import healthcareab.project.healthcare_booking_app.models.enums.EmailVerificationToken;
import healthcareab.project.healthcare_booking_app.models.enums.Role;
import healthcareab.project.healthcare_booking_app.repository.EmailVerificationTokenRepository;
import healthcareab.project.healthcare_booking_app.repository.PatientRepository;
import healthcareab.project.healthcare_booking_app.repository.UserRepository;
import healthcareab.project.healthcare_booking_app.services.AuthService;
import healthcareab.project.healthcare_booking_app.services.MailService;
import healthcareab.project.healthcare_booking_app.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerVerifyTest {

    @Test
    void verify_shouldEnableUser_whenTokenIsValid() throws Exception {

        AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);
        JwtUtil jwtUtil = Mockito.mock(JwtUtil.class);
        AuthService authService = Mockito.mock(AuthService.class);
        PatientRepository patientRepository = Mockito.mock(PatientRepository.class);
        MailService mailService = Mockito.mock(MailService.class);
        EmailVerificationTokenRepository tokenRepo = Mockito.mock(EmailVerificationTokenRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        AuthController controller = new AuthController(authenticationManager, jwtUtil, authService, patientRepository,
                mailService, tokenRepo, userRepository);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        User user = new User();
        user.setUsername("test@test.com");
        user.setRole(Role.USER);
        user.setEnabled(false);

        EmailVerificationToken token = new EmailVerificationToken();
        token.setToken("abc123");
        token.setUser(user);
        token.setExpiresAt(LocalDateTime.now().plusHours(24));
        token.setUsedAt(null);

        Mockito.when(tokenRepo.findByToken("abc123")).thenReturn(Optional.of(token));

        mockMvc.perform(get("/auth/verify").param("token", "abc123")).andExpect(status().isOk());

        verify(userRepository, times(1)).save(any(User.class));
        verify(tokenRepo, times(1)).save(any(EmailVerificationToken.class));
    }
}
