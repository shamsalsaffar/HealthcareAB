package healthcareab.project.healthcare_booking_app.services;

import healthcareab.project.healthcare_booking_app.dto.AvailabilityRequest;
import healthcareab.project.healthcare_booking_app.dto.AvailabilityResponse;
import healthcareab.project.healthcare_booking_app.dto.AvailabilityUpdateRequest;
import healthcareab.project.healthcare_booking_app.exception.ResourceNotFoundException;
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

    //TODO need to check auth before update. just wanna check if it works first
    public AvailabilityResponse createAvailability(AvailabilityRequest dtoRequest) {

        Caregiver caregiver = userRepository.findById(dtoRequest.getCaregiverId())
                .filter(Caregiver.class::isInstance)
                .map(Caregiver.class::cast)
                .orElseThrow(() -> new ResourceNotFoundException("Caregiver not found"));

        Availability availability = new Availability();
        availability.setCaregiver(caregiver);
        availability.setReoccurring(dtoRequest.getReoccurring());
        availability.setStartTime(dtoRequest.getStartTime());
        availability.setEndTime(dtoRequest.getEndTime());
        availability.setCreatedAt(LocalDate.now());

        availabilityRepository.save(availability);

        return mapToResponse(availability);
    }

    public AvailabilityResponse getAvailabilityById(Long id) {
        Availability availability = availabilityRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No availability found"));
        return mapToResponse(availability);
    }

    public List<AvailabilityResponse> getAllAvailabilities() {
        return availabilityRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public AvailabilityResponse updateAvailability(AvailabilityUpdateRequest dtoUpdate, Long id) {
        Availability availability = availabilityRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No availability found"));

        if (dtoUpdate.getStartTime() != null){
            availability.setStartTime(dtoUpdate.getStartTime());
        }
        if (dtoUpdate.getEndTime() != null){
            availability.setEndTime(dtoUpdate.getEndTime());
        }
        if (dtoUpdate.getReoccurring() != null){
            availability.setReoccurring(dtoUpdate.getReoccurring());
        }

        availabilityRepository.save(availability);

        return mapToResponse(availability);
    }

    public void deleteAvailability(Long id) {
        if (!availabilityRepository.existsById(id)) {
            throw new ResourceNotFoundException("No availability found");
        }
        availabilityRepository.deleteById(id);
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

}
