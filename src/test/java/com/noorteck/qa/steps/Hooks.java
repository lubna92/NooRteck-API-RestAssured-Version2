package com.noorteck.qa.steps;

import org.testng.asserts.SoftAssert;

import com.noorteck.qa.utils.Constants;
import com.noorteck.qa.utils.PropertyFileUtils;
 
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {
	
	@Before
	public void setUp() {
		
		//loading the propertyfile 
		Constants.prop  = PropertyFileUtils.getProperties(Constants.configFilePath);
		
		Constants.apiSoftAssert  = new SoftAssert();
		//TODO db
	}
	
	
	
	@After
	public void tearDown() {
		
		
		//close Region.json
		//closeHRAPiServerData.json
		//Close db connection
		
		Constants.apiSoftAssert.assertAll();
	}

}
