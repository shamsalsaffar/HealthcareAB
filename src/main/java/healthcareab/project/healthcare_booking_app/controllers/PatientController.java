package healthcareab.project.healthcare_booking_app.controllers;

import healthcareab.project.healthcare_booking_app.dto.PatientResponse;
import healthcareab.project.healthcare_booking_app.services.PatientService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_CAREGIVER')")
    @GetMapping("/find-by-user-id")
    public ResponseEntity<PatientResponse> findPatientByUserId(
            @RequestParam @NotNull(message = "UserId is required") Long userId) {
        PatientResponse patientResponse = patientService.findPatientByUserId(userId);
        return ResponseEntity.ok(patientResponse);
    }

}
