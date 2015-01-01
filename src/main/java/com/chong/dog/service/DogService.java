package com.chong.dog.service;

import java.util.List;

import com.chong.dog.model.Dog;

public interface DogService {
   	public void createDog(Dog dog);

	void update(Dog dog);

	Dog getById(Long id);

	List<Dog> getAllDogs();
}
