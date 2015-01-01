package com.chong.dog.model;

import java.io.Serializable;

public class Dog implements Serializable {
	private static final long serialVersionUID = 1856862670651243395L;

	private Long id;
	private String dogName;
	private Integer dogAge;
	private String dogSex;
	private String dogImageUrl;
	private String description;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDogName() {
		return dogName;
	}
	public void setDogName(String dogName) {
		this.dogName = dogName;
	}
	public Integer getDogAge() {
		return dogAge;
	}
	public void setDogAge(Integer dogAge) {
		this.dogAge = dogAge;
	}
	public String getDogSex() {
		return dogSex;
	}
	public void setDogSex(String dogSex) {
		this.dogSex = dogSex;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDogImageUrl() {
		return dogImageUrl;
	}
	public void setDogImageUrl(String dogImageUrl) {
		this.dogImageUrl = dogImageUrl;
	}
	
	
	
	
 

}
