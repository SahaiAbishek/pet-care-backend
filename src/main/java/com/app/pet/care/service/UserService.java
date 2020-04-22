package com.app.pet.care.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.pet.care.dao.UserRepo;
import com.app.pet.care.dao.UserTimelineRepo;
import com.app.pet.care.entity.PetEntity;
import com.app.pet.care.entity.PictureEntity;
import com.app.pet.care.entity.PostEntity;
import com.app.pet.care.entity.UserEntity;
import com.app.pet.care.model.Pet;
import com.app.pet.care.model.Picture;
import com.app.pet.care.model.Post;
import com.app.pet.care.model.User;
import com.app.pet.care.model.UserTimeline;

@Service
public class UserService {

	private Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private PictureService picService;

	@Autowired
	private UserTimelineRepo userTimelineRepo;

	public long validateUser(String email, String password) throws Exception {
		UserEntity entity = userRepo.findByEmailAndPassword(email, password);
		if (entity != null) {
			return entity.getUserId();
		} else {
			throw new Exception("Please verify email and password");
		}
	}

	public User getUserDetails(Long userId) throws Exception {
		if (userId == null) {
			throw new Exception("User Id cant be null");
		}
		Optional<UserEntity> optionalUser = userRepo.findById(userId);
		UserEntity userentity = optionalUser.get();
		if (userentity != null) {
			User target = new User();
			if (userentity.getEmail() != null) {
				target.setEmail(userentity.getEmail());
			}
			if (userentity.getFirstName() != null) {
				target.setFirstName(userentity.getFirstName());
			}
			if (userentity.getMiddleName() != null) {
				target.setMiddleName(userentity.getMiddleName());
			}
			if (userentity.getLastName() != null) {
				target.setLastName(userentity.getLastName());
			}
			if (userentity.getZip() != null) {
				target.setZip(userentity.getZip());
			}

			if (userentity.getPassword() != null) {
				target.setPassword(userentity.getPassword());
			}
			target.setUserId(userentity.getUserId());
			Set<PetEntity> sourcePets = userentity.getPets();
			Set<Pet> targetPets = new HashSet<>();
			if (sourcePets != null) {
				for (PetEntity sourcePet : sourcePets) {
					Pet targetPet = new Pet();
					if (sourcePet.getBreed() != null) {
						targetPet.setBreed(sourcePet.getBreed());
					}
					if (sourcePet.getName() != null) {
						targetPet.setName(sourcePet.getName());
					}
					if (sourcePet.getPetType() != null) {
						targetPet.setPetType(sourcePet.getPetType());
					}
					if (sourcePet.getPreferences() != null) {
						targetPet.setPreferences(sourcePet.getPreferences());
					}
					targetPet.setPetId(sourcePet.getPetId());
					targetPets.add(targetPet);
				}
			}
			target.setPets(targetPets);
			Set<PictureEntity> picturesEntity = userentity.getPictures();
			Set<Picture> pictures = new HashSet<>();
			if (picturesEntity != null && picturesEntity.size() > 0) {
				for (PictureEntity pictureEntity : picturesEntity) {
					Picture picture = new Picture();
					if (pictureEntity.getIsProfilePic() != null && pictureEntity.getIsProfilePic()) {
						String location = pictureEntity.getStoragelocation();
						Path path = Paths.get(location);
						picture.setPicId(pictureEntity.getPicId());
						picture.setPic(Files.readAllBytes(path));
						picture.setIsProfilePic(true);
						target.setProfilePic(picture);
					} else {
						String location = pictureEntity.getStoragelocation();
						Path path = Paths.get(location);
						picture.setPic(Files.readAllBytes(path));
						pictures.add(picture);
					}
				}
				target.setPictures(pictures);
			}
			return target;
		} else {
			throw new Exception("No use found for given id : " + userId);
		}
	}

	public Set<User> findAllcontacts(String email) throws Exception {
		UserEntity entity = userRepo.findByEmail(email);
		if (entity != null) {
			Optional<UserEntity> optional = userRepo.findById(entity.getUserId());
			UserEntity userEntity = optional.get();
			Set<User> users = new HashSet<>();
			for (UserEntity contact : userEntity.getContacts()) {
				User target = new User();
				target.setEmail(contact.getEmail());
				Set<PostEntity> sourceposts = contact.getPosts();
				Set<Post> tagetPosts = new HashSet<>();
				for (PostEntity sourcepost : sourceposts) {
					Post targetPost = new Post();
					targetPost.setText(sourcepost.getText());
					tagetPosts.add(targetPost);
				}
				target.setPosts(tagetPosts);
				users.add(target);
			}
			return users;
		} else {
			throw new Exception("User not found ");
		}
	}

	public Set<User> findAllcontactsByUserId(long userId) throws Exception {
		Optional<UserEntity> optional = userRepo.findById(userId);
		UserEntity userEntity = optional.get();
		Set<User> users = new HashSet<>();
		for (UserEntity contact : userEntity.getContacts()) {
			User target = new User();
			target.setEmail(contact.getEmail());
			Set<PostEntity> sourceposts = contact.getPosts();
			Set<Post> tagetPosts = new HashSet<>();
			for (PostEntity sourcepost : sourceposts) {
				Post targetPost = new Post();
				targetPost.setText(sourcepost.getText());
				tagetPosts.add(targetPost);
			}
			target.setPosts(tagetPosts);
			Set<PetEntity> sourcePets = contact.getPets();
			Set<Pet> targetPets = new HashSet<>();
			for (PetEntity source : sourcePets) {
				Pet targetPet = new Pet();
				targetPet.setBreed(source.getBreed());
				targetPet.setName(source.getName());
				targetPet.setPetType(source.getPetType());
				targetPet.setPreferences(source.getPreferences());
				targetPets.add(targetPet);
			}
			target.setPets(targetPets);
			users.add(target);
		}
		return users;
	}

	public List<UserTimeline> getUserTimeLine(long userId) throws Exception {
		return userTimelineRepo.findAllUsers(userId);
	}

	public boolean addUser(User user, MultipartFile file) throws Exception {
		if (user.getProfilePic() != null) {
			Picture picture = user.getProfilePic();
			picture.setPic(file.getBytes());
		}
		return addUser(user);
	}

	public boolean addUser(User user) throws Exception {
		UserEntity entity = new UserEntity();
		BeanUtils.copyProperties(user, entity);
		UserEntity resp = userRepo.save(entity);
		if (resp == null) {
			return false;
		}
		if (user.getProfilePic() != null) {
			Picture picture = user.getProfilePic();
			User picUser = new User();
			BeanUtils.copyProperties(resp, picUser);
			picture.setUser(picUser);
			boolean isPicSaved = false;
			if (user.getProfilePic().getIsProfilePic()) {
				isPicSaved = picService.addPicture(resp.getUserId(), new Post(), picture, picture.getPic());
//				picService.addPicture(picture, picture.getPic());
			} else {
				isPicSaved = picService.addPicture(resp.getUserId(), new Post(), picture, picture.getPic());
//				isPicSaved = picService.updatePicture(picture, picture.getPic());
				if (!isPicSaved) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean addFriend(long userId, long contactId) throws Exception {
		Optional<UserEntity> optional = userRepo.findById(userId);
		if (optional != null) {
			UserEntity userEntity = optional.get();
			if (userEntity != null) {
				Optional<UserEntity> optionalContact = userRepo.findById(contactId);
				if (optionalContact != null) {
					UserEntity contactEntity = optionalContact.get();
					if (contactEntity != null) {
						if (userEntity.getContacts().add(contactEntity)) {
							userRepo.save(userEntity);
							return true;
						} else {
							throw new Exception("Excption whilr adding contact");
						}

					} else {
						throw new Exception("contact not found :" + contactId);
					}
				} else {
					throw new Exception("contact not found :" + contactId);
				}

			} else {
				throw new Exception("User not found :" + userId);
			}
		}
		throw new Exception("User not found :" + userId);
	}

	public List<User> findAllUsersByZip(String zipCode, long userId) {
		log.debug("Inside findAllUsersByZip...");
		List<User> targetUsers = new ArrayList<>();
		List<UserEntity> sourceUsers = userRepo.findAllByZip(zipCode);
		for (UserEntity sourceUser : sourceUsers) {
			User targetUser = new User();
			if (sourceUser != null && userId != sourceUser.getUserId()) {
				if (sourceUser.getEmail() != null) {
					targetUser.setEmail(sourceUser.getEmail());
				}
				if (sourceUser.getFirstName() != null) {
					targetUser.setFirstName(sourceUser.getFirstName());
				}
				if (sourceUser.getLastName() != null) {
					targetUser.setLastName(sourceUser.getLastName());
				}
				if (sourceUser.getMiddleName() != null) {
					targetUser.setMiddleName(sourceUser.getMiddleName());
				}
				targetUser.setUserId(sourceUser.getUserId());
				if (sourceUser.getZip() != null) {
					targetUser.setZip(sourceUser.getZip());
				}
				Set<PostEntity> sourcePosts = sourceUser.getPosts();
				Set<Post> targetPosts = new HashSet<>();
				for (PostEntity sourcePost : sourcePosts) {
					Post targetPost = new Post();
					if (sourcePost.getCreatedAt() != null) {
						targetPost.setCreatedAt(sourcePost.getCreatedAt().toLocalDateTime());
					}

					targetPost.setPostId(sourcePost.getPostId());

					if (sourcePost.getText() != null) {
						targetPost.setText(sourcePost.getText());
					}
					targetPosts.add(targetPost);
				}
				targetUsers.add(targetUser);
			}
		}
		return targetUsers;
	}
}
