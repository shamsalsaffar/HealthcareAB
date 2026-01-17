package healthcareab.project.healthcare_booking_app.dto;

import java.time.LocalDate;

public class AvailabilityResponse {

    private Long id;
    private LocalDate startTime;
    private LocalDate endTime;
    private boolean reoccurring;
    private Long caregiverId;
    private LocalDate createdAt;

    public AvailabilityResponse(
            Long id,
            LocalDate startTime,
            LocalDate endTime,
            boolean reoccurring,
            Long caregiverId,
            LocalDate createdAt
    ) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reoccurring = reoccurring;
        this.caregiverId = caregiverId;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public boolean isReoccurring() {
        return reoccurring;
    }

    public Long getCaregiverId() {
        return caregiverId;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

}