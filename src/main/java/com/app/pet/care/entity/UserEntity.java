package com.app.pet.care.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private long userId;
	@Column(name = "EMAIL")
	private String email;
	@Column(name = "PASSWORD")
	private String password;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<PetEntity> pets;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<PostEntity> posts;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "USER_RELATION", joinColumns = {
			@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID") }, inverseJoinColumns = {
					@JoinColumn(name = "RELATION_ID", referencedColumnName = "USER_ID") })
	private Set<UserEntity> contacts;
	@ManyToMany(mappedBy = "contacts")
	private Set<UserEntity> users;

	public Set<PostEntity> getPosts() {
		return posts;
	}

	public void setPosts(Set<PostEntity> posts) {
		this.posts = posts;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<PetEntity> getPets() {
		return pets;
	}

	public void setPets(Set<PetEntity> pets) {
		this.pets = pets;
	}

	public Set<UserEntity> getUsers() {
		return users;
	}

	public void setUsers(Set<UserEntity> users) {
		this.users = users;
	}

	public Set<UserEntity> getContacts() {
		return contacts;
	}

	public void setContacts(Set<UserEntity> contacts) {
		this.contacts = contacts;
	}

	@Override
	public String toString() {
		return "UserEntity [userId=" + userId + ", email=" + email + ", password=" + password + ", pets=" + pets
				+ ", posts=" + posts + ", users=" + users + ", contacts=" + contacts + "]";
	}

}
