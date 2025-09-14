package com.ishan.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.ishan.entity.Owner;
import com.ishan.exception.OwnerNotFoundException;
import com.ishan.repository.OwnerRepository;
import com.ishan.service.OwnerService;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class OwnerServiceImpl implements OwnerService {

	private final OwnerRepository ownerRepository;
	@Value("${owner.not.found}")
	private String ownerNotFound;

	@Override
	public void updatePetDetails(int ownerId, String petName) throws OwnerNotFoundException {
		Owner owner = ownerRepository.findById(ownerId)
				.orElseThrow(() -> new OwnerNotFoundException(String.format(ownerNotFound, ownerId)));
		owner.getPet().setName(petName);
		ownerRepository.save(owner);
	}

	@Override
	public List<Object[]> findIdAndFirstNameAndLastNameAndPetNameOfPaginatedOwners(int pageNumber,
			int numberOfRecordsPerPage) {
		Pageable pageable = PageRequest.of(pageNumber, numberOfRecordsPerPage);
		return ownerRepository.findIdAndFirstNameAndLastNameAndPetName(pageable);
	}

}
