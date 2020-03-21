package com.app.pet.care.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.pet.care.model.Pet;
import com.app.pet.care.service.PetService;

@RestController
public class PetController {

	@Autowired
	private PetService petService;

	@PostMapping("/pet/")
	public ResponseEntity<Boolean> addPet(@RequestBody Pet pet, @RequestParam(name = "userName") String userName) {
		return new ResponseEntity<>(petService.addPet(pet, userName), HttpStatus.OK);
	}

	@GetMapping("/pet/")
	public ResponseEntity<Set<Pet>> getPetsforUser(@RequestParam(name = "userName") String userName) throws Exception {
		return new ResponseEntity<>(petService.findAllPets(userName), HttpStatus.OK);
	}
}
