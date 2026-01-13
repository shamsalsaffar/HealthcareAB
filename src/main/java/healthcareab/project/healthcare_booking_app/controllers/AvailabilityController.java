package healthcareab.project.healthcare_booking_app.controllers;

import healthcareab.project.healthcare_booking_app.dto.AvailabilityRequest;
import healthcareab.project.healthcare_booking_app.dto.AvailabilityResponse;
import healthcareab.project.healthcare_booking_app.services.AvailabilityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/availabilities")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @PostMapping
    public ResponseEntity<AvailabilityResponse> createAvailability(@Valid @RequestBody AvailabilityRequest availabilityRequest) {
        AvailabilityResponse availabilityResponse = availabilityService.createAvailability(availabilityRequest);
//        return ResponseEntity.ok(availabilityResponse);
        return new ResponseEntity<>(availabilityResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AvailabilityResponse>> getALlAvailabilities() {
        List<AvailabilityResponse> availabilityResponse = availabilityService.getAllAvailabilities();
        return new ResponseEntity<>(availabilityResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvailabilityResponse> getAvailabilityById(@PathVariable Long id) {
        AvailabilityResponse availabilityResponse = availabilityService.getAvailabilityById(id);
        return ResponseEntity.ok(availabilityResponse);
    }

}
