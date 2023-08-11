package com.project.BE_banjjokee.repository;

import com.project.BE_banjjokee.model.Comment;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public void save(Comment comment) {
        em.persist(comment);
    }

    public Optional<Comment> findById(Long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    public List<Comment> findByPostId(Long postId) {
        return em.createQuery(
                        "select c from Comment c" +
                                " join c.post p" +
                                " where p.id = :postId", Comment.class)
                .setParameter("postId", postId)
                .getResultList();
    }

    public List<Comment> findAllByEmail(String email) {
        return em.createQuery(
                        "select c from Comment c" +
                                " join c.writer u" +
                                " where u.email = :email", Comment.class)
                .setParameter("email", email)
                .getResultList();
    }

}
