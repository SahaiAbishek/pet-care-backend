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
		String sql = "SELECT U.USER_ID,U.EMAIL,U.FIRST_NAME,U.PROFILE_PIC_STORAGE_LOCATION, "
				+ "	P.POST_ID,P.POST_TEXT,P.CREATED_AT , PICS.PIC_ID,PICS.CREATED_AT AS PIC_CREATED,PICS.STORAGE_LOCATION,PICS.NAME,PC.COMMENT "
				+ " FROM APP_USER U inner join USER_RELATION UR on U.USER_ID = UR.RELATION_ID  "
				+ " INNER JOIN POST P ON P.USER_ID = UR.RELATION_ID LEFT OUTER JOIN PICTURES PICS ON P.POST_ID = PICS.POST_ID "
				+ " LEFT OUTER JOIN POST_COMMENT PC ON P.POST_ID = PC.POST_ID "
				+ " WHERE  UR.USER_ID = ? ORDER BY P.CREATED_AT DESC ";
		List<UserTimeline> userTimeline = jdbcTemplate.query(sql, new Object[] { userId }, new TimelineRowMapper());
		return userTimeline;
	}
}
