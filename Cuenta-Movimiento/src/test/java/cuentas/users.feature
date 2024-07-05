Feature: sample karate test script
  for help, see: https://github.com/karatelabs/karate/wiki/IDE-Support

  Background:
    * url 'http://localhost:8140'

  Scenario: get all moves
    Given path 'movimiento/findAll'
    When method get
    Then status 200

    * def first = response.payload[0]


  Scenario: get all accounts from one client
    Given path 'cuenta/'
    And params {request:1}
    When method get
    Then status 200

    * def first = response.payload[0]


