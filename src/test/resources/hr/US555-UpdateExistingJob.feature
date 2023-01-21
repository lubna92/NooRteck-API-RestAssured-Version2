Feature: Update Existing Job API

  Scenario Outline: Verify User able to Update Existing Job
    Given User set <apiName> <region> webservice api
    When User sets Header Parameters
      | Content-Type     |
      | application/json |
    And User sets request body <requestBody>
    When User sends PUT request
    Then User validates status code <statusCode>
    And User validates response body field
      | message   |
      | <message> |
    And User validates cross validates against application database

    @scrum1
    Examples: 
      | region  | apiName                 | statusCode | requestBody           | message              |
      | "scrum" | "Job:PutExistingJob" | "200"      | 'JobTestData:scrum:1' | Successfully updated |
