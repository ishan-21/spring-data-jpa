package com.ishan.repository;


import com.ishan.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Integer> {
	// all CRUD operations are already provided by JpaRepository
}
