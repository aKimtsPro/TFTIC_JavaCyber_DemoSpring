package be.tftic.spring.demo.dal;

import be.tftic.spring.demo.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
