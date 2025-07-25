package com.ishan.repository.impl;

import java.util.Objects;
import java.util.Optional;

import com.ishan.entity.Owner;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import com.ishan.entity.Pet;
import com.ishan.repository.PetRepository;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;


@Repository
public class PetRepositoryImpl implements PetRepository {

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	@Override
	public Optional<Pet> findById(int petId) {
		try(EntityManager entityManager = entityManagerFactory.createEntityManager()) {
			Pet pet = entityManager.find(Pet.class, petId);
			if(Objects.nonNull(pet)){
				Owner owner = Hibernate.unproxy(pet.getOwner(), Owner.class); // initializes the proxy in case of lazy loading
				pet.setOwner(owner);
			}
			return Optional.ofNullable(pet);
		} catch (Exception e) {
			throw new RuntimeException("Error getting pet with id: " + petId, e);
		}
	}

}
