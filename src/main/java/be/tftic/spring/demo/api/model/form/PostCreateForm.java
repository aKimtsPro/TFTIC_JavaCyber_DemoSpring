package be.tftic.spring.demo.api.model.form;

import be.tftic.spring.demo.domain.entity.Post;
import lombok.Data;

@Data
public class PostCreateForm {

    private String title;
    private String content;
    private long userId;
    private long topicId;

    public Post mapToEntity(){
        Post post = new Post();
        post.setTitle( this.title );
        post.setContent( this.content );
        return post;
    }

}
