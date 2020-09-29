Feature: Choose next domino
    As a player, I wish to be able to choose a designated domino from the next
    draft assuming that this domino has not yet been chosen by any other players

  Background: 
    Given the game is initialized for choose next domino

  #The selection variable represents what players have kings on which dominoes in the draft respectively
  Scenario Outline: Player choses a free domino
    Given the next draft is sorted with dominoes "<nextdraft>"
    Given player's domino selection "<selection>"
    Given the current player is "<currentplayer>"
    When current player chooses to place king on <chosendominoid>
    Then current player king now is on "<chosendominoid>"
    Then the selection for next draft is now equal to "<newselection>"

    Examples: 
      | nextdraft | selection               | chosendominoid | currentplayer | newselection           |
      |   5,6,7,8 | green,none,yellow,none  |              8 | pink          | green,none,yellow,pink |
      |   5,6,7,8 | none,none,yellow,none   |              8 | pink          | none,none,yellow,pink  |
      |   5,6,7,8 | none,none,none,none     |              8 | pink          | none,none,none,pink    |
      |   5,6,7,8 | green,blue,yellow,none  |              8 | pink          | green,blue,yellow,pink |

  Scenario Outline: Player choses an occupied domino
    Given the next draft is sorted with dominoes "<nextdraft>"
    Given player's domino selection "<selection>"
    Given the current player is "<currentplayer>"
    When current player chooses to place king on <chosendominoid>
    Then the selection for the next draft selection is still "<selection>"

    Examples: 
      | nextdraft | selection              | chosendominoid | currentplayer |
      |   5,6,7,8 | green,none,yellow,none |              7 | pink          |
      |   5,6,7,8 | none,none,yellow,none  |              7 | pink          |
      |   5,6,7,8 | green,blue,yellow,none |              7 | pink          |

