package ca.mcgill.ecse223.kingdomino.features;
import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.Controller;
import ca.mcgill.ecse223.kingdomino.features.*;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Gameplay;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import io.cucumber.java.en.*;
import junit.framework.Assert;

// TODO Ezra
public class DiscardingDominoStepDefinitions {
	
	
	
	@Given("the game is initialized for discarding domino")
	public void the_game_is_initialized_for_discarding_domino() {
		initializeGame();
		Controller.getStateMachine().setGamestatus("DiscardingDomino");
		
		
	}
	
	@Given("the game is initialized for discarding last domino")
	public void the_game_is_initialized_for_discarding_last_domino() {
		initializeGame();
		Controller.getStateMachine().setGamestatus("DiscardingLastDomino");
	}

	@Given("it is the last turn of the game")
	public void it_is_the_last_turn_of_the_game() {
		KingdominoApplication.getKingdomino().getCurrentGame().setNextDraft(null);
	}

	@Then("the next player shall be placing his\\/her domino")
	public void the_next_player_shall_be_placing_his_her_domino() {
		assertEquals(Gameplay.Gamestatus.Ending, Controller.getStateMachine().getGamestatus());
		assertEquals(Gameplay.GamestatusEnding.PlacingLastDomino, Controller.getStateMachine().getGamestatusEnding());
	}

	@Then("the game shall be finished")
	public void the_game_shall_be_finished() {
		assertEquals(false, KingdominoApplication.getKingdomino().getCurrentGame().hasNextDraft());
		
	}

	@Then("the final results after discard shall be computed")
	public void the_final_results_after_discard_shall_be_computed() {
	    assertEquals(Gameplay.Gamestatus.Ending, Controller.getStateMachine().getGamestatus());
		assertEquals(Gameplay.GamestatusEnding.ShowingResults, Controller.getStateMachine().getGamestatusEnding());
	}
	
	
	/*
	 * Helper Functions
	 */
	
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
		Controller.createFirstDraft();
		// Initialize state machine 
		Controller.setStateMachine();
		
	}
	

}
