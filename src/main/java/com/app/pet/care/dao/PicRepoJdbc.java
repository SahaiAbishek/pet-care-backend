package com.app.pet.care.dao;

import java.sql.Timestamp;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.app.pet.care.model.Picture;

@Repository
public class PicRepoJdbc {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	public int savePostReply(long userId, Picture picture) throws Exception {
		try {
			String insertQuery = "INSERT INTO PICTURES (USER_ID,POST_ID,CREATED_AT,STORAGE_LOCATION,NAME,PIC_TYPE,PIC_SIZE,USER_PROFILE_PIC) values (?,?,?,?,?,?,?,?)";
			return jdbcTemplate.update(insertQuery,
					new Object[] { userId, picture.getPostId(), new Timestamp(System.currentTimeMillis()),
							picture.getStoragelocation(), picture.getName(), picture.getPicType(), picture.getPicSize(),
							picture.getIsProfilePic() });
		} catch (Exception ex) {
			throw new Exception("Exception inserting favorite : " + ex.getMessage());
		}
	}

}
