package be.tftic.spring.demo.bll.impl;

import be.tftic.spring.demo.bll.PostService;
import be.tftic.spring.demo.dal.PostRepository;
import be.tftic.spring.demo.domain.entity.Post;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    @Override
    public Post getOne(long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No post found with id " + id));
    }

    @Override
    public Post create(Post post) {
        post.setId(null);
        post.setCreatedAt( LocalDateTime.now() );
        return postRepository.save(post);
    }

    @Override
    public Post update(Post post) {
        post.setLastUpdateAt( LocalDateTime.now() );
        return postRepository.save(post);
    }

    @Override
    public Post delete(long id) {
        Post toDelete = this.getOne(id);
        postRepository.delete(toDelete);
        return toDelete;
    }
}
