package healthcareab.project.healthcare_booking_app.services;

import healthcareab.project.healthcare_booking_app.dto.FeedbackRequest;
import healthcareab.project.healthcare_booking_app.dto.FeedbackResponse;
import healthcareab.project.healthcare_booking_app.exceptions.ResourceNotFoundException;
import healthcareab.project.healthcare_booking_app.models.Booking;
import healthcareab.project.healthcare_booking_app.models.Caregiver;
import healthcareab.project.healthcare_booking_app.models.Clinic;
import healthcareab.project.healthcare_booking_app.models.Feedback;
import healthcareab.project.healthcare_booking_app.models.Patient;
import healthcareab.project.healthcare_booking_app.repository.BookingRepository;
import healthcareab.project.healthcare_booking_app.repository.FeedbackRepository;
import healthcareab.project.healthcare_booking_app.repository.PatientRepository;
import healthcareab.project.healthcare_booking_app.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final PatientRepository patientRepository;

    public FeedbackService(FeedbackRepository feedbackRepository, UserRepository userRepository,
            BookingRepository bookingRepository, PatientRepository patientRepository) {
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.patientRepository = patientRepository;
    }

    public FeedbackResponse createFeedback(FeedbackRequest dtoRequest) {

        Patient patient = patientRepository.findById(dtoRequest.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        Booking booking = bookingRepository.findById(dtoRequest.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        Caregiver caregiver = booking.getCaregiver();
        Clinic clinic = caregiver.getClinic();

        Feedback feedback = new Feedback();
        feedback.setPatient(patient);
        feedback.setBooking(booking);
        feedback.setClinic(clinic);
        feedback.setRating(dtoRequest.getRating());
        feedback.setComment(dtoRequest.getComment());
        feedback.setAnonymous(dtoRequest.getAnonymous());

        feedbackRepository.save(feedback);

        return mapToFeedbackResponse(feedback);
    }

    private FeedbackResponse mapToFeedbackResponse(Feedback feedback) {
        return new FeedbackResponse(feedback.getId(), feedback.getPatient().getId(), feedback.getBooking().getId(),
                feedback.getClinic().getId(), feedback.getRating(), feedback.getComment(), feedback.getAnonymous(),
                feedback.getCreatedAt());
    }
}
