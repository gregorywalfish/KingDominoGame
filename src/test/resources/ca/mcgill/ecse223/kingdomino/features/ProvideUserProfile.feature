Feature: Provide User Profile
  As a player, I wish to use my unique user name in when a game starts. 
  I also want the Kingdomino app to maintain my game statistics
   (e.g. number of games played, won, etc.). I also wish wish to view all users.

  Background: 
    Given the program is started and ready for providing user profile

  Scenario Outline: Create the first user
    Given there are no users exist
    When I provide my username "<name>" and initiate creating a new user
    Then the user "<name>" shall be in the list of users

    Examples: 
      | name      |
      | firstuser |

  Scenario Outline: Create a new user
    Given the following users exist:
      | name  |
      | test1 |
      | test2 |
    When I provide my username "<name>" and initiate creating a new user
    Then the user creation shall "<status>"

    Examples: 
      | name  | status  |
      | test1 | fail    |
      | test2 | fail    |
      | Test1 | fail    |
      | test3 | succeed |
      |       | fail    |
      | te.() | fail    |

  Scenario: List all users
    Given the following users exist:
      | name  |
      | testa |
      | testc |
      | testb |
    When I initiate the browsing of all users
    Then the users in the list shall be in the following alphabetical order:
      | name  | placeinlist |
      | testa |           1 |
      | testc |           3 |
      | testb |           2 |

  Scenario Outline: View game statistics for a user
    Given the following users exist with their game statistics:
      | name  | playedGames | wonGames |
      | test1 |           0 |        0 |
      | test3 |           5 |        5 |
      | test2 |          10 |        6 |
    When I initiate querying the game statistics for a user "<name>"
    Then the number of games played by "<name>" shall be <playedGames> 
    Then the number of games won by "<name>" shall be <wonGames>
    
    Examples:
      | name  | playedGames | wonGames |
      | test1 |           0 |        0 |
      | test3 |           5 |        5 |
      | test2 |          10 |        6 |
