Feature: Discard domino
  As a player, I wish to discard a domino if it cannot be placed to my kingdom in a valid way

  Background: 
    Given the game is initialized for discard domino

  Scenario Outline: An arbitrary player attempts to discard a domino, with an almost complete 5x5 grid
    Given the player's kingdom has the following dominoes:
      | id | dominodir | posx | posy |
      |  7 | left      |    2 |    1 |
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
    Given domino <id> is in the current draft
    Given the current player has selected domino <id>
    Given the player preplaces domino <id> at its initial position
    When the player attempts to discard the selected domino
    Then domino <id> shall have status "<dstatus>"

    Examples: 
      | id | dstatus              |
      | 48 | erroneouslyPreplaced |
      | 45 | erroneouslyPreplaced |
      | 37 | erroneouslyPreplaced |
      | 33 | erroneouslyPreplaced |
      |  8 | erroneouslyPreplaced |
      | 41 | discarded            |
      | 44 | discarded            |
      | 27 | discarded            |
      | 29 | discarded            |
      |  2 | discarded            |

  Scenario Outline: An arbitrary player attempts to discard a domino, with 4 dominoes surrounding the kindom's castle
    Given the player's kingdom has the following dominoes:
      | id | dominodir | posx | posy |
      |  1 | down      |    0 |    2 |
      |  2 | up        |    0 |   -2 |
      |  3 | up        |    1 |    0 |
      |  4 | up        |   -1 |    0 |
    Given domino <id> is in the current draft
    Given the current player has selected domino <id>
    Given the player preplaces domino <id> at its initial position
    When the player attempts to discard the selected domino
    Then domino <id> shall have status "<dstatus>"

    Examples: 
      | id | dstatus              |
      | 13 | erroneouslyPreplaced |
      | 16 | erroneouslyPreplaced |
      | 18 | erroneouslyPreplaced |
      | 27 | erroneouslyPreplaced |
      | 43 | erroneouslyPreplaced |
      | 47 | discarded            |
      | 46 | discarded            |
      | 44 | discarded            |
      |  7 | discarded            |
      | 10 | discarded            |
