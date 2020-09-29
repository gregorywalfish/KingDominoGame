Feature: Place Domino
  As a player, I wish to place my selected domino to my Kindom.
  If I am satisfied with its placement, and its current position respects the adjacency rules,
  I wish to finalize the placement. (Actual checks of adjacency conditions are implemented as separate features) (F13)

  Background: 
    Given the game is initialized for move current domino

  Scenario Outline: Player places a domino
    Given it is "<player>"'s turn
    Given the "<player>"'s kingdom has the following dominoes:
      | domino | dominodir | posx | posy |
      |      7 | right     |    0 |    2 |
      |     14 | right     |    0 |    1 |
      |     21 | up        |   -1 |    1 |
    Given "<player>" has selected domino <id>
    Given domino <id> is tentatively placed at position <posx>:<posy> with direction "<dir>"
    Given domino <id> is in "CorrectlyPreplaced" status
    When "<player>" requests to place the selected domino <id>
    Then "<player>"'s kingdom should now have domino <id> at position <posx>:<posy> with direction "<dir>"

    Examples: 
      | player | id | posx | posy | dir   |
      | pink   |  6 |    2 |    1 | up    |
      | pink   | 16 |   -2 |   -2 | down  |
      | blue   | 18 |   -2 |    0 | right |
      | blue   | 32 |    2 |    0 | left  |
