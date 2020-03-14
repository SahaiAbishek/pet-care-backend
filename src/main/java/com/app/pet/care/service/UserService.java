package com.app.pet.care.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.pet.care.dao.UserRepo;
import com.app.pet.care.entity.UserEntity;
import com.app.pet.care.model.User;

@Service
public class UserService {

	@Autowired
	private UserRepo userRepo;

	public boolean validateUser(String email, String password) {
		UserEntity entity = userRepo.findByEmailAndPassword(email, password);
		if (entity != null) {
			return true;
		}
		return false;
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
}
