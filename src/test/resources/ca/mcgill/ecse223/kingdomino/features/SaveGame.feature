Feature: Save Game
  As a player, I want to save the current game if the game has not yet been
  finished so that I can continue it from the last position

  Background:
    Given the game is initialized for save game
    Given the game is still in progress

  Scenario Outline: Save game
    Given no file named "<filename>" exists in the filesystem
    When the user initiates saving the game to a file named "<filename>"
    Then a file named "<filename>" shall be created in the filesystem

    Examples:
      | filename                             |
      | src/test/resources/save_game_test.mov |

  Scenario Outline: Save game overwrites existing file name
    Given the file named "<filename>" exists in the filesystem
    When the user initiates saving the game to a file named "<filename>"
    When the user agrees to overwrite the existing file named "<filename>"
    Then the file named "<filename>" shall be updated in the filesystem

    Examples:
      | filename           |
      | src/test/resources/save_game_test.mov |
