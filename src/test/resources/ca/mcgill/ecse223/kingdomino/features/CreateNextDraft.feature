Feature: Create Next Draft of Dominoes
  As a player, I want the Kingdomino app to automatically provide 
  the next four dominos once the previous round is finished
	
  Background:
    Given the game is initialized to create next draft
	
  Scenario Outline: Creating next draft when there are dominoes still in the pile
    Given there has been <draftnum> drafts created
    Given there is a current draft
    Given there is a next draft
    Given the top 5 dominoes in my pile have the IDs "<list_of_ids>"
    When create next draft is initiated
    Then a new draft is created from dominoes "<draft_ids>"
    Then the next draft now has the dominoes "<draft_ids>"
    Then the dominoes in the next draft are face down
    Then the top domino of the pile is ID <topId>
    Then the former next draft is now the current draft

    Examples: 
      | draftnum | list_of_ids   | topId |   draft_ids   |
      |        2 | 9,10,11,12,13 |    13 |   9,10,11,12  |

  Scenario Outline: Revealing the next draft when there are no more dominoes still in the pile
    Given this is a <num_players> player game
    Given there has been <draftnum> drafts created
    When create next draft is initiated
    Then the pile is empty
    Then there is no next draft
    Then the former next draft is now the current draft

    Examples: 
      | num_players | draftnum |
      |           4 |       12 |
#     |           2 |        6 |
#     |           3 |       12 |

