package com.test.simpleCRUD;

import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.restauto.POJOs.UserPOJO;
import com.test.reusables.UserReusables;

import io.restassured.RestAssured;

public class GetUsers extends UserReusables {
	@BeforeMethod
	public void init() {
		RestAssured.baseURI = getBaseUri();
	}
	
	@Test
	public void login() {
		 
		String token = getToken();
		System.out.println("Token = " + token);
	}
	
	@Test(dependsOnMethods = "login")
	public void getUsers() {
		List<UserPOJO> allUsers = getUserData();
		System.out.println("First Account No: " +allUsers.get(0).getAccountno());
	}
}
