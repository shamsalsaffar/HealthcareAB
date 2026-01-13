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

    //TODO need to check auth before update. just wanna check if it works first
    public AvailabilityResponse createAvailability(AvailabilityRequest dto) {

        Caregiver caregiver = userRepository.findById(dto.getCaregiverId())
                .filter(Caregiver.class::isInstance)
                .map(Caregiver.class::cast)
                .orElseThrow(() -> new RuntimeException("Caregiver not found"));

        Availability availability = new Availability();
        availability.setCaregiver(caregiver);
        availability.setReoccurring(dto.getReoccurring());
        availability.setStartTime(dto.getStartTime());
        availability.setEndTime(dto.getEndTime());
        availability.setCreatedAt(LocalDate.now());

        availabilityRepository.save(availability);

        return mapToResponse(availability);
    }

    public AvailabilityResponse getAvailabilityById(Long id) {
        Availability availability = availabilityRepository.findById(id).orElseThrow(() ->
                new RuntimeException("No availability found"));
        return mapToResponse(availability);
    }

    public List<AvailabilityResponse> getAllAvailabilities() {
        return availabilityRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public AvailabilityResponse updateAvailability(AvailabilityRequest dto, Long id) {
        Availability availability = availabilityRepository.findById(id).orElseThrow(() ->
                new RuntimeException("No availability found"));

        if (dto.getStartTime() != null){
            availability.setStartTime(dto.getStartTime());
        }
        if (dto.getEndTime() != null){
            availability.setEndTime(dto.getEndTime());
        }
        if (dto.getReoccurring() != null){
            availability.setReoccurring(dto.getReoccurring());
        }

        availabilityRepository.save(availability);

        return mapToResponse(availability);
    }

    public void deleteAvailability(Long id) {
        if (!availabilityRepository.existsById(id)) {
            throw new RuntimeException("Availability not found");
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
