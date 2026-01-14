package healthcareab.project.healthcare_booking_app.dto;

import healthcareab.project.healthcare_booking_app.models.Booking;
import healthcareab.project.healthcare_booking_app.models.enums.BookingStatus;

import java.time.LocalDateTime;

public class BookingResponseDTO {

    private long id;
    private long patientId;
    private String patientFirstName;
    private String patientLastName;
    private long caregiverId;
    private String caregiverFirstName;
    private String caregiverLastName;
    private LocalDateTime bookingStartTime;
    private LocalDateTime bookingEndTime;
    private BookingStatus bookingStatus;

    public BookingResponseDTO() {
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public LocalDateTime getBookingEndTime() {
        return bookingEndTime;
    }

    public void setBookingEndTime(LocalDateTime bookingEndTime) {
        this.bookingEndTime = bookingEndTime;
    }

    public LocalDateTime getBookingStartTime() {
        return bookingStartTime;
    }

    public void setBookingStartTime(LocalDateTime bookingStartTime) {
        this.bookingStartTime = bookingStartTime;
    }

    public String getCaregiverLastName() {
        return caregiverLastName;
    }

    public void setCaregiverLastName(String caregiverLastName) {
        this.caregiverLastName = caregiverLastName;
    }

    public String getCaregiverFirstName() {
        return caregiverFirstName;
    }

    public void setCaregiverFirstName(String caregiverFirstName) {
        this.caregiverFirstName = caregiverFirstName;
    }

    public long getCaregiverId() {
        return caregiverId;
    }

    public void setCaregiverId(long caregiverId) {
        this.caregiverId = caregiverId;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BookingResponseDTO convertToBookingResponseDTO(Booking booking) {
        BookingResponseDTO  bookingResponseDTO = new BookingResponseDTO();
        bookingResponseDTO.setId(booking.getId());
        bookingResponseDTO.setPatientId(booking.getPatient().getId());
        bookingResponseDTO.setPatientFirstName(booking.getPatient().getFirstName());
        bookingResponseDTO.setPatientLastName(booking.getPatient().getLastName());
        bookingResponseDTO.setCaregiverId(booking.getCaregiver().getId());
        bookingResponseDTO.setCaregiverFirstName(booking.getCaregiver().getFirstName());
        bookingResponseDTO.setCaregiverLastName(booking.getCaregiver().getLastName());
        bookingResponseDTO.setBookingStartTime(booking.getBookingStartTime());
        bookingResponseDTO.setBookingEndTime(booking.getBookingEndTime());
        bookingResponseDTO.setBookingStatus(booking.getStatus());

        return bookingResponseDTO;

    }
}
