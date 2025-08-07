package com.ishan.service;

import java.time.LocalDate;
import java.util.List;
import com.ishan.dto.OwnerDTO;
import com.ishan.exception.OwnerNotFoundException;


public interface OwnerService {

	OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException;


	void updatePetDetails(int ownerId, String petName) throws OwnerNotFoundException;

	void updatePetDetailsV2(int ownerId, String petName);

	void deleteOwner(int ownerId) throws OwnerNotFoundException;

	void deleteOwners(List<Integer> ownerIds);

	void deleteOwnersV2(List<Integer> ownerIds);

	void deleteOwnersV3(List<Integer> ownerIds) throws OwnerNotFoundException;
}
