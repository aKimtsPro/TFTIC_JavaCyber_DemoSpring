package be.tftic.spring.demo.dal;

import be.tftic.spring.demo.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {}
