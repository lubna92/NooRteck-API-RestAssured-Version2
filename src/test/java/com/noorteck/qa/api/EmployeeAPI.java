package com.noorteck.qa.api;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.noorteck.qa.utils.Constants;
import com.noorteck.qa.utils.DBUtils;

import io.cucumber.datatable.DataTable;
import io.restassured.path.json.JsonPath;

public class EmployeeAPI {

	public static void empFieldLevelValidation(DataTable dataTable) {
		switch (Constants.apiRequestConfigTestDataMap.get("apiName")) {
		case "PostNewEmp":
			verifyResponseField(dataTable);
			break;
		}
	}

	public static void empDBLevelValidation() {
		switch (Constants.apiRequestConfigTestDataMap.get("apiName")) {
		case "PostNewEmp":
			verifyAgainstDB();
			break;
		}
	}

	public static void verifyResponseField(DataTable dataTable) {
		List<Map<String, String>> listMap = dataTable.asMaps(String.class, String.class);
		JsonPath jsonPath = Constants.response.jsonPath();

		for (Map<String, String> map : listMap) {

			for (Map.Entry<String, String> entry : map.entrySet()) {
				String expKey = entry.getKey();
				String expValue = entry.getValue();
				String actValue = jsonPath.getString(expKey);

				System.out.println(actValue);

				compareTwoValues(actValue, expValue);
			}
		}
	}

	public static void verifyAgainstDB() {
		List<LinkedHashMap<String, String>> expResultMaps = new ArrayList<>();
		
		String query = "SELECT * FROM hr_scrum.employees WHERE employee_id = " + Constants.requestBodyMap.get("employeeID") + ";";
		JsonPath jsonPath = Constants.response.jsonPath();
		int actID = jsonPath.getInt("id");

		// connect to database
		expResultMaps = DBUtils.makeDBRequest(query);
		for (LinkedHashMap<String, String> expResultMap : expResultMaps) {

			compareTwoValues(actID, expResultMap.get("employee_id"));
			compareTwoValues(Constants.requestBodyMap.get("firstName"), expResultMap.get("first_name"));
			compareTwoValues(Constants.requestBodyMap.get("lastName"), expResultMap.get("last_name"));
			compareTwoValues(Constants.requestBodyMap.get("email"), expResultMap.get("email"));
			compareTwoValues(Constants.requestBodyMap.get("phoneNumber"), expResultMap.get("phone_number"));
			compareTwoValues(Constants.requestBodyMap.get("hireDate"), expResultMap.get("hire_date"));
			compareTwoValues(Constants.requestBodyMap.get("jobId"), expResultMap.get("job_id"));
			compareTwoValues(Constants.requestBodyMap.get("comissionPct"), expResultMap.get("commission_pct"));
			compareTwoValues(Constants.requestBodyMap.get("managerId"), expResultMap.get("manager_id"));
			compareTwoValues(Constants.requestBodyMap.get("departmentId"), expResultMap.get("department_id"));
		}
		
		Constants.apiSoftAssert.assertAll();
	}

	public static void compareTwoValues(Object actualValue, String expValue) {
		Constants.apiSoftAssert.assertEquals(actualValue.toString().toLowerCase(), expValue.toLowerCase());
	}

}
