package com.chong.dog.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chong.dog.model.Dog;
import com.chong.dog.service.DogService;
import com.chong.login.service.UserService;
import com.chong.model.Response;
import com.hazelcast.core.IMap;

@Controller
@RequestMapping("/rest")
public class ListDogController {
	@Resource
	private DogService dogService;
	@Resource
	private UserService userService;
	
	private static Log LOG = LogFactory.getLog(ListDogController.class);
	
	@RequestMapping(value = "/dog/list.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Response listDog() {
		Response response = new Response();
			try {
				List<Dog> dogs = dogService.getAllDogs();
				response.setStatus("200");
				response.setResponseBody(dogs);
			
				
			} catch (Exception e) {
				e.printStackTrace();
				response.setError("ERROR102");
				response.setErrorMessage("Listing all dog failed");
			}
	
		return response;
	}
}
