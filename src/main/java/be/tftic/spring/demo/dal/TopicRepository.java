package be.tftic.spring.demo.dal;

import be.tftic.spring.demo.domain.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}
