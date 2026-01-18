package healthcareab.project.healthcare_booking_app.services;

import healthcareab.project.healthcare_booking_app.repository.EmailVerificationTokenRepository;
import healthcareab.project.healthcare_booking_app.repository.PatientRepository;
import healthcareab.project.healthcare_booking_app.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;

    public PatientService(PatientRepository patientRepository,
                          UserRepository userRepository,
                          EmailVerificationTokenRepository emailVerificationTokenRepository) {
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
    }

    @Transactional
    public void deletePatient(Long userId) {
        emailVerificationTokenRepository.findByUserId(userId)
                .ifPresent(emailVerificationTokenRepository::delete);
        patientRepository.deleteById(userId);
       // userRepository.deleteById(userId);

    }
}
