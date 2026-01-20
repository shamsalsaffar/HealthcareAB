package healthcareab.project.healthcare_booking_app.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import healthcareab.project.healthcare_booking_app.config.SecurityConfig;
import healthcareab.project.healthcare_booking_app.dto.PatientResponse;
import healthcareab.project.healthcare_booking_app.filters.JwtAuthenticationFilter;
import healthcareab.project.healthcare_booking_app.services.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PatientController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class,
        SecurityFilterAutoConfiguration.class }, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
                SecurityConfig.class, JwtAuthenticationFilter.class }))
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    PatientService patientService;

    @Test
    void findPatientByUserId_shouldReturnOk_whenValidRequest() throws Exception {
        Long userId = 1L;
        PatientResponse response = new PatientResponse();
        response.setUserId(userId);
        response.setFirstName("Test");
        response.setLastName("Patient");
        response.setAddress("Sesame Street 24, Gothenburg");
        response.setPersonalIdentityNumber("199012011300");
        response.setPhoneNumber("0788484848");

        when(patientService.findPatientByUserId(userId)).thenReturn(response);

        mockMvc.perform(get("/patient/find-by-user-id").param("userId", userId.toString())
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.firstName").value("Test")).andExpect(jsonPath("$.lastName").value("Patient"))
                .andExpect(jsonPath("$.address").value("Sesame Street 24, Gothenburg"))
                .andExpect(jsonPath("$.personalIdentityNumber").value("199012011300"))
                .andExpect(jsonPath("$.phoneNumber").value("0788484848"));
    }

    @Test
    void findPatientByUserId_shouldReturnBadRequest_whenUserIdIsEmpty() throws Exception {
        mockMvc.perform(get("/patient/find-by-user-id").param("userId", "").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("userId is required and must be provided"));
    }

}
