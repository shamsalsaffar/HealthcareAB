package healthcareab.project.healthcare_booking_app.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import healthcareab.project.healthcare_booking_app.config.SecurityConfig;
import healthcareab.project.healthcare_booking_app.dto.PatientRegisterRequest;
import healthcareab.project.healthcare_booking_app.models.Patient;
import healthcareab.project.healthcare_booking_app.models.enums.Role;
import healthcareab.project.healthcare_booking_app.repository.PatientRepository;
import healthcareab.project.healthcare_booking_app.services.AuthService;
import healthcareab.project.healthcare_booking_app.utils.JwtUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class,
        SecurityFilterAutoConfiguration.class }, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class))
@Disabled("WebMvcTest disabled until Security/JWT test setup is ready")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // ✅ FIX: Mocka AuthenticationManager så AuthController kan skapas
    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private PatientRepository patientRepository;

    @Test
    void register_shouldReturn201_whenValidRequest() throws Exception {
        // Arrange
        PatientRegisterRequest req = new PatientRegisterRequest("test@test.com", "Password@123!", "John", "Doe",
                "Gothenburg, Sweden", "0701234567", "199001011234");

        when(patientRepository.existsByUsername(req.getUsername())).thenReturn(false);
        when(patientRepository.existsByPersonalIdentityNumber(req.getPersonalIdentityNumber())).thenReturn(false);

        Patient saved = new Patient();
        saved.setUsername(req.getUsername());
        saved.setRole(Role.USER);

        when(authService.registerPatient(any(Patient.class))).thenReturn(saved);

        // Act + Assert
        mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))).andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Patient registered successfully"))
                .andExpect(jsonPath("$.username").value("test@test.com")).andExpect(jsonPath("$.role").value("USER"));
    }

    @Test
    void register_shouldReturn409_whenEmailExists() throws Exception {
        // Arrange
        PatientRegisterRequest req = new PatientRegisterRequest("existing@test.com", "Password@123!", "John", "Doe",
                "Gothenburg, Sweden", "0701234567", "199001011234");

        when(patientRepository.existsByUsername(req.getUsername())).thenReturn(true);

        // Act + Assert
        mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))).andExpect(status().isConflict())
                .andExpect(content().string("Email already exists."));
    }
}
