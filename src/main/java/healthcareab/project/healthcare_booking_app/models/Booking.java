package healthcareab.project.healthcare_booking_app.models;

import healthcareab.project.healthcare_booking_app.models.enums.BookingStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "caregiver_id", nullable = false)
    private Caregiver caregiver;

    @Column(nullable = false)
    private LocalDateTime booking_start_time;

    @Column(nullable = false)
    private LocalDateTime booking_end_time;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "status")
    private BookingStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Booking() {
    }

    public Booking(Patient patient, Caregiver caregiver, LocalDateTime booking_start_time,
            LocalDateTime booking_end_time, BookingStatus status) {
        this.patient = patient;
        this.caregiver = caregiver;
        this.booking_start_time = booking_start_time;
        this.booking_end_time = booking_end_time;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public Caregiver getCaregiver() {
        return caregiver;
    }

    public LocalDateTime getBooking_start_time() {
        return booking_start_time;
    }

    public LocalDateTime getBooking_end_time() {
        return booking_end_time;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
