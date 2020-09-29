Feature: Placing Domino

  Background: 
    Given the game has been initialized for placing last domino

  Scenario: Player places domino during last turn
    Given it is the last turn of the game
    Given the current player is not the last player in the turn
    Given the player's kingdom has the following dominoes:
      | id | dominodir | posx | posy |
      |  7 | right     |    0 |    1 |
      | 14 | right     |    0 |    2 |
      | 23 | up        |   -2 |    0 |
      | 21 | left      |    2 |    0 |
      | 48 | down      |   -1 |    1 |
      |  1 | right     |   -2 |    2 |
      | 16 | down      |    0 |   -1 |
      | 22 | left      |   -1 |   -2 |
      | 46 | left      |   -1 |   -1 |
      | 41 | right     |    1 |   -1 |
      | 12 | right     |    1 |   -2 |
    Given the current player is preplacing his/her domino with ID 6 at location 2:2 with direction "down"
    And the preplaced domino has the status "CorrectlyPreplaced"
    When the current player places his/her domino
    Then the next player shall be placing his/her domino

  Scenario: Fourth player places domino during last turn
    Given it is the last turn of the game
    Given the current player is the last player in the turn
    Given the player's kingdom has the following dominoes:
      | id | dominodir | posx | posy |
      |  7 | right     |    0 |    1 |
      | 14 | right     |    0 |    2 |
      | 23 | up        |   -2 |    0 |
      | 21 | left      |    2 |    0 |
      | 48 | down      |   -1 |    1 |
      |  1 | right     |   -2 |    2 |
      | 16 | down      |    0 |   -1 |
      | 22 | left      |   -1 |   -2 |
      | 46 | left      |   -1 |   -1 |
      | 41 | right     |    1 |   -1 |
      | 12 | right     |    1 |   -2 |
    Given the current player is preplacing his/her domino with ID 6 at location 2:2 with direction "down"
    And the preplaced domino has the status "CorrectlyPreplaced"
    When the current player places his/her domino
    Then the game shall be finished
    Then the final results after successful placement shall be computed
