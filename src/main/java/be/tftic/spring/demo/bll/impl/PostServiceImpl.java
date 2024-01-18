package be.tftic.spring.demo.bll.impl;

import be.tftic.spring.demo.bll.PostService;
import be.tftic.spring.demo.bll.exceptions.EntityNotFoundException;
import be.tftic.spring.demo.dal.PostRepository;
import be.tftic.spring.demo.dal.specs.PostSpecs;
import be.tftic.spring.demo.domain.entity.Post;
import be.tftic.spring.demo.utils.search.SearchCriteria;
import be.tftic.spring.demo.utils.specifications.PredicateJoiner;
import be.tftic.spring.demo.utils.specifications.SpecJoiner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
                .orElseThrow(() -> new EntityNotFoundException(id, Post.class));
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

    @Override
    public List<Post> getByCreator(String username) {
        return postRepository.findByCreatorName(username);
    }

    @Override
    public List<Post> getByCreatorForLastMonth(String username) {
        return postRepository.findAll(
                Specification.allOf(
                        PostSpecs.recent(),
                        PostSpecs.forUser(username)
                )
        );
    }

    @Override
    public Page<Post> getFiltered(
            Map<String, Object> equalities,
            int page,
            int pageSize
    ) {
        SpecJoiner<Post> joint = SpecJoiner.andJoint(Post.class);
        equalities.forEach(joint::equals);
        return postRepository.findAll(
                joint.build(),
                Pageable.ofSize(pageSize)
                        .withPage(page)
        );
    }

    @Override
    public Page<Post> getRecentActive(int page, int pageSize) {
        return postRepository.findAll(
                SpecJoiner.andJoint(Post.class)
                        .spec(PostSpecs.active(true))
                        .spec(PostSpecs.posterActive(true))
                        .spec(PostSpecs.recent())
                        .build(),
                Pageable.ofSize(pageSize).withPage(page-1)
        );
    }

    @Override
    public Page<Post> getFromCriterias(Collection<SearchCriteria<Post>> criterias){
        return getFromCriterias(criterias, PredicateJoiner.AND, 200,1);
    }

    @Override
    public Page<Post> getFromCriterias(
            Collection<SearchCriteria<Post>> criterias,
            PredicateJoiner joiner,
            int pageSize,
            int page
    ) {
        Specification<Post> spec = SpecJoiner.joint(joiner,Post.class)
                .criterias(criterias)
                .build();
        Pageable pageable = Pageable.ofSize(pageSize)
                .withPage(page-1);

        return postRepository.findAll(spec, pageable);
    }
}
