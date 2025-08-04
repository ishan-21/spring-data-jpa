package com.ishan.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import com.ishan.entity.Owner;
import com.ishan.repository.CustomOwnerRepository;
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

	@Override
	public OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException {
		Owner owner = ownerRepository.findById(ownerId);
		if(Objects.isNull(owner)) {
			throw new OwnerNotFoundException(String.format(ownerNotFound, ownerId));
		}
		return ownerMapper.ownerToOwnerDTO(owner);
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
		Owner owner = ownerRepository.findByPetId(petId);
		if(Objects.isNull(owner)) {
			throw new OwnerNotFoundException(String.format(ownerPetNotFound, petId));
		}
		return ownerMapper.ownerToOwnerDTO(owner);
	}

	@Override
	public List<OwnerDTO> findByAllOwnersByPetDateOfBirthBetween(LocalDate startDate, LocalDate endDate) {
		return ownerRepository.findByPetDateOfBirthBetween(startDate, endDate)
				.stream()
				.map(ownerMapper::ownerToOwnerDTO)
				.toList();
	}

	@Override
	public List<Object[]> findIdAndFirstNameAndLastNameAndPetNameOfPaginatedOwners(int pageNumber, int pageSize) {
		return ownerRepository.findIdAndFirstNameAndLastNameAndPetName(pageNumber,pageSize);
	}

}
