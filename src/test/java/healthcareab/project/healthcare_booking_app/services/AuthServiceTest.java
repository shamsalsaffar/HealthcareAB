package healthcareab.project.healthcare_booking_app.services;

import healthcareab.project.healthcare_booking_app.models.Patient;
import healthcareab.project.healthcare_booking_app.repository.PatientRepository;
import healthcareab.project.healthcare_booking_app.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;         
import static org.mockito.ArgumentMatchers.any;                      
import static org.mockito.Mockito.verify;                            
import static org.mockito.Mockito.when;

// Aktiverar Mockito för JUnit 5
@ExtendWith(MockitoExtension.class)                                  
class AuthServiceTest {

    // Mock: fejkad repo (används av AuthService)
    @Mock
    private UserRepository userRepository;                            
    @Mock
    private PatientRepository patientRepository;                     
    @Mock
    private PasswordEncoder passwordEncoder;                         
    // Skapar AuthService och injicerar mocks automatiskt
    @InjectMocks
    private AuthService authService;                                 

    @Test
    void registerPatient_shouldEncodePassword_andSavePatient() {
        // arrange

        // Test-data: patient som ska registreras
        Patient patient = new Patient();                            
        patient.setUsername("posttest@test.com");                      
        patient.setPassword("password!");                           
        
        // När encode anropas med "password!"
        when(passwordEncoder.encode("password!"))                      // När encode anropas med "password!"
                .thenReturn("ENCODED_PASS");  // ...returnera fejkad encoded sträng

        // När save anropas med vilken Patient som helst
        when(patientRepository.save(any(Patient.class)))              
                .thenAnswer(inv -> inv.getArgument(0)); // ...returnera samma objekt (som om DB sparade)

        // act
        
        Patient saved = authService.registerPatient(patient); // Kör metoden vi testar         

        // assert 
        assertEquals("posttest@test.com", saved.getUsername()); // Kontroll: username ska vara samma
        assertEquals("ENCODED_PASS", saved.getPassword());// Kontroll: password ska vara encoded

        verify(patientRepository).save(any(Patient.class)); // Kontroll: save() måste ha anropats 1 gång
    }
}
