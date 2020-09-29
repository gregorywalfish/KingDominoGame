package ca.mcgill.ecse223.kingdomino.features;
import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.Controller;
import ca.mcgill.ecse223.kingdomino.model.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// TODO Ricky
public class SortingAndRevealingDraftStepDefinitions {

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

	/**------------------------------------------------------------------------real deal down here-----------------------------------------------------------------*/

	@Given("there is a next draft, face down")
	public void there_is_a_next_draft_face_down() {
		Kingdomino kingdomino = new Kingdomino();
		Game SRDraft = new Game(48, kingdomino);
		SRDraft.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(SRDraft);
		// Populate game
		addDefaultUsersAndPlayers(SRDraft);
		createAllDominoes(SRDraft);
		SRDraft.setNextPlayer(SRDraft.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);

		KingdominoApplication.getKingdomino().getCurrentGame().setNextDraft(new Draft(Draft.DraftStatus.FaceDown, KingdominoApplication.getKingdomino().getCurrentGame()));
		for (int i = 0; i < KingdominoApplication.getKingdomino().getCurrentGame().getNumberOfPlayers(); i++) {
			KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().addIdSortedDominoAt(KingdominoApplication.getKingdomino().getCurrentGame().getAllDomino(i), i);
		}
	}

	@Given("all dominoes in current draft are selected")
	public void all_dominoes_in_current_draft_are_selected() {
		for (int i = 0; i < KingdominoApplication.getKingdomino().getCurrentGame().getNumberOfPlayers(); i++) {
			KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().addSelection(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(i), KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().getIdSortedDomino(i));
		}
	}

	@When("next draft is sorted")
	public void next_draft_is_sorted() {
		Controller.OrderDraft(KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft());
	}

	@When("next draft is revealed")
	public void next_draft_is_revealed() {
		Controller.RevealDraft(KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft());
	}

	@Then("the next draft shall be sorted")
	public void the_next_draft_shall_be_sorted() {

		for (int i = 0; i < KingdominoApplication.getKingdomino().getCurrentGame().getNumberOfPlayers()-1; i++) {
			int j = KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().getIdSortedDomino(i).getId();
			if (KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().getIdSortedDomino(i+1).getId() <= j) {
				assert false;
			}
		}
		assert true;
	}

	@Then("the next draft shall be facing up")
	public void the_next_draft_shall_be_facing_up() {
		if (KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().getDraftStatus() == Draft.DraftStatus.FaceUp) {
			assert true;
		}
		else assert false;
	}

	@Then("it shall be the player's turn with the lowest domino ID selection")
	public void it_shall_be_the_player_s_turn_with_the_lowest_domino_ID_selection() {
		KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
	}
}