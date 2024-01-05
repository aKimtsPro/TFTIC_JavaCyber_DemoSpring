package be.tftic.spring.demo.bll.impl;

import be.tftic.spring.demo.bll.UserService;
import be.tftic.spring.demo.dal.UserRepository;
import be.tftic.spring.demo.domain.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getOne(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No user found with this id"));
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public User delete(long id) {
        User user = getOne(id);
        userRepository.delete(user);
        return user;
    }
}
