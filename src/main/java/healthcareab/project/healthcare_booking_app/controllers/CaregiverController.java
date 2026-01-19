package healthcareab.project.healthcare_booking_app.controllers;

import healthcareab.project.healthcare_booking_app.dto.CaregiverResponse;
import healthcareab.project.healthcare_booking_app.services.CaregiverService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/caregiver")
public class CaregiverController {

    private final CaregiverService caregiverService;

    public CaregiverController(CaregiverService caregiverService) {
        this.caregiverService = caregiverService;
    }

    @GetMapping("/find-by-user-id")
    public ResponseEntity<CaregiverResponse> findCaregiverByUserId(
            @RequestParam @NotNull(message = "UserId is required") Long userId) {
        CaregiverResponse caregiverResponse = caregiverService.findCaregiverByUserId(userId);

        return ResponseEntity.ok(caregiverResponse);
    }

}
