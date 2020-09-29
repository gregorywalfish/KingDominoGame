package ca.mcgill.ecse223.kingdomino.features;
import static org.junit.Assert.assertEquals;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.Controller;
import ca.mcgill.ecse223.kingdomino.features.*;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Gameplay;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import io.cucumber.java.en.*;

// TODO Matt
public class PlacingDominoStepDefinitions {

	@Given("it is not the last turn of the game")
	public void it_is_not_the_last_turn_of_the_game() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		game.setNextDraft(new Draft(Draft.DraftStatus.FaceDown, game));
	}

	@Given("the current player is not the last player in the turn")
	public void the_current_player_is_not_the_last_player_in_the_turn() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		game.setNextPlayer(game.getPlayer(0));
	}
	
	@Then("this player now shall be making his\\/her domino selection")
	public void this_player_now_shall_be_making_his_her_domino_selection() {
		assertEquals(Gameplay.Gamestatus.InProcess, Controller.getStateMachine().getGamestatus());
		assertEquals(Gameplay.GamestatusInProcess.SelectingDomino, Controller.getStateMachine().getGamestatusInProcess());
	}

	@Given("the current player is the last player in the turn")
	public void the_current_player_is_the_last_player_in_the_turn() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		game.setNextPlayer(game.getPlayer(3));
	}
	
	@Given("the game has been initialized for placing domino")
	public void the_game_has_been_initialized_for_placing_domino() {
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		CucumberStepDefinitions.addDefaultUsersAndPlayers(game);
		CucumberStepDefinitions.createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
		Controller.createFirstDraft();
		Controller.setStateMachine();
		Controller.getStateMachine().setGamestatus("PlacingDomino");
	}
	
	@Given("the game has been initialized for placing last domino")
	public void the_game_has_been_initialized_for_placing_last_domino() {
		the_game_has_been_initialized_for_placing_domino();
		Controller.getStateMachine().setGamestatus("PlacingLastDomino");
		
	}

	@Then("the final results after successful placement shall be computed")
	public void the_final_results_after_successful_placement_shall_be_computed() {
		assertEquals(Gameplay.Gamestatus.Ending, Controller.getStateMachine().getGamestatus());
		assertEquals(Gameplay.GamestatusEnding.ShowingResults, Controller.getStateMachine().getGamestatusEnding());
	}

}
