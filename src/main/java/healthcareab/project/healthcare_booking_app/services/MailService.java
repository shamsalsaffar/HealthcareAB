package healthcareab.project.healthcare_booking_app.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class MailService {
    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;

    }

    public void sendVerificationEmail(String to, String verifyLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Verify your account");
        message.setText("Please verify your account by clicking this link:\n " + verifyLink);
        mailSender.send(message);

    }

}
