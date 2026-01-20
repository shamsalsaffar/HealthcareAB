package healthcareab.project.healthcare_booking_app.dto;

import java.time.LocalDate;

public class FeedbackResponse {

    private Long id;

    private Long patientId;

    private Long bookingId;

    private Long clinicId;

    private Integer rating;

    private String comment;

    private Boolean anonymous;

    private LocalDate createdAt;

    public FeedbackResponse(Long id, Long patientId, Long bookingId, Long clinicId, Integer rating, String comment,
            Boolean anonymous, LocalDate createdAt) {
        this.id = id;
        this.patientId = patientId;
        this.bookingId = bookingId;
        this.clinicId = clinicId;
        this.rating = rating;
        this.comment = comment;
        this.anonymous = anonymous;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getClinicId() {
        return clinicId;
    }

    public void setClinicId(Long clinicId) {
        this.clinicId = clinicId;
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
