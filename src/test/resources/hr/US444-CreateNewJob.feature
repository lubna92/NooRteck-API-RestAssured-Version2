Feature: Create New Job API

  Scenario Outline: Verify User able to create new Job
    Given User set <apiName> <region> webservice api
    When User sets Header Parameters
      | Content-Type     |
      | application/json |
    And User sets request body <requestBody>
    When User sends POST request
    Then User validates status code <statusCode>
    And User validates response body field
      | message   |
      | <message> |
    And User validates cross validates against application database

    @scrum1
    Examples: 
      | region  | apiName          | statusCode | requestBody           | message              |
      | "scrum" | "Job:PostNewJob" | "200"      | 'JobTestData:scrum:0' | Successfully created |
