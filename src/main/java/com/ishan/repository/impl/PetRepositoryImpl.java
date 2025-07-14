package com.ishan.repository.impl;

import java.util.Optional;

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
		throw new UnsupportedOperationException("Fetching pet by petId is not supported.");
	}

}
