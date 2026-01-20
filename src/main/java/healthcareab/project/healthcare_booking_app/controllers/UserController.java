package healthcareab.project.healthcare_booking_app.controllers;

import healthcareab.project.healthcare_booking_app.dto.UpdateUserRequest;
import healthcareab.project.healthcare_booking_app.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/anonymize")
    public ResponseEntity<Map<String, String>> anonymizeUser(@PathVariable Long id) {
        System.out.println("✅ HIT anonymize controller for id=" + id);
        userService.anonymizeUserById(id);
        return ResponseEntity.ok(Map.of("message", "User anonymized successfully"));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> patchUser(
            @PathVariable Long id,
            @RequestBody UpdateUserRequest request) {

        userService.updateUser(id, request);
        return ResponseEntity.noContent().build();
    }
}
