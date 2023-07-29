package com.test.scripts;

import org.hamcrest.MatcherAssert;
import static org.hamcrest.Matchers.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.restauto.POJOs.LoginDataPOJO;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.http.ContentType;
import io.restassured.http.Header;

public class TekArchAssignments {
	String extractedToken = null;

	@BeforeClass
	public void setURI() {
		RestAssured.baseURI = "https://us-central1-qa01-tekarch-accmanager.cloudfunctions.net/";
	}

	@Test
	public void loginToApi1() {
		LoginDataPOJO data = new LoginDataPOJO();

		data.setUsername("geethaac07@gmail.com");
		data.setPassword("Tekarch@123");

		Response res = RestAssured.given().contentType(ContentType.JSON).body(data).when().post("login");
		res.then().statusCode(201).contentType(ContentType.JSON);

		extractedToken = res.body().jsonPath().getString("[0].token");
		System.out.println("token=" + extractedToken);
	}

	@Test(dependsOnMethods = "loginToApi1")
	public void getTotalNoRecs() {
		Header ob = new Header("token", extractedToken);
		Response res = RestAssured.given().header(ob).when().get("getdata"); //.get --> Get Method
		res.then().statusCode(200);

		int totalRecCount = res.body().jsonPath().get("size()");
		System.out.println("Total Records Count: " +totalRecCount);
	}

	@Test(dependsOnMethods = "loginToApi1")
	public void getTotalRecLessThan10K() {
		Header ob = new Header("token", extractedToken);
		Response res = RestAssured.given().header(ob).when().get("getdata");
		res.then().statusCode(200);

		int totalRecCount = res.body().jsonPath().get("size()");
		int SizeConstraint = 10000;

		MatcherAssert.assertThat(totalRecCount, lessThan(SizeConstraint));
		System.out.println("Total record count "+totalRecCount+" is less than 10K");
	}

	@Test(dependsOnMethods = "loginToApi1")
	public void minMaxSalary() {
		Header ob = new Header("token", extractedToken);
		Response res = RestAssured.given().header(ob).when().get("getdata");

		res.then().statusCode(200);
		String salary = res.body().jsonPath().getString("salary");

		System.out.println();
	}
	
	@Test(dependsOnMethods = "loginToApi1")
	public void getAllAccounts() {
		Header ob = new Header("token", extractedToken);
		Response res = RestAssured.given().header(ob).when().get("getdata");
		res.then().statusCode(200);
		System.out.println("All the accounts are \n");
//		System.out.println(res.body().jsonPath().get("findAll{accountno}"));
		
	}
	

//List<Object> listID = res.body().jsonPath().getList("findAll{it.userid == 'lzQHg4ywe0MI87vM7fpF'}.salary");

	
//	lzQHg4ywe0MI87vM7fpF
	
	
	

//List<String> listID = res.body().jsonPath().getList("salary");
//  ArrayList<Integer> myList = new ArrayList<Integer>();
//  try {  
//  for (String str : listID) {
//   
//   myList.add(Integer.parseInt(str));
//   
//  }
//  }
//  catch(Exception e) {}
//  Integer minSalary = myList.get(0);
//        for (int i = 1; i < myList.size(); i++) {
//            if (minSalary > myList.get(i))
//             minSalary = myList.get(i);
//        }
//  
//        Integer maxSalary = myList.get(0);
//        for (int j = 1; j < myList.size(); j++) {
//            if (maxSalary < myList.get(j))
//             maxSalary = myList.get(j);
//        }
//  System.out.println("Miniumum Salary is" + minSalary);
//  System.out.println("Maximum Salary is" + maxSalary);

}
