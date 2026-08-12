Feature: Login to CRM
  As a user, I want to be able to log into the CRM system
  So that I can manage customer information

  Scenario: Successful login
    Given Navigate to login page
    When Enter username "Admin" and password "admin123"
    And Click the Login button
    Then Navigate to the Dashboard page