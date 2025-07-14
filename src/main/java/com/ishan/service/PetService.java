package com.ishan.service;

import com.ishan.dto.PetDTO;
import com.ishan.exception.PetNotFoundException;


public interface PetService {
	
	PetDTO findPet(int petId) throws PetNotFoundException;
	
}
