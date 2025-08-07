package com.ishan.repository;

import com.ishan.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface OwnerRepository extends JpaRepository<Owner, Integer> {
    @Query("UPDATE Owner o SET o.pet.name = :petName WHERE o.id = :ownerId") // Only this @Query annotation is sufficient for reading the database
    @Transactional // This annotation is necessary for modifying queries => queries which change the state of the database
    @Modifying(flushAutomatically = true, clearAutomatically = true) // This annotation is necessary for modifying queries => queries which change the state of the database
    void updatePetDetails(int ownerId, String petName);

    @Query("DELETE FROM Owner o WHERE o.id IN :ownerIds")
    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    void deleteByIds(List<Integer> ownerIds);
}
