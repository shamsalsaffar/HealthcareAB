package healthcareab.project.healthcare_booking_app.dto;

import jakarta.validation.constraints.NotNull;

public class FeedbackRequest {

    @NotNull
    private Long patient;

    @NotNull
    private Long booking;

    @NotNull
    private Long clinic;

    private Integer rating;

    private String comment;

    private Boolean anonymous;

    public @NotNull Long getPatient() {
        return patient;
    }

    public void setPatient(@NotNull Long patient) {
        this.patient = patient;
    }

    public @NotNull Long getBooking() {
        return booking;
    }

    public void setBooking(@NotNull Long booking) {
        this.booking = booking;
    }

    public @NotNull Long getClinic() {
        return clinic;
    }

    public void setClinic(@NotNull Long clinic) {
        this.clinic = clinic;
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
