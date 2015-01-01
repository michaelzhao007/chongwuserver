package com.chong.login.model;

import java.util.Date;

public class SecurToken {
	  private Long id;
	  private String token;
	  private Date date;
	  private boolean expire;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public boolean getExpire() {
		return expire;
	}
	public void setExpire(boolean expire) {
		this.expire = expire;
	}

	  

}
