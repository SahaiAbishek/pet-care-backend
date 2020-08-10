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

	/*Older sql : its complex and takes into consideration a lot of other factors

	String sql = "SELECT U.USER_ID,U.EMAIL,U.FIRST_NAME,U.PROFILE_PIC_STORAGE_LOCATION, "
			+ "	P.POST_ID,P.POST_TEXT,P.CREATED_AT , PICS.PIC_ID,PICS.CREATED_AT AS PIC_CREATED,PICS.STORAGE_LOCATION,PICS.NAME "
			+ " FROM APP_USER U inner join USER_RELATION UR on U.USER_ID = UR.RELATION_ID  "
			+ " INNER JOIN POST P ON P.USER_ID = UR.RELATION_ID LEFT OUTER JOIN PICTURES PICS ON P.POST_ID = PICS.POST_ID "
			+ " WHERE  UR.USER_ID = ? ORDER BY P.CREATED_AT DESC ";
	*/

	String timeLineSql = "SELECT relation.USER_ID,relation.EMAIL,relation.FIRST_NAME,relation.PROFILE_PIC_STORAGE_LOCATION, \n" +
			"post.POST_ID,post.POST_TEXT,post.CREATED_AT , " +
			"PIC.PIC_ID,PIC.CREATED_AT AS PIC_CREATED,PIC.STORAGE_LOCATION,PIC.NAME " +
			"from app_user usr " +
			"inner join user_relation ur on ur.user_id = usr.user_id " +
			"inner join app_user relation on relation.user_id = ur.relation_id " +
			"inner join post post on ur.relation_id = post.user_id " +
			"left join pictures pic on pic.post_id = post.post_id " +
			"where usr.user_id = ?" +
			"order by post.created_at desc;";

	@Transactional
	public List<UserTimeline> findAllUsers(long userId) {
		List<UserTimeline> userTimeline = jdbcTemplate.query(timeLineSql, new Object[] { userId }, new TimelineRowMapper());
		return userTimeline;
	}
}
