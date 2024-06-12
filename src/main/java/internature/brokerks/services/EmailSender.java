package internature.brokerks.services;

import internature.brokerks.entities.Email;

public interface EmailSender {
    void sendEmail(Email email);
}
