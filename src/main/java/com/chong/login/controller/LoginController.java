package com.chong.login.controller;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chong.login.model.SecurToken;
import com.chong.login.model.User;
import com.chong.login.service.UserService;
import com.chong.model.Response;
import com.chong.util.LoginUtil;

@Controller
@RequestMapping("/rest")
public class LoginController {

	@Resource
	private UserService userService;
	private static Log LOG = LogFactory.getLog(LoginController.class);
	
	
	@RequestMapping(value="/login.json",method=RequestMethod.POST,produces="application/json")
	public @ResponseBody Response<SecurToken> login(@RequestBody User user) {
		Response<SecurToken> response  = new Response<SecurToken>();
		String username = user.getUserName();
		String password = user.getPassWord();
		boolean find = userService.findUser(username, password);
		if(find == true) {
		 SecurToken securToken = LoginUtil.generateSecurToken(username, password);
		 userService.saveSecurToken(securToken);
		 response.setStatus("200");
		 response.setResponseBody(securToken);
		}
		else {
		 response.setStatus("403");
		 response.setError("Error300");
		 response.setErrorMessage("Authorization failed");
		}
    
    	
    	return response;
	}
	
	 @RequestMapping(value = "/{name}", method = RequestMethod.GET)
	 public @ResponseBody String getGreeting(@PathVariable String name, @RequestHeader("token") String token) {
	  String result = "";
	  if(!userService.checkExpire(token)){
	  result="Hello "+name; 
	  }
	  else{
		result = "your token expired or not authorized"; 
	  }
	
	  return result;
	
	 }
	
	

	
}
