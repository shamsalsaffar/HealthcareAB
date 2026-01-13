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

    @NotNull(message = "Adress can not be empty")
    @Size(max = 100, message = "Your adress can not exceed 100 symbols")
    private String adress;

    @NotNull(message = "Phone number cannot be empty")
    @Pattern(regexp = "^(\\+46|0)(7[02369])[\\s\\-]?\\d{3}[\\s\\-]?\\d{2}[\\s\\-]?\\d{2}$", message = "Invalid Swedish mobile phone number")
    private String phoneNumber;

    @Pattern(regexp = "^(19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{4}$", message = "Invalid personal identity number (YYYYMMDDXXXX)")
    @Column(unique = true)
    @NotNull(message = "Personal identity number cannot be empty")
    private String personalIdentityNumber;

    public Patient(String adress, String phoneNumber, String personalIdentityNumber) {
        this.adress = adress;
        this.phoneNumber = phoneNumber;
        this.personalIdentityNumber = personalIdentityNumber;
    }

    public Patient() {
    }

    public Patient(String username, String password, Role role, String adress, String phoneNumber,
            String personalIdentityNumber) {
        super(username, password, role);
        this.adress = adress;
        this.phoneNumber = phoneNumber;
        this.personalIdentityNumber = personalIdentityNumber;
    }

    public @NotNull(message = "Adress can not be empty") @Size(max = 100, message = "Your adress can not exceed 100 symbols") String getAdress() {
        return adress;
    }

    public void setAdress(
            @NotNull(message = "Adress can not be empty") @Size(max = 100, message = "Your adress can not exceed 100 symbols") String adress) {
        this.adress = adress;
    }

    public @NotNull(message = "Phone number cannot be empty") @Pattern(regexp = "^(\\+46|0)(7[02369])[\\s\\-]?\\d{3}[\\s\\-]?\\d{2}[\\s\\-]?\\d{2}$", message = "Invalid Swedish mobile phone number") String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(
            @NotNull(message = "Phone number cannot be empty") @Pattern(regexp = "^(\\+46|0)(7[02369])[\\s\\-]?\\d{3}[\\s\\-]?\\d{2}[\\s\\-]?\\d{2}$", message = "Invalid Swedish mobile phone number") String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public @Pattern(regexp = "^(19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{4}$", message = "Invalid personal identity number (YYYYMMDDXXXX)") @NotNull(message = "Personal identity number cannot be empty") String getPersonalIdentityNumber() {
        return personalIdentityNumber;
    }

    public void setPersonalIdentityNumber(
            @Pattern(regexp = "^(19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{4}$", message = "Invalid personal identity number (YYYYMMDDXXXX)") @NotNull(message = "Personal identity number cannot be empty") String personalIdentityNumber) {
        this.personalIdentityNumber = personalIdentityNumber;
    }
}
