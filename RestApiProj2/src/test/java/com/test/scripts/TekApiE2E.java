package com.test.scripts;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.test.utilities.ExtentReportUtility;
import com.test.utilities.Log4JUtility;
import com.restauto.POJOs.UserPOJO;
import com.test.reusables.ReusableResMethods;
import com.test.reusables.UserReusables;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TekApiE2E extends UserReusables{	
	
	public Log4JUtility logObj = Log4JUtility.getInstance();
	public ExtentReportUtility report = ExtentReportUtility.getInstance();

	protected Logger log = logObj.getLogger();
	
	@BeforeMethod
	public void init() {
		RestAssured.baseURI = getBaseUri();
	}
	
	@Test (priority=1)
	public void login() {
		 
		String token = getToken();
		
		log.info("Token = " + token);
		report.logTestInfo("Token = " + token);
	}
	
	@Test(priority=2)
	public void CreateUser( ) {
		Response res = addUserData();
		ReusableResMethods.verifyStatusCode(res, 201);
		String status = ReusableResMethods.getJsonPathData(res, "status");
		Assert.assertEquals(status, "success");
		log.info("User creation is successfully completed");
		report.logTestInfo("User creation is successfully completed");
	}
	
	@Test(priority=3)
	public void getUsers() {
		List<UserPOJO> allUsers = getUserData();
		log.info("First Account No: " +allUsers.get(0).getAccountno());
		
		report.logTestInfo("First Account No: " +allUsers.get(0).getAccountno());
	}
	
	@Test(priority=4)
	public void updateUser() {
		Response res = updateUserData();
		ReusableResMethods.verifyStatusCode(res, 200);
		String status = ReusableResMethods.getJsonPathData(res, "status");
		System.out.println("Update User Status::" +status);
		Assert.assertEquals(status, "success");
		
		log.info("User update is successfully completed");
		report.logTestInfo("User update is successfully completed");
	}
	@Test(priority=5)
	public void deleteUser() {
		Response res = deleteUserData();
//		ReusableResMethods.verifyStatusCode(res, 200);
		String status = ReusableResMethods.getJsonPathData(res, "status");
		System.out.println("Delete User Status::" +status);
		Assert.assertEquals(status, "success");
		
		log.info("User delete is successfully completed");
		report.logTestInfo("User delete is successfully completed");
	}
}

