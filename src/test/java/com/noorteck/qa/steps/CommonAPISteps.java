package com.noorteck.qa.steps;

import org.json.JSONException;

import com.noorteck.qa.api.DepartmentAPI;
import com.noorteck.qa.api.EmployeeAPI;
import com.noorteck.qa.api.JobAPI;
import com.noorteck.qa.utils.APIUtils;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CommonAPISteps extends APIUtils {

	@Given("User set {string} {string} webservice api")
	public void setUpAPI(String apiName, String region) {

		loadApiInfo(apiName, region);
	}

	@When("User sets Header Parameters")
	public void setHeaders(DataTable dataTable) {
		setUpHeaders(dataTable);
	}

	@When("User sets Query Parameters")
	public void setQueryParams(DataTable dataTable) {
		setUpQueryParams(dataTable);
	}

	@When("User sets request body {string}")
	public void setRequestBody(String requestBodyInfo) throws JSONException {

		String requestBodyArr[] = requestBodyInfo.split(":");

		String testDataFileName = requestBodyArr[0];
		String regionName = requestBodyArr[1];
		Integer index = Integer.valueOf(requestBodyArr[2]);

		setRequestBodyTestData(regionName, testDataFileName, index);
	}

	@When("User removes required field from request body")
	public void removeRequestBodyFields(DataTable dataTable) throws JSONException {

		removeFieldsFromRequeestBody(dataTable);

	}

	@When("User sends POST request")
	public void makePOSTrequest() {

		// call method
		postRequest();
	}

	@When("User sends PUT request")
	public void makePUTrequest() {

		// call method
		putRequest();
	}

	@Then("User validates status code {string}")
	public void validateStatusCode(String statusCode) {

		int expStatusCode = Integer.parseInt(statusCode);
		int actStatusCode = statusCode();

		apiSoftAssert.assertEquals(actStatusCode, expStatusCode, "Status Code Verification FAILED");

	}

	@Then("User validates response body field")
	public void validateResponseBody(DataTable dataTable) {

		switch (apiRequestConfigTestDataMap.get("moduloName")) {

		case "Employee":
			EmployeeAPI.empFieldLevelValidation(dataTable);
			break;
		case "Department":
			DepartmentAPI.deptFieldLevelValidation(dataTable);
			break;
		case "Job":
			JobAPI.jobFieldLevelValidation(dataTable);
			break;
		}
	}

	@Then("User validates cross validates against application database")
	public void crossValidateAgainstAppDB() {
		switch (apiRequestConfigTestDataMap.get("moduloName")) {

		case "Employee":
			EmployeeAPI.empDBLevelValidation();
			break;
		case "Department":
			DepartmentAPI.deptDBLevelValidation();
			break;
		case "Job":
			JobAPI.jobDBLevelValidation();
			break;
		}
	}
}