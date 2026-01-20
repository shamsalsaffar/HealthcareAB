package healthcareab.project.healthcare_booking_app.dto;

import jakarta.validation.constraints.NotNull;

public class FeedbackRequest {

    @NotNull
    private Long patientId;

    @NotNull
    private Long bookingId;

    private Integer rating;

    private String comment;

    private Boolean anonymous;

    public @NotNull Long getPatientId() {
        return patientId;
    }

    public void setPatientId(@NotNull Long patientId) {
        this.patientId = patientId;
    }

    public @NotNull Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(@NotNull Long bookingId) {
        this.bookingId = bookingId;
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
}
