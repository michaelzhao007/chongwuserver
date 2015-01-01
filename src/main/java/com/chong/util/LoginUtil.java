package com.chong.util;

import java.util.Date;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.chong.login.model.SecurToken;

public class LoginUtil {
	
	public static SecurToken generateSecurToken(String username, String password){
		SecurToken securToken = new SecurToken();
		securToken.setExpire(false);
		securToken.setDate(new Date());
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		String hashedUsername = passwordEncoder.encode(username);
        String securTokenString = hashedUsername+hashedPassword+new Date().getTime();
        securToken.setToken(securTokenString);
        return securToken;
	}

}
