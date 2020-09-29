package ca.mcgill.ecse223.kingdomino.features;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.Controller;
import ca.mcgill.ecse223.kingdomino.features.*;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import io.cucumber.java.en.*;

// TODO Greg
public class CalculatingPlayerScoreStepDefinitions {
	@Given("the game is initialized for calculating player score")
	public void the_game_is_initialized_for_calculating_player_score() {

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
		Controller.setStateMachine();
	}

	@Given("the current player has no dominoes in his\\/her kingdom yet")
	public void the_current_player_has_no_dominoes_in_his_her_kingdom_yet() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player aPlayer = game.getPlayer(0);
		aPlayer.setDominoSelection(null);
	}

	@Given("the score of the current player is {int}")
	public void the_score_of_the_current_player_is(Integer int1) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player aPlayer = game.getPlayer(0);
		aPlayer.setBonusScore(0);
		aPlayer.setPropertyScore(0);
	}
	
	private DominoInKingdom dominoInKingdom;
	
	@Given("the current player is preplacing his\\/her domino with ID {int} at location {int}:{int} with direction {string}")
	public void the_current_player_is_preplacing_his_her_domino_with_ID_at_location_with_direction(Integer id,
			Integer x, Integer y, String direction) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player aPlayer = game.getNextPlayer();
		Domino dominoToPreplace = Controller.getdominoByID(id);
		new DominoSelection(aPlayer, dominoToPreplace, game.getCurrentDraft());
		dominoInKingdom = new DominoInKingdom(x, y, aPlayer.getKingdom(), dominoToPreplace);
		dominoInKingdom.setDirection(getDirection(direction));
	}

	@Given("the preplaced domino has the status {string}")
	public void the_preplaced_domino_has_the_status(String status) {
		if (status.equals("CorrectlyPreplaced")) {
			dominoInKingdom.getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
		}
	}

	@When("the current player places his\\/her domino")
	public void the_current_player_places_his_her_domino() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player aPlayer = game.getPlayer(0);
		
		Controller.placeDomino(dominoInKingdom);
	}

	@Then("the score of the current player shall be {int}")
	public void the_score_of_the_current_player_shall_be(Integer int1) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player aPlayer = game.getPlayer(0);
		int playerScore = Controller.CalculatePlayerScore(aPlayer);
		
		Assertions.assertEquals(int1, playerScore);
	}

	@Given("the game has no bonus options selected")
	public void the_game_has_no_bonus_options_selected() {
		int numPlayers = Controller.getNumPlayers();
		Game game = Controller.getGame();
		Controller.setGameOptions(numPlayers, false, false);
	}
	
	@Given("the current player is placing his\\/her domino with ID {int}")
	public void the_current_player_is_placing_his_her_domino_with_ID(Integer id) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player aPlayer = game.getNextPlayer();
		new DominoSelection(aPlayer, Controller.getdominoByID(id), game.getCurrentDraft());
		
	}

	@Given("it is impossible to place the current domino in his\\/her kingdom")
	public void it_is_impossible_to_place_the_current_domino_in_his_her_kingdom() {
		KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer().getDominoSelection().getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
	}

	@When("the current player discards his\\/her domino")
	public void the_current_player_discards_his_her_domino() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player aPlayer = game.getNextPlayer();
		Controller.discardDomino(aPlayer, aPlayer.getDominoSelection().getDomino());
		Controller.triggerPlacementComplete();
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

	private DirectionKind getDirection(String dir) {
		switch (dir) {
		case "up":
			return DirectionKind.Up;
		case "down":
			return DirectionKind.Down;
		case "left":
			return DirectionKind.Left;
		case "right":
			return DirectionKind.Right;
		default:
			throw new java.lang.IllegalArgumentException("Invalid direction: " + dir);
		}
	}
	private Domino getdominoByID(int id) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Domino domino : game.getAllDominos()) {
			if (domino.getId() == id) {
				return domino;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
	}
}
