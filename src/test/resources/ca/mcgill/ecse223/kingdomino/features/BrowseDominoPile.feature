Feature: Browse Domino Pile
  As a player, I wish to browse the set of all dominoes in increasing order of 
  numbers prior to playing the game so that I can adjust my strategy, 
  view an individual domino or filter the dominoes by terrain type

  Background: 
    Given the program is started and ready for browsing dominoes

  Scenario: Browse all the dominoes
    When I initiate the browsing of all dominoes
    Then all the dominoes are listed in increasing order of identifiers

  Scenario Outline: Select and observe an individual domino
    When I provide a domino ID <id>
    Then the listed domino has "<lefttile>" left terrain
    Then the listed domino has "<righttile>" right terrain
    Then the listed domino has <crowns> crowns

    Examples: 
      | id | lefttile | righttile | crowns |
      |  1 | wheat    | wheat     |      0 |
      | 28 | lake     | forest    |      1 |
      | 44 | grass    | swamp     |      2 |
      | 48 | wheat    | mountain  |      3 |

  Scenario Outline: Filter domino by terrain type
    When I initiate the browsing of all dominoes of "<terrain>" terrain type
    Then list of dominoes with IDs "<listofids>" should be shown

    Examples: 
      | terrain  | listofids                                                             |
      | wheat    | 1,2,13,14,15,16,19,20,21,22,23,24,25,26,27,30,31,36,38,40,41,43,45,48 |
      | mountain |                                                     23,40,45,46,47,48 |
