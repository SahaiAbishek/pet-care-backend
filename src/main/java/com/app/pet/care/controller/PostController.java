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

import com.app.pet.care.model.Post;
import com.app.pet.care.service.PostService;

@RestController
public class PostController {

	@Autowired
	private PostService postService;

	@PostMapping("/post/")
	public ResponseEntity<Boolean> addPost(@RequestBody Post post, @RequestParam(name = "userName") String userName) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/post/")
	public ResponseEntity<Set<Post>> getPostsforUser(@RequestParam(name = "userName") String userName) throws Exception {
		return new ResponseEntity<>(postService.findPost(userName), HttpStatus.OK);
	}
}
