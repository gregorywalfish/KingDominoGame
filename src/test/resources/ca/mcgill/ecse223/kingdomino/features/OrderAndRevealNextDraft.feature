Feature: Order and Reveal Next Draft of Dominoes
  As a player, I want the Kingdomino app to automatically order 
  and reveal the next draft of dominos in increasing order 
  with respect to their numbers so that I know which are the more valuable dominos.

  #drafts are represented by their domino ids separated by commas and no spaces
  Scenario Outline: Ordering the next draft before revealing
    Given the game is initialized for order next draft of dominoes
    Given the next draft is "<unorderedids>"
    Given the dominoes in next draft are facing down
    When the ordering of the dominoes in the next draft is initiated
    Then the status of the next draft is sorted
    Then the order of dominoes in the draft will be "<orderedids>"

    Examples: 
      | unorderedids     | orderedids     |
      |      8,9,5,2     |    2,5,8,9     |
      |      12,25,40,41 |    12,25,40,41 |
      |      45,32,10,13 |    10,13,32,45 |
      |      1,47,22,33  |    1,22,33,47  |

  Scenario Outline: Revealing the next draft before the players start claiming dominoes
    Given the game is initialized for reveal next draft of dominoes
    Given the next draft is "<orderedids>"
    Given the dominoes in next draft are sorted
    When the revealing of the dominoes in the next draft is initiated
    Then the status of the next draft is face up

    Examples: 
      | orderedids     |
      |    2,5,8,9     |
      |    12,25,40,41 |
      |    10,13,32,45 |
      |    1,22,33,47  |

