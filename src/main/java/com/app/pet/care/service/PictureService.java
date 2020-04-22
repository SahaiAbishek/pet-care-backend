package com.app.pet.care.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.pet.care.dao.PicRepoJdbc;
import com.app.pet.care.dao.UserRepo;
import com.app.pet.care.entity.PictureEntity;
import com.app.pet.care.entity.UserEntity;
import com.app.pet.care.model.Picture;
import com.app.pet.care.model.Post;

@Service
public class PictureService {

	private Logger log = LoggerFactory.getLogger(PictureService.class);

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PicRepoJdbc picRepoJdbc;

	@Autowired
	private PostService postService;

	private static String MY_BUCKET = "C:\\abhi\\personal\\petcare";

	public boolean addPicture(Long userId, Post post, Picture picture, byte[] bytes) throws Exception {
		long postId = postService.addPost(post, userId);
		Optional<UserEntity> optionalUserEntity = userRepo.findById(userId);
		UserEntity userEntity = optionalUserEntity.get();
		if (userEntity != null) {
			String storeageLocation = userEntity.getEmail();
			String directoryName = MY_BUCKET + "\\" + storeageLocation;
			File directory = new File(directoryName);
			if (!directory.exists()) {
				directory.mkdir();
			}
			Path path = Paths.get(MY_BUCKET + "\\" + storeageLocation + "\\" + picture.getName());
			picture.setStoragelocation(MY_BUCKET + "\\" + storeageLocation + "\\" + picture.getName());
			picture.setPostId(postId);
			Files.write(path, bytes);
			int val = picRepoJdbc.savePostReply(userId, picture);
			return val > 0;
		}
		return false;
	}

	public boolean addPicture(Picture picture, byte[] bytes) throws Exception {
		Optional<UserEntity> optionalUserEntity = userRepo.findById(picture.getUser().getUserId());
		UserEntity userEntity = optionalUserEntity.get();
		if (userEntity != null) {
			PictureEntity picEntity = new PictureEntity();
			if (picture.getName() != null) {
				picEntity.setName(picture.getName());
			}
			picEntity.setPicSize(picture.getPicSize());
			if (picture.getPicType() != null) {
				picEntity.setPicType(picture.getPicType());
			}
			if (picture.getStoragelocation() != null) {
				picEntity.setStoragelocation(picture.getStoragelocation());
			}
			if (picture.getIspetProfilePic() != null) {
				picEntity.setIspetProfilePic(picture.getIspetProfilePic());
			}
			if (picture.getIsProfilePic() != null) {
				picEntity.setIsProfilePic(picture.getIsProfilePic());
			}
			picEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
			String storeageLocation = userEntity.getEmail();

			String directoryName = MY_BUCKET + "\\" + storeageLocation;
			File directory = new File(directoryName);
			if (!directory.exists()) {
				directory.mkdir();
			}
			Path path = Paths.get(MY_BUCKET + "\\" + storeageLocation + "\\" + picture.getName());
			Files.write(path, bytes);

			// place the user pics in a folder by email for now
			picEntity.setStoragelocation(MY_BUCKET + "\\" + storeageLocation + "\\" + picture.getName());
			Set<PictureEntity> pictures = new HashSet<>();
			pictures.add(picEntity);
			picEntity.setUser(userEntity);
			userEntity.setPictures(pictures);
			userRepo.save(userEntity);
			return true;
		} else {
			log.error("No such user exists");
			return false;
		}
	}

	public boolean updatePicture(Picture picture, byte[] bytes) throws Exception {
		Optional<UserEntity> optionalUserEntity = userRepo.findById(picture.getUser().getUserId());
		UserEntity userEntity = optionalUserEntity.get();
		if (userEntity != null) {
			PictureEntity picEntity = new PictureEntity();
			picEntity.setPicId(picture.getPicId());
			if (picture.getName() != null) {
				picEntity.setName(picture.getName());
			}
			picEntity.setPicSize(picture.getPicSize());
			if (picture.getPicType() != null) {
				picEntity.setPicType(picture.getPicType());
			}
			if (picture.getStoragelocation() != null) {
				picEntity.setStoragelocation(picture.getStoragelocation());
			}
			if (picture.getIspetProfilePic() != null) {
				picEntity.setIspetProfilePic(picture.getIspetProfilePic());
			}
			if (picture.getIsProfilePic() != null) {
				picEntity.setIsProfilePic(picture.getIsProfilePic());
			}
			picEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
			String storeageLocation = userEntity.getEmail();

			String directoryName = MY_BUCKET + "\\" + storeageLocation;
			File directory = new File(directoryName);
			if (!directory.exists()) {
				directory.mkdir();
			}
			Path path = Paths.get(MY_BUCKET + "\\" + storeageLocation + "\\" + picture.getName());
			Files.write(path, bytes);

			// place the user pics in a folder by email for now
			picEntity.setStoragelocation(MY_BUCKET + "\\" + storeageLocation + "\\" + picture.getName());
			Set<PictureEntity> pictures = new HashSet<>();
			pictures.add(picEntity);
			picEntity.setUser(userEntity);
			userEntity.setPictures(pictures);
			userRepo.save(userEntity);
			return true;
		} else {
			log.error("No such user exists");
			return false;
		}
	}

}
