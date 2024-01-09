package be.tftic.spring.demo.api.model.form;

import be.tftic.spring.demo.domain.entity.Post;
import lombok.Data;

@Data
public class PostUpdateForm {


    private String title;
    private String content;

    public Post mapToEntity(){
        Post post = new Post();
        post.setTitle( this.title );
        post.setContent( this.content );
        return post;
    }

}
