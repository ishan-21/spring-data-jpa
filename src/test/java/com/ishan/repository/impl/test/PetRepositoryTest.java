package com.ishan.repository.impl.test;

import static com.ishan.enums.Gender.F;
import static com.ishan.enums.Gender.M;
import static com.ishan.enums.PetType.BIRD;
import static com.ishan.enums.PetType.CAT;
import static com.ishan.enums.PetType.DOG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withPrecision;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;

import com.ishan.config.TestConfig;
import com.ishan.entity.DomesticPet;
import com.ishan.repository.PetRepository;
import com.ishan.util.TestDataUtil;


@EntityScan(basePackages = "com.ishan.entity")
@EnableJpaRepositories(basePackageClasses = PetRepository.class)
@ContextConfiguration(classes = TestConfig.class)
@DataJpaTest
class PetRepositoryTest {

	@Autowired
	private PetRepository petRepository;

	@Test
	void test_FindAverageAgeOfPet_WhenPetsExist_ShouldReturnAverageAge() {
		// Given
        // Change these lines in your test:
        DomesticPet domesticPet1 = TestDataUtil.createMockDomesticPet("PetName1", M, BIRD, LocalDate.now().minusYears(5));  // 5 years old
        DomesticPet domesticPet2 = TestDataUtil.createMockDomesticPet("PetName2", F, CAT, LocalDate.now().minusYears(3));   // 3 years old
        DomesticPet domesticPet3 = TestDataUtil.createMockDomesticPet("PetName3", F, DOG, LocalDate.now().minusYears(4));   // 4 years old
		petRepository.save(domesticPet1);
		petRepository.save(domesticPet2);
		petRepository.save(domesticPet3);
		Double expectedAverageAge = Stream
				.of(domesticPet1.getBirthDate(), domesticPet2.getBirthDate(), domesticPet3.getBirthDate())
				.mapToDouble(birthDate -> Period.between(birthDate, LocalDate.now()).getYears()).average().orElse(0.0);
		// When
		Optional<Double> actualAverageAge = petRepository.findAverageAgeOfPet();
		// Then
		assertThat(actualAverageAge).isPresent();
		assertEquals(expectedAverageAge, actualAverageAge.get());
	}

	@Test
	void test_FindAverageAgeOfPet_WhenNoPetsExist_ShouldReturnEmptyOptional() {
		// When
		Optional<Double> actualAverageAge = petRepository.findAverageAgeOfPet();
		// Then
		assertThat(actualAverageAge).isEmpty();
	}

}
