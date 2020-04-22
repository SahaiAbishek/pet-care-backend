package com.app.pet.care.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.pet.care.model.Picture;
import com.app.pet.care.model.Post;
import com.app.pet.care.service.PictureService;

@RestController
public class PictureController {

	private Logger log = LoggerFactory.getLogger(PictureController.class);

	@Autowired
	private PictureService picService;

	@PostMapping(path = "/pic/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json")
	@CrossOrigin
	public ResponseEntity<String> addUserPic(@RequestPart(value = "pic") Picture pic,
			@RequestPart("file") MultipartFile file) throws Exception {
		log.debug("Inside addUserPic....");
		try {
			pic.setName(file.getOriginalFilename());
			pic.setPicSize(file.getSize());
			pic.setPicType(file.getContentType());
			picService.addPicture(pic, file.getBytes());
			return new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Failure" + e.getMessage(), HttpStatus.BAD_REQUEST);
		} finally {

		}
	}

	@PostMapping(path = "/pic/userId", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json")
	@CrossOrigin
	public ResponseEntity<String> addPicture(@RequestParam(value = "userId") Long userId,
			@RequestParam(required = false, value = "pic") Picture pic, @RequestParam("files") MultipartFile file)
			throws Exception {
		log.debug("Inside addPictureByUserId....");
		try {
			if (pic == null) {
				pic = new Picture();
			}
			pic.setName(file.getOriginalFilename());
			pic.setPicSize(file.getSize());
			pic.setPicType(file.getContentType());
			picService.addPicture(userId,new Post(),  pic, file.getBytes());
			return new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Failure" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

}
