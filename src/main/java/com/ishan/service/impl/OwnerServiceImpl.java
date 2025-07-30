package com.ishan.service.impl;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
	@Value("${owner.pet.not.found}")
	private String ownerPetNotFound;


	public OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException {
		return ownerRepository.findById(ownerId)
				.map(ownerMapper::ownerToOwnerDTO)
				.orElseThrow(() -> new OwnerNotFoundException(String.format(ownerNotFound, ownerId)));
	}


	@Override
	public List<OwnerDTO> findAllOwnersByFirstNameInitials(String firstName) {
		return ownerRepository.findByFirstNameStartingWith(firstName)
				.stream()
				.map(ownerMapper::ownerToOwnerDTO)
				.toList();
	}

	@Override
	public OwnerDTO findOwnerByPetId(int petId) throws OwnerNotFoundException {
		return ownerRepository.findByPet_Id(petId)
				.map(ownerMapper::ownerToOwnerDTO)
				.orElseThrow(() -> new OwnerNotFoundException(String.format(ownerPetNotFound, petId)));
	}

	@Override
	public List<OwnerDTO> findByAllOwnersByPetDateOfBirthBetween(LocalDate startDate, LocalDate endDate) {
		return ownerRepository.findByPetDateOfBirthBetween(startDate, endDate)
				.stream()
				.map(ownerMapper::ownerToOwnerDTO)
				.toList();
	}

	@Override
	public List<Object[]> findIdAndFirstNameAndLastNameAndPetNameOfPaginatedOwners(int i, int pageSize) {
		Pageable pageable = PageRequest.of(i, pageSize);
		return ownerRepository.findIdAndFirstNameAndLastNameAndPetName(pageable);
	}

}
