package healthcareab.project.healthcare_booking_app.services;

import healthcareab.project.healthcare_booking_app.dto.FeedbackRequest;
import healthcareab.project.healthcare_booking_app.dto.FeedbackResponse;
import healthcareab.project.healthcare_booking_app.models.Feedback;
import healthcareab.project.healthcare_booking_app.repository.BookingRepository;
import healthcareab.project.healthcare_booking_app.repository.FeedbackRepository;
import healthcareab.project.healthcare_booking_app.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public FeedbackService(FeedbackRepository feedbackRepository, UserRepository userRepository, BookingRepository bookingRepository) {
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

    public FeedbackResponse createFeedback(FeedbackRequest dtoRequest) {

    }

    private FeedbackResponse mapToFeedbackResponse(Feedback feedback) {
        return new FeedbackResponse(feedback.getId(), feedback.getPatient().getId(), feedback.getBooking().getId(), feedback.getClinic().getId(),
                feedback.getRating(), feedback.getComment(), feedback.getAnonymous(), feedback.getCreatedAt());
    }
}
