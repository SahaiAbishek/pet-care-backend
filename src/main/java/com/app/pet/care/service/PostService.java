package com.app.pet.care.service;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Optional;
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

	public boolean addPost(Post post, long userId) throws Exception {
		Optional<UserEntity> optionalUserEntity = userRepo.findById(userId);
		if (null != optionalUserEntity) {
			UserEntity userEntity = optionalUserEntity.get();
			PostEntity postEntity = new PostEntity();
			postEntity.setText(post.getText());
			postEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
			postEntity.setUser(userEntity);
			Set<PostEntity> posts = new HashSet<>();
			posts.add(postEntity);
			userEntity.setPosts(posts);
			if (null != userRepo.save(userEntity)) {
				return true;
			}
		}
		return false;
	}
}
