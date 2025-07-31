package com.ishan.repository;

import com.ishan.entity.Owner;
import com.ishan.enums.Gender;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Integer> {


    // These methods follow Spring Data JPA's naming convention, where the method name is parsed to generate the appropriate JPQL query. The framework handles the implementation of these methods at runtime.
    List<Owner> findByFirstName(String argument);
    List<Owner> findByFirstNameStartingWith(String argument);
    List<Owner> findByLastName(String argument);
    List<Owner> findByGender(Gender gender);
    List<Owner> findByCity(String city);
    List<Owner> findByState(String state);
    Optional<Owner> findByMobileNumber(String mobileNumber);
    Optional<Owner> findByEmailId(String emailId);
    Optional<Owner> findByPet_Id(int petId);



    // Other methods like findById, deleteById, and findAll are already provided by JpaRepository
}
