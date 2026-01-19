package healthcareab.project.healthcare_booking_app.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvailabilityRequest {

    @NotNull
    private LocalDate startTime;

    @NotNull
    private LocalDate endTime;

    private Boolean reoccurring;

    @NotNull
    private Long caregiverId;

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public Boolean getReoccurring() {
        return reoccurring;
    }

    public void setReoccurring(Boolean reoccurring) {
        this.reoccurring = reoccurring;
    }

    public Long getCaregiverId() {
        return caregiverId;
    }

    public void setCaregiverId(Long caregiverId) {
        this.caregiverId = caregiverId;
    }

}
