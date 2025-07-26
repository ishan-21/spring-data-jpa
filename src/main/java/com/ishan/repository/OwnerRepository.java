package com.ishan.repository;

import com.ishan.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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
    
    // Other methods like findById, deleteById, and findAll are already provided by JpaRepository
    
}
