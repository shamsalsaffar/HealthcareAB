package healthcareab.project.healthcare_booking_app.dto;

import healthcareab.project.healthcare_booking_app.models.Clinic;
import healthcareab.project.healthcare_booking_app.models.enums.Specialisation;

public class CaregiverResponse {

    private Long userId;

    private String firstName;

    private String lastName;

    private Specialisation specialisation;

    private Clinic clinic;

    public CaregiverResponse(Long userId, String firstName, String lastName, Specialisation specialisation,
            Clinic clinic) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialisation = specialisation;
        this.clinic = clinic;
    }

    public CaregiverResponse() {
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public Specialisation getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(Specialisation specialisation) {
        this.specialisation = specialisation;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
