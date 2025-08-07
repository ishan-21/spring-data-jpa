package com.ishan.service.impl;

import java.util.List;
import com.ishan.entity.Owner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ishan.dto.OwnerDTO;
import com.ishan.exception.OwnerNotFoundException;
import com.ishan.repository.OwnerRepository;
import com.ishan.service.OwnerService;
import com.ishan.util.OwnerMapper;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class OwnerServiceImpl implements OwnerService {

	private final OwnerRepository ownerRepository;
	private final OwnerMapper ownerMapper;
	@Value("${owner.not.found}")
	private String ownerNotFound;

	@Override
	public OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException {
		return ownerRepository.findById(ownerId).map(ownerMapper::ownerToOwnerDTO)
				.orElseThrow(() -> new OwnerNotFoundException(String.format(ownerNotFound, ownerId)));
	}

	@Override
	public void updatePetDetails(int ownerId, String petName) throws OwnerNotFoundException {
		Owner owner = ownerRepository.findById(ownerId)
				.orElseThrow(() -> new OwnerNotFoundException(String.format(ownerNotFound, ownerId)));
		owner.getPet().setName(petName);
		ownerRepository.save(owner);
	}

	@Override
	public void updatePetDetailsV2(int ownerId, String petName) {
	 	ownerRepository.updatePetDetails(ownerId, petName);
	}

	@Override
	public void deleteOwner(int ownerId) throws OwnerNotFoundException {
		Owner owner = ownerRepository.findById(ownerId)
				.orElseThrow(() -> new OwnerNotFoundException(String.format(ownerNotFound, ownerId)));
		ownerRepository.delete(owner);
	}

	@Override
	public void deleteOwners(List<Integer> ownerIds) {
		List<Owner> owners = ownerRepository.findAllById(ownerIds);
		ownerRepository.deleteAll(owners);
	}

	@Override
	public void deleteOwnersV2(List<Integer> ownerIds) {
		ownerRepository.deleteByIds(ownerIds);
	}
}
