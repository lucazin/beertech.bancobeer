*** Settings ***
Library  RequestsLibrary
Library  String
Library  BuiltIn
Library  Collections
Library  ../libraries/cnpj_generator.py
Library  OperatingSystem
Library  DateTime
Library  json

*** Keywords ***
I create an insert user JSON
    [Arguments]     ${cnpj}  ${email}  ${nome}  ${password}  ${phonenumber}  ${username}
    ${userJson}=    Create Dictionary
    ...             cnpj         ${cnpj}
    ...             email        ${email}
    ...             nome         ${nome}
    ...             password     ${password}
    ...             phonenumber  ${phonenumber}
    ...             username     ${username}
    Set Test Variable       ${userJson}

I send a post request
    [Arguments]     ${user_url}  ${user_uri}
    ${header}=      Create Dictionary
    ...             Content-Type    application/json
    Create Session    post   ${user_url}
    ${resp}=        Post Request    post    uri=${user_uri}  headers=${header}  data=${userJson}
    Set Test Variable       ${resp}
    Log                     ${resp}

the response status code should be "${statusCode}"
    Should Be Equal As Strings  ${statusCode}   ${resp.status_code}

the role should be equal "${role}"
    Should Be Equal  ${role}     ${resp.json()["roles"][0]["name"]}

the respose message should be equal "${expectedMessage}"
    Should Be Equal  ${expectedMessage}   ${resp.json()["message"]}

I create a valid user JSON
    ${currentDate}=     Get Current Date
    ${cnpj}=    Generate Cnpj  1
    I create an insert user JSON  ${cnpj}  ${cnpj}@teste.com  Welligton  ${currentDate}  123455678910  ${cnpj}

I create a user JSON with invalid CNPJ
    ${currentDate}=     Get Current Date
    I create an insert user JSON  12345  ${currentDate}@teste.com  Welligton  ${currentDate}  123455678910  ${currentDate}

I create a user JSON without phonenumber
    ${currentDate}=     Get Current Date
    ${cnpj}=    Generate Cnpj  1
    I create an insert user JSON  ${cnpj}  ${cnpj}@teste.com  Welligton  ${currentDate}  ${empty}  ${cnpj}