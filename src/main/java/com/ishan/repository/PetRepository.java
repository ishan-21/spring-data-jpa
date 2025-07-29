package com.ishan.repository;


import com.ishan.entity.Pet;
import com.ishan.enums.Gender;
import com.ishan.enums.PetType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Integer> {
    // These methods follow Spring Data JPA's naming convention, where the method name is parsed to generate the appropriate JPQL query. The framework handles the implementation of these methods at runtime.
	List<Pet> findByName(String name);
    List<Pet> readByType(PetType type);
    List<Pet> getByGender(Gender gender);
}
