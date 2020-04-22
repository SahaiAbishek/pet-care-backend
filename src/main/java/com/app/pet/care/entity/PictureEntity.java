package com.app.pet.care.entity;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "PICTURES")
public class PictureEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PIC_ID")
	private Long picId;
	@Column(name = "PET_ID")
	private Long petId;
	@Column(name = "CREATED_AT")
	private Timestamp createdAt;
	@Column(name = "STORAGE_LOCATION")
	private String storagelocation;
	@Column(name = "NAME")
	private String name;
	@Column(name = "PIC_TYPE")
	private String picType;
	@Column(name = "PIC_SIZE")
	private Long picSize;
	@Column(name = "USER_PROFILE_PIC")
	private Boolean isProfilePic;
	@Column(name = "PET_PROFILE_PIC")
	private Boolean ispetProfilePic;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ID")
	private UserEntity user;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "POST_ID")
	private PostEntity post;

	public PostEntity getPost() {
		return post;
	}

	public void setPost(PostEntity post) {
		this.post = post;
	}

	public Long getPicId() {
		return picId;
	}

	public void setPicId(Long picId) {
		this.picId = picId;
	}

	public Long getPetId() {
		return petId;
	}

	public void setPetId(Long petId) {
		this.petId = petId;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
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

	public Long getPicSize() {
		return picSize;
	}

	public void setPicSize(Long picSize) {
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

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "PictureEntity [picId=" + picId + ", petId=" + petId + ", CREATED_AT=" + createdAt + ", storagelocation="
				+ storagelocation + ", name=" + name + ", picType=" + picType + ", picSize=" + picSize
				+ ", isProfilePic=" + isProfilePic + ", ispetProfilePic=" + ispetProfilePic + ", user=" + user + "]";
	}

}
