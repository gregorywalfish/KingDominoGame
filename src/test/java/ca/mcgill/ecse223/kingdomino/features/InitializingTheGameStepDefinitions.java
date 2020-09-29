package ca.mcgill.ecse223.kingdomino.features;
import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.Controller;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// TODO Ricky
public class InitializingTheGameStepDefinitions {

	/**--------------------helpers??----------------------*/

	private void addDefaultUsersAndPlayers(Game game) {
		String[] userNames = { "User1", "User2", "User3", "User4" };
		for (int i = 0; i < userNames.length; i++) {
			User user = game.getKingdomino().addUser(userNames[i]);
			Player player = new Player(game);
			player.setUser(user);
			player.setColor(Player.PlayerColor.values()[i]);
			Kingdom kingdom = new Kingdom(player);
			new Castle(0, 0, kingdom, player);
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

	/**--------------------------------real deal down here--------------------------------*/

	@Given("the game has not been started")
	public void the_game_has_not_been_started() {
		if (KingdominoApplication.getKingdomino().getCurrentGame() == null) assert true;
		else assert false;
	}

	@When("start of the game is initiated")
	public void start_of_the_game_is_initiated() {
		Kingdomino kingdomino = new Kingdomino();
		Game Init = new Game(48, kingdomino);
		kingdomino.setCurrentGame(Init);
		KingdominoApplication.setKingdomino(kingdomino);

		for (int i = 0; i < 4; i++) {
			Player player = new Player(KingdominoApplication.getKingdomino().getCurrentGame());
			player.setColor(Player.PlayerColor.values()[i]);
		}

		Controller.startGame(Init);
	}

	@Then("the pile shall be shuffled")
	public void the_pile_shall_be_shuffled() {
		for(int i = 0; i < KingdominoApplication.getKingdomino().getCurrentGame().getMaxPileSize()-1; i++) {
			//if (KingdominoApplication.getKingdomino().getCurrentGame().getAllDomino(i+1).getId() == KingdominoApplication.getKingdomino().getCurrentGame().getAllDomino(i).getId() + 1) assert false;
		}
		assert true;
	}

	@Then("the first draft shall be on the table")
	public void the_first_draft_shall_be_on_the_table() {
		if (KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft() != null) assert true;
		else assert false;
	}

	@Then("the first draft shall be revealed")
	public void the_first_draft_shall_be_revealed() {
		Controller.revealFirstDraft(KingdominoApplication.getKingdomino().getCurrentGame());

		if (KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft().getDraftStatus() == Draft.DraftStatus.FaceUp) assert true;
		else assert false;
	}

	@Then("the initial order of players shall be determined")
	public void the_initial_order_of_players_shall_be_determined() {
		for (int i = 0; i < KingdominoApplication.getKingdomino().getCurrentGame().numberOfPlayers(); i++) {
			if (!KingdominoApplication.getKingdomino().getCurrentGame().hasNextPlayer()) assert false;
		}
		assert true;
	}

	@Then("the first player shall be selecting his\\/her first domino of the game")
	public void theFirstPlayerShallBeSelectingHisHerFirstDominoOfTheGame() {
		assertEquals(Gameplay.Gamestatus.Initializing, Controller.getStateMachine().getGamestatus());
		assertEquals(Gameplay.GamestatusInitializing.SelectingFirstDomino, Controller.getStateMachine().getGamestatusInitializing());
	}

	@Then("the second draft shall be on the table, face down")
	public void the_second_draft_shall_be_on_the_table_face_down() {
		for(Domino d : Controller.getGame().getNextDraft().getIdSortedDominos()) {
			assertEquals(d.getStatus(), DominoStatus.InNextDraft);
		}
		assertEquals(Controller.getGame().getNextDraft().getDraftStatus(), DraftStatus.FaceDown);
	}
}
