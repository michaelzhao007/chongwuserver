package com.chong.dog.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.PathParam;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.chong.dog.model.Dog;
import com.chong.dog.service.DogService;
import com.chong.login.service.UserService;
import com.chong.model.Response;
import com.chong.util.AWSStorageUtil;
import com.cloudinary.Cloudinary;

@Controller
@RequestMapping("/rest")
public class CreateDogController {

	@Resource
	private DogService dogService;
	@Resource
	private UserService userService;
	
	private AWSStorageUtil awsStorageUtil = AWSStorageUtil.getInstance();

	private static Log LOG = LogFactory.getLog(CreateDogController.class);

	@RequestMapping(value = "/dog/create.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Response createDog(@RequestBody Dog dog,
			@RequestHeader("token") String token) {
		Response response = new Response();
		if (!userService.checkExpire(token)) {
			try {
				dogService.createDog(dog);
				response.setStatus("200");
				response.setResponseBody(dog);
			} catch (Exception e) {
				response.setError("ERROR102");
				response.setErrorMessage("Create dog failed");
			}
		} else {
			response.setError("ERROR101");
			response.setErrorMessage("your token expired or not authorized");
		}

		return response;
	}

	@RequestMapping(value = "/dog/image/{id}/create.json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Response createDogImage(
			@PathVariable String id,
			@RequestParam(value = "photo", required = false) MultipartFile filedata,
			@RequestHeader("token") String token) {
		Response response = new Response();
		if (!userService.checkExpire(token)) {
			try {
				if (filedata != null && !filedata.isEmpty()) {

//					String fileName = filedata.getOriginalFilename();
//					String extensionName = fileName.substring(fileName
//							.lastIndexOf(".") + 1);
//					String newFileName = String.valueOf(System
//							.currentTimeMillis()) + "." + extensionName;
					try {
						response = saveFileToS3(id, filedata);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				response.setError("ERROR102");
				response.setErrorMessage("Create dog failed");
			}
		} else {
			response.setError("ERROR101");
			response.setErrorMessage("your token expired or not authorized");
		}

		return response;
	}

	private Response saveFile(String id, String newFileName,
			MultipartFile filedata) {
		String picDir = "C:\\Users\\michaelzhao007\\workspace\\chong\\WebContent";
		String saveFilePath = picDir;
		Dog dog = dogService.getById(Long.parseLong(id));
		File fileDir = new File(saveFilePath);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		Response response = new Response();
		try {
			FileOutputStream out = new FileOutputStream(saveFilePath + "\\"
					+ newFileName);
			out.write(filedata.getBytes());
			out.flush();
			String savefileurl = "http://localhost:8090/chong/";
			dog.setDogImageUrl(savefileurl + newFileName);
			dogService.update(dog);
			response.setStatus("200");
			response.setResponseBody(dog);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			response.setError("400");
			response.setErrorMessage("Error to upload dog");
		}
		return response;
	}

	private Response saveFileCloud(String id, MultipartFile filedata) throws IOException {
		Response response = new Response();
		Dog dog = dogService.getById(Long.parseLong(id));
		Map config = new HashMap();
		config.put("cloud_name", "hbak1xhit");
		config.put("api_key", "876193772977175");
		config.put("api_secret", "KffeOS2eRSUFXEzGH-Kfn5lwrtc");
		Cloudinary cloudinary = new Cloudinary(config);
		Map uploadResult = cloudinary.uploader().upload(convert(filedata),
				Cloudinary.emptyMap());
		if(uploadResult.get("public_id")!=null){
			String imageUrl = (String) uploadResult.get("url");
			dog.setDogImageUrl(imageUrl);
			dogService.update(dog);
			response.setStatus("200");
			response.setResponseBody(dog);
		}
		else{
			response.setStatus("400");
			response.setErrorMessage("upload image fail");
			response.setResponseBody(dog);
		}
		return response;
	}
	
	private Response saveFileToS3(String id, MultipartFile filedata) {
		Response response = new Response();
		Dog dog = dogService.getById(Long.parseLong(id));
		try{
		 String url = awsStorageUtil.uploadFile(id, convert(filedata));
		 dog.setDogImageUrl(url);
			dogService.update(dog);
			response.setStatus("200");
			response.setResponseBody(dog);
		}
		catch(Exception exp){
			response.setStatus("400");
			exp.printStackTrace();
			LOG.info("details of exception: " + exp);
			response.setErrorMessage("upload image fail ");
			response.setResponseBody(dog);
		}
		return response;		
	}
	
	public File convert(MultipartFile file) throws IOException
	{    
	    File convFile = new File(file.getOriginalFilename());
	    System.out.println("path is: " +	    convFile.getAbsolutePath());
	    convFile.createNewFile(); 
	    convFile.getAbsolutePath();
	    FileOutputStream fos = new FileOutputStream(convFile); 
	    fos.write(file.getBytes());
	    fos.close(); 
	    return convFile;
	}

}
