package healthcareab.project.healthcare_booking_app.dto;

import java.time.LocalDate;

public class AvailabilityUpdateRequest {
    private LocalDate startTime;
    private LocalDate endTime;
    private Boolean reoccurring;

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

}
