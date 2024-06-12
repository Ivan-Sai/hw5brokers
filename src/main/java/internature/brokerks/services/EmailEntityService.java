package internature.brokerks.services;

import internature.brokerks.entities.Email;

import java.util.List;

public interface EmailEntityService {
    Email createEmail(String dto);

    Email saveEmail(Email email);

    List<Email> getFailedEmails();
}
