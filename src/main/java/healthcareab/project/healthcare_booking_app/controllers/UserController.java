package healthcareab.project.healthcare_booking_app.controllers;

import healthcareab.project.healthcare_booking_app.dto.UpdateUserRequest;
import healthcareab.project.healthcare_booking_app.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/{id}")
    public ResponseEntity<Void> patchUser(
            @PathVariable Long id,
            @RequestBody UpdateUserRequest request) {

        userService.updateUser(id, request);
        return ResponseEntity.noContent().build();
    }
}
