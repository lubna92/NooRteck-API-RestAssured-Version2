package com.noorteck.qa.api;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.noorteck.qa.utils.Constants;
import com.noorteck.qa.utils.DBUtils;

import io.cucumber.datatable.DataTable;
import io.restassured.path.json.JsonPath;

public class DepartmentAPI {
	public static void deptFieldLevelValidation(DataTable dataTable) {
		switch (Constants.apiRequestConfigTestDataMap.get("apiName")) {

		case "PostNewDept":
			EmployeeAPI.verifyResponseField(dataTable);
			break;
		
	case "PutExistingDept":
		EmployeeAPI.verifyResponseField(dataTable);
		break;
	}
	}
	public static void deptDBLevelValidation() {
		switch (Constants.apiRequestConfigTestDataMap.get("apiName")) {
			
		case "PostNewDept":		
			JsonPath jsonPath = Constants.response.jsonPath();
			Constants.requestBodyMap.put("departmentId",jsonPath.getInt("departmentId"));		
			verifyAgainstDB();
			break;	
			
		case "PutExistingDept":
			
			verifyAgainstDB();
			break;
		}
	}
	public static void verifyAgainstDB() {
		List<LinkedHashMap<String, String>> expResultMaps = new ArrayList<>();

		String query = "SELECT * FROM hr_scrum.departments WHERE department_id = " + Constants.requestBodyMap.get("departmentId") + ";";			
	
		
		// connect to database
		expResultMaps = DBUtils.makeDBRequest(query);
		for (LinkedHashMap<String, String> expResultMap : expResultMaps) {

			EmployeeAPI.compareTwoValues(Constants.requestBodyMap.get("departmentId"), expResultMap.get("department_id"));
			EmployeeAPI.compareTwoValues(Constants.requestBodyMap.get("departmentName"), expResultMap.get("department_name"));
			EmployeeAPI.compareTwoValues(Constants.requestBodyMap.get("managerId"), expResultMap.get("manager_id"));
			EmployeeAPI.compareTwoValues(Constants.requestBodyMap.get("locationId"), expResultMap.get("location_id"));
		}

	}
}
