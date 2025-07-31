package com.ishan.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ishan.dto.PetDTO;
import com.ishan.exception.PetNotFoundException;
import com.ishan.repository.PetRepository;
import com.ishan.service.PetService;
import com.ishan.util.PetMapper;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class PetServiceImpl implements PetService {

	private final PetRepository petRepository;
	private final PetMapper petMapper;
	@Value("${pet.not.found}")
	private String petNotFound;

	@Override
	public PetDTO findPet(int petId) throws PetNotFoundException {
		return petRepository.getById(petId)
				.map(petMapper::petToPetDTO)
				.orElseThrow(() -> new PetNotFoundException(String.format(petNotFound, petId)));
	}

	@Override
	public double findAverageAgeOfPets() {
		return petRepository.findAverageAgeOfPets();
	}

}
