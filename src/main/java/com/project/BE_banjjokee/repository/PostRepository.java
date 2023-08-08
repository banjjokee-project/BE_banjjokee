package com.project.BE_banjjokee.repository;

import com.project.BE_banjjokee.model.Post;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    public void save(Post post) {
        em.persist(post);
    }

    public List<Post> findPostsWithOffsetAndLimit(int offset, int limit) {
        return em.createQuery("select p from Post p", Post.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public Optional<Post> findById(long id) {
        return Optional.ofNullable(em.find(Post.class, id));
    }

    public List<Post> findByEmail(String email) {
        return em.createQuery(
                        "select p from Post p" +
                                " join p.writer u" +
                                " where u.email = :email", Post.class)
                .setParameter("email", email)
                .getResultList();
    }

}
