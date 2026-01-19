package healthcareab.project.healthcare_booking_app.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import healthcareab.project.healthcare_booking_app.config.SecurityConfig;
import healthcareab.project.healthcare_booking_app.dto.CaregiverResponse;
import healthcareab.project.healthcare_booking_app.filters.JwtAuthenticationFilter;
import healthcareab.project.healthcare_booking_app.models.Clinic;
import healthcareab.project.healthcare_booking_app.services.CaregiverService;
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

import java.time.LocalDateTime;

import static healthcareab.project.healthcare_booking_app.models.enums.Specialisation.NEUROLOGY;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = CaregiverController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class, SecurityFilterAutoConfiguration.class},
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
                SecurityConfig.class,
                JwtAuthenticationFilter.class
        })
)
class CaregiverControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    CaregiverService caregiverService;

    @Test
    void findCaregiverByUserId_shouldReturnOk_whenValidRequest() throws Exception {
        // Arrange
        Long userId = 1L;
        CaregiverResponse response = new CaregiverResponse();
        response.setUserId(userId);
        response.setFirstName("Care");
        response.setLastName("Giver");
        response.setSpecialisation(NEUROLOGY);
        response.setClinic(new Clinic(1L, "Clinic", "Location", "0727654231",
                LocalDateTime.now(), LocalDateTime.now()));

        when(caregiverService.findCaregiverByUserId(userId)).thenReturn(response);


        // Act + Assert
        mockMvc.perform(get("/caregiver/find-by-user-id")
                .param("userId", userId.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.firstName").value("Care"))
                .andExpect(jsonPath("$.lastName").value("Giver"))
                .andExpect(jsonPath("$.firstName").value("Care"))
                .andExpect(jsonPath("$.specialisation").value(NEUROLOGY.name()))
                .andExpect(jsonPath("$.clinic.id").value(1));
    }

    @Test
    void findCaregiverByUserId_shouldReturnBadRequest_whenUserIdIsEmpty() throws Exception {
        // Act + Assert
        mockMvc.perform(get("/caregiver/find-by-user-id")
                        .param("userId", "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("userId is required and must be provided"));
    }
}