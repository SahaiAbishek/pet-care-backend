package com.app.pet.care.dao;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.pet.care.entity.PostEntity;

@Repository
public interface PostRepo extends CrudRepository<PostEntity, Long> {
	Set<PostEntity> findPostByUserUserId(long userId);
}
