package com.ishan.service;

import java.util.List;

import com.ishan.dto.OwnerDTO;
import com.ishan.exception.OwnerNotFoundException;


public interface OwnerService {
	
	void saveOwner(OwnerDTO ownerDTO);

	OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException;

	void deleteOwner(int ownerId) throws OwnerNotFoundException;

	List<OwnerDTO> findAllOwners();

	List<OwnerDTO> findAllOwnersByFirstNameInitials(String firstName);

	OwnerDTO findOwnerByPetId(int petId) throws OwnerNotFoundException;
}
