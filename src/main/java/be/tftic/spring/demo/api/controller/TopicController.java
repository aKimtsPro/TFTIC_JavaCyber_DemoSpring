package be.tftic.spring.demo.api.controller;

import be.tftic.spring.demo.api.model.dto.PostDTO;
import be.tftic.spring.demo.bll.TopicService;
import be.tftic.spring.demo.domain.entity.Post;
import be.tftic.spring.demo.domain.entity.Topic;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/topic")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    // GET - http://localhost:8080/topic/1/posts
    @GetMapping("/{id}/posts")
    public ResponseEntity<List<PostDTO>> getPosts(@PathVariable long id){
        Topic topic = topicService.getOne(id);
        List<Post> posts = topic.getPosts();
        List<PostDTO> dtos = posts.stream()
                .map( PostDTO::mapToDto )
                .toList();

        return ResponseEntity.ok( dtos );
    }

}
