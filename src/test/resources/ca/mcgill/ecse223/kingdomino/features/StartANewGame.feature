Feature: Start a New Game
  As a Kingdomino player, I want to start a new game of Kingdomino against some opponents
  with my castle placed on my territory with the current settings of the game. 
  The initial order of player should be randomly determined.

  Background:
    Given the program is started and ready for starting a new game

  Scenario: Start a new game
    Given there are four selected players
    Given bonus options Harmony and MiddleKingdom are selected
    When starting a new game is initiated
    When reveal first draft is initiated
    Then all kingdoms shall be initialized with a single castle
    Then all castle are placed at 0:0 in their respective kingdoms
    Then the first draft of dominoes is revealed
    Then all the dominoes form the first draft are facing up
    Then all the players have no properties
    Then all player scores are initialized to zero

