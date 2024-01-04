package be.tftic.spring.demo.bll;

import be.tftic.spring.demo.domain.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAll();
    User getOne(long id);

    User create(User user);

    User update(long id, User user);

    User delete(long id);

}
