package com.ishan.repository;

import com.ishan.entity.Owner;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;


public interface CustomOwnerRepository {
    Owner findById(int ownerId);

    List<Owner> findByFirstNameStartingWith(String firstName);

    Owner findByPetId(int petId);

    List<Owner> findByPetDateOfBirthBetween(LocalDate startDate, LocalDate endDate);

    List<Object[]> findIdAndFirstNameAndLastNameAndPetName(int pageNumber, int pageSize);
}
