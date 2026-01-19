package healthcareab.project.healthcare_booking_app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PatientRegisterRequest {

    @NotBlank(message = "Email cannot be empty")
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", message = "That's not a valid email.")
    @Size(max = 50, message = "Your email cannot be longer than 50 characters.")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()\\-_=+{};:,<.>])(?=.{8,}).*$", message = "Password must be at least 8 characters long and contain at least one uppercase letter, one number, and one special character")
    private String password;

    @NotBlank(message = "First name cannot be empty")
    @Pattern(regexp = "^[A-Za-z]+(?:[ '-][A-Za-z]+)*$", message = "First name can only include alphabetic characters, spaces, hyphens, and apostrophes.")
    @Size(max = 50, message = "First name cannot be longer than 50 characters.")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty")
    @Pattern(regexp = "^[A-Za-z]+(?:[ '-][A-Za-z]+)*$", message = "Last name can only include alphabetic characters, spaces, hyphens, and apostrophes.")
    @Size(max = 50, message = "Last name cannot be longer than 50 characters.")
    private String lastName;

    @NotBlank(message = "Adress cannot be empty")
    @Size(max = 100, message = "Your adress cannot exceed 100 characters.")
    private String address;

    @NotBlank(message = "Phone number cannot be empty")
    @Pattern(regexp = "^(\\+46|0)(7[02369])[\\s\\-]?\\d{3}[\\s\\-]?\\d{2}[\\s\\-]?\\d{2}$", message = "Invalid Swedish mobile phone number")
    private String phoneNumber;

    @NotBlank(message = "Personal identity number cannot be empty")
    @Pattern(regexp = "^(19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{4}$", message = "Invalid personal identity number (YYYYMMDDXXXX)")
    private String personalIdentityNumber;

    // Required by Spring/Jackson
    public PatientRegisterRequest() {
    }

    // Optional (useful in tests)
    public PatientRegisterRequest(String username, String password, String firstName, String lastName, String address,
            String phoneNumber, String personalIdentityNumber) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.personalIdentityNumber = personalIdentityNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
