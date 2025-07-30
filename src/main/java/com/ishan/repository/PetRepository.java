package com.ishan.repository;

import com.ishan.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Integer> {

    @Query("select p from Pet p join fetch p.owner where p.id = :petId")
    Optional<Pet> getById(int petId);

    @Query("SELECT AVG(YEAR(CURRENT_DATE()) - YEAR(p.birthDate)) FROM Pet p")
    double findAverageAgeOfPets();
}
