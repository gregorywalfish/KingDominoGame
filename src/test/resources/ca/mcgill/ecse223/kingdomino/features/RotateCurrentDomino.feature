Feature: Rotate Current Domino
  As a player, I wish to evaluate a provisional placement of my current domino in my kingdom by rotating it (clockwise or counter-clockwise) (F12)

  Background: 
    Given the game is initialized for rotate current domino

  Scenario Outline: Player rotates a tentatively placed domino
    Given it is "<player>"'s turn
    Given "<player>"'s kingdom has following dominoes:
      | id | dir   | posx | posy |
      |  1 | right |    1 |    0 |
      | 12 | left  |    1 |   -1 |
      | 46 | right |    1 |   -2 |
      | 28 | right |   -2 |   -1 |
      | 18 | up    |   -1 |    0 |
    Given "<player>" has selected domino <id>
    Given domino <id> is tentatively placed at position <posx>:<posy> with direction "<dir>"
    When "<player>" requests to rotate the domino with "<rotation>"
    Then the domino <id> is still tentatively placed at <posx>:<posy> but with new direction "<newDir>"
    Then the domino <id> should have status "<dstatus>"

    Examples: 
      | player | id | posx | posy | dir   | rotation         | newDir | dstatus              |
      | pink   | 48 |    1 |    1 | right | clockwise        | down   | ErroneouslyPreplaced |
      | pink   | 48 |    1 |    1 | down  | clockwise        | left   | CorrectlyPreplaced   |
      | pink   | 48 |    1 |    1 | left  | clockwise        | up     | CorrectlyPreplaced   |
      | pink   | 48 |    1 |    1 | up    | clockwise        | right  | CorrectlyPreplaced   |
      | yellow | 22 |   -2 |   -1 | up    | counterclockwise | left   | ErroneouslyPreplaced |
      | yellow | 22 |   -2 |   -1 | left  | counterclockwise | down   | ErroneouslyPreplaced |
      | yellow | 22 |   -2 |   -1 | down  | counterclockwise | right  | ErroneouslyPreplaced |
      | yellow | 22 |   -2 |   -1 | right | counterclockwise | up     | ErroneouslyPreplaced |
      | blue   |  8 |    2 |   -1 | up    | counterclockwise | left   | ErroneouslyPreplaced |
      | blue   |  8 |    2 |   -1 | up    | clockwise        | right  | ErroneouslyPreplaced |
      | green  | 10 |    0 |    2 | left  | counterclockwise | down   | CorrectlyPreplaced   |
      | green  | 10 |    0 |    2 | down  | clockwise        | left   | CorrectlyPreplaced |

  Scenario Outline: Player attempts to rotate the tentatively placed domino but fails due to board size restrictions
    Given it is "<player>"'s turn
    Given "<player>" has selected domino <id>
    Given "<player>"'s kingdom has following dominoes:
      | id | dir   | posx | posy |
      |  1 | right |    1 |    0 |
      | 13 | down  |    1 |   -1 |
      | 28 | up    |    1 |   -4 |
      |  5 | right |    2 |   -3 |
      |  7 | right |    2 |   -4 |
    Given domino <id> is tentatively placed at position <posx>:<posy> with direction "<dir>"
    Given domino <id> has status "<dstatus>"
    When "<player>" requests to rotate the domino with "<rotation>"
    Then domino <id> is tentatively placed at the same position <posx>:<posy> with the same direction "<dir>"
    Then domino <id> should still have status "<dstatus>"

    Examples: 
      | player | id | posx | posy | dir   | rotation         | dstatus              |
      | pink   | 48 |   -4 |    0 | down  | clockwise        | ErroneouslyPreplaced |
      | pink   | 48 |    0 |   -4 | right | clockwise        | ErroneouslyPreplaced |
      | yellow |  4 |    4 |   -4 | up    | clockwise        | CorrectlyPreplaced   |
      | pink   | 48 |    2 |    4 | left  | clockwise        | ErroneouslyPreplaced |
      | yellow | 23 |   -4 |    0 | up    | counterclockwise | ErroneouslyPreplaced |
      | yellow | 23 |    0 |   -4 | left  | counterclockwise | ErroneouslyPreplaced |
      | blue   |  4 |    4 |   -3 | down  | counterclockwise | CorrectlyPreplaced   |
      | blue   | 23 |   -2 |    4 | right | counterclockwise | ErroneouslyPreplaced |

