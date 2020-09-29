Feature: Calculate bonus scores
  As a player, I want the Kingdomino app to automatically calculate the bonus scores (for Harmony and middle Kingdom) 
  if those bonus scores were selected as agame option. (F21)

  Background: 
    Given the game is initialized for calculate bonus scores
    Given the player's kingdom has the following dominoes:
      | id | dominodir | posx | posy |
      |  7 | right     |    0 |    1 |
      |  6 | down      |    2 |    2 |
      | 14 | right     |    0 |    2 |
      | 23 | up        |   -2 |    0 |
      | 21 | left      |    2 |    0 |
      | 48 | down      |   -1 |    1 |
      |  1 | right     |   -2 |    2 |

  Scenario Outline: Only Middle Kingdom bonus points
    Given Middle Kingdom is selected as bonus option
    Given the player's kingdom also includes the domino <id> at position <posx>:<posy> with the direction "<dominodir>"
    When calculate bonus score is initiated
    Then the bonus score should be <bonus>

    Examples: 
      | id | dominodir | posx | posy | bonus |
      | 16 | right     |    0 |   -1 |     0 |
      | 16 | down      |    0 |   -1 |    10 |

  Scenario: Only Harmony bonus points
    Given Harmony is selected as bonus option
    Given the player's kingdom has the following dominoes:
      | id | dominodir | posx | posy |
      | 16 | down      |    0 |    4 |
      | 22 | left      |   -1 |    3 |
      | 46 | left      |   -1 |    4 |
      | 41 | right     |    1 |    4 |
      | 12 | right     |    1 |    3 |
    When calculate bonus score is initiated
    Then the bonus score should be 5

  Scenario: Both Harmony and Middle Kingdom bonus points
    Given Harmony is selected as bonus option
    Given Middle Kingdom is selected as bonus option
    Given the player's kingdom has the following dominoes:
      | id | dominodir | posx | posy |
      | 16 | down      |    0 |   -1 |
      | 22 | left      |   -1 |   -2 |
      | 46 | left      |   -1 |   -1 |
      | 41 | right     |    1 |   -1 |
      | 12 | right     |    1 |   -2 |
    When calculate bonus score is initiated
    Then the bonus score should be 15
