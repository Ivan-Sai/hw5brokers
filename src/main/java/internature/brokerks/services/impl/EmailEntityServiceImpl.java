package internature.brokerks.services.impl;

import internature.brokerks.entities.Email;
import internature.brokerks.repositories.EmailRepository;
import internature.brokerks.services.EmailEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailEntityServiceImpl implements EmailEntityService {

    private final EmailRepository emailRepository;

    @Value("${spring.mail.username}")
    private String from;


    @Override
    public Email createEmail(String title) {
        Email email = new Email();
        email.setFrom(from);
        email.setSubject("New Book Notification: " + title);
        email.setContent("A new book titled '" + title + "' has been added.");
        email.setRecipient("admin@gmail.com");
        email.setStatus("NEW");
        email.setAttemptCount(0);
        return emailRepository.save(email);
    }

    @Override
    public Email saveEmail(Email email) {
        return emailRepository.save(email);
    }

    @Override
    public List<Email> getFailedEmails() {
        return emailRepository.findByStatus("FAILED");
    }
}
