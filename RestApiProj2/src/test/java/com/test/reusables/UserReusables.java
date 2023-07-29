package com.test.reusables;

import java.net.ResponseCache;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.Logger;

import com.restauto.POJOs.LoginPOJO;
import com.restauto.POJOs.UserPOJO;
import com.restauto.POJOs.CreateUserPOJO;
import com.restauto.POJOs.GetUserResponsePOJO;
import com.test.constants.ApiModules;
import com.test.utilities.ExtentReportUtility;
import com.test.utilities.Log4JUtility;
import com.test.utilities.PropUtility;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.http.Header;

public class UserReusables {

	/*
	 * getBAseUri(returns String (uri)) LoginToApplication getToken (returns
	 * response) getUserData (returns list of userPojo) addUserData (ret response)
	 * updateUserData(return response) deleteUserData
	 */
	public PropUtility propUtility = new PropUtility();
	public Properties propertyFile = propUtility.loadFile("taloginproperties");
	public Response response;
	public String token;

	public String accountNo = propUtility.getPropertyValue("create.accno");
	public String departmentNo = propUtility.getPropertyValue("create.dept");
	public String salary = propUtility.getPropertyValue("create.salary");
	public String pincode = propUtility.getPropertyValue("create.pincode");

	public Log4JUtility logObj = Log4JUtility.getInstance();
	public ExtentReportUtility report = ExtentReportUtility.getInstance();

	protected Logger log = logObj.getLogger();
	
	public String userId;
	public String id;

	public String getBaseUri() {
		String baseUri = propUtility.getPropertyValue("tek.base.uri");
		return baseUri;
	}

	public Response loginToApplication() {

		String userName = propUtility.getPropertyValue("tek.user.name");
		String passWord = propUtility.getPropertyValue("tek.user.password");

		LoginPOJO login = new LoginPOJO(userName, passWord);

		Response response = RestAssured.given().contentType(ContentType.JSON).body(login).when().post(ApiModules.LOGIN);
		return response;
	}

	public String getToken() {
		response = loginToApplication();
		token = response.body().jsonPath().get("[0].token");
		return token;
	}

	public List<UserPOJO> getUserData() {

		Header header = new Header("token", token);
		log.info("Token :" +token);
		report.logTestInfo("Token = " +token);
		
		response = RestAssured.given().header(header).when().get(ApiModules.GET_DATA);

		UserPOJO[] userArray = response.as(UserPOJO[].class);
		List<UserPOJO> list = Arrays.asList(userArray);

		response.then().statusCode(200);
		return list;

	}

	public Response addUserData() {

		CreateUserPOJO user = new CreateUserPOJO();
		user.setAccountno(this.accountNo);		//"TA-maygc11");
		user.setDepartmentno(this.departmentNo);
		user.setPincode(this.pincode);
		user.setSalary(this.salary);

		Header header = new Header("token", token);
		log.info("Add Token " + token);
		report.logTestInfo("Token =" +token);

		response = RestAssured.given().contentType("application/json").header(header).body(user).when()
				.post(ApiModules.CREATE_USER);
		return response;
	}

	public int getStatusCode(Response res) {
		return res.getStatusCode();
	}

	public Response updateUserData() {
		UserPOJO updateUser = new UserPOJO();
		List<UserPOJO> allUsers = getUserData();
		
		for (UserPOJO userToUpdate : allUsers) {
			if (userToUpdate.getAccountno().equals(this.accountNo)) {

				updateUser = userToUpdate;
				updateUser.setDepartmentno("7");
				System.out.println("Updated Id" +updateUser.getId());
			}
		}
		Header header = new Header("token", token);
		log.info("Update User Token " + token);
		report.logTestInfo("Update User Token " + token);
		
		response = RestAssured.given().contentType("application/json").header(header).body(updateUser).when()
				.put(ApiModules.UPDATE_USER);
	

		return response;
	}

	public Response deleteUserData() {
		
//	token = getToken();
		System.out.println("Inside reusable delete method");
		UserPOJO delUser = new UserPOJO();
		List<UserPOJO> allUsers = getUserData();
		for (UserPOJO userToDelete : allUsers) {
			if (userToDelete.getAccountno().equals(this.accountNo)) {
				delUser = userToDelete;
				delUser.getUserid();
				System.out.println("delete USER ID:: " +delUser.getUserid());
				delUser.getId();
				System.out.println("delete ID:: " +delUser.getId());
			}
		}
		Header header = new Header("Token: ", token);
		
		log.info("Delete User Token =" +token);
		report.logTestInfo("Delete User Token =" +token);
		
		response = RestAssured.given().contentType("application/json")
				.header(header).body(delUser)
				.when().delete(ApiModules.DELETE_USER);
		
		return response;
	}
}