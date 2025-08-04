package com.ishan.repository;

import com.ishan.entity.Pet;

public interface CustomPetRepository {

    Pet findById(int petId);

    double findAverageAgeOfPets();
}
