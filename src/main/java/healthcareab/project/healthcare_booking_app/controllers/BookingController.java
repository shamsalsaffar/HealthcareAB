package healthcareab.project.healthcare_booking_app.controllers;

import healthcareab.project.healthcare_booking_app.models.Booking;
import healthcareab.project.healthcare_booking_app.services.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/booking")
    public ResponseEntity createBooking(@RequestBody Booking booking){

        List<String> errors = bookingService.validateBooking(booking);

        if (!errors.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("errors", errors));
        }

        bookingService.createBooking(booking);

        return ResponseEntity.ok("Booking successful");
    }
}
