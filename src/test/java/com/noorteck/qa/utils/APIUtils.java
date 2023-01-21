package com.noorteck.qa.utils;

import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileSystemUtils;
import org.json.JSONException;
import org.json.JSONObject;

import io.cucumber.core.gherkin.messages.internal.gherkin.internal.com.eclipsesource.json.Json;
import io.cucumber.datatable.DataTable;
import io.restassured.http.ContentType;

public class APIUtils extends Constants {

	/**
	 * This method sets up the header part before sending the request
	 * 
	 * @param dataTable
	 */
	public static void setUpHeaders(DataTable dataTable) {
		// Convert datatable to List of Map
		List<Map<String, String>> listMap = dataTable.asMaps(String.class, String.class);

		// Convert List Map to a Map by Looping through

		for (Map<String, String> map : listMap) {
			headerMap = map;
		}

		if (headerMap != null) {
			request = request.headers(headerMap);
		}
	}

	/**
	 * This method sets up the queryParameter part before sending the request
	 * 
	 * @param dataTable
	 */
	public static void setUpQueryParams(DataTable dataTable) {
		// Convert datatable to List of Map
		List<Map<String, String>> listMap = dataTable.asMaps(String.class, String.class);

		// Convert List Map to a Map by Looping through

		for (Map<String, String> map : listMap) {
			queryParamMap = map;
		}

		if (queryParamMap != null) {
			request = request.queryParams(queryParamMap);

		}
	}

	/**
	 * This method sends POST Request to server
	 */
	public static void postRequest() {
		System.out.println(apiRequestConfigTestDataMap.get("requestBody") + " ^^^^^^^");
		response = request

				.body(apiRequestConfigTestDataMap.get("requestBody"))
				.when()
				.post(apiRequestConfigTestDataMap.get("endpoint"))
				.then()
				.extract()
				.response();

		response.prettyPrint();
	}
	
	public static void putRequest() {
		System.out.println(apiRequestConfigTestDataMap.get("requestBody") + " ^^^^^^^");
		response = request

				.body(apiRequestConfigTestDataMap.get("requestBody"))
				.when()
				.put(apiRequestConfigTestDataMap.get("endpoint"))
				.then()
				.extract()
				.response();

		response.prettyPrint();
	}

	/**
	 * This method returns the status code
	 * 
	 * @return
	 */
	public static int statusCode() {
		System.out.println("Status Code: " + response.getStatusCode());
		return response.getStatusCode();
	}

	public static void loadApiInfo(String apiName, String region) {
		String baseUrl = null;
		String uri = null;
		String endpoint = null;

		envDataMap = JsonFileUtils.filterJson(region, prop.getProperty("Environment"), null);

		// Employee:PostNewEmp

		String apiInfo[] = apiName.split(":");

		hrApiEnvDataMap = JsonFileUtils.filterJson(apiInfo[0], prop.getProperty("HRApiServerData"), null);

		/**
		 * From hrApiEnvDataMap retrieve the baseUrl and the correct api path
		 */

		switch (region) {

		case "scrum":
			baseUrl = (String) hrApiEnvDataMap.get("baseScrumURL");
			uri = (String) hrApiEnvDataMap.get(apiInfo[1]);
			break;
		case "sit":
			baseUrl = (String) hrApiEnvDataMap.get("baseSitURL");
			uri = (String) hrApiEnvDataMap.get(apiInfo[1]);
			break;
		}

		endpoint = baseUrl + uri;
		apiRequestConfigTestDataMap.put("endpoint", endpoint);

		apiRequestConfigTestDataMap.put("moduloName", apiInfo[0]);

		apiRequestConfigTestDataMap.put("apiName", apiInfo[1]);

	}

	public static void setRequestBodyTestData(String regionName, String testDataFileName, Integer index)
			throws JSONException {

		requestBodyMap = JsonFileUtils.filterJson(regionName, prop.getProperty(testDataFileName), index);

		String requestBody = "";

		switch (apiRequestConfigTestDataMap.get("apiName")) {

		case "PostNewEmp":

			requestBody = getRequestBody(prop.getProperty("EmpRBody"), requestBodyMap);
			break;
		case "PostNewDept":
			requestBody = getRequestBody(prop.getProperty("DeptPostRBody"), requestBodyMap);
			break;
			
		case "PostNewJob":
			requestBody = getRequestBody(prop.getProperty("JobPostRBody"), requestBodyMap);
			break;
			
		case "PutExistingJob":
			requestBody = getRequestBody(prop.getProperty("JobPutRBody"), requestBodyMap);
			break;
			
		case "PutExistingDept":
			requestBody = getRequestBody(prop.getProperty("DeptPutRBody"), requestBodyMap);
			break;

		}

		apiRequestConfigTestDataMap.put("requestBody", requestBody);

	}

	/**
	 * This method takes requestBodyFile template and takes testdata for request
	 * body then then adds the test data to request body template and returns as
	 * string
	 * 
	 * @param requestBodyFileTempaltePath
	 * @param testDataMap
	 * @return
	 * @throws JSONException
	 */

	public static String getRequestBody(String requestBodyFileTempaltePath, Map<String, Object> testDataMap)
			throws JSONException {

		String jsonToString = JsonFileUtils.jsonToStrConvertion(requestBodyFileTempaltePath);

		// Convert to JSON object
		JSONObject obj = new JSONObject(jsonToString);

		for (Map.Entry<String, Object> map : testDataMap.entrySet()) {

			/**
			 * Note, value can be integer, boolean, double, string type our script needs to
			 * handle and update the request body accordingly
			 */

			String key = map.getKey();
			Object value = map.getValue();

			/**
			 * Before adding to JsonObj, check if the type of value each field accepts
			 */

			if (obj.get(key) instanceof Integer) {
				obj.put(key, Integer.valueOf((String) value));
			} else if (obj.get(key) instanceof Double) {
				obj.put(key, Double.valueOf((String) value));
			} else if (obj.get(key) instanceof Boolean) {
				obj.put(key, Boolean.valueOf((String) value));
			} else {
				obj.put(key, value);
			}

		}
		return obj.toString();
	}

	public static void removeFieldsFromRequeestBody(DataTable dataTable) throws JSONException {

		List<Map<String, String>> listMap = dataTable.asMaps(String.class, String.class);
		// Convert to JSON object
		JSONObject obj = new JSONObject(apiRequestConfigTestDataMap.get("requestBody"));

		for (Map<String, String> map : listMap) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				String field = entry.getValue();
				obj.remove(field);
			}
		}
		apiRequestConfigTestDataMap.put("requestBody", obj.toString());

	}
}