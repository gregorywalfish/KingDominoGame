Feature: Verify No Overlapping
  As a player, I want the Kingdomino app to automatically
    check that my current domino is not overlapping with existing dominoes.

  Background: 
    Given the game is initialized to check domino overlapping
    Given the following dominoes are present in a player's kingdom:
      | id | dominodir | posx | posy |
      | 27 | up        |    0 |    2 |
      | 17 | down      |    3 |    3 |
      |  1 | right     |    0 |    1 |
      | 22 | left      |    3 |    1 |
      | 46 | right     |   -1 |   -1 |

  Scenario Outline: Current preplaced domino does not overlap with existing dominoes in the kingdom
    Given the current player preplaced the domino with ID <id> at position <x>:<y> and direction "<direction>"
    When check current preplaced domino overlapping is initiated
    Then the current-domino/existing-domino overlapping is "<result>"

    Examples: 
      | id | x  | y  | direction | result  |
      | 15 |  1 |  2 | right     | valid   |
      | 15 |  2 |  2 | right     | invalid |
      | 20 |  2 |  2 | down      | invalid |
      | 12 | -1 |  0 | up        | valid   |
      | 12 | -1 |  0 | down      | invalid |
      | 44 |  2 |  0 | down      | valid   |
      | 42 |  2 |  2 | up        | valid   |
      | 29 |  1 |  2 | left      | invalid |
      | 48 | -1 |  3 | down      | valid   |
      | 43 |  1 | -1 | left      | invalid |
