package be.tftic.spring.demo.api.controller;

import be.tftic.spring.demo.api.model.dto.PostDTO;
import be.tftic.spring.demo.api.model.dto.TopicDTO;
import be.tftic.spring.demo.api.model.form.TopicForm;
import be.tftic.spring.demo.bll.TopicService;
import be.tftic.spring.demo.domain.entity.Post;
import be.tftic.spring.demo.domain.entity.Topic;
import be.tftic.spring.demo.domain.entity.TopicCategory;
import be.tftic.spring.demo.domain.entity.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/topic")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/{id:^[0-9]$}")
    public ResponseEntity<TopicDTO> getTopic(@PathVariable long id){
        return ResponseEntity.ok(
                TopicDTO.fromEntity( topicService.getOne(id) )
        );
    }

    // GET - http://localhost:8080/topic/1/posts
    @GetMapping("/{id}/posts")
    public ResponseEntity<List<PostDTO>> getPosts(@PathVariable long id){
        Topic topic = topicService.getOne(id);
        List<Post> posts = topic.getPosts();
        List<PostDTO> dtos = posts.stream()
                .map( PostDTO::fromEntity)
                .toList();

        return ResponseEntity.ok( dtos );
    }

    @GetMapping
    public ResponseEntity<List<TopicDTO>> getAll(){
        return ResponseEntity.ok(
                topicService.getAll().stream()
                        .map(TopicDTO::fromEntity)
                        .toList()
        );
    }


    @GetMapping(params = "category")
    public ResponseEntity<List<TopicDTO>> getByCategory(
            @RequestParam(required = false) TopicCategory category
    ){
        return ResponseEntity.ok(
                topicService.getByCategory(category).stream()
                        .map( TopicDTO::fromEntity )
                        .toList()
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TopicDTO> create(@RequestBody @Valid TopicForm form, Authentication auth){
        Topic topic = form.toEntity();
        System.out.println(auth.getPrincipal());
        topic.setCreatedBy( (User) auth.getPrincipal() );
        topic = topicService.create(topic);
        return ResponseEntity.created( URI.create("/topic/"+topic.getId()) )
                .body(TopicDTO.fromEntity(topic));
    }

}
