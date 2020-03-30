package com.app.pet.care.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.app.pet.care.model.UserTimeline;

@Repository
public class UserTimelineRepo {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	public List<UserTimeline> findAllUsers(long userId) {
		String sql = "SELECT U.USER_ID,U.EMAIL,U.FIRST_NAME,P.POST_ID,P.POST_TEXT,P.CREATED_AT "
				+ "FROM APP_USER U,USER_RELATION UR,POST P "
				+ "WHERE U.USER_ID = UR.RELATION_ID AND P.USER_ID = UR.RELATION_ID AND UR.USER_ID = ? ORDER BY P.CREATED_AT DESC";
		List<UserTimeline> userTimeline = jdbcTemplate.query(sql, new Object[] { userId }, new TimelineRowMapper());
		return userTimeline;
	}
}
