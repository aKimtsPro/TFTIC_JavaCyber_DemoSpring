package be.tftic.spring.demo.bll;

import be.tftic.spring.demo.domain.entity.Post;
import be.tftic.spring.demo.domain.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAll();
    User getOne(long id);

    User create(User user);

    User update(User user);

    User delete(long id);

    void addAlias(long userId, String alias);

    List<Post> getLastThreePostsFromUser(String username);

}
