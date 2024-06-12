package internature.brokerks.repositories;

import internature.brokerks.entities.Email;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface EmailRepository extends ElasticsearchRepository<Email, String> {

    List<Email> findByStatus(String failed);
}
