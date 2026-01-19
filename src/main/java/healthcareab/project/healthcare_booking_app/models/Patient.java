package healthcareab.project.healthcare_booking_app.models;

import healthcareab.project.healthcare_booking_app.models.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "patients")
public class Patient extends User {

    @NotNull(message = "Address can not be empty")
    @Size(max = 100, message = "Your address can not exceed 100 characters")
    @Column(name = "address", nullable = false)
    private String address;

    @NotNull(message = "Phone number cannot be empty")
    @Pattern(regexp = "^(\\+46|0)(7[02369])[\\s\\-]?\\d{3}[\\s\\-]?\\d{2}[\\s\\-]?\\d{2}$", message = "Invalid Swedish mobile phone number")
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @NotNull(message = "Personal identity number cannot be empty")
    @Pattern(regexp = "^(19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{4}$", message = "Invalid personal identity number (YYYYMMDDXXXX)")
    @Column(name = "personal_identity_number", unique = true, nullable = false)
    private String personalIdentityNumber;

    public Patient() {
    }

    public Patient(String address, String phoneNumber, String personalIdentityNumber) {
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.personalIdentityNumber = personalIdentityNumber;
    }

    public Patient(String username, String password, Role role, String address, String phoneNumber,
            String personalIdentityNumber) {
        super(username, password, role);
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.personalIdentityNumber = personalIdentityNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPersonalIdentityNumber() {
        return personalIdentityNumber;
    }

    public void setPersonalIdentityNumber(String personalIdentityNumber) {
        this.personalIdentityNumber = personalIdentityNumber;
    }
}
