package healthcareab.project.healthcare_booking_app.services;

import healthcareab.project.healthcare_booking_app.dto.UpdateUserRequest;
import healthcareab.project.healthcare_booking_app.models.Caregiver;
import healthcareab.project.healthcare_booking_app.models.Patient;
import healthcareab.project.healthcare_booking_app.models.Patient;
import healthcareab.project.healthcare_booking_app.models.User;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
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
    void anonymizeUserById_shouldThrowNotFound_whenUserDoesNotExist() {
        Long userId = 999L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> userService.anonymizeUserById(userId));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertEquals("User not found", ex.getReason());

        verify(userRepository).findById(userId);
        verifyNoMoreInteractions(patientRepository, caregiverRepository, emailVerificationTokenRepository);
    }

    @Test
    void anonymizeUserById_shouldDoNothing_whenAlreadyDeleted() {
        Long userId = 1L;

        User user = new User();
        user.setId(userId);
        user.setDeleted(true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.anonymizeUserById(userId);

        verify(userRepository).findById(userId);
        verify(userRepository, never()).save(any());
        verify(patientRepository, never()).save(any());
        verify(emailVerificationTokenRepository, never()).delete(any());
    }

    @Test
    void anonymizeUserById_shouldAnonymizeUserAndPatient_whenPatientExists() {
        Long userId = 1L;

        // Arrange: user (parent table)
        User user = new User();
        user.setId(userId);
        user.setUsername("shamsalsaffar@gmail.com");
        user.setFirstName("TEST");
        user.setLastName("alsaffar");
        user.setEnabled(true);
        user.setDeleted(false);

        // Arrange: patient (child table)
        Patient patient = new Patient();
        patient.setId(userId); // same id because JOINED inheritance
        patient.setAddress("Gothenburg, Sweden");
        patient.setPhoneNumber("0701234567");
        patient.setPersonalIdentityNumber("199001011359");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        EmailVerificationToken token = new EmailVerificationToken();
        when(emailVerificationTokenRepository.findByUserId(userId)).thenReturn(Optional.of(token));

        when(patientRepository.findById(userId)).thenReturn(Optional.of(patient));
        when(caregiverRepository.findById(userId)).thenReturn(Optional.empty());

        // Act
        userService.anonymizeUserById(userId);

        // Assert user changes
        assertEquals("anon+" + userId + "@example.invalid", user.getUsername());
        assertEquals("Anonymized", user.getFirstName());
        assertEquals("Anonymized", user.getLastName());
        assertEquals(false, user.isEnabled());
        assertEquals(true, user.isDeleted());

        // Assert patient changes
        assertEquals("Anonymized", patient.getAddress());
        assertEquals("0700000000", patient.getPhoneNumber());
        assertEquals("19000101" + String.format("%04d", userId % 10000), patient.getPersonalIdentityNumber());

        // Verify repository calls
        verify(emailVerificationTokenRepository).findByUserId(userId);
        verify(emailVerificationTokenRepository).delete(token);

        verify(patientRepository).save(patient);
        verify(userRepository).save(user);

        // caregiver not saved
        verify(caregiverRepository, never()).save(any());
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
