package com.chong.login.model;

import java.util.HashSet;
import java.util.Set;

import com.chong.dog.model.Dog;

public class User {
	private Long id;
    private String userName;
    private String passWord;
    private Set<SecurToken> securToken =  new HashSet<SecurToken>();
    private Set<Dog> dog = new HashSet<Dog>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public Set<SecurToken> getSecurToken() {
		return securToken;
	}
	public void setSecurToken(Set<SecurToken> securToken) {
		this.securToken = securToken;
	}
	public Set<Dog> getDog() {
		return dog;
	}
	public void setDog(Set<Dog> dog) {
		this.dog = dog;
	}
	
    

}
