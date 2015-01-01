package com.chong.login.service;

import com.chong.login.model.SecurToken;
import com.chong.login.model.User;

public interface UserService {
   	public void registerUser(User user);
   	public boolean findUser(String username, String password);
   	public boolean checkExpire(String token);
   	public void saveSecurToken(SecurToken securToken);

}
