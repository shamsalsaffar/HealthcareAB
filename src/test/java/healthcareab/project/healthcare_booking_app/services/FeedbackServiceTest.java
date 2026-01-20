package healthcareab.project.healthcare_booking_app.services;

import healthcareab.project.healthcare_booking_app.dto.FeedbackRequest;
import healthcareab.project.healthcare_booking_app.dto.FeedbackResponse;
import healthcareab.project.healthcare_booking_app.models.Availability;
import healthcareab.project.healthcare_booking_app.models.Booking;
import healthcareab.project.healthcare_booking_app.models.Caregiver;
import healthcareab.project.healthcare_booking_app.models.Clinic;
import healthcareab.project.healthcare_booking_app.models.Feedback;
import healthcareab.project.healthcare_booking_app.models.Patient;
import healthcareab.project.healthcare_booking_app.repository.BookingRepository;
import healthcareab.project.healthcare_booking_app.repository.FeedbackRepository;
import healthcareab.project.healthcare_booking_app.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FeedbackServiceTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private FeedbackService feedbackService;

    //TODO Happy cases
    @Test
    void whenCreateFeedback_shouldReturnSuccess() {
        // Arrange
        FeedbackRequest request = new FeedbackRequest();
        request.setPatientId(6L);
        request.setBookingId(1L);
        request.setRating(5);
        request.setComment("Wonderful service");
        request.setAnonymous(false);

        Patient patient = new Patient();
        patient.setId(6L);

        Clinic clinic = new Clinic();
        clinic.setId(3L);

        Caregiver caregiver = new Caregiver();
        caregiver.setClinic(clinic);

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setCaregiver(caregiver);

//        Caregiver caregiver = booking.getCaregiver();
//        Clinic clinic = caregiver.getClinic();

        when(patientRepository.findById(6L)).thenReturn(Optional.of(patient));
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

//        when(feedbackRepository.save(any()))
//                .thenAnswer(invocation -> invocation.getArgument(0));

//        when(feedbackRepository.save(any(Feedback.class))).thenReturn((Feedback) feedbackRepository);

        // Act
        FeedbackResponse response = feedbackService.createFeedback(request);

        // Assert
        assertThat(response.getPatientId()).isEqualTo(6L);
        assertThat(response.getBookingId()).isEqualTo(1L);
        assertThat(response.getClinicId()).isEqualTo(3L);
        assertThat(response.getRating()).isEqualTo(5);
        assertThat(response.getComment()).isEqualTo("Wonderful service");
        assertThat(response.getAnonymous()).isFalse();
    }

    //TODO Unhappy cases
    @Test
    void whenPatientNotFound_shouldThrowException () {
        // Arrange

        // Act

        // Assert
    }

    @Test
    void whenBookingNotFound_shouldThrowException () {
        // Arrange

        // Act

        // Assert
    }
}
