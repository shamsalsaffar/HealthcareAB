package healthcareab.project.healthcare_booking_app.services;

import healthcareab.project.healthcare_booking_app.models.User;
import healthcareab.project.healthcare_booking_app.dto.UpdateUserRequest;
import healthcareab.project.healthcare_booking_app.models.Caregiver;
import healthcareab.project.healthcare_booking_app.models.Patient;
import healthcareab.project.healthcare_booking_app.models.User;
import healthcareab.project.healthcare_booking_app.repository.CaregiverRepository;
import healthcareab.project.healthcare_booking_app.repository.EmailVerificationTokenRepository;
import healthcareab.project.healthcare_booking_app.repository.PatientRepository;
import healthcareab.project.healthcare_booking_app.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
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
    public void anonymizeUserById(Long userId) {
        log.info("Anonymize requested for userId={}", userId);

        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

            if (user.isDeleted()) return;

            emailVerificationTokenRepository.findByUserId(userId)
                    .ifPresent(emailVerificationTokenRepository::delete);

            user.setUsername("anon+" + userId + "@example.invalid");
            user.setFirstName("Anonymized");
            user.setLastName("Anonymized");
            user.setEnabled(false);
            user.setEmailVerifiedAt(null);
            user.setDeleted(true);
            user.setDeletedAt(LocalDateTime.now());

            patientRepository.findById(userId).ifPresent(p -> {
                p.setAddress("Anonymized");
                p.setPhoneNumber("0700000000");
                p.setPersonalIdentityNumber("19000101" + String.format("%04d", userId % 10000));
                patientRepository.save(p);
            });

            caregiverRepository.findById(userId).ifPresent(c -> caregiverRepository.save(c));

            userRepository.save(user);

            log.info("Anonymize completed for userId={}", userId);

        } catch (Exception ex) {
            log.error("Anonymize FAILED for userId={}", userId, ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
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
            if (dto.getPersonalIdentityNumber() != null) {
                patient.setPersonalIdentityNumber(dto.getPersonalIdentityNumber());
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
