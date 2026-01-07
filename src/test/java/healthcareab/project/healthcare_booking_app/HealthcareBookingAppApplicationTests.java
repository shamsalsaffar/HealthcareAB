package healthcareab.project.healthcare_booking_app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class HealthcareBookingAppApplicationTests {


	@Value("${DB_URL}")
	private String dbUrl;

	@Value("${DB_USERNAME}")
	private String dbUsername;

	@Value("${DB_PASSWORD}")
	private String dbPassword;

	@Test
	void contextLoads() {
		System.out.println("DB_URL: " + dbUrl);
		System.out.println("DB_USERNAME: " + dbUsername);
		System.out.println("DB_PASSWORD: " + dbPassword);
	}

}
