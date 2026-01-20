package healthcareab.project.healthcare_booking_app.services;

import healthcareab.project.healthcare_booking_app.dto.CaregiverResponse;
import healthcareab.project.healthcare_booking_app.exceptions.BadRequestException;
import healthcareab.project.healthcare_booking_app.models.Caregiver;
import healthcareab.project.healthcare_booking_app.repository.CaregiverRepository;
import healthcareab.project.healthcare_booking_app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CaregiverService {

    private final UserRepository userRepository;
    private final CaregiverRepository caregiverRepository;

    public CaregiverService(UserRepository userRepository, CaregiverRepository caregiverRepository) {
        this.userRepository = userRepository;
        this.caregiverRepository = caregiverRepository;
    }

    public CaregiverResponse findCaregiverByUserId(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        Caregiver caregiver = caregiverRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User id does not belong to a caregiver"));
        return mapToCaregiverResponse(caregiver);
    }

    // helper methods
    private CaregiverResponse mapToCaregiverResponse(Caregiver caregiver) {
        CaregiverResponse caregiverResponse = new CaregiverResponse();
        caregiverResponse.setUserId(caregiver.getId());
        caregiverResponse.setFirstName(caregiver.getFirstName());
        caregiverResponse.setLastName(caregiver.getLastName());
        caregiverResponse.setSpecialisation(caregiver.getSpecialisation());
        caregiverResponse.setClinic(caregiver.getClinic());
        return caregiverResponse;
    }
}
