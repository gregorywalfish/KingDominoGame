Feature: Load Game
  As a player, I want to load a previously played game so that I can continue
  it from the last position

  Background:
    Given the game is initialized for load game

  Scenario Outline: Load valid incomplete game
    When I initiate loading a saved game from "<filename>"
    When each tile placement is valid
    When the game result is not yet final
    Then it shall be player <player>'s turn
    Then each of the players should have the corresponding tiles on their grid:
      | playerNumber | playerTiles |
      | 1            |  <p1tiles>  |
      | 2            |  <p2tiles>  |
      | 3            |  <p3tiles>  |
      | 4            |  <p4tiles>  |
    Then each of the players should have claimed the corresponding tiles:
      | playerNumber | claimedTile   |
      | 1            |  <p1claimed>  |
      | 2            |  <p2claimed>  |
      | 3            |  <p3claimed>  |
      | 4            |  <p4claimed>  |
    Then tiles "<unclaimed>" shall be unclaimed on the board
    Then the game shall become ready to start

    Examples:
      | filename                                      | player    | p1tiles       | p2tiles     | p3tiles       | p4tiles         | p1claimed | p2claimed | p3claimed | p4claimed | unclaimed        |
      | src/test/resources/kingdomino_test_game_1.mov | 4         | 1,5,39,41     | 2,9,15,10   | 3,4,17,6      |  8,42,28        | 34        | 37        | 29        | 44        | 22               |
      | src/test/resources/kingdomino_test_game_2.mov | 4         | 1,5,39,41     | 2,9,15,10   | 3,4,17,6      |  8,42,28,44     | 34        | 37        | 29        | 22        | 7, 21, 25, 48    |
    # Variables:
    # player - the player who's turn it is next. 
    # p<n>tiles - the tiles that have been placed on player <n>'s grid.
    # p<n>claimed - the tile which player <n> has claimed to be placed in the next turn.
    # unclaimed - the tiles which have not yet been claimed by a player.

  Scenario Outline: Invalid placement in game file
    When I initiate loading a saved game from "<filename>"
    Then the game shall notify the user that the loaded game is invalid

    Examples:
      | filename                                                       |
      | src/test/resources/kingdomino_test_game_invalid_move_1.mov     |
      | src/test/resources/kingdomino_test_game_invalid_move_2.mov     |
      | src/test/resources/kingdomino_test_game_invalid_move_3.mov     |
      | src/test/resources/kingdomino_test_game_invalid_move_4.mov     |

