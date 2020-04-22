package com.app.pet.care.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.app.pet.care.model.Comment;
import com.app.pet.care.model.User;

@Repository
public class PostRepoJdbc {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Transactional
	public int saveFavorite(long postId, long userId, Timestamp createdAt) throws Exception {
		try {
			String insertQuery = "INSERT INTO FAVORITE (POST_ID,USER_ID,CREATED_AT) values (?,?,?)";
			return jdbcTemplate.update(insertQuery, new Object[] { postId, userId, createdAt });
		} catch (Exception ex) {
			throw new Exception("Exception inserting favorite : " + ex.getMessage());
		}
	}

	@Transactional
	public int removeFavorite(long postId, long userId) throws Exception {
		try {
			String insertQuery = "DELETE FROM FAVORITE WHERE POST_ID =? AND USER_ID = ?";
			return jdbcTemplate.update(insertQuery, new Object[] { postId, userId });
		} catch (Exception ex) {
			throw new Exception("Exception inserting favorite : " + ex.getMessage());
		}
	}

	@Transactional
	public List<User> getAllUsersForFavorite(long postId) throws Exception {
		try {
			String query = "SELECT AU.EMAIL,AU.FIRST_NAME,AU.MIDDLE_NAME,AU.LAST_NAME,AU.ZIP FROM FAVORITE FAV , APP_USER AU WHERE FAV.USER_ID = AU.USER_ID AND FAV.POST_ID = ?";
			return jdbcTemplate.query(query, new Object[] { postId }, new UserRowMapper());
		} catch (Exception ex) {
			throw new Exception("Exception inserting favorite : " + ex.getMessage());
		}
	}

	private class UserRowMapper implements RowMapper<User> {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setEmail(rs.getString("EMAIL"));
			user.setFirstName(rs.getString("FIRST_NAME"));
			user.setMiddleName(rs.getString("MIDDLE_NAME"));
			user.setLastName(rs.getString("LAST_NAME"));
			user.setZip(rs.getString("ZIP"));
			return user;
		}
	}

	@Transactional
	public int savePostReply(long postId, long userId, Timestamp createdAt, String comment) throws Exception {
		try {
			String insertQuery = "INSERT INTO POST_COMMENT (POST_ID,USER_ID,CREATED_AT,COMMENT) values (?,?,?,?)";
			return jdbcTemplate.update(insertQuery, new Object[] { postId, userId, createdAt, comment });
		} catch (Exception ex) {
			throw new Exception("Exception inserting favorite : " + ex.getMessage());
		}
	}

	@Transactional
	public int deletePostReply(long commentId) throws Exception {
		try {
			String insertQuery = "DELETE FROM POST_COMMENT WHERE COMMENT_ID = ?";
			return jdbcTemplate.update(insertQuery, new Object[] { commentId });
		} catch (Exception ex) {
			throw new Exception("Exception inserting favorite : " + ex.getMessage());
		}
	}

	@Transactional
	public List<Comment> getAllRepliesForPost(long postId) throws Exception {
		try {
			String query = "SELECT PC.COMMENT_ID,PC.COMMENT,AU.USER_ID, AU.EMAIL,AU.FIRST_NAME,AU.MIDDLE_NAME,AU.LAST_NAME,AU.ZIP "
					+ "FROM POST_COMMENT PC, APP_USER AU  WHERE PC.USER_ID = AU.USER_ID AND PC.POST_ID = ? ORDER BY PC.CREATED_AT DESC";
			return jdbcTemplate.query(query, new Object[] { postId }, new CommentRowMapper());
		} catch (Exception ex) {
			throw new Exception("Exception inserting favorite : " + ex.getMessage());
		}
	}

	private class CommentRowMapper implements RowMapper<Comment> {
		@Override
		public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
			Comment comment = new Comment();
			comment.setCommentId(rs.getLong("COMMENT_ID"));
			comment.setCommentText(rs.getString("COMMENT"));
			User user = new User();
			user.setUserId(rs.getLong("USER_ID"));
			user.setEmail(rs.getString("EMAIL"));
			user.setFirstName(rs.getString("FIRST_NAME"));
			user.setMiddleName(rs.getString("MIDDLE_NAME"));
			user.setLastName(rs.getString("LAST_NAME"));
			user.setZip(rs.getString("ZIP"));
			comment.setUser(user);
			return comment;
		}

	}
}
