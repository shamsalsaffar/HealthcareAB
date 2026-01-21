package healthcareab.project.healthcare_booking_app.controllers;

import healthcareab.project.healthcare_booking_app.dto.BookingResponse;
import healthcareab.project.healthcare_booking_app.models.Booking;
import healthcareab.project.healthcare_booking_app.services.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        List<String> errors = bookingService.validateBooking(booking);
        if (!errors.isEmpty()) return ResponseEntity.badRequest().body(Map.of("errors", errors));

        Booking saved = bookingService.createBooking(booking);

        return ResponseEntity.ok(Map.of(
                "message", "Booking successful",
                "id", saved.getId()
        ));
    }

    @GetMapping("/booking")
    public BookingResponse getBookingById(@RequestParam Long id) {
        return bookingService.findBookingById(id);
    }

    @DeleteMapping("/booking")
    public ResponseEntity deleteBookingById(@RequestParam Long id) {

        return bookingService.deleteBookingById(id);
    }
}
