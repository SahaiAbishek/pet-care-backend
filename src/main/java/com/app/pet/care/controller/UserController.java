package com.app.pet.care.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.pet.care.model.User;
import com.app.pet.care.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET, path = "/user/{email}/{password}")
	@CrossOrigin
	public boolean validateUser(@PathVariable String email, @PathVariable String password) {
		return userService.validateUser(email, password);
	}

	@PostMapping("/user")
	public ResponseEntity<String> addUser(@RequestBody User user) {
		if(userService.addUser(user)){
			return new ResponseEntity<String>("Success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Failure", HttpStatus.BAD_REQUEST);
	}
}
