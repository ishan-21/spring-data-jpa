package com.ishan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Table(name = "wild_pet_table")
@Entity
public class WildPet extends Pet {

	@Column(name = "place_of_birth", nullable = false)
	private String birthPlace;

}
