package com.app.pet.care.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.app.pet.care.entity.UserEntity;

@Repository
public class UserRepoJdbc {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	public int updateUser(UserEntity user) throws Exception {
		try {
			List<Object> userDetails = new ArrayList<>();
			StringBuffer updateQuery = new StringBuffer("UPDATE APP_USER SET ");
			boolean isFirst = true;
			if (user.getEmail() != null) {
				if (isFirst) {
					isFirst = false;
				} else {
					updateQuery.append(" , ");
				}
				updateQuery.append(" EMAIL = ?");
				userDetails.add(user.getEmail());
			}
			if (user.getFirstName() != null) {
				if (isFirst) {
					isFirst = false;
				} else {
					updateQuery.append(" , ");
				}
				updateQuery.append(" FIRST_NAME = ? ");
				userDetails.add(user.getFirstName());
			}
			if (user.getMiddleName() != null) {
				if (isFirst) {
					isFirst = false;
				} else {
					updateQuery.append(" , ");
				}
				updateQuery.append(" MIDDLE_NAME = ?");
				userDetails.add(user.getMiddleName());
			}
			if (user.getLastName() != null) {
				if (isFirst) {
					isFirst = false;
				} else {
					updateQuery.append(" , ");
				}
				updateQuery.append(" LAST_NAME = ? ");
				userDetails.add(user.getLastName());
			}
			if (user.getZip() != null) {
				if (isFirst) {
					isFirst = false;
				} else {
					updateQuery.append(" , ");
				}
				updateQuery.append(" ZIP = ? ");
				userDetails.add(user.getZip());
			}
			if (user.getProfilePicStorageLocation() != null) {
				if (isFirst) {
					isFirst = false;
				} else {
					updateQuery.append(" , ");
				}
				updateQuery.append(" PROFILE_PIC_STORAGE_LOCATION = ? ");
				userDetails.add(user.getProfilePicStorageLocation());
			}
			updateQuery.append(" WHERE USER_ID = ? ");
			userDetails.add(user.getUserId());

			Object[] params = new Object[userDetails.size()];
			int i = 0;
			for (Object detail : userDetails) {
				params[i++] = detail;
			}
			return jdbcTemplate.update(updateQuery.toString(), params);
		} catch (Exception ex) {
			throw new Exception("Exception inserting favorite : " + ex.getMessage());
		}
	}

	@Transactional
	public String profilePicStorageLocation(long userId) {
		String sql = "SELECT PROFILE_PIC_STORAGE_LOCATION FROM APP_USER WHERE USER_ID = ?";
		return jdbcTemplate.queryForObject(sql, new Object[] { userId }, String.class);
	}

}
