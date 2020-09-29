Feature: Resolve Tiebreak
  As a player, I want the Kingdomino app to automatically resolve a potential tiebreak 
  (i.e. equal scorebetween players) 
  by evaluating the most extended (largest)property and 
  then the total number of crowns.

  Background: 
    Given the game is initialized for resolve tiebreak

  Scenario: Resolve tiebreak for first place based on their largest property
    Given the players have the following two dominoes in their respective kingdoms:
      | player | domino1 | dominodir1 | posx1 | posy1 | domino2 | dominodir2 | posx2 | posy2 |
      | blue   |       1 | down       |     0 |     2 |       2 | up         |     1 |     0 |
      | green  |      40 | down       |     0 |     2 |      47 | up         |     1 |     0 |
      | pink   |       3 | down       |     0 |     2 |      19 | up         |     1 |     0 |
      | yellow |      42 | down       |     0 |     2 |      10 | up         |     1 |     0 |
    When calculate ranking is initiated
    Then player standings should be the followings:
      | player | standing |
      | yellow |        1 |
      | green  |        2 |
      | pink   |        3 |
      | blue   |        4 |

  Scenario: Resolve tiebreak for first place based on total number of crowns of players
    Given the players have the following two dominoes in their respective kingdoms:
      | player | domino1 | dominodir1 | posx1 | posy1 | domino2 | dominodir2 | posx2 | posy2 |
      | blue   |       1 | down       |     0 |     2 |       2 | up         |     1 |     0 |
      | green  |      16 | down       |     0 |     2 |      39 | up         |     1 |     0 |
      | pink   |       3 | down       |     0 |     2 |      19 | up         |     1 |     0 |
      | yellow |      42 | down       |     0 |     2 |       9 | up         |     1 |     0 |
    When calculate ranking is initiated
    Then player standings should be the followings:
      | player | standing |
      | yellow |        1 |
      | green  |        2 |
      | pink   |        3 |
      | blue   |        4 |

  Scenario: Tiebreak for first place results in shared victory
    Given the players have the following two dominoes in their respective kingdoms:
      | player | domino1 | dominodir1 | posx1 | posy1 | domino2 | dominodir2 | posx2 | posy2 |
      | blue   |       1 | down       |     0 |     2 |       2 | up         |     1 |     0 |
      | green  |      16 | down       |     0 |     2 |      39 | up         |     1 |     0 |
      | pink   |       3 | down       |     0 |     2 |      19 | up         |     1 |     0 |
      | yellow |      35 | down       |     0 |     2 |      14 | up         |     1 |     0 |
    When calculate ranking is initiated
    Then player standings should be the followings:
      | player | standing |
      | yellow |        1 |
      | green  |        1 |
      | pink   |        2 |
      | blue   |        3 |
