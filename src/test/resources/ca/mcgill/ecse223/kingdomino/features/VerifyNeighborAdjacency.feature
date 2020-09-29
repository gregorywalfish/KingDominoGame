Feature: Verify Neighbor Adjacency
  As a player, I want the Kingdomino app to automatically check if my
    current domino is placed to an adjacent territory.

  Background: 
    Given the game is initialized for neighbor adjacency
    Given the following dominoes are present in a player's kingdom:
      | id | dominodir | posx | posy |
      | 27 | up        |    0 |    2 |
      | 38 | down      |    3 |    3 |
      |  1 | right     |    0 |    1 |
      | 22 | left      |    3 |    1 |
      | 46 | right     |   -1 |   -1 |

  Scenario Outline: Current domino is placed in adjacent territory with existing dominoes
    Given the current player preplaced the domino with ID <id> at position <x>:<y> and direction "<direction>"
    When check current preplaced domino adjacency is initiated
    Then the current-domino/existing-domino adjacency is "<result>"

    Examples: 
      | id | x  | y  | direction | result  |
      | 15 |  1 |  2 | right     | valid   |
      | 15 |  1 |  3 | down      | invalid |
      | 20 |  2 |  2 | left      | valid   |
      | 12 | -1 |  0 | up        | valid   |
      | 12 | -1 |  1 | up        | invalid |
      | 44 |  2 |  0 | down      | invalid |
      | 42 |  2 |  2 | up        | invalid |
      | 29 |  1 |  2 | up        | valid   |
      | 48 | -1 |  3 | down      | invalid |
      | 43 |  1 | -1 | right     | invalid |
