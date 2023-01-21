package com.noorteck.qa.utils;

import static io.restassured.RestAssured.given;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Constants {
	public static Properties prop;

	public static String configFilePath = "./src/test/resources/config/ProjectConfig.properties";

	// TODO envDataMap will hold values of env related data (ex. db info psw, usn)
	public static Map<String, Object> envDataMap = new LinkedHashMap<String, Object>();

	// TODO hrApiEnvDataMap will hold values of all api endpoint information
	public static Map<String, Object> hrApiEnvDataMap = new LinkedHashMap<String, Object>();

	// TODO requestBodyMap will hold test data for request body
	public static Map<String, Object> requestBodyMap = new HashMap<String, Object>();

	// Create Web API objects
	public static Map<String, String> headerMap = new HashMap<String, String>();

	public static Map<String, String> queryParamMap = new HashMap<String, String>();

	// TODO Keep track of apiname, region, endpoint, finalRequestBody version
	public static Map<String, String> apiRequestConfigTestDataMap = new LinkedHashMap<String, String>();

	public static SoftAssert apiSoftAssert;

	public static Response response;
	public static JsonPath jsonPath;

	public static RequestSpecification request = given();

	
	public final static String CLASSNAME = "com.mysql.cj.jdbc.Driver";
	public static Connection connection = null;
	public static Statement statement = null;
	public static ResultSet rs = null;
	public static List<LinkedHashMap<String, String>> resultsetInListOfMaps = new ArrayList<>();

}
