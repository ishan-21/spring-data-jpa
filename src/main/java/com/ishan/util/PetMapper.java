package com.ishan.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.ishan.dto.DomesticPetDTO;
import com.ishan.dto.PetDTO;
import com.ishan.dto.WildPetDTO;
import com.ishan.entity.DomesticPet;
import com.ishan.entity.Pet;
import com.ishan.entity.WildPet;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PetMapper {

	String UNSUPPORTED_PET_INSTANCE = "Unsupported pet instance: %s";

	default PetDTO petToPetDTO(Pet pet) {
		return switch (pet) {
		case DomesticPet domesticPet -> domesticPetToDomesticPetDTO(domesticPet);
		case WildPet wildPet -> wildPetToWildPetDTO(wildPet);
		default -> throw new IllegalArgumentException(String.format(UNSUPPORTED_PET_INSTANCE, pet.getClass()));
		};
	}

	@Mapping(target = "ownerDTO.petDTO", ignore = true)
	@Mapping(source = "owner", target = "ownerDTO")
	DomesticPetDTO domesticPetToDomesticPetDTO(DomesticPet domesticPet);

	@Mapping(target = "ownerDTO.petDTO", ignore = true)
	@Mapping(source = "owner", target = "ownerDTO")
	WildPetDTO wildPetToWildPetDTO(WildPet wildPet);

}
