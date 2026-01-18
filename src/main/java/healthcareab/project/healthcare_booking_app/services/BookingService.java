package healthcareab.project.healthcare_booking_app.services;

import healthcareab.project.healthcare_booking_app.models.Booking;
import healthcareab.project.healthcare_booking_app.models.Caregiver;
import healthcareab.project.healthcare_booking_app.models.Patient;
import healthcareab.project.healthcare_booking_app.repository.BookingRepository;
import healthcareab.project.healthcare_booking_app.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    public Booking createBooking(Booking booking) {

        // Validate that the patient exists
        userRepository.findById(booking.getPatient().getId())
                .filter(Patient.class::isInstance)
                .map(Patient.class::cast)
                .orElseThrow(() -> new ResourceNotFoundException ("Patient not found"));

        //Validate that the caregiver exists
        userRepository.findById(booking.getCaregiver().getId())
                .filter(Caregiver.class::isInstance)
                .map(Caregiver.class::cast)
                .orElseThrow(() -> new ResourceNotFoundException("Caregiver not found"));

        return bookingRepository.save(booking);
    }

    public List<String> validateBooking(Booking booking) {

        List<String> errors = new ArrayList<>();

        if (booking.getPatient() == null || booking.getPatient().getId() == null) {
            errors.add("Field patient is missing");
        }
        if (booking.getCaregiver() == null || booking.getCaregiver().getId() == null) {
            errors.add("Field caregiver is missing");
        }
        if (booking.getBookingStartTime() == null) {
            errors.add("Field bookingStartTime is missing");
        }
        if (booking.getBookingEndTime() == null) {
            errors.add("Field bookingEndTime is missing");
        }
        if (booking.getStatus() == null) {
            errors.add("Field status is missing");
        }

        return errors;
    }
}
