package healthcareab.project.healthcare_booking_app.services;

import healthcareab.project.healthcare_booking_app.dto.BookingResponse;
import healthcareab.project.healthcare_booking_app.exception.ResourceNotFoundException;
import healthcareab.project.healthcare_booking_app.models.Booking;
import healthcareab.project.healthcare_booking_app.repository.BookingRepository;
import healthcareab.project.healthcare_booking_app.repository.UserRepository;
import org.springframework.http.ResponseEntity;
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

        return bookingRepository.save(booking);
    }

    public BookingResponse findBookingById(long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        return new BookingResponse(booking.getId(), booking.getPatient().getFirstName(), booking.getCaregiver().getFirstName(), booking.getBookingStartTime(), booking.getBookingEndTime(), booking.getCreatedAt());
    }

    public ResponseEntity deleteBookingById(long id) {
        //validate that the booking exists before trying to delete it
        bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        bookingRepository.deleteById(id);

        return ResponseEntity.ok("Booking deleted");
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
