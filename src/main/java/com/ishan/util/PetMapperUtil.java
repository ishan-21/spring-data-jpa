package com.ishan.util;

import com.ishan.dto.DomesticPetDTO;
import com.ishan.dto.OwnerDTO;
import com.ishan.dto.PetDTO;
import com.ishan.dto.WildPetDTO;
import com.ishan.entity.DomesticPet;
import com.ishan.entity.Owner;
import com.ishan.entity.Pet;
import com.ishan.entity.WildPet;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@Deprecated
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PetMapperUtil {

	private static final String UNSUPPORTED_PET_INSTANCE = "Unsupported pet instance: %s";

	public static PetDTO petToPetDTO(Pet pet) {
		return switch (pet) {
		case DomesticPet domesticPet -> domesticPetToDomesticPetDTO(domesticPet);
		case WildPet wildPet -> wildPetToWildPetDTO(wildPet);
		default -> throw new IllegalArgumentException(String.format(UNSUPPORTED_PET_INSTANCE, pet.getClass()));
		};
	}

	private static DomesticPetDTO domesticPetToDomesticPetDTO(DomesticPet domesticPet) {
		OwnerDTO ownerDTO = ownerToOwnerDTOWithoutPet(domesticPet.getOwner());
		return DomesticPetDTO.builder()
				.id(domesticPet.getId())
				.name(domesticPet.getName())
				.gender(domesticPet.getGender())
				.type(domesticPet.getType())
				.birthDate(domesticPet.getBirthDate())
				.ownerDTO(ownerDTO)
				.build();
	}

	private static WildPetDTO wildPetToWildPetDTO(WildPet wildPet) {
		OwnerDTO ownerDTO = ownerToOwnerDTOWithoutPet(wildPet.getOwner());
		return WildPetDTO.builder()
				.id(wildPet.getId())
				.name(wildPet.getName())
				.gender(wildPet.getGender())
				.type(wildPet.getType())
				.birthPlace(wildPet.getBirthPlace())
				.ownerDTO(ownerDTO)
				.build();
	}

	private static OwnerDTO ownerToOwnerDTOWithoutPet(Owner owner) {
		return OwnerDTO.builder()
				.id(owner.getId())
				.firstName(owner.getFirstName())
				.lastName(owner.getLastName())
				.gender(owner.getGender())
				.city(owner.getCity())
				.state(owner.getState())
				.mobileNumber(owner.getMobileNumber())
				.emailId(owner.getEmailId())
				.build();
	}

}
