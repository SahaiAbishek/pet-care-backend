package com.app.pet.care.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.pet.care.dao.PostRepo;
import com.app.pet.care.dao.UserRepo;
import com.app.pet.care.entity.PostEntity;
import com.app.pet.care.entity.UserEntity;
import com.app.pet.care.model.Post;

@Service
public class PostService {

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private PostRepo postRepo;

	public Set<Post> findPost(String userName) throws Exception {
		UserEntity userEntity = userRepo.findByEmail(userName);
		if (userEntity != null) {
			Set<Post> result = new HashSet<>();
			Set<PostEntity> postEntities = postRepo.findPostByUserUserId(userEntity.getUserId());
			for (PostEntity source : postEntities) {
				Post target = new Post();
				BeanUtils.copyProperties(source, target);
				result.add(target);
			}
			return result;
		} else {
			throw new Exception("No such user : " + userName);
		}
	}
}
