package be.tftic.spring.demo.bll.impl;

import be.tftic.spring.demo.bll.TopicService;
import be.tftic.spring.demo.dal.TopicRepository;
import be.tftic.spring.demo.domain.entity.Topic;
import be.tftic.spring.demo.domain.entity.TopicCategory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    public TopicServiceImpl(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public List<Topic> getAll() {
        return topicRepository.findAll();
    }

    @Override
    public Topic getOne(long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No topic found with id: "+ id));
    }

    @Override
    public Topic create(Topic topic) {
        return topicRepository.save(topic);
    }

    @Override
    public List<Topic> getByCategory(TopicCategory category) {
        return topicRepository.findByCategory(category);
    }
}
