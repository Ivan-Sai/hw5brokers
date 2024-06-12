package internature.brokerks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BrokersApplication {

    public static void main(String[] args) {
        SpringApplication.run(BrokersApplication.class, args);
    }

}
