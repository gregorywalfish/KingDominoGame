Feature: Calculating Player Score

  Background: 
    Given the game is initialized for calculating player score

  Scenario: Update player score upon placing the first domino
    Given the current player has no dominoes in his/her kingdom yet
    Given the score of the current player is 0
    Given the current player is preplacing his/her domino with ID 19 at location 1:1 with direction "left"
    And the preplaced domino has the status "CorrectlyPreplaced"
    When the current player places his/her domino
    Then the score of the current player shall be 1

  Scenario: Update player score upon placing a domino
    Given the game has no bonus options selected
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
    Given the score of the current player is 28
    Given the current player is preplacing his/her domino with ID 30 at location 2:2 with direction "down"
    And the preplaced domino has the status "CorrectlyPreplaced"
    When the current player places his/her domino
    Then the score of the current player shall be 32

  Scenario: Update player score upon discarding a domino
    Given the game has no bonus options selected
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
    Given the score of the current player is 28
    Given the current player is placing his/her domino with ID 3
    And it is impossible to place the current domino in his/her kingdom
    When the current player discards his/her domino
    Then the score of the current player shall be 28
