Feature: Shuffle Dominos
  As a player, I want to have a randomly shuffled pile of dominoes
  so that every game becomes unique.

  Background:
    Given the game is initialized for shuffle dominoes

  Scenario Outline: Shuffle Dominos
    # Example table provided to be extended for the case of 2 and 3 players bonus.
    Given there are <nplayers> players playing
    When the shuffling of dominoes is initiated
    Then the first draft shall exist
    Then the first draft should have <dominoesonboard> dominoes on the board face down
    Then there should be <dominoesleft> dominoes left in the draw pile

    Examples:
      | nplayers  | dominoesonboard| dominoesleft  |
      | 4         |             4 |          44  |
    # Bonus: Fill in data table for 2 and 3 players if you decide to
    # implement that functionality as a bonus feature.

  Scenario Outline: Fixed Arrangement
    # Example table provided to be extended for the case of 2 and 3 players bonus.
    Given there are <nplayers> players playing
    When I initiate to arrange the domino in the fixed order <dominoarrangement>
    Then the first draft shall exist
    Then the first draft should have <dominoesonboard> dominoes on the board face down
    Then the draw pile should consist of everything in <dominoarrangement> except the first <dominoesonboard> dominoes with their order preserved

    Examples:
      | nplayers   | dominoesonboard       | dominoarrangement                                                                                                                                                                       |
      | 4          |    4                 | "34, 2, 41, 24, 38, 28, 3, 25, 8, 14, 32, 46, 27, 20, 31, 47, 29, 26, 23, 10, 13, 40, 1, 33, 30, 43, 5, 48, 15, 22, 39, 4, 12, 36, 42, 17, 9, 21, 35, 45, 37, 18, 44, 16, 7, 11, 6, 19" |
    # Bonus: Fill in data table for 2 and 3 players if you decide to
    # implement that functionality as a bonus feature.