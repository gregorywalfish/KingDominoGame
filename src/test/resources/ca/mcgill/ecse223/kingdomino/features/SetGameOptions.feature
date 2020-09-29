Feature: Set Game Options
  As a player, I want to configure the designated options of the Kingdomino game 
  including the number of players (2, 3 or 4) 
  and the bonus scoring options. (F1)

  Background: 
    Given the game is initialized for set game options

  Scenario Outline: Configuring game
    When set game options is initiated
    When the number of players is set to <numplayer>
    When Harmony "<isUsingHarmony>" selected as bonus option
    When Middle Kingdom "<isUsingMiddleKingdom>" selected as bonus option
    Then the number of players shall be <numplayer>
    Then Harmony "<isUsingHarmony>" an active bonus
    Then Middle Kingdom "<isUsingMiddleKingdom>" an active bonus

    Examples: 
      | numplayer | isUsingHarmony | isUsingMiddleKingdom |
      |         4 | is not         | is not               |
      |         4 | is             | is not               |
      |         4 | is not         | is                   |
      |         4 | is             | is                   |
