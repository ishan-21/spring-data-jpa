package com.ishan.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.ishan.repository.PetRepository;

class PetServiceImplTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetServiceImpl petService;

    public PetServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_FindAverageAgeOfPet_WhenPetsExist_ShouldReturnAverageAge() {
        // Given
        Double expectedAverageAge = 5.0;
        when(petRepository.findAverageAgeOfPet()).thenReturn(Optional.of(expectedAverageAge));

        // When
        Double actualAverageAge = petService.findAverageAgeOfPet();

        // Then
        assertEquals(expectedAverageAge, actualAverageAge);
        verify(petRepository, times(1)).findAverageAgeOfPet();
    }

    @Test
    void test_FindAverageAgeOfPet_WhenPetsDoNotExist_ShouldReturnZero() {
        // Given
        Double expectedAverageAge = 0.0;
        when(petRepository.findAverageAgeOfPet()).thenReturn(Optional.empty());

        // When
        Double actualAverageAge = petService.findAverageAgeOfPet();

        // Then
        assertEquals(expectedAverageAge, actualAverageAge);
        verify(petRepository, times(1)).findAverageAgeOfPet();
    }
}