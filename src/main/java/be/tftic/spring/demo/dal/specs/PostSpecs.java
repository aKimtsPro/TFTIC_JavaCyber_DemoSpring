package be.tftic.spring.demo.dal.specs;


import be.tftic.spring.demo.domain.entity.Post;
import be.tftic.spring.demo.domain.entity.Post_;
import be.tftic.spring.demo.domain.entity.User_;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public abstract class PostSpecs {

    public static Specification<Post> recent(){
        return (r,q,cb) -> cb.greaterThan(r.get(Post_.createdAt), LocalDateTime.now().minusMonths(1));
    }

    public static Specification<Post> active(boolean isActive){
        return (r,q,cb) -> cb.greaterThan(r.get("active"), isActive);
    }

    public static Specification<Post> posterActive(boolean isActive){
        return (r,q,cb) -> cb.equal(r.join(Post_.createdBy).get("active"), isActive);
    }

    public static Specification<Post> forUser(long id){
        return (r,q,cb) -> cb.equal(r.join(Post_.createdBy).get(User_.id), id);
    }

    public static Specification<Post> forUser(String username){
        return (r,q,cb) -> cb.equal(r.join(Post_.createdBy).get(User_.username), username);
    }

}
