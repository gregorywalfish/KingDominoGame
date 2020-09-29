package ca.mcgill.ecse223.kingdomino.features;
import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.Controller;
import ca.mcgill.ecse223.kingdomino.features.*;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Gameplay;
import ca.mcgill.ecse223.kingdomino.model.Gameplay.Gamestatus;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import io.cucumber.java.en.*;

public class SelectingDominoStepDefinitions {
	
	private boolean validationSuccess;
	
	/**
	 * @author Zhekai Jiang
	 */
	@Given("the game has been initialized for selecting domino")
	public void the_game_has_been_initialized_for_selecting_domino() {
		initializeGame();
		Controller.getStateMachine().setGamestatus("SelectingDomino");
	}
	
	/**
	 * @author Zhekai Jiang
	 */
	@Given("the order of players is {string}")
	public void the_order_of_players_is(String orderStr) {
		String[] splitted = orderStr.split(",");
		Game currentGame = KingdominoApplication.getKingdomino().getCurrentGame();
		int index = 0;
		for(String i : splitted) {
			currentGame.addOrMovePlayerAt(getPlayerByColor(getPlayerColor(i)), index);
			index++;
		}
		
	
	}

	/**
	 * @author Zhekai Jiang
	 */
	@Given("the next draft has the dominoes with ID {string}")
	public void the_next_draft_has_the_dominoes_with_ID(String dominoesStr) {
		String[] splitted = dominoesStr.split(",");
		Game currentGame = KingdominoApplication.getKingdomino().getCurrentGame();		
		Draft nextDraft = new Draft(DraftStatus.FaceUp, currentGame);
		for(String i : splitted) {
			nextDraft.addIdSortedDomino(getdominoByID(Integer.parseInt(i)));
		}
		currentGame.setNextDraft(nextDraft);
	}


	/**
	 * @author Zhekai Jiang
	 */
	@Given("the {string} is selecting his\\/her domino with ID {int}")
	public void the_is_selecting_his_her_domino_with_ID(String colorStr, Integer id) {
		Game currentGame = KingdominoApplication.getKingdomino().getCurrentGame();
		Player currentPlayer = getPlayerByColor(getPlayerColor(colorStr));
		currentGame.setNextPlayer(currentPlayer);
		
		//Controller.chooseNextDomino(currentGame.getNextDraft(), getdominoByID(id), currentPlayer);
	}

	/**
	 * @author Zhekai Jiang
	 */
	@Given("the validation of domino selection returns {string}")
	public void the_validation_of_domino_selection_returns(String result) {
		validationSuccess = result.equals("success");
	}

	/**
	 * @author Zhekai Jiang
	 */
	@When("the {string} player completes his\\/her domino selection")
	public void the_player_completes_his_her_domino_selection(String string) {
	    if(validationSuccess)
	    	Controller.triggerSelectionComplete();
	}

	/**
	 * @author Zhekai Jiang
	 */
	@Then("the {string} player shall be {string} his\\/her domino")
	public void the_player_shall_be_his_her_domino(String colorStr, String status) {
		Game currentGame = KingdominoApplication.getKingdomino().getCurrentGame();
	    assertEquals(getPlayerByColor(getPlayerColor(colorStr)), currentGame.getNextPlayer());
	    if(status.equals("selecting")) {
	    	assert(currentGame.getGameplay().getGamestatusFullName().equals("Initializing.SelectingFirstDomino")
	    			|| currentGame.getGameplay().getGamestatusFullName().equals("InProcess.SelectingDomino"));
	    }
	    if(status.equals("placing")) {
	    	assert(currentGame.getGameplay().getGamestatusFullName().equals("InProcess.PlacingDomino")
	    			|| currentGame.getGameplay().getGamestatusFullName().equals("Ending.PlacingLastDomino"));
	    }
	}

	/**
	 * @author Zhekai Jiang
	 */
	@Given("the {string} player is selecting his\\/her first domino of the game with ID {int}")
	public void the_player_is_selecting_his_her_first_domino_of_the_game_with_ID(String colorStr, Integer id) {
		the_is_selecting_his_her_domino_with_ID(colorStr, id);
	}

	/**
	 * @author Zhekai Jiang
	 */
	@When("the {string} player completes his\\/her domino selection of the game")
	public void the_player_completes_his_her_domino_selection_of_the_game(String string) {
		if(validationSuccess)
	    	Controller.triggerSelectionComplete();
	}

	/**
	 * @author Zhekai Jiang
	 */
	@Then("a new draft shall be available, face down")
	public void a_new_draft_shall_be_available_face_down() {
		Game currentGame = KingdominoApplication.getKingdomino().getCurrentGame();
	    assert(currentGame.getNextDraft() != null && currentGame.getNextDraft().getIdSortedDominos().size() > 0);
	    assertEquals(DraftStatus.FaceUp, currentGame.getNextDraft().getDraftStatus());
	}
	
	/**
	 * @author Zhekai Jiang
	 */
	@Given("the game has been initialized for selecting first domino")
	public void the_game_has_been_initialized_for_selecting_first_domino() {
	    initializeGame();
		Controller.getStateMachine().setGamestatus("SelectingFirstDomino");
	}

	/**
	 * @author Zhekai Jiang
	 */
	@Given("the initial order of players is {string}")
	public void the_initial_order_of_players_is(String orderStr) {
		the_order_of_players_is(orderStr);
	}

	/**
	 * @author Zhekai Jiang
	 */
	@Given("the current draft has the dominoes with ID {string}")
	public void the_current_draft_has_the_dominoes_with_ID(String dominoesStr) {
		String[] splitted = dominoesStr.split(",");
		Game currentGame = KingdominoApplication.getKingdomino().getCurrentGame();		
		Draft currentDraft = new Draft(DraftStatus.FaceUp, currentGame);
		for(String i : splitted) {
			currentDraft.addIdSortedDomino(getdominoByID(Integer.parseInt(i)));
		}
		currentGame.setCurrentDraft(currentDraft);
	}

	/**
	 * @author Zhekai Jiang
	 */
	@Given("player's first domino selection of the game is {string}")
	public void player_s_first_domino_selection_of_the_game_is(String selectionStr) {
		String[] splitted = selectionStr.split(",");
		Game currentGame = KingdominoApplication.getKingdomino().getCurrentGame();
		for(int i = 0; i < splitted.length; ++i) {
			if(!splitted[i].equals("none")) {
				new DominoSelection(getPlayerByColor(getPlayerColor(splitted[i])), currentGame.getCurrentDraft().getIdSortedDomino(i), currentGame.getCurrentDraft());
			}
		}
	}

	/**
	 * @author Zhekai Jiang
	 */
	@Given("the {string} player is selecting his\\/her domino with ID {int}")
	public void the_player_is_selecting_his_her_domino_with_ID(String colorStr, Integer id) {
		Game currentGame = KingdominoApplication.getKingdomino().getCurrentGame();
		currentGame.setNextPlayer(getPlayerByColor(getPlayerColor(colorStr)));
		//Controller.chooseNextDomino(currentGame.getCurrentDraft(), getdominoByID(id), getPlayerByColor(getPlayerColor(colorStr)));	
	}

	/**
	 * @author Zhekai Jiang
	 */
	@Given("the {string} player is selecting his\\/her first domino with ID {int}")
	public void the_player_is_selecting_his_her_first_domino_with_ID(String colorStr, Integer id) {
		the_player_is_selecting_his_her_domino_with_ID(colorStr, id);
	}
	
	/**
	 * Helper methods from the code given in DiscardDominoStepDefinitions.java
	 */
	
	private void initializeGame() {
		// Intialize empty game
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		addDefaultUsersAndPlayers(game);
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);

		// Initialize state machine 
		Controller.setStateMachine();
	}
	
	private void addDefaultUsersAndPlayers(Game game) {
		String[] userNames = { "User1", "User2", "User3", "User4" };
		for (int i = 0; i < userNames.length; i++) {
			User user = game.getKingdomino().addUser(userNames[i]);
			Player player = new Player(game);
			player.setUser(user);
			player.setColor(PlayerColor.values()[i]);
			Kingdom kingdom = new Kingdom(player);
			new Castle(0, 0, kingdom, player);
		}
	}
	
	private void createAllDominoes(Game game) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/alldominoes.dat"));
			String line = "";
			String delimiters = "[:\\+()]";
			while ((line = br.readLine()) != null) {
				String[] dominoString = line.split(delimiters); // {id, leftTerrain, rightTerrain, crowns}
				int dominoId = Integer.decode(dominoString[0]);
				TerrainType leftTerrain = getTerrainType(dominoString[1]);
				TerrainType rightTerrain = getTerrainType(dominoString[2]);
				int numCrown = 0;
				if (dominoString.length > 3) {
					numCrown = Integer.decode(dominoString[3]);
				}
				new Domino(dominoId, leftTerrain, rightTerrain, numCrown, game);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new java.lang.IllegalArgumentException(
					"Error occured while trying to read alldominoes.dat: " + e.getMessage());
		}
	}
	
	private TerrainType getTerrainType(String terrain) {
		switch (terrain) {
		case "W":
			return TerrainType.WheatField;
		case "F":
			return TerrainType.Forest;
		case "M":
			return TerrainType.Mountain;
		case "G":
			return TerrainType.Grass;
		case "S":
			return TerrainType.Swamp;
		case "L":
			return TerrainType.Lake;
		default:
			throw new java.lang.IllegalArgumentException("Invalid terrain type: " + terrain);
		}
	}

	private PlayerColor getPlayerColor(String color) {
		switch (color) {
		case "blue":
			return PlayerColor.Blue;
		case "pink":
			return PlayerColor.Pink;
		case "green":
			return PlayerColor.Green;
		case "yellow":
		case "yelow": // it seems there is a typo in the Gherkin scenario -zhekai
			return PlayerColor.Yellow;
		default:
			throw new java.lang.IllegalArgumentException("Invalid player colour: " + color);
		}
	}
	
	/**
	 * @author Gregory Walfish
	 */
	private Player getPlayerByColor(PlayerColor pcolor) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Player> players = game.getPlayers();
		for (Player player : players) {
			if (player.getColor().equals(pcolor)) {
				return player;
			}

		}
		return null;
	}
	
	/**
	 * Get the object of the domino corresponding to the ID given From the code
	 * given in DiscardDominoStepDefinitions.java
	 *
	 * @param id The ID of the domino requested
	 * @return Domino The corresponding Domino object
	 */
	static Domino getdominoByID(int id) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Domino domino : game.getAllDominos()) {
			if (domino.getId() == id) {
				return domino;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
	}

}
