package healthcareab.project.healthcare_booking_app.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "clinics")
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100, message = "Name of clinic should not exceed 100 symbols")
    @NotNull(message = "Name of clinic is obligatory")
    @NotEmpty(message = "Name of clinic can not be empty")
    @Column(unique = true)
    private String name;

    @Size(max = 100, message = "Location of clinic should not exceed 100 symbols")
    @NotNull(message = "Location of clinic is obligatory")
    @NotEmpty(message = "Location of clinic can not be empty")
    @Column(unique = true)
    private String location;

    @NotNull(message = "Phone number of clinic cannot be empty")
    @Pattern(
            regexp = "^(\\+46|0)(7[02369])[\\s\\-]?\\d{3}[\\s\\-]?\\d{2}[\\s\\-]?\\d{2}$",
            message = "Invalid Swedish mobile phone number for the clinic phone number"
    )
    @Column(unique = true)
    private String phoneNumber;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Clinic() {
    }

    public Clinic(Long id, String name, String location, String phoneNumber, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public @Size(max = 100, message = "Name of clinic should not exceed 100 symbols") @NotNull(message = "Name of clinic is obligatory") @NotEmpty(message = "Name of clinic can not be empty") String getName() {
        return name;
    }

    public void setName(@Size(max = 100, message = "Name of clinic should not exceed 100 symbols") @NotNull(message = "Name of clinic is obligatory") @NotEmpty(message = "Name of clinic can not be empty") String name) {
        this.name = name;
    }

    public @Size(max = 100, message = "Location of clinic should not exceed 100 symbols") @NotNull(message = "Location of clinic is obligatory") @NotEmpty(message = "Location of clinic can not be empty") String getLocation() {
        return location;
    }

    public void setLocation(@Size(max = 100, message = "Location of clinic should not exceed 100 symbols") @NotNull(message = "Location of clinic is obligatory") @NotEmpty(message = "Location of clinic can not be empty") String location) {
        this.location = location;
    }

    public @NotNull(message = "Phone number cannot be empty") @Pattern(
            regexp = "^(\\+46|0)(7[02369])[\\s\\-]?\\d{3}[\\s\\-]?\\d{2}[\\s\\-]?\\d{2}$",
            message = "Invalid Swedish mobile phone number"
    ) String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@NotNull(message = "Phone number cannot be empty") @Pattern(
            regexp = "^(\\+46|0)(7[02369])[\\s\\-]?\\d{3}[\\s\\-]?\\d{2}[\\s\\-]?\\d{2}$",
            message = "Invalid Swedish mobile phone number"
    ) String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
