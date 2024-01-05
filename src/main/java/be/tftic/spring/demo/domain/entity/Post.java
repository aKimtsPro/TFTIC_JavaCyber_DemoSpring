package be.tftic.spring.demo.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;
    @Column(name= "post_title", nullable = false)
    private String title;
    @Column(name = "post_content", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_created_by", nullable = false, updatable = false)
    private User createdBy;

}
