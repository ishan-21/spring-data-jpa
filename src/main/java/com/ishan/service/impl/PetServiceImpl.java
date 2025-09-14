package com.ishan.service.impl;

import org.springframework.stereotype.Service;

import com.ishan.repository.PetRepository;
import com.ishan.service.PetService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class PetServiceImpl implements PetService {

	private final PetRepository petRepository;

	@Override
	public Double findAverageAgeOfPet() {
		return petRepository.findAverageAgeOfPet()
				.orElse(0.0);
	}

}
