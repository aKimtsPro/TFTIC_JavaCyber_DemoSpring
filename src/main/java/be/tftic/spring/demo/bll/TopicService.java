package be.tftic.spring.demo.bll;

import be.tftic.spring.demo.domain.entity.Topic;
import be.tftic.spring.demo.domain.entity.TopicCategory;

import java.util.List;

public interface TopicService {

    List<Topic> getAll();
    Topic getOne(long id);
    Topic create(Topic topic);

    List<Topic> getByCategory(TopicCategory category);

}
