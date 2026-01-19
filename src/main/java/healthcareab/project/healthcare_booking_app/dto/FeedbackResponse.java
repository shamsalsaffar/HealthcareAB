package healthcareab.project.healthcare_booking_app.dto;

import java.time.LocalDate;

public class FeedbackResponse {

    private Long id;

    private Long patientId;

    private Long bookingId;

    private Long clinicID;

    private Integer rating;

    private String comment;

    private Boolean anonymous;

    private LocalDate createdAt;

    public FeedbackResponse(Long id, Long patientId, Long bookingId, Long clinicID, Integer rating, String comment, Boolean anonymous, LocalDate createdAt) {
        this.id = id;
        this.patientId = patientId;
        this.bookingId = bookingId;
        this.clinicID = clinicID;
        this.rating = rating;
        this.comment = comment;
        this.anonymous = anonymous;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getClinicID() {
        return clinicID;
    }

    public void setClinicID(Long clinicID) {
        this.clinicID = clinicID;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        this.anonymous = anonymous;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
