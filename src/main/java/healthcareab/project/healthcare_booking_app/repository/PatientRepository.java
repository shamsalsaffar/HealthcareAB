package healthcareab.project.healthcare_booking_app.repository;

import healthcareab.project.healthcare_booking_app.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<Patient> findByPersonalIdentityNumber(String pin);

    boolean existsByPersonalIdentityNumber(String pin);
}
