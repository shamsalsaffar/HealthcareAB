package healthcareab.project.healthcare_booking_app.services;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MailServiceTest {

    @Test
    void sendVerificationEmail_shouldSendEmailWithCorrectContent() {

        // arrange
        JavaMailSender mailSender = mock(JavaMailSender.class);
        MailService mailService = new MailService(mailSender);

        String to = "test@gmail.com";
        String link = "http://localhost:8080/auth/verify?token=abc123";

        // act
        mailService.sendVerificationEmail(to, link);

        // assert
        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(captor.capture());

        SimpleMailMessage message = captor.getValue();

        assertNotNull(message);
        assertEquals(to, message.getTo()[0]);
        assertTrue(message.getText().contains(link));
        assertEquals("Verify your account", message.getSubject());
    }
}
