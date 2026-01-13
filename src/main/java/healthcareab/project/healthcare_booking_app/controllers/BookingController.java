package healthcareab.project.healthcare_booking_app.controllers;

import healthcareab.project.healthcare_booking_app.models.Booking;
import healthcareab.project.healthcare_booking_app.services.BookingService;
import org.springframework.stereotype.Controller;

@Controller
public class BookingController {

    private BookingService bookingService;

    public Booking createBooking(Booking booking){

        return bookingService.createBooking(booking);
    }
}
