package com.ishan.service.impl;

import java.util.List;

import com.ishan.repository.OwnerRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import com.ishan.dto.OwnerDTO;
import com.ishan.service.OwnerService;
import com.ishan.util.OwnerMapper;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class OwnerServiceImpl implements OwnerService {

	private final OwnerRepository ownerRepository;
	private final OwnerMapper ownerMapper;

	@Override
	public List<OwnerDTO> findAllOwners() {
		return ownerRepository.findAll() 
				.stream()
				.map(ownerMapper::ownerToOwnerDTO)
				.toList();
	}

	@Override
	public List<OwnerDTO> findAllSortedOwners(String sortingParameter, boolean sortDescending) {
		Direction direction = sortDescending ? Direction.DESC : Direction.ASC;
		Sort sort = Sort.by(direction, sortingParameter);
		return ownerRepository.findAll(sort)
				.stream()
				.map(ownerMapper::ownerToOwnerDTO)
				.toList();
	}

	@Override
	public List<OwnerDTO> findAllPaginatedOwners(int pageNumber, int numberOfRecordsPerPage) {
		Pageable pageable = PageRequest.of(pageNumber, numberOfRecordsPerPage);
		return ownerRepository.findAll(pageable)
				.stream()
				.map(ownerMapper::ownerToOwnerDTO)
				.toList();
	}

	@Override
	public List<OwnerDTO> findAllPaginatedAndSortedOwners(int pageNumber, int numberOfRecordsPerPage,
			String sortingParameter, boolean sortDescending) {
		Direction direction = sortDescending ? Direction.DESC : Direction.ASC;
		Sort sort = Sort.by(direction, sortingParameter);
		Pageable pageable = PageRequest.of(pageNumber, numberOfRecordsPerPage, sort);
		return ownerRepository.findAll(pageable)
				.stream()
				.map(ownerMapper::ownerToOwnerDTO)
				.toList();
	}

}
