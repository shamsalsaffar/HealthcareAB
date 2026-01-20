package healthcareab.project.healthcare_booking_app.services;

import healthcareab.project.healthcare_booking_app.dto.PatientResponse;
import healthcareab.project.healthcare_booking_app.exceptions.BadRequestException;
import healthcareab.project.healthcare_booking_app.models.Patient;
import healthcareab.project.healthcare_booking_app.repository.PatientRepository;
import healthcareab.project.healthcare_booking_app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    public PatientService(PatientRepository patientRepository, UserRepository userRepository) {
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
    }

    public PatientResponse findPatientByUserId(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        Patient patient = patientRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User id does not belong to a patient"));
        return mapToPatientResponse(patient);
    }

    // helper methods
    private PatientResponse mapToPatientResponse(Patient patient) {
        PatientResponse patientResponse = new PatientResponse();
        patientResponse.setUserId(patient.getId());
        patientResponse.setFirstName(patient.getFirstName());
        patientResponse.setLastName(patient.getLastName());
        patientResponse.setAddress(patient.getAddress());
        patientResponse.setPersonalIdentityNumber(patient.getPersonalIdentityNumber());
        patientResponse.setPhoneNumber(patient.getPhoneNumber());
        return patientResponse;
    }
}
