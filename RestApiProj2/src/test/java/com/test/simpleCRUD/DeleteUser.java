package com.test.simpleCRUD;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.test.reusables.ReusableResMethods;
import com.test.reusables.UserReusables;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class DeleteUser extends UserReusables{
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
	public void deleteUser() {
		Response res = deleteUserData();
		ReusableResMethods.verifyStatusCode(res, 200);
		String status = ReusableResMethods.getJsonPathData(res, "status");
		System.out.println("Delete User Status::" +status);
		Assert.assertEquals(status, "success");
	}
}
