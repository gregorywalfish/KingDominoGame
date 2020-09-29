Feature: Initializing the Game

  Scenario: Initialize game and create first draft
    Given the game has not been started
    When start of the game is initiated
    Then the pile shall be shuffled
    Then the first draft shall be on the table
    Then the first draft shall be revealed
    Then the initial order of players shall be determined
    Then the first player shall be selecting his/her first domino of the game
    Then the second draft shall be on the table, face down
