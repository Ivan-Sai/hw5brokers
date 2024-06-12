package internature.brokerks.listeners;

import internature.brokerks.entities.Email;
import internature.brokerks.services.EmailEntityService;
import internature.brokerks.services.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailListener {

    private final EmailEntityService emailService;
    private final EmailSender emailSender;


    @KafkaListener(topics = "${kafka.topic.email}", groupId = "email-group")
    public void emailToSent(String title) {
        Email email = emailService.createEmail(title);
        emailSender.sendEmail(email);
    }
}
