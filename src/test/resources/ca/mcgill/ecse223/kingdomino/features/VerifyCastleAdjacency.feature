Feature: Verify Castle Adjacency
  As a player, I want the Kingdomino app to automatically check if my
    current domino is placed next to my castle

  Background: 
    Given the game is initialized for castle adjacency

  Scenario Outline: Current domino is adjacent to the castle
    Given the current player preplaced the domino with ID 1 at position <x>:<y> and direction "<direction>"
    When check castle adjacency is initiated
    Then the castle/domino adjacency is "<result>"

    Examples: 
      | x  | y  | direction | result  |
      |  0 |  0 | up        | invalid |
      |  1 |  0 | right     | valid   |
      |  0 |  1 | left      | valid   |
      | -1 |  0 | down      | valid   |
      |  0 | -1 | right     | valid   |
      | -2 |  0 | right     | valid   |
      | -1 | -1 | up        | valid   |
      |  1 |  1 | right     | invalid |
      |  1 |  1 | down      | valid   |
      |  1 | -1 | left      | valid   |
