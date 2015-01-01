package com.chong.login.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chong.login.model.SecurToken;
import com.chong.login.model.User;
import com.chong.login.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService  {
	
	@Resource
	private SessionFactory sessionFactory;
	
	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	public void registerUser(User user) {
		// TODO Auto-generated method stub
		getSession().save(user);
	}
	
	public boolean findUser(String userName, String passWord) {
  		List<User> userList =  getSession().createQuery("from User where userName= :userName and passWord= :passWord").
  				                         setParameter("userName", userName).
  				                                setParameter("passWord", passWord).list();
		if(userList.size() == 1) {return true;}
		else return false;
	}

	public boolean checkExpire(String token) {
		List<SecurToken> securTokens =  getSession().createQuery("from SecurToken where token= :token").setParameter("token", token).list();
		if(securTokens.size() == 1) {
		SecurToken securToken = securTokens.get(0);
		long diff = new Date().getTime() -  securToken.getDate().getTime();
		
		long diffMins = diff/(60*1000);
		if(diffMins < 30){
			return false;
		}
		else{
			securToken.setExpire(true);
			getSession().update(securToken);
			return true;

		}
	    
		}
		return true;
	}

	public void saveSecurToken(SecurToken securToken) {
		getSession().save(securToken);
	}

}
