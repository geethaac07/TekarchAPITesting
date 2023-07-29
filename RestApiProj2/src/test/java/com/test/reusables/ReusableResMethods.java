package com.test.reusables;

import java.util.concurrent.TimeUnit;

import com.restauto.POJOs.CreateUserPOJO;

import io.restassured.response.Response;

public class ReusableResMethods {

	public static int getStatusCode(Response response) {
		return response.getStatusCode();
	}
	
	public static String getContentType(Response response) {
		return response.getContentType();
	}
	
	public static long getResponseTimeIn(Response response, TimeUnit unit) {
		return response.getTimeIn(unit);
	}
	public static void verifyStatusCode(Response response, int statusCode) {
		response.then().statusCode(statusCode);
	}
	public static String getJsonPathData (Response response, String status) {
		return response.jsonPath().getString(status);
	}
}
