package com.ishan.repository;

import com.ishan.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Integer>, CustomPetRepository {


}
