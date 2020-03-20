package com.app.pet.care.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PetController {

	@PostMapping("/pet")
	public boolean addPet(){
		return false;
	}
}
