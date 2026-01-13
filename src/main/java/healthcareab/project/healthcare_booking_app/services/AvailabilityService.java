package healthcareab.project.healthcare_booking_app.services;

import healthcareab.project.healthcare_booking_app.dto.AvailabilityRequest;
import healthcareab.project.healthcare_booking_app.dto.AvailabilityResponse;
import healthcareab.project.healthcare_booking_app.models.Availability;
import healthcareab.project.healthcare_booking_app.repository.AvailabilityRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AvailabilityService {

    private final AvailabilityRepository availabilityRepository;

    public AvailabilityService(AvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }

    private AvailabilityResponse mapToResponse(Availability availability) {
        return new AvailabilityResponse(
                availability.getId(),
                availability.getStartTime(),
                availability.getEndTime(),
                availability.isReoccurring(),
                availability.getCaregiverId().getId(),
                availability.getCreatedAt()
        );
    }

    public AvailabilityResponse createAvailability(AvailabilityRequest dto) {
        Availability availability = new Availability();
        availability.setCaregiverId();
        availability.setReoccurring(dto.isReoccurring());
        availability.setStartTime(dto.getStartTime());
        availability.setEndTime(dto.getEndTime());
        availability.setCreatedAt(LocalDate.now());

        Availability saved = availabilityRepository.save(availability);

        return mapToResponse(saved);
    }

    public AvailabilityRepository getAvailabilityRepository() {
        return availabilityRepository;
    }

    public List<AvailabilityResponse> getAllAvailabilities() {
        return availabilityRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}
