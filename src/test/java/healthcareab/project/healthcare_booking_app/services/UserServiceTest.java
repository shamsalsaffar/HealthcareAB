package healthcareab.project.healthcare_booking_app.services;

import healthcareab.project.healthcare_booking_app.dto.UpdateUserRequest;
import healthcareab.project.healthcare_booking_app.models.Caregiver;
import healthcareab.project.healthcare_booking_app.models.Patient;
import healthcareab.project.healthcare_booking_app.models.enums.EmailVerificationToken;
import healthcareab.project.healthcare_booking_app.models.enums.Specialisation;
import healthcareab.project.healthcare_booking_app.repository.CaregiverRepository;
import healthcareab.project.healthcare_booking_app.repository.EmailVerificationTokenRepository;
import healthcareab.project.healthcare_booking_app.repository.PatientRepository;
import healthcareab.project.healthcare_booking_app.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailVerificationTokenRepository emailVerificationTokenRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private CaregiverRepository caregiverRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void deleteUserById_shouldDeleteTokenChildAndUser_whenUserExists() {
        // arrange
        Long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(true);

        EmailVerificationToken token = new EmailVerificationToken();
        when(emailVerificationTokenRepository.findByUserId(userId)).thenReturn(Optional.of(token));

        when(patientRepository.existsById(userId)).thenReturn(true);
        when(caregiverRepository.existsById(userId)).thenReturn(false);

        // act
        userService.deleteUserById(userId);

        // assert
        verify(emailVerificationTokenRepository).findByUserId(userId);
        verify(emailVerificationTokenRepository).delete(token);

        verify(patientRepository).existsById(userId);
        verify(patientRepository).deleteById(userId);

        verify(caregiverRepository).existsById(userId);
        verify(caregiverRepository, never()).deleteById(userId);

        verify(userRepository).deleteById(userId);
    }

    @Test
    void deleteUserById_shouldThrowNotFound_whenUserDoesNotExist() {
        // arrange
        Long userId = 999L;
        when(userRepository.existsById(userId)).thenReturn(false);

        // act + assert
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> userService.deleteUserById(userId));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertEquals("User not found", ex.getReason());
    }

    @Test
    void updateUser_shouldUpdatePatientFields() {
        Patient patient = new Patient();
        patient.setFirstName("Old");
        patient.setLastName("Name");
        patient.setPhoneNumber("123");

        when(userRepository.findById(1L)).thenReturn(Optional.of(patient));

        UpdateUserRequest dto = new UpdateUserRequest();
        dto.setFirstName("New");
        dto.setLastName("User");
        dto.setPhoneNumber("999");
        dto.setAddress("New Address");
        dto.setPersonalIdentityNumber("PIN123");

        userService.updateUser(1L, dto);

        assertEquals("New", patient.getFirstName());
        assertEquals("User", patient.getLastName());
        assertEquals("999", patient.getPhoneNumber());
        assertEquals("New Address", patient.getAddress());
        assertEquals("PIN123", patient.getPersonalIdentityNumber());
    }

    @Test
    void updateUser_shouldUpdateCaregiverFields() {
        Caregiver caregiver = new Caregiver();
        caregiver.setSpecialisation(Specialisation.CARDIOLOGY);

        when(userRepository.findById(1L)).thenReturn(Optional.of(caregiver));

        UpdateUserRequest dto = new UpdateUserRequest();
        dto.setSpecialisation(Specialisation.NEUROLOGY);

        userService.updateUser(1L, dto);

        assertEquals(Specialisation.NEUROLOGY, caregiver.getSpecialisation());
    }

    @Test
    void updateUser_shouldThrowNotFound_whenUserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        UpdateUserRequest dto = new UpdateUserRequest();

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> userService.updateUser(1L, dto));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }
}
