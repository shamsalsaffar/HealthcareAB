package healthcareab.project.healthcare_booking_app.services;

import healthcareab.project.healthcare_booking_app.models.Booking;
import healthcareab.project.healthcare_booking_app.models.Caregiver;
import healthcareab.project.healthcare_booking_app.models.Patient;
import healthcareab.project.healthcare_booking_app.models.enums.BookingStatus;
import healthcareab.project.healthcare_booking_app.repository.BookingRepository;
import healthcareab.project.healthcare_booking_app.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;


@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    BookingRepository bookingRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    Patient patient;

    @Mock
    Caregiver caregiver;

    @Mock
    BookingStatus status;

    @InjectMocks
    BookingService bookingService;

    @Test
    void createBooking_shouldSaveBooking() {
        Booking booking = new Booking();
        Mockito.when(bookingRepository.save(booking)).thenReturn(booking);

        Booking saved = bookingService.createBooking(booking);

        Mockito.verify(bookingRepository).save(booking);
        assertThat(saved).isEqualTo(booking);
    }

    @Test
    void validateBooking_shouldReturnEmptyErrors_whenBookingIsValid() {
        Booking booking = validBooking();

        var errors = bookingService.validateBooking(booking);

        assertThat(errors.isEmpty());
    }

    @Test
    void validateBooking_shouldFailWhenInputFieldsIsMissing() {
        //Running test with all fields that can be manually added missing
        Booking booking = validBooking();

        booking.setPatient(null);
        booking.setCaregiver(null);
        booking.setBookingStartTime(null);
        booking.setBookingEndTime(null);
        booking.setStatus(null);

        List<String> errors = bookingService.validateBooking(booking);

        List<String> expected = Arrays.asList(
                "Field patient is missing",
                "Field caregiver is missing",
                "Field bookingStartTime is missing",
                "Field bookingEndTime is missing",
                "Field status is missing"
        );

        Collections.sort(errors);
        Collections.sort(expected);

        assertIterableEquals(expected, errors);
    }


    private Booking validBooking() {
        Booking booking = new Booking();
        booking.setPatient(patient);
        booking.setCaregiver(caregiver);
        booking.setBookingStartTime(LocalDateTime.now());
        booking.setBookingEndTime(LocalDateTime.now().plusMinutes(30));
        booking.setStatus(BookingStatus.ACTIVE);
        return booking;
    }

}