package healthcareab.project.healthcare_booking_app.services;

import healthcareab.project.healthcare_booking_app.dto.PatientResponse;
import healthcareab.project.healthcare_booking_app.exceptions.BadRequestException;
import healthcareab.project.healthcare_booking_app.models.Patient;
import healthcareab.project.healthcare_booking_app.models.User;
import healthcareab.project.healthcare_booking_app.repository.PatientRepository;
import healthcareab.project.healthcare_booking_app.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PatientServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ===================== SUCCESS CASE =====================

    @Test
    void findPatientByUserId_shouldReturnPatient_whenUserIsPatient() {
        // GIVEN
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        Patient patient = new Patient();
        patient.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        when(patientRepository.findById(userId)).thenReturn(Optional.of(patient));

        // WHEN
        PatientResponse response = patientService.findPatientByUserId(userId);

        // THEN
        assertNotNull(response);
        assertEquals(userId, response.getUserId());

        verify(userRepository, times(1)).findById(userId);
        verify(patientRepository, times(1)).findById(userId);
    }

    // ===================== USER NOT FOUND =====================

    @Test
    void findPatientByUserId_shouldThrowException_whenUserDoesNotExist() {
        // GIVEN
        Long userId = 2L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // WHEN + THEN
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> patientService.findPatientByUserId(userId));

        assertEquals("User not found", exception.getMessage());

        verify(userRepository, times(1)).findById(userId);
        verify(patientRepository, never()).findById(any());
    }

    // ===================== NOT A PATIENT =====================

    @Test
    void findPatientByUserId_shouldThrowBadRequest_whenUserIsNotPatient() {
        // GIVEN
        Long userId = 3L;

        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        when(patientRepository.findById(userId)).thenReturn(Optional.empty());

        // WHEN + THEN
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> patientService.findPatientByUserId(userId));

        assertEquals("User id does not belong to a patient", exception.getMessage());

        verify(userRepository, times(1)).findById(userId);
        verify(patientRepository, times(1)).findById(userId);
    }
}
