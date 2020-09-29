Feature: Calculate player score
  As a player, I want the Kingdomino app 
  to automatically calculate the score for each player 
  by summing up their property scores and their bonus scores. (F22)

  Background: 
    Given the game is initialized for calculate player score

  Scenario Outline: 
    Given the player's kingdom has the following dominoes:
      | id | dominodir | posx | posy |
      |  7 | right     |    0 |    1 |
      |  6 | down      |    2 |    2 |
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
    Given the game has "<bonus>" bonus option
    When calculate player score is initiated
    Then the total score should be <playerscore>

    Examples: 
      | bonus          | playerscore |
      | no             |          28 |
      | Middle Kingdom |          38 |
      | Harmony        |          33 |
