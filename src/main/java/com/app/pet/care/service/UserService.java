package com.app.pet.care.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.pet.care.dao.UserRepo;
import com.app.pet.care.dao.UserTimelineRepo;
import com.app.pet.care.entity.PetEntity;
import com.app.pet.care.entity.PostEntity;
import com.app.pet.care.entity.UserEntity;
import com.app.pet.care.model.Pet;
import com.app.pet.care.model.Post;
import com.app.pet.care.model.User;
import com.app.pet.care.model.UserTimeline;

@Service
public class UserService {

	@Autowired
	private UserRepo userRepo;
	
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

	public boolean addUser(User user) {
		UserEntity entity = new UserEntity();
		BeanUtils.copyProperties(user, entity);
		UserEntity resp = userRepo.save(entity);
		if (resp != null) {
			return true;
		}
		return false;
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
}
