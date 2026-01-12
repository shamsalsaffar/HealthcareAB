package healthcareab.project.healthcare_booking_app.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "availabilities")
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Caregiver id is required")
    @Column(name = "caregiver_id", nullable = false)
    private Long caregiverId;

    @NotNull(message = "Start time is required")
    @Column(name = "start_time", nullable = false)
    private LocalDate startTime;

    @NotNull(message = "End time is required")
    @Column(name = "end_time", nullable = false)
    private LocalDate endTime;

    @Column(name = "reoccurring", nullable = false)
    private boolean reoccurring;

    @Column(name = "created_at")
    private LocalDate createdAt;

    public Availability() {
    }

    public Availability(
            Long caregiverId,
            LocalDate startTime,
            LocalDate endTime,
            boolean reoccurring
    ) {
        this.caregiverId = caregiverId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reoccurring = reoccurring;
        this.createdAt = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public Long getCaregiverId() {
        return caregiverId;
    }

    public void setCaregiverId(Long caregiverId) {
        this.caregiverId = caregiverId;
    }

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

    public boolean isReoccurring() {
        return reoccurring;
    }

    public void setReoccurring(boolean reoccurring) {
        this.reoccurring = reoccurring;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
