package healthcareab.project.healthcare_booking_app.services;

import healthcareab.project.healthcare_booking_app.models.Booking;
import healthcareab.project.healthcare_booking_app.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Booking createBooking(Booking booking) {


        return bookingRepository.save(booking);
    }

    public List<String> validateBooking(Booking booking) {

        List<String> errors = new ArrayList<>();

        if (booking.getPatient() == null) {
            errors.add("Field patient is missing");
        }
        if (booking.getCaregiver() == null) {
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
