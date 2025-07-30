package com.ishan.repository;

import com.ishan.entity.Owner;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;



public interface OwnerRepository extends JpaRepository<Owner, Integer> {

    @Query("select o from Owner o join fetch o.pet where o.id = :ownerId")
    Optional<Owner> findById(int ownerId);

    @Query("select o from Owner o join fetch o.pet where o.firstName like concat(:firstName, '%')")
    List<Owner> findByFirstNameStartingWith(String firstName);

    @Query("select o from Owner o inner join fetch o.pet p where p.id = :petId")
    Optional<Owner> findByPet_Id(int petId);

    @Query("select o from Owner o join fetch o.pet p where p.birthDate BETWEEN :startDate AND :endDate")
    List<Owner> findByPetDateOfBirthBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT o.id, o.firstName, o.lastName, o.pet.name FROM Owner o JOIN o.pet")
    List<Object[]> findIdAndFirstNameAndLastNameAndPetName(Pageable pageable);
}
