package com.chong.util;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.chong.dog.model.Dog;
import com.chong.model.Response;

public class AWSStorageUtil {
	private BasicAWSCredentials credentials;
	private AmazonS3 s3;
	private String bucketName;
	private static final String accessKey = "";
	private static final String secretKey = "";
	private static AWSStorageUtil awsStorageUtil;
	
	private AWSStorageUtil(){
		this.credentials = new BasicAWSCredentials(accessKey, secretKey);
	    this.s3 = new AmazonS3Client(this.credentials);
	    this.bucketName = "dogadoption";
	}
	
	public static AWSStorageUtil getInstance(){
		if(awsStorageUtil == null){
		awsStorageUtil = new AWSStorageUtil();
		}
		return awsStorageUtil;
	}
	
	public String uploadFile(String id, File filedata){
		Date current = new Date();
		String key = id + filedata.getName() + current.getTime();
		s3.putObject(this.bucketName, key, filedata);
		String uploadFileUrl = "https://s3-us-west-2.amazonaws.com/" +this.bucketName+"/"+key;
	    return uploadFileUrl;
	}

}
