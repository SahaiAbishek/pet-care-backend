package com.app.pet.care.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.app.pet.care.model.Picture;
import com.app.pet.care.model.User;
import com.app.pet.care.model.UserTimeline;

public class TimelineRowMapper implements RowMapper<UserTimeline> {

	@Override
	public UserTimeline mapRow(ResultSet rs, int nowNum) throws SQLException {
		UserTimeline timeline = new UserTimeline();
		User user = new User();
		user.setUserId(rs.getLong("USER_ID"));
		user.setEmail(rs.getString("EMAIL"));
		user.setFirstName(rs.getString("FIRST_NAME"));
		user.setProfilePicStorageLocation(rs.getString("PROFILE_PIC_STORAGE_LOCATION"));
		if(null != user.getProfilePicStorageLocation()){
			Path path = Paths.get(user.getProfilePicStorageLocation());
			try {
				user.setPic(Files.readAllBytes(path));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		timeline.setUser(user);
		timeline.setPostId(rs.getLong("POST_ID"));
		timeline.setPostText(rs.getString("POST_TEXT"));
		timeline.setCreatedAt(rs.getTimestamp("CREATED_AT").toLocalDateTime());
		Picture picture = new Picture();
//		picture.setCreatedAt(rs.getTimestamp("PIC_CREATED").toLocalDateTime());
		picture.setPicId(rs.getLong("PIC_ID"));
		picture.setStoragelocation(rs.getString("STORAGE_LOCATION"));
		if (null != picture.getStoragelocation()) {
			Path path = Paths.get(picture.getStoragelocation());
			try {
				picture.setPic(Files.readAllBytes(path));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		picture.setName(rs.getString("NAME"));
		timeline.setPostComment(rs.getString("COMMENT"));
		timeline.setPicture(picture);
		return timeline;
	}

}
