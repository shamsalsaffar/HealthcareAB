package healthcareab.project.healthcare_booking_app.models;

import healthcareab.project.healthcare_booking_app.models.enums.Role;
import healthcareab.project.healthcare_booking_app.models.enums.Specialisation;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

@Entity
@Table(name = "caregivers")
public class Caregiver extends User {


    @Enumerated(EnumType.STRING)
    private Specialisation specialisation;

    @ManyToOne
    @JoinColumn(name = "clinic_id", nullable = false)
    @NotNull(message = "A caregiver has to belong to a clinic.")
    private Clinic clinic;


    public Caregiver(Specialisation specialisation, Clinic clinic) {
        this.specialisation = specialisation;
        this.clinic = clinic;
    }

    public Caregiver(String username, String password, Set<Role> roles, Specialisation specialisation, Clinic clinic) {
        super(username, password, roles);  // Call the parent class constructor to initialize User fields
        this.specialisation = specialisation;
        this.clinic = clinic;
    }

    public Caregiver() {

    }

    public Specialisation getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(Specialisation specialisation) {
        this.specialisation = specialisation;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }
}
