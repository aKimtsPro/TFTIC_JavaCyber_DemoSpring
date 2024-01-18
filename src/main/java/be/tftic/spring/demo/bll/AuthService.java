package be.tftic.spring.demo.bll;

import be.tftic.spring.demo.domain.entity.User;

public interface AuthService {

    User login(String username, String password);
    User register(User user);

}
