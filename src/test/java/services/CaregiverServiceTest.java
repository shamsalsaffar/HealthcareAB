package healthcareab.project.healthcare_booking_app.services;

import healthcareab.project.healthcare_booking_app.dto.CaregiverResponse;
import healthcareab.project.healthcare_booking_app.exceptions.BadRequestException;
import healthcareab.project.healthcare_booking_app.models.Caregiver;
import healthcareab.project.healthcare_booking_app.models.User;
import healthcareab.project.healthcare_booking_app.repository.CaregiverRepository;
import healthcareab.project.healthcare_booking_app.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CaregiverServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CaregiverRepository caregiverRepository;

    @InjectMocks
    private CaregiverService caregiverService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ===================== SUCCESS CASE =====================

    @Test
    void findCaregiverByUserId_shouldReturnCaregiver_whenUserIsCaregiver() {
        // GIVEN
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        Caregiver caregiver = new Caregiver();
        caregiver.setId(userId);

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(caregiverRepository.findById(userId))
                .thenReturn(Optional.of(caregiver));

        // WHEN
        CaregiverResponse response =
                caregiverService.findCaregiverByUserId(userId);

        // THEN
        assertNotNull(response);
        assertEquals(userId, response.getUserId());

        verify(userRepository, times(1)).findById(userId);
        verify(caregiverRepository, times(1)).findById(userId);
    }

    // ===================== USER NOT FOUND =====================

    @Test
    void findCaregiverByUserId_shouldThrowException_whenUserDoesNotExist() {
        // GIVEN
        Long userId = 2L;

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        // WHEN + THEN
        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> caregiverService.findCaregiverByUserId(userId)
        );

        assertEquals("User not found", exception.getMessage());

        verify(userRepository, times(1)).findById(userId);
        verify(caregiverRepository, never()).findById(any());
    }

    // ===================== NOT A CAREGIVER =====================

    @Test
    void findCaregiverByUserId_shouldThrowBadRequest_whenUserIsNotCaregiver() {
        // GIVEN
        Long userId = 3L;

        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(caregiverRepository.findById(userId))
                .thenReturn(Optional.empty());

        // WHEN + THEN
        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> caregiverService.findCaregiverByUserId(userId)
        );

        assertEquals(
                "User id does not belong to a caregiver",
                exception.getMessage()
        );

        verify(userRepository, times(1)).findById(userId);
        verify(caregiverRepository, times(1)).findById(userId);
    }
}
