package be.tftic.spring.demo.dal.custom;

import be.tftic.spring.demo.domain.entity.Comment;
import be.tftic.spring.demo.domain.entity.Comment_;
import be.tftic.spring.demo.domain.entity.User_;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;

// Il faut que le nom soit le meme que l'interface custom mais avec 'Impl' à la fin
public class CustomCommentRepositoryImpl implements CustomCommentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Comment> findByPostIdCustom(Long id) {
        TypedQuery<Comment> query = entityManager.createQuery(""" 
               SELECT c
               FROM Comment c
               WHERE c.aboutPost.id = :postId
            """,
            Comment.class
        );

        query.setParameter("postId", id);

        return query.getResultList();
    }

    @Override
    public Optional<Comment> findByUserUsername(String username) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> query = cb.createQuery(Comment.class);
        Root<Comment> root = query.from(Comment.class);

        root.fetch(Comment_.writtenBy, JoinType.LEFT);
        query = query.where(
                cb.equal(
                        root.get(Comment_.writtenBy).get(User_.username),
                        username
                )
        );

        return Optional.ofNullable(
                entityManager.createQuery(query)
                        .getSingleResult()
        );
    }
}
