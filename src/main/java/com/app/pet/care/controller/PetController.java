package com.app.pet.care.controller;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.pet.care.model.Pet;
import com.app.pet.care.service.PetService;

@RestController
public class PetController {
	
	private Logger log = LoggerFactory.getLogger(PetController.class);

	@Autowired
	private PetService petService;

	@PostMapping("/pet/UserName")
	@CrossOrigin
	public ResponseEntity<Boolean> addPetByUserName(@RequestBody Pet pet,
			@RequestParam(name = "userName") String userName) {
		log.debug("Inside addPetByUserName..");
		return new ResponseEntity<>(petService.addPet(pet, userName), HttpStatus.OK);
	}

	@PostMapping("/pet/")
	@CrossOrigin
	public ResponseEntity<Boolean> addPet(@RequestBody Pet pet) {
		log.debug("Inside addPet..");
		return new ResponseEntity<>(petService.addPet(pet), HttpStatus.OK);
	}

	@GetMapping("/pet/user/userName")
	@CrossOrigin
	public ResponseEntity<Set<Pet>> getPetsforUserByUserName(@RequestParam(name = "userName") String userName) throws Exception {
		log.debug("Inside getPetsforUserByUserName..");
		return new ResponseEntity<>(petService.findAllPets(userName), HttpStatus.OK);
	}
	
	@GetMapping("/pet/user/{userId}")
	@CrossOrigin
	public ResponseEntity<Set<Pet>> getPetsforUserById(@PathVariable Long userId) throws Exception {
		log.debug("Inside getPetsforUserById..");
		return new ResponseEntity<>(petService.findAllPetsByUserId(userId), HttpStatus.OK);
	}
}
