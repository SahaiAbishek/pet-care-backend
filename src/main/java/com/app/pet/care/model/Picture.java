package com.app.pet.care.model;

import java.time.LocalDateTime;

public class Picture {

	private long picId;
	private long petId;
	private long postId;
	private LocalDateTime createdAt;
	private String storagelocation;
	private String name;
	private String picType;
	private long picSize;
	private Boolean isProfilePic;
	private User user;
	private Boolean ispetProfilePic;
	private byte[] pic;

	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}

	public byte[] getPic() {
		return pic;
	}

	public void setPic(byte[] pic) {
		this.pic = pic;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getPicId() {
		return picId;
	}

	public void setPicId(long picId) {
		this.picId = picId;
	}

	public long getPetId() {
		return petId;
	}

	public void setPetId(long petId) {
		this.petId = petId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getStoragelocation() {
		return storagelocation;
	}

	public void setStoragelocation(String storagelocation) {
		this.storagelocation = storagelocation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicType() {
		return picType;
	}

	public void setPicType(String picType) {
		this.picType = picType;
	}

	public long getPicSize() {
		return picSize;
	}

	public void setPicSize(long picSize) {
		this.picSize = picSize;
	}

	public Boolean getIsProfilePic() {
		return isProfilePic;
	}

	public void setIsProfilePic(Boolean isProfilePic) {
		this.isProfilePic = isProfilePic;
	}

	public Boolean getIspetProfilePic() {
		return ispetProfilePic;
	}

	public void setIspetProfilePic(Boolean ispetProfilePic) {
		this.ispetProfilePic = ispetProfilePic;
	}

}
