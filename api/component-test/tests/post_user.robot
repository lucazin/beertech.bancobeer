*** Settings ***
Resource    ../commons/commons_keywords.robot
Resource    ../commons/commons_variables.robot

*** Test Cases ***
I send a post request and the user was succesfully created
    Given I create a valid user JSON
    When I send a post request    ${user_url}  ${user_uri}
    Then the response status code should be "201"

I send a post request and the user was succesfully created with user role
    Given I create a valid user JSON
    When I send a post request    ${user_url}  ${user_uri}
    Then the response status code should be "201"
    And the role should be equal "ROLE_USER"

I send a post request with invalid CNPJ
    Given I create a user JSON with invalid CNPJ
    When I send a post request    ${user_url}  ${user_uri}
    Then the response status code should be "400"
    And the respose message should be equal "Erro: CNPJ inválido!"

I send a post request without phonenumber
    Given I create a user JSON without phonenumber
    When I send a post request    ${user_url}  ${user_uri}
    Then the response status code should be "400"
    And the respose message should be equal "Erro: O número de telefone é obrigatório!"
