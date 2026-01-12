package healthcareab.project.healthcare_booking_app.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne
    @JoinColumn(name = "patient_id")
    private User patient;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User caregiver;

    @Column(nullable = false)
    private LocalDateTime booking_start_time;

    @Column(nullable = false)
    private LocalDateTime booking_end_time;

    @Column(nullable = false)
    private String status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Booking() {
    }

    public Booking(User patient, User caregiver, LocalDateTime booking_start_time, LocalDateTime booking_end_time, String status) {
        this.patient = patient;
        this.caregiver = caregiver;
        this.booking_start_time = booking_start_time;
        this.booking_end_time = booking_end_time;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public User getPatient() {
        return patient;
    }

    public User getCaregiver() {
        return caregiver;
    }

    public LocalDateTime getBooking_start_time() {
        return booking_start_time;
    }

    public LocalDateTime getBooking_end_time() {
        return booking_end_time;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
