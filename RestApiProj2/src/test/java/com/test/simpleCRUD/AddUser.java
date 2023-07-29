package com.test.simpleCRUD;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.test.reusables.ReusableResMethods;
import com.test.reusables.UserReusables;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AddUser extends UserReusables {
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
	public void CreateUser( ) {
		Response res = addUserData();
		ReusableResMethods.verifyStatusCode(res, 201);
		String status = ReusableResMethods.getJsonPathData(res, "status");
		Assert.assertEquals(status, "success");
	}
	
}
