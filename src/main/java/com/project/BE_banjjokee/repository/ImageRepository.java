package com.project.BE_banjjokee.repository;

import com.project.BE_banjjokee.model.Image;
import com.project.BE_banjjokee.model.Pet;
import com.project.BE_banjjokee.model.PetImage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ImageRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Image image) {
        em.persist(image);
    }

    public Optional<Image> findById(Long id) {
        return Optional.ofNullable(em.find(Image.class, id));
    }

    public List<Image> findAll() {
        return em.createQuery("select i from Image i", Image.class)
                .getResultList();
    }

    public Optional<PetImage> findByPetId(Long petId) {
        return Optional.ofNullable(
                em.createQuery(
                        "select pi from PetImage pi" +
                                " join pi.pet p" +
                                " where p.id = :petId", PetImage.class)
                .setParameter("petId", petId)
                .getSingleResult());
    }

    public void deleteImage(Image image) {
        em.remove(image);
    }

}
