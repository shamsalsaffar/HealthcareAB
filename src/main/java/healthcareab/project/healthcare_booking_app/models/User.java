package healthcareab.project.healthcare_booking_app.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, name = "email")
    @NotEmpty(message = "Email cannot be empty")
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", message = "That's not a valid email.")
    @Size(max = 50, message = "Your email cannot be longer than 50 characters.")
    private String username;

    @Column(nullable = false)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()\\-_=+{};:,<.>])(?=.{8,})"
            + ".*$", message = "Password must be at least 8 characters long and contain at least "
                    + "one uppercase letter, one number, and one special character")
    private String password;

    @Column(nullable = false)
    @Pattern(regexp = "^[A-Za-z]+(?:[ '-][A-Za-z]+)*$", message = "First name can only include alphabetic characters, spaces, hyphens, and apostrophes. Example: 'John Doe' or 'Mary-Anne O'Conner'.")
    @Size(max = 50, message = "Your email cannot be longer than 50 characters.")
    private String firstName;

    @Column(nullable = false)
    @Pattern(regexp = "^[A-Za-z]+(?:[ '-][A-Za-z]+)*$", message = "First name can only include alphabetic characters, spaces, hyphens, and apostrophes. Example: 'John Doe' or 'Mary-Anne O'Conner'.")
    @Size(max = 50, message = "Your email cannot be longer than 50 characters.")
    private String lastName;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<Role> roles;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public User() {
    }

    public User(String username, String password, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public @NotEmpty(message = "Email cannot be empty") @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", message = "That's not a valid email.") @Size(max = 50, message = "Your email cannot be longer than 50 characters.") String getUsername() {
        return username;
    }

    public void setUsername(
            @NotEmpty(message = "Email cannot be empty") @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", message = "That's not a valid email.") @Size(max = 50, message = "Your email cannot be longer than 50 characters.") String username) {
        this.username = username;
    }

    public @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()\\-_=+{};:,<.>])(?=.{8,})"
            + ".*$", message = "Password must be at least 8 characters long and contain at least "
                    + "one uppercase letter, one number, and one special character") String getPassword() {
        return password;
    }

    public void setPassword(@Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()\\-_=+{};:,<.>])(?=.{8,})"
            + ".*$", message = "Password must be at least 8 characters long and contain at least "
                    + "one uppercase letter, one number, and one special character") String password) {
        this.password = password;
    }

    public @Pattern(regexp = "^[A-Za-z]+(?:[ '-][A-Za-z]+)*$", message = "First name can only include alphabetic characters, spaces, hyphens, and apostrophes. Example: 'John Doe' or 'Mary-Anne O'Conner'.") @Size(max = 50, message = "Your email cannot be longer than 50 characters.") String getFirstName() {
        return firstName;
    }

    public void setFirstName(
            @Pattern(regexp = "^[A-Za-z]+(?:[ '-][A-Za-z]+)*$", message = "First name can only include alphabetic characters, spaces, hyphens, and apostrophes. Example: 'John Doe' or 'Mary-Anne O'Conner'.") @Size(max = 50, message = "Your email cannot be longer than 50 characters.") String firstName) {
        this.firstName = firstName;
    }

    public @Pattern(regexp = "^[A-Za-z]+(?:[ '-][A-Za-z]+)*$", message = "First name can only include alphabetic characters, spaces, hyphens, and apostrophes. Example: 'John Doe' or 'Mary-Anne O'Conner'.") @Size(max = 50, message = "Your email cannot be longer than 50 characters.") String getLastName() {
        return lastName;
    }

    public void setLastName(
            @Pattern(regexp = "^[A-Za-z]+(?:[ '-][A-Za-z]+)*$", message = "First name can only include alphabetic characters, spaces, hyphens, and apostrophes. Example: 'John Doe' or 'Mary-Anne O'Conner'.") @Size(max = 50, message = "Your email cannot be longer than 50 characters.") String lastName) {
        this.lastName = lastName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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
