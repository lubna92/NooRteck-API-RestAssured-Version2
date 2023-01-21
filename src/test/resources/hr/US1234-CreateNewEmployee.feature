Feature: Create New Employee API

  Scenario Outline: Verify User able to create new employee
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
      | region  | apiName               | statusCode | requestBody           | message          |
      | "scrum" | "Employee:PostNewEmp" | "201"      | 'EmpTestData:scrum:0' | Created employee |

  Scenario Outline: Verify Response Error message
    Given User set <apiName> <region> webservice api
    When User sets Header Parameters
      | Content-Type     |
      | application/json |
    And User sets request body <requestBody>
    And User removes required field from request body
      | field_name   |
      | <field_name> |
    When User sends POST request
    Then User validates status code <statusCode>
    And User validates response body field
      | message   | errors[0].msg | errors[0].param | errors[0].location |
      | <message> | <msg>         | <param>         | <location>         |

    @Stage
    Examples: 
      | region  | apiName               | statusCode | requestBody           | field_name   | message          | msg           | param        | location |
      | "scrum" | "Employee:PostNewEmp" | "422"      | 'EmpTestData:scrum:0' | departmentId | Validation error | Invalid value | departmentId | body     |
      | "scrum" | "Employee:PostNewEmp" | "422"      | 'EmpTestData:scrum:0' | managerId    | Validation error | Invalid value | managerId    | body     |
      | "scrum" | "Employee:PostNewEmp" | "422"      | 'EmpTestData:scrum:0' | salary       | Validation error | Invalid value | salary       | body     |
