package internature.brokerks.services.impl;

import internature.brokerks.entities.Email;
import internature.brokerks.services.EmailEntityService;
import internature.brokerks.services.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailSenderImpl implements EmailSender {

    private final EmailEntityService emailService;
    private final JavaMailSender mailSender;

    public void sendEmail(Email email) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(email.getFrom());
            message.setTo(email.getRecipient());
            message.setSubject(email.getSubject());
            message.setText(email.getContent());
            mailSender.send(message);
            email.setStatus("SENT");
            email.setLastAttemptTime(Instant.now());
            emailService.saveEmail(email);
            System.out.println("Email sent successfully");
        } catch (Exception e) {
            System.out.println("Email sending failed");
            email.setStatus("FAILED");
            email.setAttemptCount(email.getAttemptCount() + 1);
            email.setLastAttemptTime(Instant.now());
            email.setErrorMessage("Exception: " + e.getClass() + " message: " + e.getMessage());
            emailService.saveEmail(email);
        }
    }

    @Scheduled(cron = "0 */5 * * * ?")
    private void resendEmails() {
        List<Email> emails = emailService.getFailedEmails();
        emails.forEach(this::sendEmail);
    }
}
