package com.app.pet.care.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.pet.care.model.User;
import com.app.pet.care.model.UserTimeline;
import com.app.pet.care.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET, path = "/user/{email}/{password}")
	@CrossOrigin
	public ResponseEntity<Long> validateUser(@PathVariable String email, @PathVariable String password)
			throws Exception {
		return new ResponseEntity<Long>(userService.validateUser(email, password), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/user/email/{email}/")
	@CrossOrigin
	public ResponseEntity<Set<User>> findAllContacts(@PathVariable String email) throws Exception {
		return new ResponseEntity<>(userService.findAllcontacts(email), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/user/userid/{userId}/")
	@CrossOrigin
	public ResponseEntity<Set<User>> findAllContactsByUserId(@PathVariable Long userId) throws Exception {
		return new ResponseEntity<>(userService.findAllcontactsByUserId(userId), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/user/timeline/{userId}/")
	@CrossOrigin
	public ResponseEntity<List<UserTimeline>> getUserTimeLine(@PathVariable Long userId) throws Exception {
		return new ResponseEntity<>(userService.getUserTimeLine(userId), HttpStatus.OK);
	}

	@PostMapping("/user")
	@CrossOrigin
	public ResponseEntity<String> addUser(@RequestBody User user) {
		if (userService.addUser(user)) {
			return new ResponseEntity<String>("Success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Failure", HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/user/friend")
	@CrossOrigin
	public ResponseEntity<String> addFriend(@RequestParam long userId, @RequestParam long contactId) throws Exception {
		if (userService.addFriend(userId, contactId)) {
			return new ResponseEntity<String>("Friend Request Sent", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Contact not found", HttpStatus.BAD_REQUEST);
	}
}
