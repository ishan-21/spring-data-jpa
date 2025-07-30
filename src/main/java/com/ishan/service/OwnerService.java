package com.ishan.service;

import java.time.LocalDate;
import java.util.List;
import com.ishan.dto.OwnerDTO;
import com.ishan.exception.OwnerNotFoundException;


public interface OwnerService {

	OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException;

	List<OwnerDTO> findAllOwnersByFirstNameInitials(String firstName);

	OwnerDTO findOwnerByPetId(int petId) throws OwnerNotFoundException;

    List<OwnerDTO> findByAllOwnersByPetDateOfBirthBetween(LocalDate startDate, LocalDate endDate);

	List<Object[]> findIdAndFirstNameAndLastNameAndPetNameOfPaginatedOwners(int i, int pageSize);
}
