package healthcareab.project.healthcare_booking_app.controllers;

import healthcareab.project.healthcare_booking_app.models.Booking;
import healthcareab.project.healthcare_booking_app.services.BookingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/booking")
    public Booking createBooking(@RequestBody Booking booking){

//        List<String> errors;
//
//        errors = bookingService.validateBooking(booking);
//
//        if (!errors.isEmpty()){
//            StringBuilder realError = new StringBuilder();
//            for (String error : errors){
//                realError.append(error);
//            }
//            throw new IllegalArgumentException(realError.toString());
//        }

        return bookingService.createBooking(booking);
    }
}
