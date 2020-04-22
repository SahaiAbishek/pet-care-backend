package com.app.pet.care.controller;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.pet.care.model.Comment;
import com.app.pet.care.model.Picture;
import com.app.pet.care.model.Post;
import com.app.pet.care.model.User;
import com.app.pet.care.service.PictureService;
import com.app.pet.care.service.PostService;

@RestController
public class PostController {

	private Logger log = LoggerFactory.getLogger(PostController.class);

	@Autowired
	private PostService postService;

	@Autowired
	private PictureService picService;

	@PostMapping("/post/")
	@CrossOrigin
	public ResponseEntity<Boolean> addPost(@RequestBody Post post, @RequestParam(name = "userName") String userName) {
		log.debug("Inside add post..");
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/post/favorite/{postId}/{userId}")
	@CrossOrigin
	public ResponseEntity<Integer> addPostAsFavorite(@PathVariable Long postId, @PathVariable Long userId)
			throws Exception {
		log.debug("Inside addPostAsFavorite..post id = " + postId + " user id = " + userId);
		return new ResponseEntity<>(postService.addPostAsFavorite(postId, userId), HttpStatus.OK);
	}

	@GetMapping("/post/favorite/{postId}/")
	@CrossOrigin
	public ResponseEntity<List<User>> getUsersForFavouritePost(@PathVariable Long postId) throws Exception {
		log.debug("Inside getUsersForFavouritePost..post id = " + postId);
		return new ResponseEntity<>(postService.getUsersForFavouritePost(postId), HttpStatus.OK);
	}

	@DeleteMapping("/post/userId/favorite/{userId}")
	@CrossOrigin
	public ResponseEntity<Integer> removePostAsFavorite(@PathVariable Long postId, @PathVariable Long userId)
			throws Exception {
		log.debug("Inside removePostAsFavorite..post id = " + postId + " user id = " + userId);
		return new ResponseEntity<>(postService.addPostAsFavorite(postId, userId), HttpStatus.OK);
	}

	@DeleteMapping("/post/userId/comment/{commentId}")
	@CrossOrigin
	public ResponseEntity<Integer> removeCommentFromPost(@PathVariable Long commentId) throws Exception {
		log.debug("Inside removeCommentFromPost..post id = " + commentId);
		return new ResponseEntity<>(postService.removeCommentFromPost(commentId), HttpStatus.OK);
	}

	@PostMapping("/post/comment/{postId}/{userId}")
	@CrossOrigin
	public ResponseEntity<Integer> addReplyToPost(@PathVariable Long postId, @PathVariable Long userId,
			@RequestBody String comment) throws Exception {
		log.debug("Inside addReplyToPost..post id = " + postId + " user id = " + userId);
		return new ResponseEntity<>(postService.addCommentToPost(postId, userId, comment), HttpStatus.OK);
	}

	@PostMapping("/post/comment/{postId}/")
	@CrossOrigin
	public ResponseEntity<List<Comment>> getAllCommentsforPost(@PathVariable Long postId) throws Exception {
		log.debug("Inside getAllCommentsforPost..post id = " + postId);
		return new ResponseEntity<>(postService.getAllcommentsForPost(postId), HttpStatus.OK);
	}

	@PostMapping("/post/userId/{userId}")
	@CrossOrigin
	public ResponseEntity<String> addPostByUserId(@RequestBody Post post, @PathVariable Long userId) throws Exception {
		log.debug("Inside addPostByUserId.... ");
		postService.addPost(post, userId);
		try {
			return new ResponseEntity<>("Post Added Succesfully", HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception while adding post : " + e.getMessage());
			return new ResponseEntity<>("Exception while adding post " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/post/pic/userId")
	@CrossOrigin
	public ResponseEntity<String> addPostWithPic(@RequestParam Long userId,
			@RequestParam(required = false) String postText, @RequestParam("files") MultipartFile file)
			throws Exception {
		log.debug("Inside addPostWithPic.... ");
		Post post = new Post();
		if (postText != null && postText.trim().length() > 0) {
			post.setText(postText);
		}
		Picture pic = new Picture();
		pic.setName(file.getOriginalFilename());
		pic.setPicSize(file.getSize());
		pic.setPicType(file.getContentType());
		picService.addPicture(userId, post, pic, file.getBytes());
		return null;

	}

	@GetMapping("/post/")
	@CrossOrigin
	public ResponseEntity<Set<Post>> getPostsforUser(@RequestParam(name = "userName") String userName)
			throws Exception {
		return new ResponseEntity<>(postService.findPost(userName), HttpStatus.OK);
	}
}
