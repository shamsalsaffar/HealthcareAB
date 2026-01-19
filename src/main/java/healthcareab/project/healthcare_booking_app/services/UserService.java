package healthcareab.project.healthcare_booking_app.services;

import healthcareab.project.healthcare_booking_app.dto.UpdateUserRequest;
import healthcareab.project.healthcare_booking_app.models.Caregiver;
import healthcareab.project.healthcare_booking_app.models.Patient;
import healthcareab.project.healthcare_booking_app.models.User;
import healthcareab.project.healthcare_booking_app.repository.CaregiverRepository;
import healthcareab.project.healthcare_booking_app.repository.EmailVerificationTokenRepository;
import healthcareab.project.healthcare_booking_app.repository.PatientRepository;
import healthcareab.project.healthcare_booking_app.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final CaregiverRepository caregiverRepository;
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;

    public UserService(UserRepository userRepository, PatientRepository patientRepository,
            CaregiverRepository caregiverRepository,
            EmailVerificationTokenRepository emailVerificationTokenRepository) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.caregiverRepository = caregiverRepository;
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
    }

    @Transactional
    public void deleteUserById(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        // 1) delete token first (FK safe)
        emailVerificationTokenRepository.findByUserId(userId).ifPresent(emailVerificationTokenRepository::delete);

        // 2) delete child row if exists (safe fallback)
        if (patientRepository.existsById(userId)) {
            patientRepository.deleteById(userId);
        }
        if (caregiverRepository.existsById(userId)) {
            caregiverRepository.deleteById(userId);
        }

        // 3) delete parent (users)
        userRepository.deleteById(userId);
    }

    @Transactional
    public void updateUser(Long userId, UpdateUserRequest dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        //update users shared fields
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());

        //update patient specific fields
        if (user instanceof Patient patient) {
            if (dto.getPhoneNumber() != null) {
                patient.setPhoneNumber(dto.getPhoneNumber());
            }
            if (dto.getAddress() != null) {
                patient.setAddress(dto.getAddress());
            }
        }
        //update caregiver specific fields
        else if (user instanceof Caregiver caregiver) {
            if (dto.getSpecialisation() != null) {
                caregiver.setSpecialisation(dto.getSpecialisation());
            }
        }
    }
}
