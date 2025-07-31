package com.ishan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ishan.entity.Owner;


public interface OwnerRepository extends JpaRepository<Owner, Integer>{
	
}
