Feature: Sort and reveal draft

  Scenario: Sort and reveal draft
    Given there is a next draft, face down
    And all dominoes in current draft are selected
    When next draft is sorted
    When next draft is revealed
    Then the next draft shall be sorted
    Then the next draft shall be facing up
    Then it shall be the player's turn with the lowest domino ID selection
