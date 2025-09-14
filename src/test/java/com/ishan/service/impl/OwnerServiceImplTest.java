package com.ishan.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;
import com.ishan.entity.Owner;
import com.ishan.entity.Pet;
import com.ishan.exception.OwnerNotFoundException;
import com.ishan.repository.OwnerRepository;

class OwnerServiceImplTest {

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private OwnerServiceImpl ownerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updatePetDetails() {
        int ownerId = 1;
        String petName = "NewName";
        Owner owner = new Owner();
        Pet pet = new Pet(){};
        pet.setName("OldName");
        owner.setPet(pet);

        when(ownerRepository.findById(ownerId)).thenReturn(Optional.of(owner));

        try {
            ownerService.updatePetDetails(ownerId, petName);
        } catch (OwnerNotFoundException e) {
            throw new RuntimeException(e);
        }

        assertEquals(petName, owner.getPet().getName());
        verify(ownerRepository).save(owner);
    }

    @Test
    void findIdAndFirstNameAndLastNameAndPetNameOfPaginatedOwners() {
        // Given
        int pageNumber = 0;
        int numberOfRecordsPerPage = 5;

        // Create test data - array of objects representing the selected columns
        Object[] testData = new Object[]{1, "John", "Doe", "Buddy"};
        List<Object[]> mockResult = new ArrayList<>();
        mockResult.add(testData);

        // Mock the repository to return the list of Object[]
        when(ownerRepository.findIdAndFirstNameAndLastNameAndPetName(any(Pageable.class)))
                .thenReturn(mockResult);

        // When
        List<Object[]> result = ownerService.findIdAndFirstNameAndLastNameAndPetNameOfPaginatedOwners(
                pageNumber, numberOfRecordsPerPage);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertArrayEquals(testData, result.get(0));
    }
}