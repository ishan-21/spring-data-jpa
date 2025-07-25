package com.ishan.repository.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.ishan.entity.Pet;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import com.ishan.entity.Owner;
import com.ishan.repository.OwnerRepository;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;


@Repository
public class OwnerRepositoryImpl implements OwnerRepository {

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	@Override
	public void save(Owner owner) {
		try(EntityManager entityManager = entityManagerFactory.createEntityManager()) {
			EntityTransaction entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			entityManager.persist(owner);
			entityTransaction.commit();
		}
	}

	@Override
	public Optional<Owner> findById(int ownerId) {
		try(EntityManager entityManager = entityManagerFactory.createEntityManager()) {
			Owner owner = entityManager.find(Owner.class, ownerId);
			if(Objects.nonNull(owner)) {
				Pet pet = Hibernate.unproxy(owner.getPet(), Pet.class); // initializes the proxy in case of lazy loading
				owner.setPet(pet);
			}
			return Optional.ofNullable(owner);
		}
	}

	@Override
	public void updatePetDetails(int ownerId, String petName) {
		try(EntityManager entityManager = entityManagerFactory.createEntityManager()) {
			Optional<Owner> owner = findById(ownerId);
			if(owner.isPresent()){
				EntityTransaction entityTransaction = entityManager.getTransaction();
				entityTransaction.begin();
				Owner existingOwner = entityManager.merge(owner.get()); // brings the entity into the persistence context IMPORTANT!!!
				Pet pet = existingOwner.getPet();
				pet.setName(petName); // we do not need to merge the pet entity as it is already in the persistence context
				entityTransaction.commit();
			}
		}
	}

	@Override
	public void deleteById(int ownerId) {
		try(EntityManager entityManager = entityManagerFactory.createEntityManager()) {
			Optional<Owner> owner = findById(ownerId);
			if(owner.isPresent()) {
				EntityTransaction entityTransaction = entityManager.getTransaction();
				Owner existingOwner = entityManager.merge(owner.get()); // brings the entity into the persistence context IMPORTANT!!!
				entityTransaction.begin();
				entityManager.remove(existingOwner);
				entityTransaction.commit();
			}
		}
	}

	@Override
	public List<Owner> findAll() {
		String jpqlQuery = "SELECT o FROM Owner o JOIN FETCH o.pet";
		try(EntityManager entityManager = entityManagerFactory.createEntityManager()) {
			List<Owner> owners = entityManager.createQuery(jpqlQuery, Owner.class)
					.getResultList();
			return owners;
		}
	}

}
