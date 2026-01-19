package healthcareab.project.healthcare_booking_app.dto;

import java.time.LocalDateTime;

public class BookingResponse {

    private long id;
    private String patientFirstName;
    private String caregiverFirstName;
    private LocalDateTime bookingStartTime;
    private LocalDateTime bookingEndTime;
    private LocalDateTime createdAt;

    public BookingResponse(long id, String patientFirstName, String caregiverFirstName, LocalDateTime bookingStartTime,
            LocalDateTime bookingEndTime, LocalDateTime createdAt) {
        this.id = id;
        this.patientFirstName = patientFirstName;
        this.caregiverFirstName = caregiverFirstName;
        this.bookingStartTime = bookingStartTime;
        this.bookingEndTime = bookingEndTime;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public String getCaregiverFirstName() {
        return caregiverFirstName;
    }

    public void setCaregiverFirstName(String caregiverFirstName) {
        this.caregiverFirstName = caregiverFirstName;
    }

    public LocalDateTime getBookingStartTime() {
        return bookingStartTime;
    }

    public void setBookingStartTime(LocalDateTime bookingStartTime) {
        this.bookingStartTime = bookingStartTime;
    }

    public LocalDateTime getBookingEndTime() {
        return bookingEndTime;
    }

    public void setBookingEndTime(LocalDateTime bookingEndTime) {
        this.bookingEndTime = bookingEndTime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
