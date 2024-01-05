package be.tftic.spring.demo.bll.impl;

import be.tftic.spring.demo.bll.TopicService;
import be.tftic.spring.demo.dal.TopicRepository;
import be.tftic.spring.demo.domain.entity.Topic;
import org.springframework.stereotype.Service;

@Service
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    public TopicServiceImpl(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public Topic getOne(long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No topic found with id: "+ id));
    }
}
