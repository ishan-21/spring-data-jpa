package com.ishan.repository.impl;

import com.ishan.entity.DomesticPet;
import com.ishan.entity.Owner;
import com.ishan.entity.Pet;
import com.ishan.repository.CustomOwnerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public class CustomOwnerRepositoryImpl implements CustomOwnerRepository {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Override
    public Owner findById(int ownerId) {
        try(EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            // 1. Get CriteriaBuilder from EntityManager
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

            // 2. Create a CriteriaQuery specifying the result type
            CriteriaQuery<Owner> criteriaQuery = criteriaBuilder.createQuery(Owner.class);

            // 3. Define the root entity (FROM clause)
            Root<Owner> root = criteriaQuery.from(Owner.class);

            // 4. Eagerly fetch the 'pet' relationship to avoid N+1 problem
            root.fetch("pet");

            // 5. Create a conjunction (AND condition container)
            Predicate where = criteriaBuilder.conjunction();

            // 6. Add condition: where id = ownerId
            where = criteriaBuilder.and(where, criteriaBuilder.equal(root.get("id"), ownerId));

            // 7. Build the final query
            criteriaQuery.select(root).where(where); // select o from Owner o fetch o.pet where o.id = :ownerId

            // 8. Execute query and return single result
            return entityManager.createQuery(criteriaQuery).getSingleResult();
        }
    }

    @Override
    public List<Owner> findByFirstNameStartingWith(String firstName) {
        try(EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Owner> criteriaQuery = criteriaBuilder.createQuery(Owner.class);
            Root<Owner> root = criteriaQuery.from(Owner.class);
            root.fetch("pet"); // Eagerly fetch the 'pet' relationship
            Predicate where = criteriaBuilder.like(root.get("firstName"), firstName + "%");
            criteriaQuery.select(root).where(where); // select o from Owner o where o.firstName like :firstName
            return entityManager.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public Owner findByPetId(int petId) {
        try(EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Owner> criteriaQuery = criteriaBuilder.createQuery(Owner.class);
            Root<Owner> root = criteriaQuery.from(Owner.class);
            root.fetch("pet"); // Eagerly fetch the 'pet' relationship
            Predicate where = criteriaBuilder.equal(root.join("pet").get("id"), petId);
            criteriaQuery.select(root).where(where); // select o from Owner o join fetch o.pet p where p.id = :petId
            return entityManager.createQuery(criteriaQuery).getSingleResult();
        }
    }

    @Override
    public List<Owner> findByPetDateOfBirthBetween(LocalDate startDate, LocalDate endDate) {
        try(EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Owner> criteriaQuery = criteriaBuilder.createQuery(Owner.class);
            Root<Owner> root = criteriaQuery.from(Owner.class);

            // Join to Pet and then treat as DomesticPet
            Join<Owner, Pet> petJoin = root.join("pet");
            Join<Owner, DomesticPet> domesticPetJoin = criteriaBuilder.treat(petJoin, DomesticPet.class);

            // Now you can access DomesticPet specific fields
            Predicate where = criteriaBuilder.between(
                    domesticPetJoin.get("birthDate"),
                    startDate,
                    endDate
            );

            criteriaQuery.select(root).where(where);
            return entityManager.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<Object[]> findIdAndFirstNameAndLastNameAndPetName(int pageNumber, int pageSize) {
        try(EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
            Root<Owner> root = criteriaQuery.from(Owner.class);
            Join<Owner, Pet> petJoin = root.join("pet");

            // Select only the required fields
            criteriaQuery.multiselect(
                    root.get("id"),
                    root.get("firstName"),
                    root.get("lastName"),
                    petJoin.get("name")
            );

            // Apply pagination
            return entityManager.createQuery(criteriaQuery)
                    .setFirstResult((pageNumber-1) * pageSize)
                    .setMaxResults(pageSize)
                    .getResultList();
        }
    }
}
