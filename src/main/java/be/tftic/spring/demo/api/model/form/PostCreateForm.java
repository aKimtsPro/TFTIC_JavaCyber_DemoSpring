package be.tftic.spring.demo.api.model.form;

import be.tftic.spring.demo.domain.entity.Post;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostCreateForm {

    @NotNull
    private String title;
    @NotNull
    private String content;
    @NotNull
    private Long userId;
    @NotNull
    private Long topicId;

    public Post mapToEntity(){
        Post post = new Post();
        post.setTitle( this.title );
        post.setContent( this.content );
        return post;
    }

}
