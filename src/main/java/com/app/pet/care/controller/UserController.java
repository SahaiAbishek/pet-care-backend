package com.app.pet.care.controller;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.pet.care.model.Picture;
import com.app.pet.care.model.User;
import com.app.pet.care.model.UserTimeline;
import com.app.pet.care.service.UserService;

@RestController
public class UserController {

	private Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET, path = "/user/{email}/{password}")
	@CrossOrigin
	public ResponseEntity<Long> validateUser(@PathVariable String email, @PathVariable String password)
			throws Exception {
		log.debug("Indide Validate User..");
		return new ResponseEntity<Long>(userService.validateUser(email, password), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/user/email/{email}/")
	@CrossOrigin
	public ResponseEntity<Set<User>> findAllContacts(@PathVariable String email) throws Exception {
		log.debug("Inside findAllContacts....");
		return new ResponseEntity<>(userService.findAllcontacts(email), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/user/{userId}/")
	@CrossOrigin
	public ResponseEntity<User> getUserDetails(@PathVariable Long userId) throws Exception {
		log.debug("Inside findAllContacts....");
		return new ResponseEntity<>(userService.getUserDetails(userId), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/user/userid/{userId}/")
	@CrossOrigin
	public ResponseEntity<Set<User>> findAllContactsByUserId(@PathVariable Long userId) throws Exception {
		log.debug("Inside findAllContactsByUserId....");
		return new ResponseEntity<>(userService.findAllcontactsByUserId(userId), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/user/timeline/{userId}/")
	@CrossOrigin
	public ResponseEntity<List<UserTimeline>> getUserTimeLine(@PathVariable Long userId) throws Exception {
		log.debug("Inside getUserTimeLine....");
		return new ResponseEntity<>(userService.getUserTimeLine(userId), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/user/zip/{zipCode}/user/{userId}")
	@CrossOrigin
	public ResponseEntity<List<User>> getUsersByZip(@PathVariable String zipCode, @PathVariable Long userId)
			throws Exception {
		log.debug("Inside getUsersByZip....");
		List<User> users = userService.findAllUsersByZip(zipCode, userId);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@PostMapping("/user")
	@CrossOrigin
	public ResponseEntity<String> addUser(@RequestBody User user) throws Exception {
		log.debug("Inside addUser....");
		if (userService.addUser(user)) {
			log.debug("Add user successful");
			return new ResponseEntity<String>("Success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Failure", HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/user/pic")
	@CrossOrigin
	public ResponseEntity<String> addUserwithPic(@RequestParam String email, @RequestParam String password,
			@RequestParam String firstName, @RequestParam String middleName, @RequestParam String lastName,
			@RequestParam String zip, @RequestParam Boolean isProfilePic,
			@RequestParam(required = false, name = "file") MultipartFile file) throws Exception {
		log.debug("Inside addUserwithPic....");
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setMiddleName(middleName);
		user.setZip(zip);
		Picture picture = new Picture();
		picture.setName(file.getOriginalFilename());
		picture.setPic(file.getBytes());
		picture.setPicSize(file.getSize());
		picture.setPicType(file.getContentType());
		picture.setIsProfilePic(isProfilePic);
		user.setProfilePic(picture);
		if (userService.addUser(user)) {
			log.debug("Add user successful");
			return new ResponseEntity<String>("Success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Failure", HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/user/pic/userId")
	@CrossOrigin
	public ResponseEntity<String> updateUser(@RequestParam Long userId, @RequestParam String email,
			@RequestParam String password, @RequestParam String firstName, @RequestParam String middleName,
			@RequestParam String lastName, @RequestParam String zip, @RequestParam Long picId,
			@RequestParam Boolean isProfilePic, @RequestParam(required = false, name = "file") MultipartFile file)
			throws Exception {
		log.debug("Inside updateUser....");

		User user = new User();
		user.setUserId(userId);
		user.setEmail(email);
		// user.setPassword("test123");
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setMiddleName(middleName);
		user.setZip(zip);
		if (file != null) {
			Picture picture = new Picture();
			if (picId != -1)
				picture.setPicId(picId);
			picture.setName(file.getOriginalFilename());
			picture.setPic(file.getBytes());
			picture.setPicSize(file.getSize());
			picture.setPicType(file.getContentType());
			picture.setIsProfilePic(isProfilePic);
			user.setProfilePic(picture);
		}
		if (userService.updateUser(user)) {
			log.debug("Add user successful");
			return new ResponseEntity<String>("Success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Failure", HttpStatus.BAD_REQUEST);
	}

	@PutMapping("/user/friend")
	@CrossOrigin
	public ResponseEntity<String> addFriend(@RequestParam long userId, @RequestParam long contactId) throws Exception {
		log.debug("Inside addFriend....");
		try {
			if (userService.addFriend(userId, contactId)) {
				log.debug("Friend Request Sent......");
				return new ResponseEntity<String>("Friend Request Sent", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("Contact not found", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception ex) {
			return new ResponseEntity<String>("Contact not found " + ex.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}
}
