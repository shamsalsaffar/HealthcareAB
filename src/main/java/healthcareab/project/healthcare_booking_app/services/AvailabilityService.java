package healthcareab.project.healthcare_booking_app.services;

import healthcareab.project.healthcare_booking_app.dto.AvailabilityRequest;
import healthcareab.project.healthcare_booking_app.dto.AvailabilityResponse;
import healthcareab.project.healthcare_booking_app.models.Availability;
import healthcareab.project.healthcare_booking_app.models.Caregiver;
import healthcareab.project.healthcare_booking_app.repository.AvailabilityRepository;
import healthcareab.project.healthcare_booking_app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AvailabilityService {

    private final AvailabilityRepository availabilityRepository;
    private final UserRepository userRepository;

    public AvailabilityService(AvailabilityRepository availabilityRepository, UserRepository userRepository) {
        this.availabilityRepository = availabilityRepository;
        this.userRepository = userRepository;
    }

    private AvailabilityResponse mapToResponse(Availability availability) {
        return new AvailabilityResponse(
                availability.getId(),
                availability.getStartTime(),
                availability.getEndTime(),
                availability.isReoccurring(),
                availability.getCaregiver().getId(),
                availability.getCreatedAt()
        );
    }

    public AvailabilityResponse createAvailability(AvailabilityRequest dto) {

        Caregiver caregiver = (Caregiver) userRepository.findById(dto.getCaregiverId())
                .filter(Caregiver.class::isInstance)
                .stream()
                .map(Caregiver.class::cast);

        Availability availability = new Availability();
        availability.setCaregiver(caregiver);
        availability.setReoccurring(dto.isReoccurring());
        availability.setStartTime(dto.getStartTime());
        availability.setEndTime(dto.getEndTime());
        availability.setCreatedAt(LocalDate.now());

        Availability saved = availabilityRepository.save(availability);

        return mapToResponse(saved);
    }

    public List<AvailabilityResponse> getAllAvailabilities() {
        return availabilityRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public AvailabilityResponse getAvailabilityById(Long id) {
        Availability availability = availabilityRepository.findById(id).orElse(null);
        return mapToResponse(availability);
    }
}
