package com.ishan.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;



@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OwnerMapper { 

	String UNSUPPORTED_PET_INSTANCE = "Unsupported pet instance: %s";
	
	@Mapping(source = "pet", target = "petDTO")
	com.ishan.dto.OwnerDTO ownerToOwnerDTO(com.ishan.entity.Owner owner);

	default com.ishan.dto.PetDTO petToPetDTO(com.ishan.entity.Pet pet) {
		return switch (pet) {
		case com.ishan.entity.DomesticPet domesticPet -> domesticPetToDomesticPetDTO(domesticPet);
		case com.ishan.entity.WildPet wildPet -> wildPetToWildPetDTO(wildPet);
		default -> throw new IllegalArgumentException(String.format(UNSUPPORTED_PET_INSTANCE, pet.getClass()));
		};
	}

	@Mapping(target = "ownerDTO", ignore = true)
	com.ishan.dto.DomesticPetDTO domesticPetToDomesticPetDTO(com.ishan.entity.DomesticPet domesticPet);

	@Mapping(target = "ownerDTO", ignore = true)
	com.ishan.dto.WildPetDTO wildPetToWildPetDTO(com.ishan.entity.WildPet wildPet);

}
