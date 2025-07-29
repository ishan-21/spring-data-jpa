package com.ishan.repository;

import com.ishan.entity.Owner;
import com.ishan.enums.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Integer> {
    
    /**
     * Updates the pet name for a given owner
     * @param ownerId The ID of the owner whose pet details need to be updated
     * @param petName The new name for the pet
     */
    @Modifying
    @Query("UPDATE Owner o SET o.pet.name = :petName WHERE o.id = :ownerId")
    @Transactional
    void updatePetDetails(@Param("ownerId") int ownerId, @Param("petName") String petName);


    // These methods follow Spring Data JPA's naming convention, where the method name is parsed to generate the appropriate JPQL query. The framework handles the implementation of these methods at runtime.
    List<Owner> findByFirstName(String argument);
    List<Owner> findByFirstNameStartingWith(String argument);
    List<Owner> findByLastName(String argument);
    List<Owner> findByGender(Gender gender);
    List<Owner> findByCity(String city);
    List<Owner> findByState(String state);
    Optional<Owner> findByMobileNumber(String mobileNumber);
    Optional<Owner> findByEmailId(String emailId);

    // Other methods like findById, deleteById, and findAll are already provided by JpaRepository
}
