package ca.mcgill.ecse223.kingdomino.features;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.Controller;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import org.junit.After;
import org.junit.jupiter.api.Assertions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;

public class CucumberStepDefinitions {

	private Kingdom kingdom;
	private int id, leftX, leftY;
	private DirectionKind direction;
	private boolean verificationResult;
	private int bonusScore;
	private int totalScore;
	private int properties;
	private boolean succesfulLoad;
	private Player currentPlayer;
	private Domino dominoToPreplace;
	private DominoInKingdom dominoInKingdom;
	private DominoInKingdom cok;
	private DominoInKingdom fixk;

	@Given("the player's kingdom has the following dominoes:")
	public void the_player_s_kingdom_has_the_following_dominoes(io.cucumber.datatable.DataTable dataTable) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			Integer id = Integer.decode(map.get("id"));
			DirectionKind dir = getDirection(map.get("dominodir"));
			Integer posx = Integer.decode(map.get("posx"));
			Integer posy = Integer.decode(map.get("posy"));

			// Add the domino to a player's kingdom
			Domino dominoToPlace = getdominoByID(id);
			Kingdom kingdom = game.getNextPlayer().getKingdom();
			DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
			domInKingdom.setDirection(dir);
			dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);
		}
		this.kingdom = game.getNextPlayer().getKingdom(); // Zhekai Jiang: added for Feature 17
	}
	
	@Given("the {string}'s kingdom has the following dominoes:")
	public void player_has_following_dominos_color(String color, io.cucumber.datatable.DataTable dataTable) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			Integer id = Integer.decode(map.get("domino"));
			DirectionKind dir = getDirection(map.get("dominodir"));
			Integer posx = Integer.decode(map.get("posx"));
			Integer posy = Integer.decode(map.get("posy"));

			// Add the domino to a player's kingdom
			Domino dominoToPlace = getdominoByID(id);
			Kingdom kingdom = game.getNextPlayer().getKingdom();
			DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
			domInKingdom.setDirection(dir);
			dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);
		}
		this.kingdom = game.getNextPlayer().getKingdom();
	}

	/**
	 * Provide User Profile
	 *
	 * @param game
	 */

	@Given("the program is started and ready for providing user profile")
	public void the_program_is_started_and_ready_for_providing_user_profile() {
		Kingdomino model = new Kingdomino();
		KingdominoApplication.setKingdomino(model);
	}

	@Given("there are no users exist")
	public void there_are_no_users_exist() {
		// Write code here that turns the phrase above into concrete actions

		Kingdomino model = KingdominoApplication.getKingdomino();
		if (model.hasUsers()) {
			// DO something here to make sure there are,
			// i.e. delete all users
			for (User u : model.getUsers()) {
				model.removeUser(u);
			}
		}
	}

	boolean t;

	@When("I provide my username {string} and initiate creating a new user")
	public void i_provide_my_username_and_initiate_creating_a_new_user(String string) {
		t = Controller.NewUser(string);
	}

	@Then("the user {string} shall be in the list of users")
	public void the_user_shall_be_in_the_list_of_users(String string) {

		// Write code here that turns the phrase above into concrete actions
		Kingdomino model = KingdominoApplication.getKingdomino();
		List<User> x = model.getUsers();
		boolean y = false;
		for (User u : x) {
			if (u.getName().equals(string)) {
				y = true;
			}
		}

		Assert.assertTrue("First user was not created", y);
	}

	@Given("the following users exist:")
	public void the_following_users_exist(io.cucumber.datatable.DataTable dataTable) {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
		// Map<K, List<V>>. E,K,V must be a String, Integer, Float,
		// Double, Byte, Short, Long, BigInteger or BigDecimal.
		//
		// For other transformations you can register a DataTableType.

		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		if (kingdomino.hasUsers()) {
			// DO something here to make sure there are,
			// i.e. delete all users
			for (User u : kingdomino.getUsers()) {
				kingdomino.removeUser(u);
			}

		}
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			kingdomino.addUser(map.get("name"));

		}

	}

	@Then("the user creation shall {string}")
	public void the_user_creation_shall(String string) {
		// Write code here that turns the phrase above into concrete actions
		Assert.assertEquals(t ? "succeed" : "fail", string);

	}

	List<User> x;

	@When("I initiate the browsing of all users")
	public void i_initiate_the_browsing_of_all_users() {
		// Write code here that turns the phrase above into concrete actions
		x = Controller.browseUsers();
	}

	@Then("the users in the list shall be in the following alphabetical order:")
	public void the_users_in_the_list_shall_be_in_the_following_alphabetical_order(
			io.cucumber.datatable.DataTable dataTable) {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
		// Map<K, List<V>>. E,K,V must be a String, Integer, Float,
		// Double, Byte, Short, Long, BigInteger or BigDecimal.
		//
		// For other transformations you can register a DataTableType.
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			String expectedName = map.get("name");
			Assert.assertEquals(x.get(Integer.decode(map.get("placeinlist")) - 1).getName(), expectedName);

		}

	}

	@Given("the following users exist with their game statistics:")
	public void the_following_users_exist_with_their_game_statistics(io.cucumber.datatable.DataTable dataTable) {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
		// Map<K, List<V>>. E,K,V must be a String, Integer, Float,
		// Double, Byte, Short, Long, BigInteger or BigDecimal.
		//
		// For other transformations you can register a DataTableType.

		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		if (kingdomino.hasUsers()) {
			// DO something here to make sure there are,
			// i.e. delete all users
			for (User u : kingdomino.getUsers()) {
				kingdomino.removeUser(u);
			}

		}
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			User x = kingdomino.addUser(map.get("name"));
			x.setPlayedGames(Integer.parseInt(map.get("playedGames")));
			x.setWonGames(Integer.parseInt(map.get("wonGames")));

		}

	}

	int p;
	int y;
	String userName;

	@When("I initiate querying the game statistics for a user {string}")
	public void i_initiate_querying_the_game_statistics_for_a_user(String string) {
		// Write code here that turns the phrase above into concrete actions
		p = Controller.queryGamesPlayed(string);
		y = Controller.queryGamesWon(string);
		userName = string;

	}

	@Then("the number of games played by and games won by the user shall be the following:")
	public void the_number_of_games_played_by_and_games_won_by_the_user_shall_be_the_following(
			io.cucumber.datatable.DataTable dataTable) {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
		// Map<K, List<V>>. E,K,V must be a String, Integer, Float,
		// Double, Byte, Short, Long, BigInteger or BigDecimal.
		//
		// For other transformations you can register a DataTableType.

		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			if (map.get("name").equals(userName)) {
				Assert.assertEquals(p, (Integer.parseInt(map.get("playedGames"))));
				Assert.assertEquals(y, (Integer.parseInt(map.get("wonGames"))));
			}

		}

	}

	@Then("the number of games played by {string} shall be {int}")
	public void the_number_of_games_played_by_shall_be(String string, Integer int1) {
		p = Controller.queryGamesPlayed(string);
		Assert.assertEquals(p, int1.intValue());
	}

	@Then("the number of games won by {string} shall be {int}")
	public void the_number_of_games_won_by_shall_be(String string, Integer int1) {
		y = Controller.queryGamesWon(string);
		Assert.assertEquals(y, int1.intValue());
	}

	/**
	 * Discard Domino Feature
	 */

	@Given("the game is initialized for discard domino")
	public void the_game_is_initialized_for_discard_domino() {
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
		// Make a draft?
		game.setCurrentDraft(new Draft(DraftStatus.FaceDown, game));
		Controller.setStateMachine();
	}

	@Given("domino {int} is in the current draft")
	public void domino_is_in_the_current_draft(Integer domID) {
		Game g = KingdominoApplication.getKingdomino().getCurrentGame();
		g.getCurrentDraft().addIdSortedDomino(getdominoByID(domID));
	}

	@Given("the current player has selected domino {int}")
	public void the_current_player_has_selected_domino(Integer domID) {
		Game g = KingdominoApplication.getKingdomino().getCurrentGame();
		g.getCurrentDraft().addSelection(g.getNextPlayer(), getdominoByID(domID));
	}

	@Given("the player preplaces domino {int} at its initial position")
	public void the_player_preplaces_domino_at_its_initial_position(Integer domID) {
		// TODO: Write code here that turns the phrase above into concrete actions
		Game g = KingdominoApplication.getKingdomino().getCurrentGame();
		Player p = g.getNextPlayer();
		Domino dom = getdominoByID(domID);
		dom.setStatus(DominoStatus.ErroneouslyPreplaced);
		DominoInKingdom dik = new DominoInKingdom(0, 0, p.getKingdom(), dom);
	}

	ArrayList<ArrayList<Integer>> ret = null;

	@When("the player attempts to discard the selected domino")
	public void the_player_attempts_to_discard_the_selected_domino() {
		// TODO: Call your Controller method here.
		Game g = KingdominoApplication.getKingdomino().getCurrentGame();
		List<DominoSelection> dsList = g.getCurrentDraft().getSelections();
		Domino dom = null;
		for (DominoSelection ds : dsList) {
			if (ds.getPlayer() == g.getNextPlayer()) {
				dom = ds.getDomino();
			}
		}

		ret = Controller.discardDomino(g.getNextPlayer(), dom);
	}

	@Then("domino {int} shall have status {string}")
	public void domino_shall_have_status(Integer domID, String domStatus) {
		DominoStatus actualStatus = getdominoByID(domID).getStatus();
		DominoStatus expectedStatus = getDominoStatus(domStatus);
		assertEquals(expectedStatus, actualStatus);

	}

	/**
	 * Resolve Tie Break
	 *
	 * @param game
	 */

	@Given("the game is initialized for resolve tiebreak")
	public void the_game_is_initialized_for_resolve_tiebreak() {

		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		addDefaultUsersAndPlayers(game);
		createAllDominoes(game);
		game.setNextPlayer(game.getNextPlayer());
		KingdominoApplication.setKingdomino(kingdomino);
		Controller.setStateMachine();
	}

	HashMap<String, Integer> rankings = null;

	@Then("player standings should be the followings:")
	public void player_standings_should_be_the_followings(io.cucumber.datatable.DataTable dataTable) {

		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> rank : valueMaps) {
			String color = rank.get("player");
			Integer standing = rankings.get(color);
			Assert.assertEquals(Integer.decode(rank.get("standing")), standing);
		}
	}

	/**
	 * Identify Properties
	 *
	 * @param game
	 */

	@Given("the game is initialized for identify properties")
	public void the_game_is_initialized_for_identify_properties() {

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
		Controller.setStateMachine();
		
	}

	@When("the properties of the player are identified")
	public void the_properties_of_the_player_are_identified() {

		Controller.generateProperties(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer());
	}

	@Then("the player shall have the following properties:")
	public void the_player_shall_have_the_following_properties(io.cucumber.datatable.DataTable dataTable) {

		List<Map<String, String>> valueMaps = dataTable.asMaps();
		List<Property> props = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer().getKingdom()
				.getProperties();
		List<String> propStrings = new ArrayList<String>();
		for (Property prop : props) {
			List<Domino> dominoes = prop.getIncludedDominos();
			String[] doms = new String[dominoes.size()];
			for (int i = 0; i < doms.length; i++) {
				doms[i] = dominoes.get(i).getId() + "";
			}
			inPlaceNumericalSort(doms);
			String domsStr = String.join(",", doms);
			propStrings.add(Controller.terrainTypeToString(prop.getLeftTile()) + "[" + domsStr + "]");
		}

		for (Map<String, String> row : valueMaps) {
			String type = row.get("type");
			String[] expDoms = row.get("dominoes").split(",");
			inPlaceNumericalSort(expDoms);
			String expPropStr = type + "[" + String.join(",", expDoms) + "]";

			if (!propStrings.contains(expPropStr)) {
				String toPrint = "";
				for (String propString : propStrings) {
					toPrint += propString + "\n";
				}
				Assert.assertEquals(expPropStr, toPrint);
			}

		}
	}

	/**
	 * Scenario: Given it is "<player>"'s turn (Feature #11, 12, 13)
	 * 
	 * @author Zhekai Jiang
	 * 
	 * @param color String The color of the current player.
	 */
	@Given("it is {string}'s turn")
	public void it_is_player_s_turn(String color) {
		currentPlayer = getPlayerByColor(getPlayerColor(color));
	}

	/**
	 * Scenario: Given "<player>" has selected domino <id> (Features #11, 12, 13)
	 * 
	 * @author Zhekai Jiang
	 * 
	 * @param color String The color of the current player.
	 * @param id    Integer The ID of the domino chosen.
	 */
	@Given("{string} has selected domino {int}")
	public void has_selected_domino(String color, Integer id) {
		this.id = id;
		Draft newDraft = new Draft(DraftStatus.FaceUp, KingdominoApplication.getKingdomino().getCurrentGame());
		newDraft.addIdSortedDomino(getdominoByID(id));
		new DominoSelection(getPlayerByColor(getPlayerColor(color)), getdominoByID(id), newDraft);
	}

	/**
	 * Scenario: Given the "<player>"'s kingdom has following dominoes: (Features
	 * #11, 12) Adapted from the code given in DiscardDominoStepDefinitions.java.
	 * 
	 * @author Zhekai Jiang
	 * 
	 * @param color     String The color of the current player.
	 * @param dataTable DataTable The data table of the current player's dominoes in
	 *                  the kingdom
	 */
	@Given("{string}'s kingdom has following dominoes:")
	public void s_kingdom_has_following_dominoes(String color, io.cucumber.datatable.DataTable dataTable) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			Integer id = Integer.decode(map.get("id"));
			DirectionKind dir = getDirection(map.get("dir"));
			Integer posx = Integer.decode(map.get("posx"));
			Integer posy = Integer.decode(map.get("posy"));

			// Add the domino to a player's kingdom
			Domino dominoToPlace = getdominoByID(id);
			Kingdom kingdom = currentPlayer.getKingdom();
			DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
			domInKingdom.setDirection(dir);
			dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);
		}
	}

	/**
	 * Scenario: Given domino <id> is tentatively placed at position <posx>:<posy>
	 * with direction "<dir>" (Features #11, 12, 13)
	 * 
	 * @author Zhekai Jiang
	 * 
	 * @param id           Integer The ID of the domino chosen.
	 * @param x            Integer The x coordinate of the tentative placement.
	 * @param y            Integer The y coordinate of the tentative placement.
	 * @param directionStr String The direction of the tentative placement.
	 */
	@Given("domino {int} is tentatively placed at position {int}:{int} with direction {string}")
	public void domino_is_tentatively_placed_at_position_with_direction(Integer id, Integer x, Integer y,
			String directionStr) {
		this.leftX = x;
		this.leftY = y;
		this.direction = getDirection(directionStr);

		dominoToPreplace = getdominoByID(id);
		dominoInKingdom = new DominoInKingdom(x, y, currentPlayer.getKingdom(), dominoToPreplace);
		dominoInKingdom.setDirection(direction);
	}

	/**
	 * Scenario: Given the game is initialized for rotate current domino (Feature
	 * #12)
	 *
	 * @author Zhekai Jiang
	 */
	@Given("the game is initialized for rotate current domino")
	public void the_game_is_initialized_for_rotate_current_domino() {
		initializeGame();
	}

	/**
	 * Scenario: When "<player>" requests to rotate the domino with "<rotation>"
	 * (Feature #12)
	 * 
	 * @author Zhekai Jiang
	 * 
	 * @param color    String The current player's color
	 * @param rotation String Either "clockwise" or "counterclockwise"
	 */
	@When("{string} requests to rotate the domino with {string}")
	public void requests_to_rotate_the_domino_with(String color, String rotation) {
		Controller.rotateCurrentDomino(dominoInKingdom, rotation);
	}

	/**
	 * Scenario: Then the domino <id> is still tentatively placed at <posx>:<posy>
	 * but with new direction "<newDir>" (Feature #12)
	 * 
	 * @author Zhekai Jiang
	 * 
	 * @param id           Integer The ID of the current domino
	 * @param x            Integer The x coordinate of the leftTile of the current
	 *                     domino
	 * @param y            Integer The y coordinate of the leftTile of the current
	 *                     domino
	 * @param directionStr String The expected new direction
	 */
	@Then("the domino {int} is still tentatively placed at {int}:{int} but with new direction {string}")
	public void the_domino_is_still_tentatively_placed_at_but_with_new_direction(Integer id, Integer x, Integer y,
			String directionStr) {
		DominoInKingdom dominoInKingdom = getDominoInKingdomByID(id);
		assert (dominoInKingdom != null);
		assertEquals(x.intValue(), dominoInKingdom.getX());
		assertEquals(y.intValue(), dominoInKingdom.getY());
		assertEquals(getDirection(directionStr), dominoInKingdom.getDirection());
	}

	/**
	 * Scenario: Then the domino <id> should have status "<dstatus>" (Feature #12)
	 * 
	 * @author Zhekai Jiang
	 * 
	 * @param id     Integer The ID of the current domino
	 * @param status String Either "CorrectlyPreplaced" or "ErroneouslyPreplaced"
	 */
	@Then("the domino {int} should have status {string}")
	public void the_domino_should_have_status(Integer id, String status) {
		if (status.equals("CorrectlyPreplaced")) {
			assertEquals(DominoStatus.CorrectlyPreplaced, getdominoByID(id).getStatus());
		} else if (status.equals("ErroneouslyPreplaced")) {
			assertEquals(DominoStatus.ErroneouslyPreplaced, getdominoByID(id).getStatus());
		} else {
			throw new IllegalArgumentException(status + " is an invalid status.");
		}
	}

	/**
	 * Scenario: Given domino <id> has status "<dstatus>" (Feature #12)
	 * 
	 * @author Zhekai Jiang
	 * 
	 * @param id     The ID of the current domino
	 * @param status The status of the current domino, either "CorrectlyPreplaced"
	 *               or "ErroneouslyPreplaced"
	 */
	@Given("domino {int} has status {string}")
	public void domino_has_status(Integer id, String status) {
		if (status.equals("CorrectlyPreplaced")) {
			getdominoByID(id).setStatus(DominoStatus.CorrectlyPreplaced);
		} else if (status.equals("ErroneouslyPreplaced")) {
			getdominoByID(id).setStatus(DominoStatus.ErroneouslyPreplaced);
		} else {
			throw new IllegalArgumentException(status + " is not a valid status.");
		}
	}

	/**
	 * Scenario: Then domino <id> is tentatively placed at the same position
	 * <posx>:<posy> with the same direction "<dir>" (Feature #12)
	 * 
	 * @author Zhekai Jiang
	 * 
	 * @param id           Integer The ID of the current domino
	 * @param x            Integer The x coordinate of the leftTile of the current
	 *                     domino
	 * @param y            Integer The y coordinate of the leftTile of the current
	 *                     domino
	 * @param directionStr String The expected direction
	 */
	@Then("domino {int} is tentatively placed at the same position {int}:{int} with the same direction {string}")
	public void domino_is_tentatively_placed_at_the_same_position_with_the_same_direction(Integer id, Integer x,
			Integer y, String directionStr) {
		the_domino_is_still_tentatively_placed_at_but_with_new_direction(id, x, y, directionStr);
	}

	/**
	 * Scenario: Then domino <id> should still have status "<dstatus>" (Feature #12)
	 * 
	 * @author Zhekai Jiang
	 * 
	 * @param id     Integer The ID of the current domino
	 * @param status String Either "CorrectlyPreplaced" or "ErroneouslyPreplaced"
	 */
	@Then("domino {int} should still have status {string}")
	public void domino_should_still_have_status(Integer id, String status) {
		the_domino_should_have_status(id, status);
	}

	/**
	 * Scenario: Given the game is initialized for castle adjacency (Feature #14)
	 *
	 * @author Zhekai Jiang
	 */
	@Given("the game is initialized for castle adjacency")
	public void the_game_is_initialized_for_castle_adjacency() {
		initializeGame();
	}

	/**
	 * Scenario: Given the current player preplaced the domino with ID <id> at
	 * position <x>:<y> and direction "<direction>" (Features #14, 15, 16)
	 *
	 * @author Zhekai Jiang
	 *
	 * @param id        Integer The ID of the preplaced domino
	 * @param x         Integer The x coordinate of the position of the preplaced
	 *                  domino
	 * @param y         Integer The y coordinate of the position of the preplaced
	 *                  domino
	 * @param direction String The direction of the preplaced domino. It could be
	 *                  "up", "down", "left", or "right".
	 */
	@Given("the current player preplaced the domino with ID {int} at position {int}:{int} and direction {string}")
	public void the_current_player_preplaced_the_domino_with_ID_id_at_position_and_direction(Integer id, Integer x,
			Integer y, String direction) {

		this.leftX = x;
		this.leftY = y;

		this.id = id;
		this.direction = getDirection(direction);
	}

	/**
	 * Scenario: When check castle adjacency is initiated (Feature #14)
	 *
	 * @author Zhekai Jiang
	 */
	@When("check castle adjacency is initiated")
	public void check_castle_adjacency_is_initiated() {
		verificationResult = Controller.verifyCastleAdjacency(leftX, leftY, direction);
	}

	/**
	 * Scenario: Then the castle/domino adjacency is "<result>" (Feature #14)
	 *
	 * @author Zhekai Jiang
	 *
	 * @param expectedResult String The expected verification result, either "valid"
	 *                       or "invalid"
	 */
	@Then("the castle\\/domino adjacency is {string}")
	public void the_castle_domino_adjacency_is(String expectedResult) {
		assertEquals(verificationResult ? "valid" : "invalid", expectedResult);
	}

	/**
	 * Scenario: Given the game is initialized for neighbor adjacency (Feature #15)
	 *
	 * @author Zhekai Jiang
	 */
	@Given("the game is initialized for neighbor adjacency")
	public void the_game_is_initialized_for_neighbor_adjacency() {
		initializeGame();
	}

	/**
	 * Scenario: Given the following dominoes are present in a player's kingdom
	 * (Features #15, 16) Adapted from the code given in
	 * DiscardDominoStepDefinitions.java
	 *
	 * @author Zhekai Jiang
	 *
	 * @param dataTable io.cucumber.datatable.DataTable The data table of the
	 *                  dominoes present in the player's kingdom
	 */
	@Given("the following dominoes are present in a player's kingdom:")
	public void the_following_dominoes_are_present_in_a_player_s_kingdom(io.cucumber.datatable.DataTable dataTable) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			Integer id = Integer.decode(map.get("id"));
			DirectionKind dir = getDirection(map.get("dominodir"));
			Integer posx = Integer.decode(map.get("posx"));
			Integer posy = Integer.decode(map.get("posy"));

			// Add the domino to a player's kingdom
			Domino dominoToPlace = getdominoByID(id);
			Kingdom kingdom = game.getNextPlayer().getKingdom();
			DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
			domInKingdom.setDirection(dir);
			dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);
		}
		this.kingdom = game.getNextPlayer().getKingdom();
	}

	/**
	 * Scenario: When check current preplaced domino adjacency is initiated (Feature
	 * #15)
	 *
	 * @author Zhekai Jiang
	 */
	@When("check current preplaced domino adjacency is initiated")
	public void check_current_preplaced_domino_adjacency_is_initiated() {
		verificationResult = Controller.verifyNeighborAdjacency(kingdom, id, leftX, leftY, direction);
	}

	/**
	 * Scenario: Then the current-domino/existing-domino adjacency is "<result>"
	 * (Feature #15)
	 *
	 * @author Zhekai Jiang
	 *
	 * @param expectedResult String The expected verification result, either "valid"
	 *                       or "invalid"
	 */
	@Then("the current-domino\\/existing-domino adjacency is {string}")
	public void the_current_domino_existing_domino_adjacency_is(String expectedResult) {
		assertEquals(verificationResult ? "valid" : "invalid", expectedResult);
	}

	/**
	 * Scenario: Given the game is initialized to check domino overlapping (Feature
	 * #16)
	 *
	 * @author Zhekai Jiang
	 */
	@Given("the game is initialized to check domino overlapping")
	public void the_game_is_initialized_to_check_domino_overlapping() {
		initializeGame();
	}

	/**
	 * Scenario: When check current preplaced domino overlapping is initiated
	 * (Feature #16)
	 *
	 * @author Zhekai Jiang
	 */
	@When("check current preplaced domino overlapping is initiated")
	public void check_current_preplaced_domino_overlapping_is_initiated() {

		verificationResult = Controller.verifyNoOverlapping(kingdom, id, leftX, leftY, direction);

	}

	/**
	 * Scenario: Then the current-domino/existing-domino overlapping is "<result>"
	 * (Feature #16)
	 *
	 * @author Zhekai Jiang
	 *
	 * @param expectedResult String The expected verification result, either "valid"
	 *                       or "invalid"
	 */
	@Then("the current-domino\\/existing-domino overlapping is {string}")
	public void the_current_domino_existing_domino_overlapping_is(String expectedResult) {
		assertEquals(verificationResult ? "valid" : "invalid", expectedResult);
	}

	/**
	 * Scenario: Given the game is initialized for verify grid size (Feature #17)
	 *
	 * @author Zhekai Jiang
	 */
	@Given("the game is initialized for verify grid size")
	public void the_game_is_initialized_for_verify_grid_size() {
		initializeGame();
		id = 0;
	}

	/**
	 * Scenario: Given the player preplaces domino <id> to their kingdom at position
	 * <posx>:<posy> with direction "<dominodir>" (Feature #17)
	 *
	 * @author Zhekai Jiang
	 *
	 * @param id        Integer The ID of the preplaced domino
	 * @param x         Integer The x coordinate of the position of the preplaced
	 *                  domino
	 * @param y         Integer The y coordinate of the position of the preplaced
	 *                  domino
	 * @param direction String The direction of the preplaced domino. It could be
	 *                  "up", "down", "left", or "right".
	 */
	@Given("the  player preplaces domino {int} to their kingdom at position {int}:{int} with direction {string}")
	public void the_player_preplaces_domino_to_their_kingdom_at_position_with_direction(Integer id, Integer x,
			Integer y, String direction) {
		this.id = id;
		this.leftX = x;
		this.leftY = y;
		this.direction = getDirection(direction);
	}

	/**
	 * Scenario: When validation of the grid size is initiated (Feature #17)
	 *
	 * @author Zhekai Jiang
	 */
	@When("validation of the grid size is initiated")
	public void validation_of_the_grid_size_is_initiated() {
		if (id == 0)
			verificationResult = Controller.verifyKingdomGridSize(kingdom);
		else
			verificationResult = Controller.verifyKingdomGridSize(kingdom, id, leftX, leftY, direction);

	}

	/**
	 * Scenario: Then the grid size of the player's kingdom shall be {string}
	 * (Feature #17)
	 *
	 * @author Zhekai Jiang
	 *
	 * @param expectedResult String The expected verification result, either "valid"
	 *                       or "invalid"
	 */
	@Then("the grid size of the player's kingdom shall be {string}")
	public void the_grid_size_of_the_player_s_kingdom_shall_be(String expectedResult) {
		assertEquals(verificationResult ? "valid" : "invalid", expectedResult);
	}

	/**
	 * Feature #21: Calculate bonus scores
	 *
	 * @author Gregory Walfish
	 *
	 */

	@Given("the game is initialized for calculate bonus scores")
	public void the_game_is_initialized_for_calculate_bonus_scores() {
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
		Controller.setStateMachine();
	}

	@Given("Middle Kingdom is selected as bonus option")
	public void middle_Kingdom_is_selected_as_bonus_option() {
		int numPlayers = Controller.getNumPlayers();
		Game game = Controller.getGame();
		Controller.setGameOptions(numPlayers, false, true);
	}

	@Given("the player's kingdom also includes the domino {int} at position {int}:{int} with the direction {string}")
	public void the_player_s_kingdom_also_includes_the_domino_at_position_with_the_direction(Integer id, Integer posx,
			Integer posy, String dir) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Domino dominoToPlace = getdominoByID(id);
		Kingdom kingdom = game.getNextPlayer().getKingdom();
		DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
		DirectionKind direction = getDirection(dir);
		domInKingdom.setDirection(direction);
		dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);

	}

	@When("calculate bonus score is initiated")
	public void calculate_bonus_score_is_initiated() {

		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player aPlayer = game.getNextPlayer();
		Controller.generateProperties(aPlayer);
		bonusScore = Controller.CalculateBonusScores(aPlayer);
	}

	@Then("the bonus score should be {int}")
	public void the_bonus_score_should_be(Integer int1) {
		Assertions.assertEquals(int1, bonusScore);
	}

	@Given("Harmony is selected as bonus option")
	public void harmony_is_selected_as_bonus_option() {
		int numPlayers = Controller.getNumPlayers();
		Game game = Controller.getGame();
		Controller.setGameOptions(numPlayers, true, false);
	}

	@Given("the player's kingdom also includes the following dominoes:")
	public void the_player_s_kingdom_also_includes_the_following_dominoes(io.cucumber.datatable.DataTable dataTable) {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
		// Map<K, List<V>>. E,K,V must be a String, Integer, Float,
		// Double, Byte, Short, Long, BigInteger or BigDecimal.
		//
		// For other transformations you can register a DataTableType.
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			Integer id = Integer.decode(map.get("id"));
			DirectionKind dir = getDirection(map.get("dominodir"));
			Integer posx = Integer.decode(map.get("posx"));
			Integer posy = Integer.decode(map.get("posy"));

			// Add the domino to a player's kingdom
			Domino dominoToPlace = getdominoByID(id);
			Kingdom kingdom = game.getNextPlayer().getKingdom();
			DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
			domInKingdom.setDirection(dir);
			dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);
		}
	}

	/**
	 * Feature #22: Calculate player Score
	 *
	 * @author Gregory Walfish
	 *
	 */

	@Given("the game is initialized for calculate player score")
	public void the_game_is_initialized_for_calculate_player_score() {
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
		Controller.setStateMachine();
	}

	@Given("the game has {string} bonus option")
	public void the_game_has_bonus_option(String string) {
		int numPlayers = Controller.getNumPlayers();
		Game game = Controller.getGame();
		boolean mK = false;
		boolean h = false;
		if (string.equalsIgnoreCase("MiddleKingdom") || string.equalsIgnoreCase("Middle Kingdom")) {
			string = "MiddleKingdom";
			mK = true;
		}
		if (string.equalsIgnoreCase("Harmony")) {
			string = "Harmony";
			h = true;
		}
		Controller.setGameOptions(numPlayers, h, mK);

	}

	@When("calculate player score is initiated")
	public void calculate_player_score_is_initiated() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player aPlayer = game.getNextPlayer();
		totalScore = Controller.CalculatePlayerScore(aPlayer);
	}

	@Then("the total score should be {int}")
	public void the_total_score_should_be(Integer int1) {
		Assertions.assertEquals(int1, totalScore);
	}

	/**
	 * Feature #20: Calculate property attributes
	 *
	 * @author Gregory Walfish
	 *
	 */

	@Given("the game is initialized for calculate property attributes")
	public void the_game_is_initialized_for_calculate_property_attributes() {
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
		Controller.setStateMachine();
	}

	@Given("the players kingdom has the following dominoes:")
	public void the_players_kingdom_has_the_following_dominoes(io.cucumber.datatable.DataTable dataTable) {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
		// Map<K, List<V>>. E,K,V must be a String, Integer, Float,
		// Double, Byte, Short, Long, BigInteger or BigDecimal.
		//
		// For other transformations you can register a DataTableType.
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			Integer id = Integer.decode(map.get("id"));
			DirectionKind dir = getDirection(map.get("dominodir"));
			Integer posx = Integer.decode(map.get("posx"));
			Integer posy = Integer.decode(map.get("posy"));

			// Add the domino to a player's kingdom
			Domino dominoToPlace = getdominoByID(id);
			Kingdom kingdom = game.getNextPlayer().getKingdom();
			DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
			domInKingdom.setDirection(dir);
			dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);
		}
		// this.kingdom = game.getPlayer(0).getKingdom();
	}

	@When("calculate property attributes is initiated")
	public void calculate_property_attributes_is_initiated() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player aPlayer = game.getNextPlayer();
		Controller.generateProperties(aPlayer);
		List<Property> propertiesInGame = aPlayer.getKingdom().getProperties();
		for (Property property : propertiesInGame) {
			Controller.CalculatePropertyAttributes(property);
			// System.out.println(property.toString());
		}
		properties = propertiesInGame.size();

	}

	@Then("the player shall have a total of {int} properties")
	public void the_player_shall_have_a_total_of_properties(Integer int1) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
//		Player aPlayer = game.getPlayer(0);
//		//Controller.generateProperties(aPlayer);
//		List<Property> propertiesInGame = aPlayer.getKingdom().getProperties();

		Assertions.assertEquals(int1, properties);
	}

	@Then("the player shall have properties with the following attributes:")
	public void the_player_shall_have_properties_with_the_following_attributes(
			io.cucumber.datatable.DataTable dataTable) {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
		// Map<K, List<V>>. E,K,V must be a String, Integer, Float,
		// Double, Byte, Short, Long, BigInteger or BigDecimal.
		//
		// For other transformations you can register a DataTableType.
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			String type = map.get("type");
			Integer size = Integer.decode(map.get("size"));
			Integer crowns = Integer.decode(map.get("crowns"));
		}
	}

	/**
	 * Feature #11: Move current domino
	 *
	 * @author Gregory Walfish // Ezra Gomolin
	 *
	 */

	@Given("the game is initialized for move current domino")
	public void the_game_is_initialized_for_move_current_domino() {
		initializeGame();
	}

	@When("{string} removes his king from the domino {int}")
	public void removes_his_king_from_the_domino(String string, Integer int1) {
		Player current = getPlayerByColor(getPlayerColor(string));
		Domino currentDomino = getdominoByID(int1);
		DominoInKingdom dik = new DominoInKingdom(0, 0, current.getKingdom(), currentDomino);
		
		cok = Controller.RemoveKing(dik);
		
	}

	@Then("domino {int} should be tentative placed at position {int}:{int} of {string}'s kingdom with ErroneouslyPreplaced status")
	public void domino_should_be_tentative_placed_at_position_of_s_kingdom_with_ErroneouslyPreplaced_status(
			Integer int1, Integer int2, Integer int3, String string) {
		
		
		Assertions.assertEquals(int2, cok.getX());
		Assertions.assertEquals(int3, cok.getY());
	}

	@When("{string} requests to move the domino {string}")
	public void requests_to_move_the_domino(String player, String movement) {
		
		Controller.MoveDomino(dominoInKingdom, movement);
	}

	int idDik;

	@Then("the domino {int} should be tentatively placed at position {int}:{int} with direction {string}")
	public void the_domino_should_be_tentatively_placed_at_position_with_direction(Integer id, Integer x, Integer y,
			String directionStr) {
		idDik = id;
		DominoInKingdom dominoInKingdom = getDominoInKingdomByID(id);
		
		assert (dominoInKingdom != null);
		assertEquals(x.intValue(), dominoInKingdom.getX());
		assertEquals(y.intValue(), dominoInKingdom.getY());
		assertEquals(getDirection(directionStr), dominoInKingdom.getDirection());
	}

	@Then("the new status of the domino is {string}")
	public void the_new_status_of_the_domino_is(String status) {
		if (status.equals("CorrectlyPreplaced")) {
			assertEquals(DominoStatus.CorrectlyPreplaced, getdominoByID(idDik).getStatus());
		} else if (status.equals("ErroneouslyPreplaced")) {
			assertEquals(DominoStatus.ErroneouslyPreplaced, getdominoByID(idDik).getStatus());
		} else {
			throw new IllegalArgumentException(status + " is an invalid status.");
		}
	}

	@Then("the domino {int} is still tentatively placed at position {int}:{int}")
	public void the_domino_is_still_tentatively_placed_at_position(Integer id, Integer x, Integer y) {
		idDik = id;
		DominoInKingdom dominoInKingdom = getDominoInKingdomByID(id);
		assert (dominoInKingdom != null);
		assertEquals(x.intValue(), dominoInKingdom.getX());
		assertEquals(y.intValue(), dominoInKingdom.getY());

	}

	@Then("the domino should still have status {string}")
	public void the_domino_should_still_have_status(String string) {
		the_new_status_of_the_domino_is(string);
	}

	/**
	 * Feature #23: Calculate ranking
	 *
	 * @author Gregory Walfish
	 *
	 */

	@Given("the game is initialized for calculate ranking")
	public void the_game_is_initialized_for_calculate_ranking() {
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
		Controller.setStateMachine();
	}

	@Given("the players have the following two dominoes in their respective kingdoms:")
	public void the_players_have_the_following_two_dominoes_in_their_respective_kingdoms(
			io.cucumber.datatable.DataTable dataTable) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			String color = map.get("player");
			int domino1 = Integer.decode(map.get("domino1"));
			int domino2 = Integer.decode(map.get("domino2"));
			String direction1 = map.get("dominodir1");
			String direction2 = map.get("dominodir2");
			int posx1 = Integer.decode(map.get("posx1"));
			int posy1 = Integer.decode(map.get("posy1"));
			int posx2 = Integer.decode(map.get("posx2"));
			int posy2 = Integer.decode(map.get("posy2"));

			DirectionKind dir1 = getDirection(direction1);
			DirectionKind dir2 = getDirection(direction2);

			Domino dom1 = getdominoByID(domino1);
			Domino dom2 = getdominoByID(domino2);

			PlayerColor col = getPlayerColor(color);
			Player player = null;
			for (Player p : game.getPlayers()) {
				if (p.getColor() == col) {
					player = p;
					break;
				}
			}

			Kingdom kingdom = player.getKingdom();
			DominoInKingdom domInKingdom1 = new DominoInKingdom(posx1, posy1, kingdom, dom1);
			domInKingdom1.setDirection(dir1);
			dom1.setStatus(DominoStatus.PlacedInKingdom);

			DominoInKingdom domInKingdom2 = new DominoInKingdom(posx2, posy2, kingdom, dom2);
			domInKingdom2.setDirection(dir2);
			dom2.setStatus(DominoStatus.PlacedInKingdom);
		}

		for (Player p : game.getPlayers()) {
			Controller.generateProperties(p);
			Controller.CalculatePropertyScore(p);
		}

	}

	@Given("the players have no tiebreak")
	public void the_players_have_no_tiebreak() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Player> players = game.getPlayers();
		HashSet<Integer> set = new HashSet<Integer>();
		for (Player player : players) {
			int playerScore = Controller.CalculatePlayerScore(player);
			set.add(playerScore);
		}
		if (!(set.size() == game.getPlayers().size())) {
			fail();
		}

	}

	@When("calculate ranking is initiated")
	public void calculate_ranking_is_initiated() {
		Controller.CalculateRanking();
		rankings = Controller.calculateRankings();
	}

	@Then("player standings shall be the followings:")
	public void player_standings_shall_be_the_followings(io.cucumber.datatable.DataTable dataTable) {

		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			String color = map.get("player");
			PlayerColor pColor = getPlayerColor(color);
			Player current = getPlayerByColor(pColor);
			HashMap<Player, Integer> ranks = getRanking();
			Integer standing = ranks.get(current);
			Assert.assertEquals(Integer.decode(map.get("standing")), standing);
		}
	}

	/**
	 * Helper method to initialize the game From the code given in
	 * DiscardDominoStepDefinitions.java
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
		Controller.setStateMachine();
	}

	// (adapted from DiscardDominoStepDefinitions.java)

	private Domino getdominoByID(int id) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Domino domino : game.getAllDominos()) {
			if (domino.getId() == id) {
				return domino;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
	}

	/**
	 * Helper method: Get the DominoInKingdom instance corresponding to the domino
	 * with a given ID Throws an IllegalArgumentException if no such an instance
	 * exists
	 * 
	 * @author Zhekai Jiang
	 * 
	 * @param id int The ID of the domino
	 * @return DominoInKingdom The DominoInKingdom instance
	 */
	private DominoInKingdom getDominoInKingdomByID(int id) {
		if (getdominoByID(id).getDominoSelection() == null)
			throw new java.lang.IllegalArgumentException("Domino with ID " + id + " is not selected yet.");
		for (KingdomTerritory i : getdominoByID(id).getDominoSelection().getPlayer().getKingdom().getTerritories()) {
			if (i instanceof DominoInKingdom && ((DominoInKingdom) i).getDomino().getId() == id) {
				return (DominoInKingdom) i;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " is not found in any kingdom.");
	}

	private static TerrainType getTerrainType(String terrain) {
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

	private DominoStatus getDominoStatus(String status) {
		switch (status) {
		case "inPile":
			return DominoStatus.InPile;
		case "excluded":
			return DominoStatus.Excluded;
		case "inCurrentDraft":
			return DominoStatus.InCurrentDraft;
		case "inNextDraft":
			return DominoStatus.InNextDraft;
		case "erroneouslyPreplaced":
			return DominoStatus.ErroneouslyPreplaced;
		case "correctlyPreplaced":
			return DominoStatus.CorrectlyPreplaced;
		case "placedInKingdom":
			return DominoStatus.PlacedInKingdom;
		case "discarded":
			return DominoStatus.Discarded;
		default:
			throw new java.lang.IllegalArgumentException("Invalid domino status: " + status);
		}
	}

	/**
	 *
	 * @author Gregory Walfish
	 */
	private HashMap<Player, Integer> getRanking() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Player> players = game.getPlayers();
		HashMap<Player, Integer> rankings = new HashMap<Player, Integer>();
		for (Player player : players) {
			rankings.put(player, player.getCurrentRanking());
		}

		return rankings;

	}

	/**
	 *
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

	@Given("the game is initialized for set game options")
	public void the_game_is_initialized_for_set_game_options() {
		// Intialize empty game
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		kingdomino.setCurrentGame(game);
		KingdominoApplication.setKingdomino(kingdomino);
	}

	@When("set game options is initiated")
	public void set_game_options_is_initiated() {
		Controller.clearGameOptions(KingdominoApplication.getKingdomino().getCurrentGame());
	}

	@When("the number of players is set to {int}")
	public void set_number_of_players(Integer numPlayers) {
		Controller.setNumPlayers(KingdominoApplication.getKingdomino().getCurrentGame(), numPlayers);
	}

	@When("Harmony {string} selected as bonus option")
	public void set_harmony(String harmony) {
		if (harmony.equals("is")) {
			Controller.setBonusOption("Harmony");
		}
	}

	@When("Middle Kingdom {string} selected as bonus option")
	public void set_middle_kingdom(String MiddleKingdom) {
		if (MiddleKingdom.equals("is")) {
			Controller.setBonusOption("MiddleKingdom");
		}
	}

	@Then("the number of players shall be {int}")
	public void number_of_players(Integer numplayers) {
		int actualNumber = KingdominoApplication.getKingdomino().getCurrentGame().getNumberOfPlayers();
		int expectedNumber = numplayers;
		assertEquals(expectedNumber, actualNumber);
	}

	@Then("Harmony {string} an active bonus")
	public void has_harmony(String toggle) {
		BonusOption harmony = Controller.getBonusOption(KingdominoApplication.getKingdomino(), "Harmony");
		int actualIndex = KingdominoApplication.getKingdomino().getCurrentGame().indexOfSelectedBonusOption(harmony);
		if (toggle.equals("is")) {
			assertTrue(actualIndex >= 0);
		} else {
			assertTrue(actualIndex < 0);
		}

	}

	@Then("MiddleKingdom {string} an active bonus")
	public void has_middle_kingdom(String toggle) {
		BonusOption MiddleKingdom = Controller.getBonusOption(KingdominoApplication.getKingdomino(), "MiddleKingdom");
		int actualIndex = KingdominoApplication.getKingdomino().getCurrentGame()
				.indexOfSelectedBonusOption(MiddleKingdom);
		if (toggle.equals("is")) {
			assertTrue(actualIndex >= 0);
		} else {
			assertTrue(actualIndex < 0);
		}

	}

	@Then("Middle Kingdom {string} an active bonus") // It seems the step definitions now have a white space between
														// Middle and Kingdom (Added by Zhekai)
	public void has_middle_kingdom_2(String toggle) {
		BonusOption MiddleKingdom = Controller.getBonusOption(KingdominoApplication.getKingdomino(), "MiddleKingdom");
		int actualIndex = KingdominoApplication.getKingdomino().getCurrentGame()
				.indexOfSelectedBonusOption(MiddleKingdom);
		if (toggle.equals("is")) {
			assertTrue(actualIndex >= 0);
		} else {
			assertTrue(actualIndex < 0);
		}

	}

	@Given("the program is started and ready for starting a new game")
	public void the_program_is_ready_for_new_game() {
		// Intialize empty game
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		kingdomino.setCurrentGame(game);
		KingdominoApplication.setKingdomino(kingdomino);
	}

	@Given("there are four selected players")
	public void four_players() {

		for (int i = 0; i < 4; i++) {
			Player player = new Player(KingdominoApplication.getKingdomino().getCurrentGame());
			player.setColor(PlayerColor.values()[i]);
		}
	}

	@Given("bonus options Harmony and MiddleKingdom are selected")
	public void bonus_options() {

		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Controller.setBonusOption("Harmony");
		Controller.setBonusOption("MiddleKingdom");
	}

	@When("starting a new game is initiated")
	public void start_game() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Controller.startGame(game);
	}

	@When("reveal first draft is initiated")
	public void reveal_first_draft() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Controller.revealFirstDraft(game);
	}

	@Then("all kingdoms shall be initialized with a single castle")
	public void single_castle_check() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Player p : game.getPlayers()) {
			int castleCount = 0;
			for (KingdomTerritory t : p.getKingdom().getTerritories()) {
				if (t instanceof Castle) {
					castleCount++;
				}
			}
			assertEquals(castleCount, 1);
		}

	}

	@Then("all castle are placed at 0:0 in their respective kingdoms")
	public void castle_position_check() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Player p : game.getPlayers()) {
			for (KingdomTerritory t : p.getKingdom().getTerritories()) {
				if (t instanceof Castle) {
					assertEquals(t.getX(), 0);
					assertEquals(t.getY(), 0);
				}
			}

		}

	}

	@Then("the first draft of dominoes is revealed")
	public void first_draft_reveal_check() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		assertTrue(game.getCurrentDraft().getDraftStatus() == DraftStatus.FaceUp);

	}

	@Then("all the dominoes form the first draft are facing up")
	public void domino_faceup_check() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Domino d : game.getCurrentDraft().getIdSortedDominos()) {
			assertTrue(d.getStatus() != DominoStatus.InPile);
		}

	}

	@Then("all the players have no properties")
	public void no_properties_check() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Player p : game.getPlayers()) {
			assertEquals(p.getKingdom().numberOfProperties(), 0);

		}

	}

	@Then("all player scores are initialized to zero")
	public void initial_score_check() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Player p : game.getPlayers()) {
			assertEquals(p.getPropertyScore(), 0);
			assertEquals(p.getBonusScore(), 0);
			assertEquals(p.getTotalScore(), 0);
		}

	}

	@Given("the game is initialized for save game")
	public void the_game_is_initialized_for_save_game() {
		Kingdomino kingdomino = new Kingdomino();
		KingdominoApplication.setKingdomino(kingdomino);
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		for (int i = 0; i < 4; i++) {
			game.addPlayer();
		}
		Controller.startGame(game);
		Draft draft = new Draft(DraftStatus.FaceUp, game);
		for (int i = 0; i < 4; i++) {
			draft.addIdSortedDomino(game.getAllDomino(i));
		}
		game.setCurrentDraft(draft);

		int count = 0;
		for (Player p : game.getPlayers()) {
			new DominoSelection(p, draft.getIdSortedDomino(count), draft);
			count++;
		}

		draft = new Draft(DraftStatus.FaceUp, game);
		for (int i = 4; i < 8; i++) {
			draft.addIdSortedDomino(game.getAllDomino(i));
		}
		game.setNextDraft(draft);

	}

	@Given("the game is still in progress")
	public void game_in_progress() {

	}

	@Given("no file named {string} exists in the filesystem")
	public void no_file_exists(String filename) {

		File file = new File(filename);
		if (file.exists()) {
			file.delete();
		}
	}

	@When("the user initiates saving the game to a file named {string}")
	public void user_saves_file(String filename) {

		Controller.saveGame(KingdominoApplication.getKingdomino().getCurrentGame(), filename, true);
	}

	@Then("a file named {string} shall be created in the filesystem")
	public void file_created_check(String filename) {

		File file = new File(filename);
		assertTrue(file.exists());

	}

	@Given("the file named {string} exists in the filesystem")
	public void file_exists_check(String filename) {

		File file = new File(filename);
		assertTrue(file.exists());

	}

	@When("the user agrees to overwrite the existing file named {string}")
	public void user_agrees_overwrite(String filename) {

	}

	@Then("the file named {string} shall be updated in the filesystem")
	public void file_updated_check(String filename) {

		File file = new File(filename);
		assertTrue(file.exists());

	}

	@Given("the game is initialized for load game")
	public void the_game_is_initialized_for_load_game() {
		Kingdomino kingdomino = new Kingdomino();
		KingdominoApplication.setKingdomino(kingdomino);

	}

	@When("I initiate loading a saved game from {string}")
	public void user_loads_file(String filename) {

		succesfulLoad = Controller.loadGame(filename);
	}

	@When("each tile placement is valid")
	public void load_valid() {

		assertTrue(succesfulLoad);
	}

	@When("the game result is not yet final")
	public void game_not_final() {

	}

	@Then("it shall be player {int}'s turn")
	public void player_turn_check(Integer player) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		assertEquals(player - 1, game.indexOfPlayer(game.getNextPlayer()));
	}

	@Then("each of the players should have the corresponding tiles on their grid:")
	public void player_kingdom_tile_check(io.cucumber.datatable.DataTable dataTable) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			Integer player = Integer.decode(map.get("playerNumber"));
			String[] tiles = map.get("playerTiles").split(",");

			List<Domino> expected = new ArrayList<Domino>();
			List<Domino> actual = new ArrayList<Domino>();

			for (String s : tiles) {
				expected.add(getdominoByID(Integer.decode(s)));
//				expected.add(game.getAllDomino(Integer.decode(s) - 1));
			}

			for (KingdomTerritory t : game.getPlayer(player - 1).getKingdom().getTerritories()) {
				if (t instanceof DominoInKingdom) {
					actual.add(((DominoInKingdom) t).getDomino());
				}
			}

			assertEquals(expected.size(), actual.size());

			for (Domino d : expected) {
				assertTrue(actual.contains(d));
			}

		}
	}

	@Then("each of the players should have claimed the corresponding tiles:")
	public void player_claimed_tile_check(io.cucumber.datatable.DataTable dataTable) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			Integer player = Integer.decode(map.get("playerNumber"));
			Integer tile = Integer.decode(map.get("claimedTile"));

			Domino expected = getdominoByID(tile);// game.getAllDomino(tile);
			Domino actual = game.getPlayer(player - 1).getDominoSelection().getDomino();

			assertEquals(expected, actual);

		}
	}

	@Then("tiles {string} shall be unclaimed on the board")
	public void player_claimed_tile_check(String unclaimed) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Domino> expected = new ArrayList<Domino>();
		List<Domino> actual = new ArrayList<Domino>();

		for (String s : unclaimed.split(", ")) { // there are also white spaces in the test cases
			expected.add(getdominoByID(Integer.decode(s)));
			// expected.add(game.getAllDomino(Integer.decode(s)));
		}

		for (Domino d : game.getNextDraft().getIdSortedDominos()) {
			if (!d.hasDominoSelection())
				actual.add(d);
		}

		assertEquals(expected.size(), actual.size());

		for (Domino d : expected) {
			assertTrue(actual.contains(d));
		}

	}

	@Then("the game shall become ready to start")
	public void game_ready_to_start() {

	}

	@Then("the game shall notify the user that the loaded game is invalid")
	public void load_invalid() {

		assertTrue(!succesfulLoad);
	}

	@After
	public void tearDown() {
		KingdominoApplication.getKingdomino().delete();
	}

	private void inPlaceNumericalSort(String[] arr) {
		Arrays.sort(arr, new Comparator<String>() {
			@Override
			public int compare(String a, String b) {
				return Integer.valueOf(a).compareTo(Integer.valueOf(b));
			}
		});
	}

	public static void addDefaultUsersAndPlayers(Game game) {
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

	public static void createAllDominoes(Game game) {
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

	// helper function
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

	/*****
	 * -----------------------------------------BROWSE
	 * DOMINOES--------------------------------------------------
	 *****/

	/**
	 * browse dominoes
	 *
	 * @throws Throwable
	 */

	@Given("the program is started and ready for browsing dominoes")
	public void programStartAndReadyToBrowse() {
		Kingdomino kingdomino = new Kingdomino();
		Game orderDraft = new Game(48, kingdomino);
		orderDraft.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(orderDraft);
		// Populate game
		addDefaultUsersAndPlayers(orderDraft);
		createAllDominoes(orderDraft);
		orderDraft.setNextPlayer(orderDraft.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
	}

	List<Domino> dominoList;

	@When("I initiate the browsing of all dominoes")
	public void initiateBrowsingAllDominoes() {
		dominoList = Controller.listAll(KingdominoApplication.getKingdomino().getCurrentGame());
	}

	@Then("all the dominoes are listed in increasing order of identifiers")
	public void listDominoesbyID() {
		System.out.println(dominoList);
	}

	Domino d;

	@When("I provide a domino ID {int}")
	public void provideID(int id) {
		d = Controller.SelectAndObserveDomino(KingdominoApplication.getKingdomino().getCurrentGame().getAllDominos(),
				id);
	}

	@Then("the listed domino has {string} left terrain")
	public void hasLeftTerrain(String t) {

		String e = Controller.terrainTypeToString(d.getLeftTile());

		assertEquals(e, t);
	}

	@Then("the listed domino has {string} right terrain")
	public void hasRightTerrain(String t) {
		String e = Controller.terrainTypeToString(d.getRightTile());

		assertEquals(e, t);
	}

	@Then("the listed domino has {int} crowns")
	public void hasCrowns(int crowns) {
		assertEquals(d.getLeftCrown() + d.getRightCrown(), crowns);
	}

	List<Integer> sameTerrain;

	@When("I initiate the browsing of all dominoes of {string} terrain type")
	public void browseByTerrainType(String t) {

		if (t.equalsIgnoreCase("wheat")) {
			t = "WheatField";
		} else if (t.equalsIgnoreCase("mountain")) {
			t = "Mountain";
		} else if (t.equalsIgnoreCase("lake")) {
			t = "Lake";
		} else if (t.equalsIgnoreCase("swamp")) {
			t = "Swamp";
		} else if (t.equalsIgnoreCase("forest")) {
			t = "Forest";
		} else if (t.equalsIgnoreCase("grass")) {
			t = "Grass";
		}

		sameTerrain = Controller.filterbyTerrain(TerrainType.valueOf(t));
	}

	@Then("list of dominoes with IDs {string} should be shown")
	public void showListOfIDSbyTerrain(String list) {
		assertEquals(sameTerrain.toString().replaceAll("\\s+", "").replaceAll("\\[", "").replaceAll("\\]", ""), list);
	}

	@After
	public void close1() {
		KingdominoApplication.getKingdomino().delete();
	}

	/*****
	 * ---------------------------------------------------SHUFFLE
	 * DOMINOES-----------------------------------------------
	 *****/

	/**
	 * Shuffle dominoes
	 */

	@Given("the game is initialized for shuffle dominoes")
	public void gameStartAndReadyToShuffle() throws Throwable {
		Kingdomino kingdomino = new Kingdomino();
		Game shuffle = new Game(48, kingdomino);
		shuffle.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(shuffle);
		// Populate game
		addDefaultUsersAndPlayers(shuffle);
		createAllDominoes(shuffle);
		shuffle.setNextPlayer(shuffle.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
		KingdominoApplication.getKingdomino().getCurrentGame()
				.setCurrentDraft(new Draft(Draft.DraftStatus.FaceDown, shuffle));
		
	}

	@Given("there are {int} players playing")
	public void thereAreNPlayersPlaying(int nb) {
		KingdominoApplication.getKingdomino().getCurrentGame().setNumberOfPlayers(nb);
	}

	List<Domino> shuffledList;
	Draft draft;

	@When("the shuffling of dominoes is initiated")
	public void shuffleInitiated() {
		shuffledList = Controller
				.shuffleDominoes(KingdominoApplication.getKingdomino().getCurrentGame().getAllDominos());
	}

	@Then("the first draft shall exist")
	public void firstDraftExists() {
		draft = KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft();
		if (draft != null) {
			assert true;
		} else
			assert false;
	}

	@Then("the first draft should have {int} dominoes on the board face down")
	public void firstDraftStatus(int onBoard) {
		assertEquals(draft.numberOfIdSortedDominos(), onBoard);
	}

	@Then("there should be {int} dominoes left in the draw pile")
	public void dominoesLeft(int left) {
		assertEquals(shuffledList.size(), left);
	}

	List<Domino> arranged;

	/** -------String instead of int array??????????---- */
	@When("I initiate to arrange the domino in the fixed order (.*)")
	public void arrangeDraft(String arrangement) {
		List<Integer> fixedList = StrToInt(arrangement,
				KingdominoApplication.getKingdomino().getCurrentGame().numberOfAllDominos());
		arranged = Controller.fixedArrangement(fixedList);
	}

	@Then("the draw pile should consist of everything in (.*) except the first (.*) dominoes with their order preserved")
	public void pileConsists(String arrangement, int onBoard) {

		List<Integer> arrangedIntList = new ArrayList<>();

		for (int i = 0; i < KingdominoApplication.getKingdomino().getCurrentGame().numberOfAllDominos(); i++) {
			arrangedIntList.add(arranged.get(i).getId());
		}

		List<Integer> aye = StrToInt(arrangement,
				KingdominoApplication.getKingdomino().getCurrentGame().numberOfAllDominos());

		assertEquals(draft.numberOfIdSortedDominos(), onBoard);
		assertEquals(arrangedIntList, aye);
	}

	// END OF PAIN

	@After
	public void close2() {
		KingdominoApplication.getKingdomino().delete();
	}

	/*****
	 * --------------------------------------------------CREATE NEXT
	 * DRAFT---------------------------------------------------
	 *****/

	/**
	 * create next draft
	 */

	@Given("the game is initialized to create next draft")
	public void gameIsInitializedToCreateNextDraft() {
		Kingdomino kingdomino = new Kingdomino();
		Game createDraft = new Game(48, kingdomino);
		createDraft.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(createDraft);
		// Populate game
		addDefaultUsersAndPlayers(createDraft);
		createAllDominoes(createDraft);
		createDraft.setNextPlayer(createDraft.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
		KingdominoApplication.getKingdomino().getCurrentGame()
				.setCurrentDraft(new Draft(Draft.DraftStatus.FaceDown, createDraft)); // is this legit?
		KingdominoApplication.getKingdomino().getCurrentGame()
				.setNextDraft(new Draft(Draft.DraftStatus.FaceDown, createDraft));
	}

	@Given("there has been (.*) drafts created")
	public void numDraftsCreated(int numDrafts) {
		if (KingdominoApplication.getKingdomino().getCurrentGame().getNumberOfPlayers() > 2) {
			if (numDrafts < 12) {
				assertEquals(KingdominoApplication.getKingdomino().getCurrentGame().numberOfAllDrafts(), numDrafts);
			} else if (numDrafts == 12) {
				for (int i = 0; i < 10; i++) {
					Draft draft = new Draft(Draft.DraftStatus.FaceDown,
							KingdominoApplication.getKingdomino().getCurrentGame());
					KingdominoApplication.getKingdomino().getCurrentGame().addAllDraft(draft);
				}
				assertEquals(KingdominoApplication.getKingdomino().getCurrentGame().numberOfAllDrafts(), numDrafts);
			}
		} else {
			if (numDrafts < 6) {
				assertEquals(KingdominoApplication.getKingdomino().getCurrentGame().numberOfAllDrafts(), numDrafts);
			} else if (numDrafts == 6) {
				for (int i = 0; i < 4; i++) {
					Draft draft = new Draft(Draft.DraftStatus.FaceDown,
							KingdominoApplication.getKingdomino().getCurrentGame());
					KingdominoApplication.getKingdomino().getCurrentGame().addAllDraft(draft);

				}
				assertEquals(KingdominoApplication.getKingdomino().getCurrentGame().numberOfAllDrafts(), numDrafts);
			}
		}
	}

	@Given("there is a current draft")
	public void thereIsACurrentDraft() {
		if (KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft() != null) {
			assert true;
		}
	}

	@Given("there is a next draft")
	public void thereIsANextDraft() {
		if (KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft() != null) {
			assert true;
		}
	}


	@Given("the top 5 dominoes in my pile have the IDs {string}")
	public void top5withIDs(String ids) {
		List<Integer> top5 = StrToInt(ids, 5);

		List<Integer> idList = new ArrayList<>();

		int x = KingdominoApplication.getKingdomino().getCurrentGame().numberOfAllDrafts()
				* KingdominoApplication.getKingdomino().getCurrentGame().getNumberOfPlayers();

		for (int i = x; i < x + 5; i++) {
			idList.add(KingdominoApplication.getKingdomino().getCurrentGame().getAllDomino(i).getId());
		}

		assertEquals(idList, top5);
	}


	Draft nextDraft;
	Draft oldNextDraft;

	@When("create next draft is initiated")
	public void initiateCreateNextDraft() {
		oldNextDraft = Controller.getGame().getNextDraft();
		nextDraft = Controller.CreateNextDraft(KingdominoApplication.getKingdomino().getCurrentGame().getAllDominos());
	}

	// why are these the same??????

	@Then("a new draft is created from dominoes {string}")
	public void newDraftMade(String d) {

		List<Integer> deez = StrToInt(d, KingdominoApplication.getKingdomino().getCurrentGame().getNumberOfPlayers());
		List<Integer> idList = new ArrayList<>();

		for (int i = 0; i < KingdominoApplication.getKingdomino().getCurrentGame().getNumberOfPlayers(); i++) {
			idList.add(nextDraft.getIdSortedDomino(i).getId());
		}
		assertEquals(idList, deez);
	}

	@Then("the next draft now has the dominoes {string}")
	public void nextDraftNowHas(String d) {
		List<Integer> deez = StrToInt(d, KingdominoApplication.getKingdomino().getCurrentGame().getNumberOfPlayers());
		List<Integer> idList = new ArrayList<>();

		for (int i = 0; i < KingdominoApplication.getKingdomino().getCurrentGame().getNumberOfPlayers(); i++) {
			idList.add(nextDraft.getIdSortedDomino(i).getId());
		}
		assertEquals(idList, deez);
	}

	//

	@Then("the dominoes in the next draft are face down")
	public void areTheyFaceDown() {
		if (nextDraft.getDraftStatus() == Draft.DraftStatus.FaceDown) {
			assert true;
		} else
			assert false;
	}


	@Then("the top domino of the pile is ID (.*)")
	public void topOfPile(int id) {
		assertEquals(
				KingdominoApplication.getKingdomino().getCurrentGame().getTopDominoInPile().getId(),
				id);
	}


	@Then("the former next draft is now the current draft")
	public void nowIsCurrentDraft() {
		assertEquals(KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft(), oldNextDraft);
	}

	@Given("this is a (.*) player game")
	public void nbOfPlayers(int num) {
		assertEquals(KingdominoApplication.getKingdomino().getCurrentGame().getNumberOfPlayers(), num);
	}

	@Then("the pile is empty")
	public void checkPileEmpty() {
		assertEquals(null, null);
	}

	@Then("there is no next draft")
	public void noNextDraft() {
		assertEquals(KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft(), null);
	}

	@After
	public void close3() {
		KingdominoApplication.getKingdomino().delete();
	}

	/*****
	 * ----------------------------------------REVEAL AND ORDER NEXT
	 * DRAFT-------------------------------------------------------
	 *****/

	/**
	 * Reveal and Order next draft
	 */

	@Given("the game is initialized for order next draft of dominoes")
	public void gameInitializedForOrderNextDraft() {
		Kingdomino kingdomino = new Kingdomino();
		Game orderDraft = new Game(48, kingdomino);
		orderDraft.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(orderDraft);
		// Populate game
		addDefaultUsersAndPlayers(orderDraft);
		createAllDominoes(orderDraft);
		orderDraft.setNextPlayer(orderDraft.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
		// Controller.setStateMachine();
		KingdominoApplication.getKingdomino().getCurrentGame()
				.setNextDraft(new Draft(Draft.DraftStatus.FaceDown, orderDraft));
	
	}

	@Given("the dominoes in next draft are facing down")
	public void orderFaceDown() {
		KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().getDraftStatus();
	}

	@Given("the next draft is {string}")
	public void nextDraftIs(String ids) { // SHOULD BE A STRING BUT IT DOESNT WORK

		List<Integer> idInts = StrToInt(ids,
				KingdominoApplication.getKingdomino().getCurrentGame().getNumberOfPlayers());

		if (!KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().hasIdSortedDominos()) {
			for (int i = 0; i < KingdominoApplication.getKingdomino().getCurrentGame().getNumberOfPlayers(); i++) {
				KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().addIdSortedDomino(
						KingdominoApplication.getKingdomino().getCurrentGame().getAllDomino(idInts.get(i) - 1));
			}
		}
		KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().getIdSortedDominos();
	}

	@When("the ordering of the dominoes in the next draft is initiated")
	public void orderDraft() {
		Controller.OrderDraft(KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft());
	}

	@Then("the status of the next draft is sorted")
	public void StatusSorted() {
		if (KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft()
				.setDraftStatus(Draft.DraftStatus.Sorted))
			assert true;
		else
			assert false;
	}

	@Then("the order of dominoes in the draft will be {string}")
	public void orderOfDominoesWillBe(String d) {

		List<Integer> deez = StrToInt(d,
				KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().numberOfIdSortedDominos());

		List<Integer> dominoes = new ArrayList<>();

		for (int i = 0; i < KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft()
				.numberOfIdSortedDominos(); i++) {
			dominoes.add(
					KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().getIdSortedDomino(i).getId());
		}

		assertEquals(dominoes, deez);
	}

	@Given("the game is initialized for reveal next draft of dominoes")
	public void gameInitializedForRevealNextDraft() {
		Kingdomino kingdomino = new Kingdomino();
		Game orderDraft = new Game(48, kingdomino);
		orderDraft.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(orderDraft);
		// Populate game
		addDefaultUsersAndPlayers(orderDraft);
		createAllDominoes(orderDraft);
		orderDraft.setNextPlayer(orderDraft.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);

		KingdominoApplication.getKingdomino().getCurrentGame()
				.setNextDraft(new Draft(Draft.DraftStatus.FaceDown, orderDraft));
		Controller.setStateMachine();
	}

	@Given("the dominoes in next draft are sorted")
	public void nextIsSorted() {
		Controller.OrderDraft(KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft());
	}

	@When("the revealing of the dominoes in the next draft is initiated")
	public void revealDraft() {
		Controller.RevealDraft(KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft());
	}

	@Then("the status of the next draft is face up")
	public void StatusFaceUp() {
		if (KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft()
				.setDraftStatus(Draft.DraftStatus.FaceUp))
			assert true;
		else
			assert false;
	}
	
	@Given("domino {int} is in \"CorrectlyPreplaced\" status")
    public void dominoIsCorrectlyPreplaced(int id) {
    	Controller.getdominoByID(id).setStatus(DominoStatus.CorrectlyPreplaced);
    }
    
    @When("{string} requests to place the selected domino {int}")
    public void playerPlacesDomino(String player, int id) {
    	Player p = getPlayerByColor(getPlayerColor(player));
    	Controller.placeDomino(Controller.getDominoInKingdom(p, id));
    }
    
    @Then("{string}'s kingdom should now have domino {int} at position {int}:{int} with direction {string}")
    public void dominoHasBeenPlaced(String player, int id, int _x, int _y, String dir){
    	
    	boolean found = false;
    	
    	for(KingdomTerritory t : getPlayerByColor(getPlayerColor(player)).getKingdom().getTerritories()) {
    		if(t instanceof DominoInKingdom ) {
    			DominoInKingdom d = (DominoInKingdom)t;
    			if(d.getDomino().getId() == id) {
    				found = true;
    				assertEquals(_x, d.getX());
    				assertEquals(_y, d.getY());
    				assertEquals(getDirection(dir), d.getDirection());
    			}
    			
    		}
    	}
    	
    	assertTrue(found);
    }


	@After
	public void close4() {
		KingdominoApplication.getKingdomino().delete();
	}

	private List<Integer> StrToInt(String str, int length) {
		Scanner scanner = new Scanner(str);
		scanner.useDelimiter(",\\s*|\\s+|\"\\s*");

		List<Integer> intList = new ArrayList<>();
		for (int i = 0; i < length; i++) {
			intList.add(scanner.nextInt());
		}

		return intList;
	}

	/*****-----------------------------------------------------------------------choose next domino-----------------------------------------------------------------***/

	@Given("the game is initialized for choose next domino")
	public void gameInitializedForChooseNextDomino() {
		Kingdomino kingdomino = new Kingdomino();
		Game chooseNextDom = new Game(48, kingdomino);
		chooseNextDom.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(chooseNextDom);
		// Populate game
		addDefaultUsersAndPlayers(chooseNextDom);
		createAllDominoes(chooseNextDom);
		chooseNextDom.setNextPlayer(chooseNextDom.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);

		KingdominoApplication.getKingdomino().getCurrentGame().setCurrentDraft(new Draft(Draft.DraftStatus.FaceDown, chooseNextDom));
		KingdominoApplication.getKingdomino().getCurrentGame().setNextDraft(new Draft(Draft.DraftStatus.FaceDown, chooseNextDom));
		
	}

	@Given("the next draft is sorted with dominoes {string}")
	public void nextDraftIsSorted(String d) {
		List<Integer> nd = StrToInt(d, KingdominoApplication.getKingdomino().getCurrentGame().getNumberOfPlayers());

		for (int i = 0; i < KingdominoApplication.getKingdomino().getCurrentGame().getNumberOfPlayers(); i++) {
			KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().addIdSortedDomino(getdominoByID(nd.get(i)));
		}
		
	}

	List<DominoSelection> selections = new ArrayList<>();

	@Given("player's domino selection {string}")
	public void domSelectionis(String d) {

		Scanner scan = new Scanner(d);
		scan.useDelimiter(",");

		List<String> colors = new ArrayList<>();

		for (int i = 0; i < KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().getIdSortedDominos().size(); i++) {
			colors.add(scan.next());
		}

		for (int i = 0; i < KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().getIdSortedDominos().size(); i++) {
			Player p;
			if (!colors.get(i).equalsIgnoreCase("none")) {
				p = getPlayerByColor(getPlayerColor(colors.get(i)));
				KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().addSelection(p, KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().getIdSortedDomino(i));
			}
		}
	}

	Player player;

	@Given("the current player is {string}")
	public void currentplayeris(String d) {
		player = getPlayerByColor(getPlayerColor(d));
	}

	@When("current player chooses to place king on {int}")
	public void placeking(Integer id) {
		Controller.chooseNextDomino(KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft(), getdominoByID(id), player);
	}

	@Then("current player king now is on {string}")
	public void kingisnowon(String id) {
		List<Integer> chosenid = StrToInt(id, id.length());
		//KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().addSelection(player, getdominoByID(chosenid.get(0)));
	}

	@Then("the selection for next draft is now equal to {string}")
	public void newSelectionis(String sel) {

		Scanner scan = new Scanner(sel);
		scan.useDelimiter(",");

		List<String> colors = new ArrayList<>();

		for (int i = 0; i < KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().getIdSortedDominos().size(); i++) {
			colors.add(scan.next());
		}

		List<DominoSelection> temp = new ArrayList<>();

		for (int i = 0; i < KingdominoApplication.getKingdomino().getCurrentGame().getNumberOfPlayers(); i++) {
			if (!colors.get(i).equalsIgnoreCase("none")) {
				temp.add(getPlayerByColor(getPlayerColor(colors.get(i))).getDominoSelection());
			}
		}

		assertEquals(KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().getSelections(), temp);
	}

	@Then("the selection for the next draft selection is still {string}")
	public void stillSelection(String sel) {

		Scanner scan = new Scanner(sel);
		scan.useDelimiter(",");

		List<String> colors = new ArrayList<>();

		for (int i = 0; i < KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().getIdSortedDominos().size(); i++) {
			colors.add(scan.next());
		}

		List<DominoSelection> temp = new ArrayList<>();

		for (int i = 0; i < KingdominoApplication.getKingdomino().getCurrentGame().getNumberOfPlayers(); i++) {
			if (!colors.get(i).equalsIgnoreCase("none")) {
				temp.add(getPlayerByColor(getPlayerColor(colors.get(i))).getDominoSelection());
			}
		}

		assertEquals(KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().getSelections(), temp);
	}

	@After
	public void close5() {
		KingdominoApplication.getKingdomino().delete();
	}

}
