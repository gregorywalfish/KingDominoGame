Feature: Verify kingdom grid size
  As a player, I want the Kingdomino app to automatically check if the grid of my kingdom has not yet exceeded a square of 5x5 tiles (including my castle)

  Background: 
    Given the game is initialized for verify grid size

  Scenario: An arbitrary player verifies their Kingdom grid size with a complete 5x5 grid
    Given the player's kingdom has the following dominoes:
      | id | dominodir | posx | posy |
      |  7 | left      |    2 |    1 |
      |  8 | up        |   -1 |   -3 |
      | 20 | up        |   -1 |   -1 |
      | 22 | up        |    3 |   -2 |
      | 23 | right     |    0 |   -3 |
      | 24 | left      |    2 |   -1 |
      | 30 | up        |    3 |    0 |
      | 31 | right     |   -1 |    1 |
      | 32 | down      |    0 |   -1 |
      | 38 | right     |    1 |   -2 |
      | 40 | left      |    2 |    0 |
      | 43 | right     |    2 |   -3 |
    When validation of the grid size is initiated
    Then the grid size of the player's kingdom shall be "valid"

  Scenario Outline: An arbitrary player adds various dominoes to their kingodom and validates the grid size
    Given the player's kingdom has the following dominoes:
      | id | dominodir | posx | posy |
      |  8 | left      |    0 |   -1 |
      | 20 | right     |   -1 |    1 |
      | 22 | left      |    2 |    0 |
      | 38 | up        |    3 |   -1 |
      | 40 | up        |    0 |    2 |
    Given the  player preplaces domino <id> to their kingdom at position <posx>:<posy> with direction "<dominodir>"
    When validation of the grid size is initiated
    Then the grid size of the player's kingdom shall be "<result>"

    Examples: 
      | id | dominodir | posx | posy | result  |
      | 41 | up        |    1 |    1 | valid   |
      |  6 | up        |   -1 |    2 | valid   |
      | 43 | left      |    1 |    1 | valid   |
      | 15 | left      |    2 |   -1 | valid   |
      | 16 | down      |    3 |    2 | valid   |
      | 43 | down      |    4 |    0 | invalid |
      | 42 | right     |   -1 |   -2 | invalid |
      | 23 | left      |    0 |    4 | invalid |
      | 21 | left      |    3 |   -2 | invalid |
      | 20 | up        |   -2 |    1 | invalid |
