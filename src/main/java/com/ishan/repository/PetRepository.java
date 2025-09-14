package com.ishan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ishan.entity.Pet;


public interface PetRepository extends JpaRepository<Pet, Integer> {
	
	@Query("SELECT AVG(YEAR(CURRENT_DATE()) - YEAR(p.birthDate)) FROM Pet p")
	Optional<Double> findAverageAgeOfPet();

}
