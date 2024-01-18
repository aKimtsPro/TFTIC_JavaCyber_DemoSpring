package be.tftic.spring.demo.bll;

import be.tftic.spring.demo.domain.entity.Post;
import be.tftic.spring.demo.utils.search.SearchCriteria;
import be.tftic.spring.demo.utils.specifications.PredicateJoiner;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface PostService {

    List<Post> getAll();
    Post getOne(long id);

    Post create(Post post);

    Post update(Post post);

    Post delete(long id);

    List<Post> getByCreator(String username);

    List<Post> getByCreatorForLastMonth(String username);

    Page<Post> getFiltered(
        Map<String, Object> filters,
        int page,
        int pageSize
    );

    Page<Post> getRecentActive(
            int page,
            int pageSize
    );

    Page<Post> getFromCriterias(Collection<SearchCriteria<Post>> criterias);
    Page<Post> getFromCriterias(Collection<SearchCriteria<Post>> criterias, PredicateJoiner joiner, int pageSize, int page);
}
