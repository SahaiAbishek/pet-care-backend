package com.app.pet.care.service;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.pet.care.dao.PostRepo;
import com.app.pet.care.dao.PostRepoJdbc;
import com.app.pet.care.dao.UserRepo;
import com.app.pet.care.entity.PostEntity;
import com.app.pet.care.entity.UserEntity;
import com.app.pet.care.model.Comment;
import com.app.pet.care.model.Post;
import com.app.pet.care.model.User;

@Service
public class PostService {

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private PostRepoJdbc postRepoJdbc;

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

	public int addPostAsFavorite(long postId, long userId) throws Exception {
		return postRepoJdbc.saveFavorite(postId, userId, new Timestamp(System.currentTimeMillis()));
	}

	public int removePostAsFavorite(long postId, long userId) throws Exception {
		return postRepoJdbc.removeFavorite(postId, userId);
	}

	public List<User> getUsersForFavouritePost(long postId) throws Exception {
		return postRepoJdbc.getAllUsersForFavorite(postId);
	}

	public int addCommentToPost(long postId, long userId, String comment) throws Exception {
		return postRepoJdbc.savePostReply(postId, userId, new Timestamp(System.currentTimeMillis()), comment);
	}

	public int removeCommentFromPost(long commentId) throws Exception {
		return postRepoJdbc.deletePostReply(commentId);
	}

	public List<Comment> getAllcommentsForPost(long postId) throws Exception {
		return postRepoJdbc.getAllRepliesForPost(postId);
	}

	public long addPost(Post post, long userId) throws Exception {
		Optional<UserEntity> optionalUserEntity = userRepo.findById(userId);
		if (null != optionalUserEntity) {
			UserEntity userEntity = optionalUserEntity.get();
			PostEntity postEntity = new PostEntity();
			if (null != post.getText())
				postEntity.setText(post.getText());
			postEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
			postEntity.setUser(userEntity);
//			Set<PostEntity> posts = new HashSet<>();
//			posts.add(postEntity);
//			userEntity.setPosts(posts);
			PostEntity createdPost = postRepo.save(postEntity);
			return createdPost.getPostId();
		}
		throw new Exception("No user found for given user id : " + userId);
	}
}
