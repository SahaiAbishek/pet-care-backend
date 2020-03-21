package com.app.pet.care.service;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.pet.care.dao.PetRepo;
import com.app.pet.care.dao.UserRepo;
import com.app.pet.care.entity.PetEntity;
import com.app.pet.care.entity.UserEntity;
import com.app.pet.care.model.Pet;

@Service
public class PetService {

	private Logger log = LoggerFactory.getLogger(PetService.class);

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PetRepo petRepo;

	public boolean addPet(Pet pet, String userName) {
		UserEntity userEntity = userRepo.findByEmail(userName);
		if (userEntity != null) {
			PetEntity petEntity = new PetEntity();
			BeanUtils.copyProperties(pet, petEntity);
			petEntity.setUser(userEntity);
			petRepo.save(petEntity);
			return true;
		} else {
			log.error("User ID :{" + userName + "} is not correct");
			return false;
		}
	}

	public Set<Pet> findAllPets(String userName) throws Exception {
		UserEntity userEntity = userRepo.findByEmail(userName);
		if (userEntity != null) {
			Set<Pet> result = new HashSet<>();
			Set<PetEntity> pets = userEntity.getPets();
			for (PetEntity source : pets) {
				Pet target = new Pet();
				BeanUtils.copyProperties(source, target);
				result.add(target);
			}
			return result;
		} else {
			throw new Exception("No user with User Name : " + userName);
		}

	}
}
