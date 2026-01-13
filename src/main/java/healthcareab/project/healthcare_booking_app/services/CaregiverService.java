package healthcareab.project.healthcare_booking_app.services;

import healthcareab.project.healthcare_booking_app.dto.CaregiverResponse;
import healthcareab.project.healthcare_booking_app.models.Caregiver;
import org.springframework.stereotype.Service;

@Service
public class CaregiverService {

    //mapping helper method
    public CaregiverResponse mapToCaregiverResponse(Caregiver caregiver) {
        CaregiverResponse caregiverResponse = new CaregiverResponse();
        caregiverResponse.setUserId(caregiver.getId());
        caregiverResponse.setFirstName(caregiver.getFirstName());
        caregiverResponse.setLastName(caregiver.getLastName());
        caregiverResponse.setSpecialisation(caregiver.getSpecialisation());
        caregiverResponse.setClinic(caregiver.getClinic());
        return caregiverResponse;
    }
}
