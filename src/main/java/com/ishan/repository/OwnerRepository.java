package com.ishan.repository;

import java.util.List;
import java.util.Optional;

import com.ishan.entity.Owner;


public interface OwnerRepository {
	
	void save(Owner owner);

	Optional<Owner> findById(int ownerId);

	void updatePetDetails(int ownerId, String petName);

	void deleteById(int ownerId);

	List<Owner> findAll();
	
}
