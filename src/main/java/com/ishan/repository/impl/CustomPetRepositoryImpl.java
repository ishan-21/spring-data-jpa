package com.ishan.repository.impl;

import com.ishan.entity.DomesticPet;
import com.ishan.entity.Pet;
import com.ishan.repository.CustomPetRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.criteria.*;

import java.time.LocalDate;

public class CustomPetRepositoryImpl implements CustomPetRepository {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Override
    public Pet findById(int petId) {
        try (var entityManager = entityManagerFactory.createEntityManager()) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Pet> criteriaQuery = criteriaBuilder.createQuery(Pet.class);
            Root<Pet> root = criteriaQuery.from(Pet.class);
            root.fetch("owner"); // Eagerly fetch the 'owner' relationship
            Predicate where = criteriaBuilder.conjunction();
            where = criteriaBuilder.and(where, criteriaBuilder.equal(root.get("id"), petId));
            criteriaQuery.select(root).where(where); // select p from Pet p fetch p.owner where p.id = :petId
            return entityManager.createQuery(criteriaQuery).getSingleResult();
        }
    }

    @Override
    public double findAverageAgeOfPets() {
        try(var entityManager = entityManagerFactory.createEntityManager()) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Double> criteriaQuery = criteriaBuilder.createQuery(Double.class);
            Root<Pet> root = criteriaQuery.from(Pet.class);
            Selection<Double> selection = criteriaBuilder.avg(criteriaBuilder.diff(LocalDate.now().getYear(), criteriaBuilder.function("year", Integer.class, root.get("birthDate"))));
            criteriaQuery.select(selection);
            return entityManager.createQuery(criteriaQuery).getSingleResult();
        }
    }
}
