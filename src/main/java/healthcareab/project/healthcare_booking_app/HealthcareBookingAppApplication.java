package healthcareab.project.healthcare_booking_app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HealthcareBookingAppApplication {


	@Value("${DB_URL}")
	private String dbUrl;

	@Value("${DB_USERNAME}")
	private String dbUsername;

	@Value("${DB_PASSWORD}")
	private String dbPassword;

	public static void main(String[] args) {
		SpringApplication.run(HealthcareBookingAppApplication.class, args);
		System.out.println("DB_URL: " + dbUrl);
		System.out.println("DB_USERNAME: " + dbUsername);
		System.out.println("DB_PASSWORD: " + dbPassword);
	}

}
