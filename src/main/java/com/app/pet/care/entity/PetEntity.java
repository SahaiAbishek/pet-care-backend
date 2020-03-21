package com.app.pet.care.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "PET")
public class PetEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PET_ID")
	private long petId;
	@Column(name = "PET_TYPE")
	private String petType;
	@Column(name = "BREED")
	private String breed;
	@Column(name = "NAME")
	private String name;
	@Column(name = "PREFERENCES")
	private String preferences;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ID")
	private UserEntity user;

	public long getPetId() {
		return petId;
	}

	public void setPetId(long petId) {
		this.petId = petId;
	}

	public String getPetType() {
		return petType;
	}

	public void setPetType(String petType) {
		this.petType = petType;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPreferences() {
		return preferences;
	}

	public void setPreferences(String preferences) {
		this.preferences = preferences;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

}
