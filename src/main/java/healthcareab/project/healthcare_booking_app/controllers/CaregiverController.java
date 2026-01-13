package healthcareab.project.healthcare_booking_app.controllers;

import healthcareab.project.healthcare_booking_app.dto.CaregiverResponse;
import healthcareab.project.healthcare_booking_app.models.Caregiver;
import healthcareab.project.healthcare_booking_app.repository.CaregiverRepository;
import healthcareab.project.healthcare_booking_app.services.CaregiverService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/caregiver")
public class CaregiverController {

    private final CaregiverRepository caregiverRepository;
    private final CaregiverService caregiverService;

    public CaregiverController(CaregiverRepository caregiverRepository, CaregiverService caregiverService) {
        this.caregiverRepository = caregiverRepository;
        this.caregiverService = caregiverService;
    }

    @GetMapping("/find-by-user-id")
    public ResponseEntity<CaregiverResponse> findCaregiverByUserId(@RequestParam Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        Caregiver caregiver = caregiverRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("Caregiver not found for user ID: " + userId));
        CaregiverResponse caregiverResponse = caregiverService.mapToCaregiverResponse(caregiver);
        return ResponseEntity.ok(caregiverResponse);
    }
}
