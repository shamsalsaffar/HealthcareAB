package healthcareab.project.healthcare_booking_app.controllers;

import healthcareab.project.healthcare_booking_app.dto.AvailabilityRequest;
import healthcareab.project.healthcare_booking_app.dto.AvailabilityResponse;
import healthcareab.project.healthcare_booking_app.dto.AvailabilityUpdateRequest;
import healthcareab.project.healthcare_booking_app.services.AvailabilityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/availabilities")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    // TODO at the moment it doesnt check auth. fix for later issue.
    // @PreAuthorize("hasRole('CAREGIVER')")
    @PostMapping
    public ResponseEntity<AvailabilityResponse> createAvailability(@Valid @RequestBody AvailabilityRequest dtoRequest) {
        AvailabilityResponse dtoResponse = availabilityService.createAvailability(dtoRequest);
        return new ResponseEntity<>(dtoResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AvailabilityResponse>> getAllAvailabilities() {
        List<AvailabilityResponse> dtoResponse = availabilityService.getAllAvailabilities();
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvailabilityResponse> getAvailabilityById(@PathVariable Long id) {
        AvailabilityResponse dtoResponse = availabilityService.getAvailabilityById(id);
        return ResponseEntity.ok(dtoResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AvailabilityResponse> updateAvailability(@PathVariable Long id,
            @Valid @RequestBody AvailabilityUpdateRequest dtoUpdate) {
        return ResponseEntity.ok(availabilityService.updateAvailability(dtoUpdate, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AvailabilityResponse> deleteAvailability(@PathVariable Long id) {
        availabilityService.deleteAvailability(id);
        return ResponseEntity.noContent().build();
    }

}
