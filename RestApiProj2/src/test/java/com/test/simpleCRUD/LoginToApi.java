package com.test.simpleCRUD;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.test.reusables.UserReusables;

import io.restassured.RestAssured;

public class LoginToApi extends UserReusables{
	@BeforeMethod
	public void init() {
		RestAssured.baseURI = getBaseUri();
	}
	
	@Test
	public void login() {
		 
		String token = getToken();
		System.out.println("Token = " + token);
	}
}
