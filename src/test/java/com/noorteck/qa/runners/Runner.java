package com.noorteck.qa.runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)

@CucumberOptions(
		          features = "src/test/resources/hr/", 
                  glue = "com.noorteck.qa.steps", 
                  tags = "@Stage", 
                  plugin = {
		                    "summary", "pretty", "html:target/cucumber-reports.html",
		                    "json:target/cucumber-reports",
		                     "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" 
		                    },
                  monochrome = true
                  )
public class Runner {

}
