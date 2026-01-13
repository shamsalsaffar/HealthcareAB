package healthcareab.project.healthcare_booking_app.dto;

import healthcareab.project.healthcare_booking_app.models.enums.Role;

public class AuthResponse {
    private String message;
    private String jwtToken;
    private String username;
    private Role role;
    private String email;
    private String firstName;
    private String lastName;

    public AuthResponse(String jwtToken, String username, Role role, String email, String firstName, String lastName,
            String message) {
        this.message = message;
        this.jwtToken = jwtToken;
        this.username = username;
        this.role = role;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

}
