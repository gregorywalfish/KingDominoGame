Feature: Selecting Domino

  Background:
    Given the game has been initialized for selecting domino

  Scenario Outline: Select a domino and the current turn goes on
    Given the order of players is "<playerorder>"
    Given the next draft has the dominoes with ID "1,2,3,4"
    Given player's domino selection "<currentselection>"
    Given the "<currentplayer>" is selecting his/her domino with ID <chosendominoid>
    And the validation of domino selection returns "<result>"
    When the "<currentplayer>" player completes his/her domino selection
    Then the "<nextplayer>" player shall be "<action>" his/her domino

    Examples:
      | playerorder            | currentplayer | currentselection     | chosendominoid | result  | nextplayer | action    |
      | blue,green,pink,yellow | blue          | none,none,none,none  | 1              | success | green      | placing   |
      | blue,green,pink,yellow | pink          | blue,green,none,none | 3              | success | yellow     | placing   |
      | green,pink,blue,yellow | pink          | green,none,none,none | 2              | success | blue       | placing   |
      | green,pink,yellow,blue | pink          | green,none,none,none | 1              | error   | pink       | selecting |
      | blue,green,pink,yellow | yellow        | blue,green,pink,none | 3              | error   | yellow     | selecting |

  Scenario Outline: Complete a turn of domino selection
    Given the order of players is "<playerorder>"
    Given the next draft has the dominoes with ID "1,2,3,4"
    Given player's domino selection "<currentselection>"
    Given the "<currentplayer>" player is selecting his/her first domino of the game with ID <chosendominoid>
    And the validation of domino selection returns "<result>"
    When the "<currentplayer>" player completes his/her domino selection of the game
    Then a new draft shall be available, face down

    Examples:
      | playerorder            | currentselection     | chosendominoid | currentplayer | result  |
      | blue,green,pink,yellow | blue,green,pink,none | 4              | yellow        | success |
