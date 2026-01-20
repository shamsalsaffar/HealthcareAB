package healthcareab.project.healthcare_booking_app.services;

import healthcareab.project.healthcare_booking_app.models.User;
import healthcareab.project.healthcare_booking_app.models.enums.Role;
import healthcareab.project.healthcare_booking_app.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Aktiverar Mockito
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService; // Klassen vi testar

    @Test
    void loadUserByUsername_shouldReturnUserDetails_withRoleAuthority() {
        // arrange
        User user = new User(); // Skapa test-user
        user.setUsername("test@test.com"); // Username
        user.setPassword("ENC_PASS"); // Encoded password (som det skulle vara i DB)
        user.setRole(Role.USER); // Roll

        when(userRepository.findByUsername("test@test.com")).thenReturn(Optional.of(user)); // När repo letar, returnera
                                                                                            // user

        // act
        UserDetails details = customUserDetailsService.loadUserByUsername("test@test.com");

        // assert
        assertEquals("test@test.com", details.getUsername()); // username matchar
        assertEquals("ENC_PASS", details.getPassword()); // password matchar

        // Kontrollera authority: ska bli "USER"
        assertTrue(details.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER")));
    }

    @Test
    void loadUserByUsername_shouldThrow_whenUserNotFound() {
        // arrange
        when(userRepository.findByUsername("missing@test.com")).thenReturn(Optional.empty()); // Repo hittar ingen user

        // act + assert
        assertThrows(UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername("missing@test.com"));
    }
}
