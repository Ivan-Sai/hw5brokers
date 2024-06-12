package internature.brokerks.service;

import internature.brokerks.entities.Email;
import internature.brokerks.services.EmailEntityService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.annotation.DirtiesContext;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class EmailListenerTest {

    @Autowired
    KafkaOperations<String, String> kafkaOperations;
    @Value("${kafka.topic.email}")
    private String topic;

    @MockBean
    private JavaMailSender javaMailSender;

    @MockBean
    private EmailEntityService emailEntityService;


    @Test
    void testSuccessfulEmailSending() {
        // given
        Email email = createEmail();
        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));
        Mockito.when(emailEntityService.createEmail(any(String.class))).thenReturn(email);

        // when
        kafkaOperations.send(topic, "Test Title");

        // then
        verify(javaMailSender, after(5000)).send(any(SimpleMailMessage.class));
        verify(emailEntityService, atLeastOnce()).saveEmail(any(Email.class));
        assertThat(email.getStatus()).isEqualTo("SENT");
    }

    @Test
    void testFailedEmailSending() {
        // given
        Email email = createEmail();
        doThrow(new RuntimeException("Failed to send email")).when(javaMailSender).send(any(SimpleMailMessage.class));
        Mockito.when(emailEntityService.createEmail(any(String.class))).thenReturn(email);

        // when
        kafkaOperations.send(topic, "Test Title");

        // then
        verify(javaMailSender, after(5000)).send(any(SimpleMailMessage.class));
        verify(emailEntityService, atLeastOnce()).saveEmail(any(Email.class));
        assertThat(email.getStatus()).isEqualTo("FAILED");
        assertThat(email.getAttemptCount()).isGreaterThan(0);
    }

    private Email createEmail() {
        Email email = new Email();
        email.setFrom("vaniasai05@gmail.com");
        email.setRecipient("ivansai02mail@gmail.com");
        email.setSubject("New Book Notification: Test Title");
        email.setContent("A new book titled 'Test Title' has been added.");
        email.setStatus("NEW");
        email.setAttemptCount(0);
        email.setLastAttemptTime(Instant.now());
        return email;
    }
}
