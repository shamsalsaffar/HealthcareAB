package healthcareab.project.healthcare_booking_app.models;

import jakarta.persistence.*;

@Entity
@Table(name = "clinics")
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
