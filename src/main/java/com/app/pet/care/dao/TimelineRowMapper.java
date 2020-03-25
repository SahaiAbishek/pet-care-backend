package com.app.pet.care.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.app.pet.care.model.User;
import com.app.pet.care.model.UserTimeline;

public class TimelineRowMapper implements RowMapper<UserTimeline> {

	@Override
	public UserTimeline mapRow(ResultSet rs, int nowNum) throws SQLException {
		UserTimeline timeline = new UserTimeline();
		User user = new User();
		user.setId(rs.getLong("USER_ID"));
		user.setEmail(rs.getString("EMAIL"));
		timeline.setUser(user);
		timeline.setPostId(rs.getLong("POST_ID"));
		timeline.setPostText(rs.getString("POST_TEXT"));
		timeline.setCreatedAt(rs.getTimestamp("CREATED_AT").toLocalDateTime());
		return timeline;
	}

}
