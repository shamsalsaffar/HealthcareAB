package healthcareab.project.healthcare_booking_app.services;

import healthcareab.project.healthcare_booking_app.dto.AvailabilityRequest;
import healthcareab.project.healthcare_booking_app.dto.AvailabilityResponse;
import healthcareab.project.healthcare_booking_app.models.Availability;
import healthcareab.project.healthcare_booking_app.models.Caregiver;
import healthcareab.project.healthcare_booking_app.repository.AvailabilityRepository;
import healthcareab.project.healthcare_booking_app.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
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

    @Test
    void whenCreateAvailability_shouldReturnSuccess() {
        //Arrange
        AvailabilityRequest availability = validRequest();
        Availability save = new Availability();
        save.setCaregiver(caregiver);

        when(caregiver.getId()).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(caregiver));
        when(availabilityRepository.save(any(Availability.class))).thenReturn(save);

        //Act
        AvailabilityResponse response = availabilityService.createAvailability(availability);

        //Assert
        assertThat(response.getCaregiverId()).isEqualTo(1L);
    }

    @Test
    void whenUpdateAvailability_shouldReturnSuccess() {
        //Arrange

        //Act

        //Assert
    }

    @Test
    void whenDeleteAvailability_shouldReturnSuccess() {
        //Arrange

        //Act

        //Assert
    }

    @Test
    void whenGetAvailabilityById_shouldReturnSuccess() {
        //Arrange

        //Act

        //Assert
    }

    @Test
    void whenGetAllAvailability_shouldReturnSuccess() {
        //Arrange

        //Act

        //Assert
    }

    @Test
    void whenCreateAvailability_shouldReturnCaregiverNotFound() {
        //Arrange

        //Act

        //Assert
    }


    void whenGetAvailabilityById_shouldThrowAvailabilityNotFound() {
        //Arrange

        //Act

        //Assert
    }


    void whenUpdateAvailabilityWithEmptyDto_shouldNotChangeFields() {
        //Arrange

        //Act

        //Assert
    }


    void whenDeleteAvailability_shouldThrowNotFound() {
        //Arrange

        //Act

        //Assert
    }

    private AvailabilityRequest validRequest() {
        AvailabilityRequest availability = new AvailabilityRequest();
        availability.setCaregiverId(1L);
        availability.setReoccurring(false);
        availability.setStartTime(LocalDate.now());
        availability.setEndTime(LocalDate.now());
        return availability;
    }

}
