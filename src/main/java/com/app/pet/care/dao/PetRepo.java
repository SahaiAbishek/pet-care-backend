package com.app.pet.care.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.pet.care.entity.PetEntity;

@Repository
public interface PetRepo extends CrudRepository<PetEntity, Long> {

}
