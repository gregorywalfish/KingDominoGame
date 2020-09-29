Feature: Calculate ranking
  As a player, I want the Kingdomino app to automatically calculate the ranking 
  in order to know the winner of a finished game. (F23)

  Background: 
    Given the game is initialized for calculate ranking

  Scenario: Determine standings without tiebreak
    Given the players have the following two dominoes in their respective kingdoms:
      | player | domino1 | dominodir1 | posx1 | posy1 | domino2 | dominodir2 | posx2 | posy2 |
      | blue   |       1 | down       |     0 |     2 |       2 | up         |     1 |     0 |
      | green  |      48 | down       |     0 |     2 |      47 | up         |     1 |     0 |
      | pink   |       3 | down       |     0 |     2 |      19 | up         |     1 |     0 |
      | yellow |      23 | down       |     0 |     2 |      21 | up         |     1 |     0 |
    Given the players have no tiebreak
    When calculate ranking is initiated
    Then player standings shall be the followings:
      | player | standing |
      | green  |        1 |
      | yellow |        2 |
      | pink   |        3 |
      | blue   |        4 |
