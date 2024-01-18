package be.tftic.spring.demo.dal.specs;


import be.tftic.spring.demo.domain.entity.Post;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public abstract class PostSpecs {

    public static Specification<Post> recent(){
        return (r,q,cb) -> cb.greaterThan(r.get("createdAt"), LocalDateTime.now().minusMonths(1));
    }

    public static Specification<Post> active(boolean isActive){
        return (r,q,cb) -> cb.greaterThan(r.get("active"), isActive);
    }

    public static Specification<Post> posterActive(boolean isActive){
        return (r,q,cb) -> cb.equal(r.join("createdBy").get("active"), isActive);
    }

    public static Specification<Post> forUser(long id){
        return (r,q,cb) -> cb.equal(r.join("createdBy").get("id"), id);
    }

    public static Specification<Post> forUser(String username){
        return (r,q,cb) -> cb.equal(r.join("createdBy").get("username"), username);
    }

}
