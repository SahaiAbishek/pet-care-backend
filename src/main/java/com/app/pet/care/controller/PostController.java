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

import com.app.pet.care.model.Post;
import com.app.pet.care.service.PostService;

@RestController
public class PostController {

	private Logger log = LoggerFactory.getLogger(PostController.class);

	@Autowired
	private PostService postService;

	@PostMapping("/post/")
	@CrossOrigin
	public ResponseEntity<Boolean> addPost(@RequestBody Post post, @RequestParam(name = "userName") String userName) {
		log.debug("Inside add post..");
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/post/userId/{userId}")
	@CrossOrigin
	public ResponseEntity<String> addPostByUserId(@RequestBody Post post, @PathVariable Long userId) throws Exception {
		log.debug("Inside addPostByUserId.... ");
		if (postService.addPost(post, userId)) {
			try {
				return new ResponseEntity<>("Post Added Succesfully", HttpStatus.OK);
			} catch (Exception e) {
				log.error("Exception while adding post : " + e.getMessage());
				return new ResponseEntity<>("Exception while adding post " + e.getMessage(), HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<>("Exception while adding post ", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/post/")
	@CrossOrigin
	public ResponseEntity<Set<Post>> getPostsforUser(@RequestParam(name = "userName") String userName)
			throws Exception {
		return new ResponseEntity<>(postService.findPost(userName), HttpStatus.OK);
	}
}
