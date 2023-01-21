package com.noorteck.qa.api;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.noorteck.qa.utils.Constants;
import com.noorteck.qa.utils.DBUtils;

import io.cucumber.datatable.DataTable;
import io.restassured.path.json.JsonPath;

public class JobAPI {
	
	public static void jobFieldLevelValidation(DataTable dataTable) {
		switch (Constants.apiRequestConfigTestDataMap.get("apiName")) {
		case "PostNewJob":
			
		case "PutExistingJob":
			EmployeeAPI.verifyResponseField(dataTable);
			break;
		}
		
	}
	public static void jobDBLevelValidation() {
		switch (Constants.apiRequestConfigTestDataMap.get("apiName")) {
		case "PostNewJob":
			JsonPath jsonPath = Constants.response.jsonPath();
			Constants.requestBodyMap.put("jobId", jsonPath.getString("jobId"));
			verifyAgainstDB();
			
			break;
			
		case "PutExistingJob":
			verifyAgainstDB();
			break;
		}
	}

	public static void verifyAgainstDB() {
		List<LinkedHashMap<String, String>> expResultMaps = new ArrayList<>();
		
		String query = "SELECT * FROM hr_scrum.jobs WHERE job_id = " + Constants.requestBodyMap.get("jobId") + ";";
		

		// connect to database
		expResultMaps = DBUtils.makeDBRequest(query);
		for (LinkedHashMap<String, String> expResultMap : expResultMaps) {

			EmployeeAPI.compareTwoValues(Constants.requestBodyMap.get("jobId"), expResultMap.get("job_id"));
			EmployeeAPI.compareTwoValues(Constants.requestBodyMap.get("jobTitle"), expResultMap.get("job_title"));
			EmployeeAPI.compareTwoValues(Constants.requestBodyMap.get("minSalary"), expResultMap.get("min_salary"));
			EmployeeAPI.compareTwoValues(Constants.requestBodyMap.get("maxSalary"), expResultMap.get("max_salary"));
		
		}
		

	}
}

