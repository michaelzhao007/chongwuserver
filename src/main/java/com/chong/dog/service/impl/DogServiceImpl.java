package com.chong.dog.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chong.dog.model.Dog;
import com.chong.dog.service.DogService;

@Service
@Transactional
public class DogServiceImpl implements DogService {
	@Resource
	private SessionFactory sessionFactory;
	
	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	@Override
//	@CacheEvict(value="chong-dogs-cache",allEntries=true)
	public void createDog(Dog dog) {
		getSession().save(dog);
	}
	
	@Override
	public void update(Dog dog) {
		
		getSession().update(dog);
		// TODO Auto-generated method stub
		getSession().flush();
		
	}
	
	@Override
	public Dog getById(Long id) {
		if(id==null){
			return null;
		}else{
		return (Dog)getSession().get(Dog.class, id);
		}
		}
	
	@Override
//	@Cacheable("chong-dogs-cache")
	public List<Dog> getAllDogs(){
		System.out.println("fetching dogs");
		return getSession().createQuery(
				"FROM Dog")//
				.list();
	}
	

}
