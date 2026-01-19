package healthcareab.project.healthcare_booking_app.services;

import healthcareab.project.healthcare_booking_app.dto.AvailabilityRequest;
import healthcareab.project.healthcare_booking_app.dto.AvailabilityResponse;
import healthcareab.project.healthcare_booking_app.dto.AvailabilityUpdateRequest;
import healthcareab.project.healthcare_booking_app.exceptions.ResourceNotFoundException;
import healthcareab.project.healthcare_booking_app.models.Availability;
import healthcareab.project.healthcare_booking_app.models.Caregiver;
import healthcareab.project.healthcare_booking_app.models.User;
import healthcareab.project.healthcare_booking_app.repository.AvailabilityRepository;
import healthcareab.project.healthcare_booking_app.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AvailabilityServiceTest {

    @Mock
    AvailabilityRepository availabilityRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    Caregiver caregiver;

    @InjectMocks
    AvailabilityService availabilityService;

    // TODO Happy cases
    @Test
    void whenCreateAvailability_shouldReturnSuccess() {
        // Arrange
        AvailabilityRequest request = validRequest();
        Availability save = new Availability();
        save.setCaregiver(caregiver);

        mockCaregiverFound();

        when(availabilityRepository.save(any(Availability.class))).thenReturn(save);

        // Act
        AvailabilityResponse response = availabilityService.createAvailability(request);

        // Assert
        assertThat(response.getCaregiverId()).isEqualTo(1L);
    }

    @Test
    void whenGetAvailabilityById_shouldReturnSuccess() {
        // Arrange
        Availability existing = availabilityEntity();
        when(caregiver.getId()).thenReturn(1L);
        when(availabilityRepository.findById(10L)).thenReturn(Optional.of(existing));

        // Act
        AvailabilityResponse response = availabilityService.getAvailabilityById(10L);

        // Assert
        assertThat(response.getCaregiverId()).isEqualTo(1L);
    }

    @Test
    void whenGetAllAvailability_shouldReturnSuccess() {
        // Arrange
        Availability a1 = availabilityEntity();
        Availability a2 = availabilityEntity();

        when(availabilityRepository.findAll()).thenReturn(List.of(a1, a2));
        when(caregiver.getId()).thenReturn(1L);

        // Act
        List<AvailabilityResponse> result = availabilityService.getAllAvailabilities();

        // Assert
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getCaregiverId()).isEqualTo(1L);
    }

    @Test
    void whenUpdateAvailability_shouldReturnSuccess() {
        // Arrange
        AvailabilityUpdateRequest updateRequest = new AvailabilityUpdateRequest();
        updateRequest.setReoccurring(true);

        Availability existing = availabilityEntity();

        when(availabilityRepository.findById(10L)).thenReturn(Optional.of(existing));
        when(availabilityRepository.save(any(Availability.class))).thenReturn(existing);

        // Act
        AvailabilityResponse response = availabilityService.updateAvailability(updateRequest, 10L);

        // Assert
        assertThat(response.isReoccurring()).isTrue();
    }

    @Test
    void whenDeleteAvailability_shouldReturnSuccess() {
        // Arrange
        when(availabilityRepository.existsById(10L)).thenReturn(true);

        // Act
        availabilityService.deleteAvailability(10L);

        // Assert
        verify(availabilityRepository).deleteById(10L);
    }

    // TODO Unhappy cases
    @Test
    void whenCreateAvailability_shouldReturnCaregiverNotFound() {
        // Arrange
        AvailabilityRequest request = validRequest();
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> availabilityService.createAvailability(request));
    }

    @Test
    void whenCreateAvailability_shouldThrowIfUserIsNotCaregiver() {
        // Arrange
        AvailabilityRequest request = validRequest();

        // Mock a user that's not a Caregiver
        User normalUser = mock(User.class);

        when(userRepository.findById(1L)).thenReturn(Optional.of(normalUser));

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> availabilityService.createAvailability(request));
    }

    @Test
    void whenGetAvailabilityById_shouldThrowAvailabilityNotFound() {
        // Arrange
        when(availabilityRepository.findById(10L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> availabilityService.getAvailabilityById(10L));
    }

    @Test
    void whenUpdateAvailability_shouldThrowNotFound() {
        // Arrange
        AvailabilityUpdateRequest updateRequest = new AvailabilityUpdateRequest();
        when(availabilityRepository.findById(10L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> availabilityService.updateAvailability(updateRequest, 10L));
    }

    @Test
    void whenDeleteAvailability_shouldThrowNotFound() {
        // Arrange
        when(availabilityRepository.existsById(10L)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> availabilityService.deleteAvailability(10L));
    }

    // help method so the same code don't need to be repeated
    private AvailabilityRequest validRequest() {
        AvailabilityRequest availability = new AvailabilityRequest();
        availability.setCaregiverId(1L);
        availability.setReoccurring(false);
        availability.setStartTime(LocalDate.now());
        availability.setEndTime(LocalDate.now());
        return availability;
    }

    private Availability availabilityEntity() {
        Availability entity = new Availability();
        entity.setCaregiver(caregiver);
        entity.setStartTime(LocalDate.now());
        entity.setEndTime(LocalDate.now());
        entity.setReoccurring(false);
        return entity;
    }

    private void mockCaregiverFound() {
        when(caregiver.getId()).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(caregiver));
    }

}
