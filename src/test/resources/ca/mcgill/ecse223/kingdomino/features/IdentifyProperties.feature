Feature: Identify Kingdom Properties
   As a player, I want the Kingdomino app to automatically determine each properties of my kingdom so that my score can be calculated

  Background: 
    Given the game is initialized for identify properties

  Scenario: An arbitrary player identifies their properties on a complete 5x5 kingdom
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
    When the properties of the player are identified
    Then the player shall have the following properties:
      | type     | dominoes    |
      | wheat    |       20,31 |
      | wheat    | 22,24,30,40 |
      | wheat    |    23,38,43 |
      | lake     |     7,30,31 |
      | lake     |     8,20,32 |
      | forest   |       24,32 |
      | swamp    |    22,38,43 |
      | mountain |          23 |
      | mountain |          40 |

  Scenario: An arbitrary player identifies their properties on an incomplete kingdom
    Given the player's kingdom has the following dominoes:
      | id | dominodir | posx | posy |
      |  1 | up        |   -4 |   -1 |
      |  9 | right     |   -1 |    1 |
      | 10 | left      |   -1 |   -3 |
      | 12 | up        |   -4 |   -3 |
      | 13 | left      |    0 |   -1 |
      | 16 | left      |   -1 |   -2 |
      | 21 | up        |    0 |   -3 |
      | 23 | left      |   -3 |    1 |
      | 24 | right     |   -2 |    0 |
      | 26 | right     |   -3 |   -1 |
      | 38 | up        |   -3 |   -3 |
    When the properties of the player are identified
    Then the player shall have the following properties:
      | type     | dominoes |
      | wheat    |  1,23,26 |
      | wheat    |       38 |
      | wheat    |       24 |
      | wheat    | 13,16,21 |
      | lake     |        9 |
      | forest   | 13,24,26 |
      | swamp    | 12,38,16 |
      | mountain |       23 |
      | grass    |    10,21 |
