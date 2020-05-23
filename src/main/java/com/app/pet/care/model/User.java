package com.app.pet.care.model;

import java.util.Set;

import com.app.pet.care.entity.UserEntity;

public class User {

	private long userId;
	private String email;
	private String password;
	private Set<Pet> pets;
	private Set<Post> posts;
	private Set<Picture> pictures;
	private Picture profilePic;
	private String firstName;
	private String middleName;
	private String lastName;
	private String zip;
	private String profilePicStorageLocation;
	private byte[] pic;
	private Set<UserEntity> contacts;

	public Set<UserEntity> getContacts() {
		return contacts;
	}

	public void setContacts(Set<UserEntity> contacts) {
		this.contacts = contacts;
	}

	public String getProfilePicStorageLocation() {
		return profilePicStorageLocation;
	}

	public void setProfilePicStorageLocation(String profilePicStorageLocation) {
		this.profilePicStorageLocation = profilePicStorageLocation;
	}

	public byte[] getPic() {
		return pic;
	}

	public void setPic(byte[] pic) {
		this.pic = pic;
	}

	public Set<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(Set<Picture> pictures) {
		this.pictures = pictures;
	}

	public Picture getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(Picture profilePic) {
		this.profilePic = profilePic;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	public Set<Pet> getPets() {
		return pets;
	}

	public void setPets(Set<Pet> pets) {
		this.pets = pets;
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

	@Override
	public String toString() {
		return "User [email=" + email + ", password=" + password + "]";
	}

}
