package be.tftic.spring.demo.api.controller;

import be.tftic.spring.demo.api.model.dto.PostDTO;
import be.tftic.spring.demo.api.model.form.PostCreateForm;
import be.tftic.spring.demo.api.model.form.PostUpdateForm;
import be.tftic.spring.demo.bll.PostService;
import be.tftic.spring.demo.bll.UserService;
import be.tftic.spring.demo.domain.entity.Post;
import be.tftic.spring.demo.domain.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final UserService userService;

    public PostController(
            PostService postService,
            UserService userService
    ) {
        this.postService = postService;
        this.userService = userService;
    }

    // GET - http://localhost:8080/post
    @GetMapping
    public ResponseEntity<List<PostDTO>> getAll(){
        List<Post> posts = postService.getAll();
        List<PostDTO> dtos = posts.stream()
                .map( PostDTO::mapToDto )
                .toList();
        return ResponseEntity.ok( dtos );
    }

    // GET - http://localhost:8080/post/1
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getOne(@PathVariable long id){
        Post post = postService.getOne( id );
        PostDTO dto = PostDTO.mapToDto( post );
        return ResponseEntity.ok( dto );
    }

    // POST - http://localhost:8080/post
    @PostMapping
    public ResponseEntity<PostDTO> create(@RequestBody PostCreateForm form){
        Post toCreate = form.mapToEntity();
        User creator = userService.getOne( form.getUserId() );
        toCreate.setCreatedBy( creator );

        Post created = postService.create( toCreate );
        PostDTO dto = PostDTO.mapToDto( created );
        return ResponseEntity.status( HttpStatus.CREATED )
                .body( dto );
    }

    // PUT - http://localhost:8080/post/{id}
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> update(@PathVariable long id, @RequestBody PostUpdateForm form){
        Post toUpdate = postService.getOne(id);
        toUpdate.setTitle(form.getTitle());
        toUpdate.setContent(form.getContent());
        Post updated = postService.update(toUpdate);
        PostDTO dto = PostDTO.mapToDto( updated );
        return ResponseEntity.ok( dto );
    }

    // DELETE - http://localhost:8080/post/1
    @DeleteMapping("/{id}")
    public ResponseEntity<PostDTO> delete(@PathVariable long id){
        Post deleted = postService.delete(id);
        PostDTO dto = PostDTO.mapToDto(deleted);
        return ResponseEntity.ok(dto);
    }

}
