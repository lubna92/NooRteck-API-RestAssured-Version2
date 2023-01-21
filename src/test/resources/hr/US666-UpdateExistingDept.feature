Feature: Update Existing Department API

  Scenario Outline: Verify User able to Update Existing Department
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
      | region  | apiName                      | statusCode | requestBody            | message              |
      | "scrum" | "Department:PutExistingDept" | "200"      | 'DeptTestData:scrum:0' | Updated successfully |
