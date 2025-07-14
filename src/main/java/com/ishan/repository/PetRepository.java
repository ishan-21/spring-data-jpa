package com.ishan.repository;

import java.util.Optional;

import com.ishan.entity.Pet;


public interface PetRepository {
	
	Optional<Pet> findById(int petId);
	
}
