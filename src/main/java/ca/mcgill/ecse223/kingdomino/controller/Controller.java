package ca.mcgill.ecse223.kingdomino.controller;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;

import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.*;

public interface Controller {
	
	// TODO Methods for state machine
	
	/**
	 * TODO JavaDoc
	 */
	public static Gameplay getStateMachine() {
		return getCurrentGame().getGameplay();
	}

	static void setStateMachine() {
		getCurrentGame().setGameplay(new Gameplay(KingdominoApplication.getKingdomino().getCurrentGame()));
	}
	
	static void setStateMachine(String status) {
		getCurrentGame().setGameplay(new Gameplay(KingdominoApplication.getKingdomino().getCurrentGame(), status));
	}
	/**
	 * TODO JavaDoc
	 */
	public static void triggerSelectionComplete() {
		getStateMachine().selectionComplete();
	}
	
	/**
	 * TODO JavaDoc
	 */
	public static void triggerPlacementComplete() {
		getStateMachine().placementComplete();
	}
	
	/**
	 * TODO JavaDoc?
	 * @author Zhekai Jiang
	 */
	public static void moveCurrentDomino(String direction) {
		MoveDomino(getDominoInKingdomByID(getCurrentGame().getNextPlayer().getDominoSelection().getDomino().getId()), direction);
	}
	
	/**
	 * TODO JavaDoc?
	 * @author Zhekai Jiang
	 */
	public static void rotateCurrentDomino(String rotation) {
		rotateCurrentDomino(getDominoInKingdomByID(getCurrentGame().getNextPlayer().getDominoSelection().getDomino().getId()), rotation);
	}
	
	/**
	 * TODO JavaDoc?
	 * @author Zhekai Jiang
	 */
	public static void placeCurrentDomino() {
		placeDomino(getDominoInKingdomByID(getCurrentGame().getNextPlayer().getDominoSelection().getDomino().getId()));
	}
	
	public static boolean verifyNeighborAdjacency() {
		return verifyNeighborAdjacency(getDominoInKingdomByID(getCurrentGame().getNextPlayer().getDominoSelection().getDomino().getId()));
	}
	
	public static boolean verifyNoOverlapping() {
		return verifyNoOverlapping(getDominoInKingdomByID(getCurrentGame().getNextPlayer().getDominoSelection().getDomino().getId()));
	}
	
	public static boolean verifyKingdomGridSize() {
		return verifyKingdomGridSize(getCurrentGame().getNextPlayer().getKingdom());
	}
	
	public static String getCurrentDominoDirection() {
		return getDominoInKingdomByID(getCurrentGame().getNextPlayer().getDominoSelection().getDomino().getId()).getDirection().toString();
	}
	
	public static void addCurrentDominoToKingdom() {
		Player currentPlayer = getCurrentGame().getNextPlayer();
		new DominoInKingdom(0, 0, currentPlayer.getKingdom(), currentPlayer.getDominoSelection().getDomino());
	}
	
	public static void discardCurrentDomino() {
		Player currentPlayer = getCurrentGame().getNextPlayer();
		discardDomino(currentPlayer, currentPlayer.getDominoSelection().getDomino());
	}
	
	public static void triggerDiscardComplete() {
		getStateMachine().placementComplete();
	}
	
	static Game getCurrentGame() {
		return KingdominoApplication.getKingdomino().getCurrentGame();
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
	static DominoInKingdom getDominoInKingdomByID(int id) {
		if (getdominoByID(id).getDominoSelection() == null)
			throw new java.lang.IllegalArgumentException("Domino with ID " + id + " is not selected yet.");
		for (KingdomTerritory i : getdominoByID(id).getDominoSelection().getPlayer().getKingdom().getTerritories()) {
			if (i instanceof DominoInKingdom && ((DominoInKingdom) i).getDomino().getId() == id) {
				return (DominoInKingdom) i;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " is not found in any kingdom.");
	}

	/**
	 * ProvideUserProfile Feature #2
	 * 
	 * @author Ezra Gomolin
	 *
	 *         NewUser() adds a new user to the game if a user with the provided
	 *         name doesn't already exist.
	 * @param name The name of the new user
	 * @return boolean True if user added; False if a user with that name already
	 *         exists
	 *
	 *         browseUsers() iterates over all the users in the game
	 * @return List of users sorted in alphabetical order
	 *
	 *         queryGamesPlayed() iterates through all the users in the game and if
	 *         one of the players name matches the given name, it returns the number
	 *         of games played by the user
	 *
	 * @param name The name of the user
	 * @return Integer the number of games played by that user
	 *
	 *         queryGamesWon() iterates through all the users in the game and if one
	 *         of the players name matches the given name, it returns the number of
	 *         games won by the user
	 * @param name The name of the user
	 * @return Integer the number of games played by that user
	 */

	public static boolean NewUser(String name) {

		Kingdomino model = KingdominoApplication.getKingdomino();
		List<User> users = model.getUsers();
		for (int i = 0; i < users.size(); i++) {

			if (users.get(i).getName().equalsIgnoreCase(name) || name.equals("") || !name.matches("[a-zA-Z0-9]*")) {
				return false;
			}

		}
		model.addUser(name);
		return true;

	}

	public static List<User> browseUsers() {
		Kingdomino model = KingdominoApplication.getKingdomino();
		ArrayList<User> users = new ArrayList<User>(model.getUsers());

		for (int i = 0; i < users.size(); i++) {
			for (int j = i; j < users.size() - 1; j++) {
				if (users.get(j).getName().compareToIgnoreCase(users.get(j + 1).getName()) > 0) {
					User temp = users.get(j);
					users.set(j, users.get(j + 1));
					users.set(j + 1, temp);
				}
			}

		}

		return users;

	}

	public static int queryGamesPlayed(String name) {
		Kingdomino model = KingdominoApplication.getKingdomino();
		List<User> users = model.getUsers();
		for (User u : users) {
			if (u.getName().equals(name)) {
				return u.getPlayedGames();
			}
		}
		return 0;
	}

	public static int queryGamesWon(String name) {
		Kingdomino model = KingdominoApplication.getKingdomino();
		List<User> users = model.getUsers();
		for (User u : users) {
			if (u.getName().equals(name)) {
				return u.getWonGames();
			}
		}
		return 0;
	}

	/**
	 * Discard Domino Feature
	 *
	 * Updates the status of a provided domino based on its initial position and
	 * compatibility with the Kingdom.
	 *
	 * - Generates a list of all empty tiles on the Kingdom map - Generates a list
	 * of all possible holes (pairs of adjacent empty tiles) - Adds holes in both
	 * orientations (ex: will add 3,1 & 2,1 and also 2,1 & 3,1) - Reduces the list
	 * of possible holes to a list of valid holes - For each possible hole, checks:
	 * - That of the two tiles in the hole (say, t1 and t2): - There is at least one
	 * TerrainType match between the new domino's left tile and t1; OR - There is at
	 * least one TerrainType match between the new domino's right tile and t2 - AND
	 * that adding the domino in the hole would not push the kingdom past a size of
	 * 5x5 - If both checks are passed, the possible hole is considered valid - If
	 * no valid holes exist, the domino must be discarded - If the domino's initial
	 * position is in the list of valid holes, it has been CorrectlyPreplaced -
	 * Otherwise, it is ErroneouslyPreplaced
	 *
	 * @author Ezra Gomolin
	 * @param p   The player who is trying to place the domino
	 * @param dom The domino the player is trying to place
	 */
	public static ArrayList<ArrayList<Integer>> discardDomino(Player p, Domino dom) {
		Game g = KingdominoApplication.getKingdomino().getCurrentGame();
		DominoInKingdom dik = null;
		for (KingdomTerritory kt : p.getKingdom().getTerritories()) {
			if (kt instanceof DominoInKingdom) {
				DominoInKingdom currDik = (DominoInKingdom) kt;
				if (currDik.getDomino().getId() == dom.getId()) {
					dik = currDik;
				}
			}
		}

		TerrainType[][] map = kingdomToTerrainMap(p.getKingdom());
		ArrayList<Point> emptyTiles = new ArrayList<Point>();
		for (int mapY = 0; mapY < map.length; mapY++) {
			for (int mapX = 0; mapX < map[mapY].length; mapX++) {
				TerrainType t = map[mapY][mapX];
				if (t == null && !(mapX == 4 && mapY == 4)) {
					emptyTiles.add(new Point(mapX, mapY));
				}
			}
		}

		ArrayList<ArrayList<Integer>> possibleHoles = new ArrayList<ArrayList<Integer>>();
		for (Point emptyT1 : emptyTiles) {
			int x1 = emptyT1.x;
			int y1 = emptyT1.y;
			for (Point emptyT2 : emptyTiles) {
				int x2 = emptyT2.x;
				int y2 = emptyT2.y;
				if (areAdjacent(x1, y1, x2, y2)) {
					ArrayList<Integer> possibleHole = new ArrayList<Integer>();
					possibleHole.add(x1);
					possibleHole.add(y1);
					possibleHole.add(x2);
					possibleHole.add(y2);
					if (!possibleHoles.contains(possibleHole)) {
						possibleHoles.add(possibleHole);
					}
				}
			}
		}

		ArrayList<ArrayList<Integer>> validHoles = new ArrayList<ArrayList<Integer>>();
		for (ArrayList<Integer> hole : possibleHoles) {
			Point a = new Point(hole.get(0), hole.get(1));
			Point b = new Point(hole.get(2), hole.get(3));

			// Check if TerrainType match with a neighbour
			ArrayList<Point> aNs = getNeighbours(a);
			ArrayList<Point> bNs = getNeighbours(b);

			TerrainType left = dom.getLeftTile();
			TerrainType right = dom.getRightTile();

			boolean foundFit = false;
			for (Point n : aNs) {
				if (n.x < 0 || n.x > 8 || n.y < 0 || n.y > 8) {
					continue;
				}
				TerrainType curr = map[n.y][n.x];
				if (left == curr) {
					foundFit = true;
					break;
				}
			}
			for (Point n : bNs) {
				if (n.x < 0 || n.x > 8 || n.y < 0 || n.y > 8) {
					continue;
				}
				TerrainType curr = map[n.y][n.x];
				if (right == curr) {
					foundFit = true;
					break;
				}
			}
			if (!foundFit) {
				continue;
			}

			// Would it make the kingdom too large?
			if (!verifyKingdomGridSize(p.getKingdom(), a, b)) {
				continue;
			}

			// Hole passed the test; add to valid holes
			validHoles.add(hole);
		}

		if (validHoles.size() == 0) {
			dom.setStatus(DominoStatus.Discarded);
			triggerPlacementComplete();
		}

		return validHoles;
	}

	/**
	 * Resolve TieBreak Feature
	 *
	 * TiebreakPlayerComparator compares success of player A to player B in terms of
	 * (in descending order of priority): - Total score - Size of largest property -
	 * Total number of crowns in Kingdom
	 *
	 * @author Ezra Gomolin
	 */

	class TiebreakPlayerComparator implements Comparator<Player> {

		/**
		 * @author Ezra Gomolin
		 * @param A An arbitrary player A
		 * @param B An arbitrary player B
		 * @return int 1 if A wins over B; -1 if B wins over A; 0 if A and B are truly
		 *         tied
		 */
		@Override
		public int compare(Player A, Player B) {
			// Check scores
			if (A.getTotalScore() > B.getTotalScore()) {
				return 1;
			} else if (A.getTotalScore() < B.getTotalScore()) {
				return -1;
			}

			// Scores are equal; compare largest property size
			int propA = getLargestProperty(A).getSize();
			int propB = getLargestProperty(B).getSize();

			if (propA > propB) {
				return 1;
			} else if (propA < propB) {
				return -1;
			}

			// Largest prop sizes equal; compare crown count
			int crownsA = getCrownCount(A);
			int crownsB = getCrownCount(B);

			if (crownsA > crownsB) {
				return 1;
			} else if (crownsA < crownsB) {
				return -1;
			}

			// Real tie
			return 0;
		}
	}

	/**
	 * Calculates the rankings of all players in the current Game.
	 *
	 * - Sorts players in descending order of score using TiebreakPlayerComparator -
	 * Assigns the 0th player 1st place - For all other players (i=1 to n): -
	 * Compares success of player [i] to player [i-1] in terms of (in descending
	 * order of priority): - Total score - Size of largest property - Total number
	 * of crowns in Kingdom - If a success factor gives [i] the win over [i-1], the
	 * ranking of [i-1] is set to (ranking[i] + 1) - If not, the players are
	 * considered truly tied, and the ranking of [i-1] is set to (ranking[i])
	 *
	 * @author Ezra Gomolin
	 */

	public static HashMap<String, Integer> calculateRankings() {
		HashMap<String, Integer> rankings = new HashMap<String, Integer>();

		List<Player> players = new ArrayList<Player>(
				KingdominoApplication.getKingdomino().getCurrentGame().getPlayers());
		players.sort(new TiebreakPlayerComparator());
		Collections.reverse(players);

		rankings.put(players.get(0).getColor().toString().toLowerCase(), 1);
		players.get(0).setCurrentRanking(1);
		for (int i = 1; i < players.size(); i++) {
			Player curr = players.get(i);
			Player last = players.get(i - 1);

			Integer lastRank = rankings.get(last.getColor().toString().toLowerCase());

			if (curr.getTotalScore() < last.getTotalScore()) {
				curr.setCurrentRanking(lastRank + 1);
				rankings.put(curr.getColor().toString().toLowerCase(), lastRank + 1);
				continue;
			}

			// Scores are equal; check property size
			int propCurr = getLargestProperty(curr).getSize();
			int propLast = getLargestProperty(last).getSize();

			if (propCurr < propLast) {
				curr.setCurrentRanking(lastRank + 1);
				rankings.put(curr.getColor().toString().toLowerCase(), lastRank + 1);
				continue;
			}

			// Largest property sizes are equal; check crown count
			int crownsCurr = getCrownCount(curr);
			int crownsLast = getCrownCount(last);

			if (crownsCurr < crownsLast) {
				curr.setCurrentRanking(lastRank + 1);
				rankings.put(curr.getColor().toString().toLowerCase(), lastRank + 1);
				continue;
			}

			// Crowns are equal => it's a tie
			curr.setCurrentRanking(lastRank);
			rankings.put(curr.getColor().toString().toLowerCase(), lastRank);
		}

		return rankings;
	}

	/**
	 * Identify Properties Attributes Function
	 *
	 * - Iterate over each tile in the Kingdom map - Keep a list of tiles which
	 * shouldn't be checked because they've already been added to a property - For
	 * each tile in the Kingdom map: - We will be looping over the recursive
	 * neighbours of the tile to determine the extent of a property - If we've
	 * spread to a tile and added it to a property, it will be added to the toSkip
	 * list - So, it's safe to assume that any new tile in the For loop will be the
	 * start of a new property - Keep a list of tile in the property - Recurse over
	 * the neighbours of all tiles in this list - If a neighbour has the same type
	 * as the original tile, add to the property tile list - If a neighbour is the
	 * Crown square, also add to the property tile list - This is to make sure
	 * properties aren't interrupted by the Crown square as it part of any property
	 * - Stop recursing when the last has not grown since last iteration - Remove
	 * the Crown square from the property tile list - Iterate over all tiles and add
	 * up their Crown count for the total Property Crown count - Set Property data
	 * (e.g. size, Crown count, domino list) and add to Kingdom
	 *
	 * @author Ezra Gomolin
	 * @param p The player whose Kingdom is having its Properties generated
	 */

	public static void generateProperties(Player p) {
		TerrainType[][] map = kingdomToTerrainMap(p.getKingdom());

		p.getKingdom().clearProperty();

		ArrayList<Point> toSkip = new ArrayList<Point>();
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				Point current = new Point(x, y);

				if (toSkip.contains(current))
					continue;

				// Make sure that the tile is not an empty space
				TerrainType tile = map[y][x];
				if (tile == null)
					continue;

				// We've already made sure we're not re-checking any tiles (they were added to
				// toSkip)
				// So tile is the new type of our property

				Property prop = p.getKingdom().addProperty();
				prop.setLeftTile(tile);

				int lastSize = 0;
				ArrayList<Point> forProperty = new ArrayList<Point>();
				forProperty.add(current);

				while (forProperty.size() > lastSize) {
					lastSize = forProperty.size();

					ArrayList<Point> toAdd = new ArrayList<Point>();
					for (Point pt : forProperty) {
						// Check all surroundings of these points
						// If correct terrain type, add to forProperty

						ArrayList<Point> neighbours = getNeighbours(pt);
						for (Point neighbour : neighbours) {
							int nx = neighbour.x;
							int ny = neighbour.y;

							if (nx < 0 || nx > 8 || ny < 0 || ny > 8)
								continue;

							if (map[ny][nx] == tile)
								toAdd.add(neighbour);
						}
					}

					for (Point pt : toAdd) {
						if (!forProperty.contains(pt)) {
							forProperty.add(pt);
						}
					}
				}

				forProperty.remove(new Point(4, 4));

				int crownCount = 0;
				for (Point pt : forProperty) {
					toSkip.add(pt);

					for (KingdomTerritory terr : p.getKingdom().getTerritories()) {
						if (terr instanceof DominoInKingdom) {
							DominoInKingdom dik = (DominoInKingdom) terr;
							Point left = new Point(dik.getX() + 4, dik.getY() + 4);
							Point right = new Point(left.x, left.y);
							switch (dik.getDirection()) {
							case Up:
								right.y += 1;
								break;
							case Down:
								right.y -= 1;
								break;
							case Left:
								right.x -= 1;
								break;
							case Right:
								right.x += 1;
								break;
							}

							if (pt.equals(left) || pt.equals(right)) {
								prop.addIncludedDomino(dik.getDomino());
							}

							if (pt.equals(left)) {
								crownCount += dik.getDomino().getLeftCrown();
							}
							if (pt.equals(right)) {
								crownCount += dik.getDomino().getRightCrown();
							}
						}
					}
				}

				prop.setSize(forProperty.size());
				prop.setCrowns(crownCount);
				prop.setScore(crownCount * forProperty.size());
				p.getKingdom().addProperty(prop);
			}
		}
	}

	/**
	 * Feature 11: MoveCurrentDomino
	 *
	 * @author greg and ezra
	 */

	public static DominoInKingdom RemoveKing(DominoInKingdom currentDomino) {
		currentDomino.setX(0);
		currentDomino.setY(0);
		currentDomino.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
		return currentDomino;
	}

	public static void MoveDomino(DominoInKingdom domino, String movement) {
		int x = domino.getX();
		int y = domino.getY();
		if (movement.equals("right")) {
			domino.setX(domino.getX() + 1);

		} else if (movement.equals("left")) {
			domino.setX(domino.getX() - 1);

		} else if (movement.equals("down")) {
			domino.setY(domino.getY() - 1);

		} else if (movement.equals("up")) {
			domino.setY(domino.getY() + 1);
		}
		
		for (KingdomTerritory i : domino.getKingdom().getTerritories()) {
			if (i instanceof DominoInKingdom) {
				int leftX = i.getX(), leftY = i.getY(), rightX = getRightX(leftX, ((DominoInKingdom) i).getDirection()),
						rightY = getRightY(leftY, ((DominoInKingdom) i).getDirection());

				// fails due to board size restrictions (9*9)
				if (leftX >= 5 || leftX <= -5 || leftY >= 5 || leftY <= -5 || rightX >= 5 || rightX <= -5 || rightY >= 5
						|| rightY <= -5) {
					domino.setX(x);
					domino.setY(y);
					
					return;
				}
			}
		}
		if (verifyNeighborAdjacency(domino) && verifyNoOverlapping(domino)
				&& verifyKingdomGridSize(domino.getKingdom())) {
			domino.getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
		} else {
			domino.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
		}
		
		
		
	}
	

	/**
	 * Feature #12: Rotate current domino
	 * 
	 * @author Zhekai Jiang
	 * 
	 * @param domino   DominoInKingdom The domino to rotate
	 * @param rotation String Either "clockwise" or "counterclockwise"
	 */
	public static void rotateCurrentDomino(DominoInKingdom domino, String rotation) {
		DirectionKind oldDirection = domino.getDirection();
		if (rotation.equals("clockwise")) {
			switch (domino.getDirection()) {
			case Up:
				domino.setDirection(DirectionKind.Right);
				break;
			case Down:
				domino.setDirection(DirectionKind.Left);
				break;
			case Left:
				domino.setDirection(DirectionKind.Up);
				break;
			case Right:
				domino.setDirection(DirectionKind.Down);
				break;
			}
		} else if (rotation.equals("counterclockwise")) {
			switch (domino.getDirection()) {
			case Up:
				domino.setDirection(DirectionKind.Left);
				break;
			case Down:
				domino.setDirection(DirectionKind.Right);
				break;
			case Left:
				domino.setDirection(DirectionKind.Down);
				break;
			case Right:
				domino.setDirection(DirectionKind.Up);
				break;
			}
		} else {
			throw new IllegalArgumentException("Rotation " + rotation + " is invalid.");
		}

		for (KingdomTerritory i : domino.getKingdom().getTerritories()) {
			if (i instanceof DominoInKingdom) {
				int leftX = i.getX(), leftY = i.getY(), rightX = getRightX(leftX, ((DominoInKingdom) i).getDirection()),
						rightY = getRightY(leftY, ((DominoInKingdom) i).getDirection());

				// fails due to board size restrictions (9*9)
				if (leftX >= 5 || leftX <= -5 || leftY >= 5 || leftY <= -5 || rightX >= 5 || rightX <= -5 || rightY >= 5
						|| rightY <= -5) {
					domino.setDirection(oldDirection);
					return;
				}
			}
		}

		if (verifyNeighborAdjacency(domino) && verifyNoOverlapping(domino)
				&& verifyKingdomGridSize(domino.getKingdom())) {
			domino.getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
		} else {
			domino.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
		}
	}

	/**
	 * Feature #14: Verify castle adjacency Note: This verification has been
	 * included in Feature #15 Verify neighbor adjacency.
	 *
	 * @author Zhekai Jiang
	 *
	 * @param x         int The x coordinate of the leftTile of the current domino
	 *                  preplaced
	 * @param y         int The y coordinate of the leftTile of the current domino
	 *                  preplaced
	 * @param direction DirectionKind The direction of the current domino preplaced
	 * @return boolean True if the current domino is preplaced next to the castle
	 *         (valid), false otherwise (invalid)
	 */
	public static boolean verifyCastleAdjacency(int x, int y, DirectionKind direction) {
		int rightX = getRightX(x, direction), rightY = getRightY(y, direction);

		// Overlaps with the castle, invalid
		if ((x == 0 && y == 0) || (rightX == 0 && rightY == 0))
			return false;

		// Adjacent to the castle, valid
		if (areAdjacent(x, y, 0, 0) || areAdjacent(rightX, rightY, 0, 0))
			return true;

		return false;
	}

	/**
	 * Feature #15: Verify neighbor adjacency (Overloaded to support Features 10-13
	 * better. The new domino should have been added to the kingdom passed in.)
	 * 
	 * @author Zhekai Jiang
	 * 
	 * @param domino DominoInKingdom The current domino in kingdom to be verified
	 * @return boolean True if the current domino is preplaced to an adjacent
	 *         territory (valid), false otherwise (invalid)
	 */
	public static boolean verifyNeighborAdjacency(DominoInKingdom domino) {
		return verifyNeighborAdjacency(domino.getKingdom(), domino.getDomino().getId(), domino.getX(), domino.getY(),
				domino.getDirection());
	}

	/**
	 * Feature #15: Verify neighbor adjacency
	 *
	 * @author Zhekai Jiang
	 *
	 * @param kingdom   Kingdom The current player's kingdom
	 * @param id        int The ID of the current domino preplaced
	 * @param x         int The x coordinate of the leftTile of the current domino
	 *                  preplaced
	 * @param y         int The y coordinate of the leftTile of the current domino
	 *                  preplaced
	 * @param direction DirectionKind The direction of the current domino preplaced
	 * @return boolean True if the current domino is preplaced to an adjacent
	 *         territory (valid), false otherwise (invalid)
	 */
	public static boolean verifyNeighborAdjacency(Kingdom kingdom, int id, int x, int y, DirectionKind direction) {

		TerrainType leftTerrain = getdominoByID(id).getLeftTile(), rightTerrain = getdominoByID(id).getRightTile();
		int rightX = getRightX(x, direction), rightY = getRightY(y, direction);

		for (KingdomTerritory i : kingdom.getTerritories()) {

			if (i instanceof Castle) {
				int i_leftX = i.getX(), i_leftY = i.getY();

				// One of the tiles is adjacent to castle, valid
				if (areAdjacent(x, y, i_leftX, i_leftY) || areAdjacent(rightX, rightY, i_leftX, i_leftY))
					return true;
			}

			if (i instanceof DominoInKingdom && ((DominoInKingdom) i).getDomino().getId() != id) {
				DominoInKingdom i_asDominoInKingdom = (DominoInKingdom) i;
				DirectionKind i_direction = i_asDominoInKingdom.getDirection();
				TerrainType i_leftTerrain = i_asDominoInKingdom.getDomino().getLeftTile(),
						i_rightTerrain = i_asDominoInKingdom.getDomino().getRightTile();
				int i_leftX = i.getX(), i_leftY = i.getY();
				int i_rightX = getRightX(i_leftX, i_direction), i_rightY = getRightY(i_leftY, i_direction);

				if (leftTerrain == i_leftTerrain && areAdjacent(x, y, i_leftX, i_leftY))
					return true;
				if (leftTerrain == i_rightTerrain && areAdjacent(x, y, i_rightX, i_rightY))
					return true;
				if (rightTerrain == i_leftTerrain && areAdjacent(rightX, rightY, i_leftX, i_leftY))
					return true;
				if (rightTerrain == i_rightTerrain && areAdjacent(rightX, rightY, i_rightX, i_rightY))
					return true;
			}
		}

		return false;
	}

	/**
	 * Feature #16: Verify no overlappin (Overloaded to support Features 10-13
	 * better. The new domino should have been added to the kingdom passed in.)
	 * 
	 * @author Zhekai Jiang
	 * 
	 * @param domino DominoInKingdom The current domino in kingdom to be verified
	 * @return boolean True if the current domino is not overlapping with existing
	 *         dominos (valid), false otherwise (invalid)
	 */
	public static boolean verifyNoOverlapping(DominoInKingdom domino) {
		return verifyNoOverlapping(domino.getKingdom(), domino.getDomino().getId(), domino.getX(), domino.getY(),
				domino.getDirection());
	}

	/**
	 * Feature #16: Verify no overlapping
	 *
	 * @author Zhekai Jiang
	 *
	 * @param kingdom   Kingdom The current player's kingdom
	 * @param id        int The ID of the current domino preplaced
	 * @param x         int The x coordinate of the leftTile of the current domino
	 *                  preplaced
	 * @param y         int The y coordinate of the leftTile of the current domino
	 *                  preplaced
	 * @param direction DirectionKind The direction of the current domino preplaced
	 * @return boolean True if the current domino is not overlapping with existing
	 *         dominos (valid), false otherwise (invalid)
	 */
	public static boolean verifyNoOverlapping(Kingdom kingdom, int id, int x, int y, DirectionKind direction) {
		int rightX = getRightX(x, direction), rightY = getRightY(y, direction);

		for (KingdomTerritory i : kingdom.getTerritories()) {

			if (i instanceof Castle) {
				int i_leftX = i.getX(), i_leftY = i.getY();

				// Overlapping with castle, invalid
				if ((x == i_leftX && y == i_leftY) || (rightX == i_leftX && rightY == i_leftY))
					return false;
			}

			if (i instanceof DominoInKingdom && ((DominoInKingdom) i).getDomino().getId() != id) {
				DominoInKingdom i_asDominoInKingdom = (DominoInKingdom) i;
				int i_leftX = i.getX(), i_leftY = i.getY();
				DirectionKind i_direction = i_asDominoInKingdom.getDirection();
				int i_rightX = getRightX(i_leftX, i_direction), i_rightY = getRightY(i_leftY, i_direction);

				if ((x == i_leftX && y == i_leftY) || (x == i_rightX && y == i_rightY)
						|| (rightX == i_leftX && rightY == i_leftY) || (rightX == i_rightX && rightY == i_rightY)) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Feature #17: Verify kingdom grid size (overloaded #1 - input kingdom only)
	 *
	 * @author Zhekai Jiang
	 *
	 * @param kingdom Kingdom The current player's kingdom
	 * @return boolean True if the grid of the kingdom has not yet exceeded a square
	 *         of 5*5 tiles (valid), false otherwise (invalid)
	 */
	public static boolean verifyKingdomGridSize(Kingdom kingdom) {
		return verifyKingdomGridSizeHelper(kingdom, 0x7fffffff, 0x80000000, 0x7fffffff, 0x80000000);
	}

	/**
	 * Feature #17: Verify kingdom grid size (overloaded #2 - input kingdom and
	 * preplaced domino)
	 *
	 * @author Zhekai Jiang
	 *
	 * @param kingdom   Kingdom The current player's kingdom
	 * @param id        int The ID of the current domino preplaced
	 * @param x         int The x coordinate of the leftTile of the current domino
	 *                  preplaced
	 * @param y         int The y coordinate of the leftTile of the current domino
	 *                  preplaced
	 * @param direction DirectionKind The direction of the current domino preplaced.
	 * @return boolean True if the grid of the kingdom has not yet exceeded a square
	 *         of 5*5 tiles (valid), false otherwise (invalid)
	 */
	public static boolean verifyKingdomGridSize(Kingdom kingdom, int id, int x, int y, DirectionKind direction) {
		int rightX = getRightX(x, direction), rightY = getRightY(y, direction);
		return verifyKingdomGridSizeHelper(kingdom, Math.min(x, rightX), Math.max(x, rightX), Math.min(y, rightY),
				Math.max(y, rightY));
	}

	/**
	 * Helper method for Feature #17: Verify kingdom grid size Verify kingdom grid
	 * size with initial minimum and maximum values of x and y in the kingdom
	 *
	 * @author Zhekai Jiang
	 *
	 * @param kingdom Kingdom The current player's kingdom
	 * @param x_min   int Initial minimum value of x in the kingdom
	 * @param x_max   int Initial maximum value of x in the kingdom
	 * @param y_min   int Initial minimum value of y in the kingdom
	 * @param y_max   int Initial maximum value of y in the kingdom
	 * @return True if the grid of the kingdom has not yet exceeded a square of 5*5
	 *         tiles (valid), false otherwise (invalid)
	 */
	static boolean verifyKingdomGridSizeHelper(Kingdom kingdom, int x_min, int x_max, int y_min, int y_max) {
		for (KingdomTerritory i : kingdom.getTerritories()) {
			if (i instanceof Castle) {
				x_min = Math.min(x_min, i.getX());
				x_max = Math.max(x_max, i.getX());
				y_min = Math.min(y_min, i.getY());
				y_max = Math.max(y_max, i.getY());
			}

			if (i instanceof DominoInKingdom) {
				int leftX = i.getX(), leftY = i.getY();
				DirectionKind direction = ((DominoInKingdom) i).getDirection();
				int rightX = getRightX(leftX, direction), rightY = getRightY(leftY, direction);
				x_min = Math.min(x_min, Math.min(leftX, rightX));
				x_max = Math.max(x_max, Math.max(leftX, rightX));
				y_min = Math.min(y_min, Math.min(leftY, rightY));
				y_max = Math.max(y_max, Math.max(leftY, rightY));
			}

			// Note the condition should not be > 5 since there could be 0 (e.g. max = 2 and
			// min = -3, max - min = 5 but we have -3, -2, -1, 0, 1, 2)
			if (x_max - x_min >= 5 || y_max - y_min >= 5)
				return false;
		}

		return true;
	}

	/**
	 * Feature #20: Calculate property attributes
	 *
	 * @author Gregory Walfish
	 *
	 * @param aProperty aProperty to calculate the property attributes. calls on the
	 *                 methods to do so for each property.
	 *
	 *                 Calculate number of crowns takes in a property and goes
	 *                 through all the dominos checking for crowns and sums them up
	 *                 and sets them.
	 *
	 *                 Calculate size iterates through the property of all the
	 *                 dominos and sums up the size then calculate property
	 *                 attributes and calls on both methods
	 *
	 */

	public static void CalculateNumberOfCrowns(Property aProperty) {
		TerrainType terrain = aProperty.getLeftTile();
		List<Domino> includedDominos = aProperty.getIncludedDominos();
		int numOfCrowns = 0;
		for (int i = 0; i < aProperty.numberOfIncludedDominos(); i++) {
			if (includedDominos.get(i).getLeftTile().equals(terrain)) {
				numOfCrowns += includedDominos.get(i).getLeftCrown();
			}
			if (includedDominos.get(i).getRightTile().equals(terrain)) {
				numOfCrowns += includedDominos.get(i).getRightCrown();
			}

		}
		aProperty.setCrowns(numOfCrowns);
	}

	public static int CalculateSize(Property aProperty) {
		TerrainType terrain = aProperty.getLeftTile();
		List<Domino> includedDominos = aProperty.getIncludedDominos();
		int size = 0;
		for (int i = 0; i < aProperty.numberOfIncludedDominos(); i++) {
			if (includedDominos.get(i).getLeftTile().equals(terrain)) {
				size++;
			}
			if (includedDominos.get(i).getRightTile().equals(terrain)) {
				size++;
			}
		}

		aProperty.setSize(size);
		return size;
	}

	public static void CalculatePropertyAttributes(Property aProperty) {
		CalculateSize(aProperty);
		CalculateNumberOfCrowns(aProperty);

	}

	/**
	 * Feature #21: Calculate bonus scores
	 *
	 * @author Gregory Walfish
	 *
	 * @param aPlayer aPlayer player to calculate the bonus score and sets it.
	 *               summing up their bonus scores.
	 *
	 */

	public static int CalculateBonusScores(Player aPlayer) {
		Kingdomino KD = KingdominoApplication.getKingdomino();
		int bonusScore = 0;
		int index = KD.getCurrentGame().indexOfPlayer(aPlayer);
		List<BonusOption> bonusOptions = KD.getCurrentGame().getSelectedBonusOptions();
		for (BonusOption option : KD.getCurrentGame().getSelectedBonusOptions()) {
			if (option.getOptionName().equalsIgnoreCase("Harmony")) {
				int numOfDominos = KD.getCurrentGame().getPlayer(index).getKingdom().numberOfTerritories();
				if (numOfDominos == 13) {
					bonusScore += 5;
				}

			}
			if (option.getOptionName().equalsIgnoreCase("MiddleKingdom")) {
				Kingdom kingdom = KD.getCurrentGame().getPlayer(index).getKingdom();
				if (verifyMiddleKingdom(kingdom, 0x7fffffff, 0x80000000, 0x7fffffff, 0x80000000)) {
					bonusScore += 10;
				}
			}

			aPlayer.setBonusScore(bonusScore);
		}

		return bonusScore;
	}

	/**
	 * Feature #22: Calculate player score
	 *
	 * @author Gregory Walfish
	 *
	 * @param aPlayer aPlayer player to calculate the score and sets it. summing up
	 *               their property scores and their bonus scores.
	 * @return
	 *
	 */

	public static int CalculatePropertyScore(Player aPlayer) {
		generateProperties(aPlayer);
		int propertyScore = 0;
		Kingdomino KD = KingdominoApplication.getKingdomino();
		int index = KD.getCurrentGame().indexOfPlayer(aPlayer);
		List<Property> properties = KD.getCurrentGame().getPlayer(index).getKingdom().getProperties();

		if (KD.getCurrentGame().getPlayer(index).getKingdom().hasProperties()) {
			for (int i = 0; i < KD.getCurrentGame().getPlayer(index).getKingdom().numberOfProperties(); i++) {
				Property current = properties.get(i);
				int numOfSquares = current.getSize();
				int numOfCrowns = current.getCrowns();

				propertyScore += numOfSquares * numOfCrowns;

			}

		}
		aPlayer.setPropertyScore(propertyScore);
		return propertyScore;
	}

	public static int CalculatePlayerScore(Player aPlayer) {
		int propertyScore = CalculatePropertyScore(aPlayer);
		int bonusScore = CalculateBonusScores(aPlayer);

		return propertyScore + bonusScore;
	}
	public static void CalculateScore() {
		List<Player> allPlayers = new ArrayList<Player>(
				KingdominoApplication.getKingdomino().getCurrentGame().getPlayers());
		for(Player current : allPlayers) {
			CalculatePropertyScore(current);
			CalculateBonusScores(current);
		}
		
	}
	/**
	 * Feature #23: Calculate Ranking
	 *
	 * @author Gregory Walfish
	 *
	 *         Calculates the ranking for a specific game.
	 *
	 */
public static List<Player> CalculateRanking() {
		Kingdomino KD = KingdominoApplication.getKingdomino();
//			List<Player> players = KD.getCurrentGame().getPlayers();
		List<Player> rankedPlayers = new ArrayList<Player>(
				KingdominoApplication.getKingdomino().getCurrentGame().getPlayers());
		int aCurrentRanking = KD.getCurrentGame().getNumberOfPlayers();
		int numSorted = 0;
		for (int i = 0; i < rankedPlayers.size(); i++) {
			CalculatePlayerScore(rankedPlayers.get(i));
		}
		for (int j = 0; j < rankedPlayers.size(); j++) {
			for (int i = 0; i < rankedPlayers.size() - numSorted - 1; i++) {
				if (rankedPlayers.get(i).getTotalScore() > rankedPlayers.get(i + 1).getTotalScore()) {
					Collections.swap(rankedPlayers, i, i + 1);

				}
			}
			numSorted++;

		}
		for (int k = 0; k < rankedPlayers.size(); k++) {
			rankedPlayers.get(k).setCurrentRanking(aCurrentRanking);
			aCurrentRanking--;

		}
		return rankedPlayers;

	}

	/**
	 * Get the x coordinate of the right tile of a domino, given the x coordinate of
	 * its left tile
	 *
	 * @author Zhekai Jiang
	 *
	 * @param x         int The x coordinate of the left tile of the domino
	 * @param direction DirectionKind The direction of the domino
	 * @return int The x coordinate of the right tile of the domino
	 */
	static int getRightX(int x, DirectionKind direction) {
		switch (direction) {
		case Up:
		case Down:
			return x;
		case Left:
			return x - 1;
		case Right:
			return x + 1;
		default:
			throw new java.lang.IllegalArgumentException("Invalid domino direction: " + direction);
		}
	}

	/**
	 * Get the y coordinate of the right tile of a domino, given the y coordinate of
	 * its left tile
	 *
	 * @author Zhekai Jiang
	 *
	 * @param y         int The y coordinate of the left tile of the domino
	 * @param direction DirectionKind The direction of the domino
	 * @return int The y coordinate of the right tile of the domino
	 */
	static int getRightY(int y, DirectionKind direction) {
		switch (direction) {
		case Up:
			return y + 1;
		case Down:
			return y - 1;
		case Left:
		case Right:
			return y;
		default:
			throw new java.lang.IllegalArgumentException("Invalid domino direction: " + direction);
		}
	}

	/**
	 * Verify whether two positions are adjacent
	 *
	 * @author Zhekai Jiang
	 *
	 * @param x1 int The x coordinate of the first position
	 * @param y1 int The y coordinate of the first position
	 * @param x2 int The x coordinate of the second position
	 * @param y2 int The y coordinate of the second position
	 * @return boolean True if the two positions are adjacent, false otherwise
	 */
	static boolean areAdjacent(int x1, int y1, int x2, int y2) {
		return Math.abs(x1 - x2) + Math.abs(y1 - y2) == 1;
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

	/**
	 *
	 *
	 * @author Ezra Gomolin
	 * @param t The TerrainType to be converted to a String
	 * @return The String version of the provided TerrainType
	 */
	public static String terrainTypeToString(TerrainType t) {
		switch (t) {
		case WheatField:
			return "wheat";
		case Forest:
			return "forest";
		case Lake:
			return "lake";
		case Grass:
			return "grass";
		case Mountain:
			return "mountain";
		case Swamp:
			return "swamp";
		default:
			throw new IllegalArgumentException("This shouldn't even be possible");
		}
	}

	// Provided from professor in Step Definitions

	/**
	 * - Finds all instances of DominoInKingdom - Initializes all elements of the
	 * map to null so that spaces will remain null - Iterates over all Dominos and
	 * inserts l&r tile TerrainTypes into map
	 *
	 * @author Ezra Gomolin
	 * @param k The Kingdom to be connverted to a TerrainType map
	 * @return The 2-D TerrainType array representing the map
	 */

	public static TerrainType[][] kingdomToTerrainMap(Kingdom k) {
		ArrayList<DominoInKingdom> dominoes = new ArrayList<DominoInKingdom>();
		for (KingdomTerritory terr : k.getTerritories()) {
			if (terr instanceof DominoInKingdom) {
				dominoes.add(((DominoInKingdom) terr));
			}
		}

		TerrainType[][] map = new TerrainType[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				map[i][j] = null;
			}
		}

		for (DominoInKingdom dik : dominoes) {
			int LrealX = dik.getX();
			int LrealY = dik.getY();
			int LmapX = LrealX + 4;
			int LmapY = LrealY + 4;

			int RmapX = getRightX(LmapX, dik.getDirection());
			int RmapY = getRightY(LmapY, dik.getDirection());

			Domino dom = dik.getDomino();
			map[LmapY][LmapX] = dom.getLeftTile();
			map[RmapY][RmapX] = dom.getRightTile();
		}

		return map;
	}

	/**
	 * Helper fcn to translate a Point by X and Y
	 *
	 * @author Ezra Gomolin
	 * @param current The old Point
	 * @param relX    The translation amount in X
	 * @param relY    The translation amount in Y
	 * @return
	 */
	public static Point getNeighbour(Point current, int relX, int relY) {
		int newX = current.x + relX;
		int newY = current.y + relY;
		return new Point(newX, newY);
	}

	/**
	 * Helper fcn to return all 4 cardinal neighbours of a point
	 *
	 * @author Ezra Gomolin
	 * @param current The point for which to find neighbours
	 * @return ArrayList<Point> The list of all cardinal neighbours around the point
	 */
	public static ArrayList<Point> getNeighbours(Point current) {
		ArrayList<Point> neighbours = new ArrayList<Point>();
		neighbours.add(getNeighbour(current, 1, 0));
		neighbours.add(getNeighbour(current, -1, 0));
		neighbours.add(getNeighbour(current, 0, 1));
		neighbours.add(getNeighbour(current, 0, -1));
		return neighbours;
	}

	/**
	 * Calculates player Property score by adding up the scores of all the player's
	 * Properties
	 *
	 * @author Ezra Gomolin
	 * @param p Player for whom to calculate score
	 */
	public static void calculatePlayerPropertyScore(Player p) {
		int playerScore = 0;
		for (Property current : p.getKingdom().getProperties()) {
			playerScore += current.getScore();
		}
		p.setPropertyScore(playerScore);
	}

	/**
	 * Iterates over all the Player's Kingdom's and stores and returns the largest
	 * one
	 *
	 * @author Ezra Gomolin
	 * @param p The Player to find the largest property of
	 * @return The largest Property in the Player's Kingdom
	 */
	static Property getLargestProperty(Player p) {
		List<Property> props = p.getKingdom().getProperties();
		Property largest = props.get(0);
		for (int i = 1; i < props.size(); i++) {
			Property curr = props.get(i);
			if (curr.getSize() > largest.getSize()) {
				largest = curr;
			}
		}
		return largest;
	}

	/**
	 * Iterates over all the player's dominos and adds up the Crowns on all left and
	 * right tiles
	 *
	 * @author Ezra Gomolin
	 * @param p The Player for whom to get the Crown count
	 * @return The Player's Crown count
	 */
	static int getCrownCount(Player p) {
		int ret = 0;
		List<KingdomTerritory> terrs = p.getKingdom().getTerritories();
		for (KingdomTerritory terr : terrs) {
			if (terr instanceof DominoInKingdom) {
				DominoInKingdom dik = (DominoInKingdom) terr;
				Domino dom = dik.getDomino();
				ret += dom.getLeftCrown();
				ret += dom.getRightCrown();
			}
		}
		return ret;
	}

	/**
	 * Overloads Zhekai's feature function and uses his helper function. Converts
	 * the Points of a hole into max and min coordinate values and makes sure a
	 * Kingdom would fit in a 5x5 space if it had that hole.
	 *
	 * @author Ezra Gomolin
	 * @param kingdom The Kingdom to check
	 * @param a       The left tile of the hole
	 * @param b       The right tile of the hole
	 * @return boolean True if the hole would fit; False if it would exceed
	 *         dimension constraints
	 */

	public static boolean verifyKingdomGridSize(Kingdom kingdom, Point a, Point b) {
		int x_min = Math.min(a.x, b.x) - 4;
		int x_max = Math.max(a.x, b.x) - 4;
		int y_min = Math.min(a.y, b.y) - 4;
		int y_max = Math.max(a.y, b.y) - 4;
		return verifyKingdomGridSizeHelper(kingdom, x_min, x_max, y_min, y_max);

	}

	/**
	 *
	 * @author Gregory Walfish
	 */
	static boolean verifyMiddleKingdom(Kingdom kingdom, int x_min, int x_max, int y_min, int y_max) {
		for (KingdomTerritory i : kingdom.getTerritories()) {
			if (i instanceof DominoInKingdom) {
				int leftX = i.getX(), leftY = i.getY();
				DirectionKind direction = ((DominoInKingdom) i).getDirection();
				int rightX = getRightX(leftX, direction), rightY = getRightY(leftY, direction);
				x_min = Math.min(x_min, Math.min(leftX, rightX));
				x_max = Math.max(x_max, Math.max(leftX, rightX));
				y_min = Math.min(y_min, Math.min(leftY, rightY));
				y_max = Math.max(y_max, Math.max(leftY, rightY));
			}

			if (x_max + x_min == 0 && y_max + y_min == 0)
				return true;
		}

		return false;
	}

	/**
	 *
	 * @author Gregory Walfish
	 */

	public static int getNumPlayers() {
		Kingdomino KD = KingdominoApplication.getKingdomino();
		return KD.getCurrentGame().getPlayers().size();
	}

	/**
	 *
	 * @author Gregory Walfish
	 */
	public static Game getGame() {
		Kingdomino KD = KingdominoApplication.getKingdomino();
		return KD.getCurrentGame();
	}
	
	public static void setGame(Game game) {
		Kingdomino KD = KingdominoApplication.getKingdomino();
		KD.setCurrentGame(game);
	}

	/**
	 * Feature 4: Browse domino pile
	 *
	 * @author ricky
	 */

	public static List<Domino> listAll(Game game) {
		return game.getAllDominos();
	}

	public static Domino SelectAndObserveDomino(List<Domino> dominoList, int index) {
		Domino s = dominoList.get(index - 1);
		return s;
	}

	public static List<Integer> filterbyTerrain(TerrainType t) {
		List<Domino> r = listAll(KingdominoApplication.getKingdomino().getCurrentGame());

		List<Integer> ids = new ArrayList<>();

		for (int i = 0; i < r.size(); i++) {
			if (r.get(i).getLeftTile() == t || r.get(i).getRightTile() == t) {
				ids.add(r.get(i).getId());
			}
		}
		return ids;
	}

	/**
	 * Feature 5: Shuffle Domino pile
	 *
	 * @author ricky
	 */

	public static List<Domino> shuffleDominoes(List<Domino> d) {
		Random random = new Random();
		List<Domino> shuffled = new ArrayList<>(d);

		Collections.shuffle(shuffled);
		
		// Added Apr 5 Start
		for(Domino i : shuffled) {
			KingdominoApplication.getKingdomino().getCurrentGame().removeAllDomino(i);
		}
		
		for(int i = 0; i < shuffled.size(); ++i) {
			shuffled.get(i).setPrevDomino(i == 0 ? null : shuffled.get(i - 1));
			shuffled.get(i).setNextDomino(i == shuffled.size() - 1 ? null : shuffled.get(i + 1));
			KingdominoApplication.getKingdomino().getCurrentGame().addOrMoveAllDominoAt(shuffled.get(i), i);
			// KingdominoApplication.getKingdomino().getCurrentGame().addAllDominoAt(shuffled.get(i), i);
		}
		// Added Apr 5 End

		/*
		Draft firstDraft = new Draft(Draft.DraftStatus.FaceDown,
				KingdominoApplication.getKingdomino().getCurrentGame());

		int index = 0;

		for (int i = 0; i < KingdominoApplication.getKingdomino().getCurrentGame().getNumberOfPlayers(); i++) {
			firstDraft.addIdSortedDomino(shuffled.get(i));
			shuffled.remove(i);
			index++;
		}

		sortDraft(firstDraft);

		KingdominoApplication.getKingdomino().getCurrentGame().addAllDraft(firstDraft);
		KingdominoApplication.getKingdomino().getCurrentGame().setCurrentDraft(firstDraft);
		KingdominoApplication.getKingdomino().getCurrentGame().setTopDominoInPile(d.get(index));
		 */
		
		createFirstDraft(shuffled);
		return shuffled;
	}

	public static int[] ShuffledDominoList(List<Domino> d) {

		int[] shuffledList = new int[d.size() - 4];

		for (int i = 0; i < d.size() - 4; i++) {
			shuffledList[i] = d.get(i + 3).getId();
		}

		return shuffledList;
	}

	public static List<Domino> fixedArrangement(List<Integer> list) {
		List<Domino> fixedArrangement = new ArrayList<>();

		for (int i = 0; i < KingdominoApplication.getKingdomino().getCurrentGame().numberOfAllDominos(); i++) {
			fixedArrangement.add(KingdominoApplication.getKingdomino().getCurrentGame().getAllDomino(list.get(i) - 1));
		}

		List<Domino> copy = new ArrayList<>(KingdominoApplication.getKingdomino().getCurrentGame().getAllDominos());
		createFirstDraft(copy);

		return fixedArrangement;
	}
	
	static void createFirstDraft() {
		createFirstDraft(new ArrayList<>(KingdominoApplication.getKingdomino().getCurrentGame().getAllDominos()));
	}
	
	static void createFirstDraft(List<Domino> pile) {
		

		Draft firstDraft = new Draft(Draft.DraftStatus.FaceDown,
				KingdominoApplication.getKingdomino().getCurrentGame());

		int index = 0;

		for (int i = 0; i < KingdominoApplication.getKingdomino().getCurrentGame().getNumberOfPlayers(); i++) {
			firstDraft.addIdSortedDomino(pile.get(0));
			pile.get(0).setStatus(DominoStatus.InCurrentDraft);
			pile.remove(0);
			index++;
		}

		sortDraft(firstDraft);

		KingdominoApplication.getKingdomino().getCurrentGame().addAllDraft(firstDraft);
		KingdominoApplication.getKingdomino().getCurrentGame().setCurrentDraft(firstDraft);
		KingdominoApplication.getKingdomino().getCurrentGame()
				.setTopDominoInPile(KingdominoApplication.getKingdomino().getCurrentGame().getAllDomino(index));
	}

	/**
	 * Features 8 and 9: Create, Order & Reveal next draft
	 *
	 * @author ricky
	 */

	public static Draft CreateNextDraft(List<Domino> d) {
		Game thisGame = KingdominoApplication.getKingdomino().getCurrentGame();
		Draft oldnext = thisGame.getNextDraft();
		Draft newnext = new Draft(DraftStatus.FaceDown, thisGame);

		List<Domino> listofIDs = new ArrayList<>();
		List<Domino> copy = new ArrayList<>(d);

		if (thisGame.getCurrentDraft() != null) {
			if (KingdominoApplication.getKingdomino().getCurrentGame().numberOfAllDrafts() < 12) {
				for (int i = (thisGame.numberOfAllDrafts() - 1) * thisGame.getNumberOfPlayers(); i < (thisGame.numberOfAllDrafts()-1) * thisGame.getNumberOfPlayers() + thisGame.getNumberOfPlayers() + 1; i++) {
					listofIDs.add(d.get(i));
					if (i == ((thisGame.numberOfAllDrafts() - 1) * thisGame.getNumberOfPlayers()) + thisGame.getNumberOfPlayers()) {
						thisGame.setTopDominoInPile(d.get(i));
						copy.remove(d.get(i));
					}
				}

				for (int i = 0; i < thisGame.getNumberOfPlayers(); i++) {
					newnext.addIdSortedDomino(listofIDs.get(i));
				}
				if(oldnext != null) thisGame.setCurrentDraft(oldnext);
				thisGame.setNextDraft(newnext);
				
				thisGame.getNextDraft().setDraftStatus(DraftStatus.FaceDown); // Added by Zhekai Apr 5
				for(Domino a : thisGame.getNextDraft().getIdSortedDominos()) {
					a.setStatus(DominoStatus.InNextDraft);
				}
			} else {
				thisGame.setNextDraft(null);
				thisGame.setTopDominoInPile(null);
				thisGame.setCurrentDraft(oldnext);
			}
			
			for(Domino a : thisGame.getCurrentDraft().getIdSortedDominos()) {
				a.setStatus(DominoStatus.InCurrentDraft);
			}
			
			
		} else {
			throw new Error("something's wrong");
		}

		return newnext;
	}

	static void OrderDraft(Draft draft) {
		if (draft != null) {
			if (draft.getDraftStatus() == Draft.DraftStatus.FaceDown) {
				for (int i = 0; i < Draft.maximumNumberOfIdSortedDominos(); i++) {
					for (int j = 0; j < Draft.maximumNumberOfIdSortedDominos() - i - 1; j++) {
						if (draft.getIdSortedDomino(j).getId() > draft.getIdSortedDomino(j + 1).getId()) {
							Domino temp = draft.getIdSortedDomino(j);
							draft.addOrMoveIdSortedDominoAt(draft.getIdSortedDomino(j + 1), j);
							draft.addOrMoveIdSortedDominoAt(temp, j + 1);
						}
					}
				}
			}
		}
	}

	static void RevealDraft(Draft draft) {
		if (draft.hasIdSortedDominos()) {
			draft.setDraftStatus(Draft.DraftStatus.FaceUp);
		}
	}

	/**
	 * Helper method
	 *
	 * @param draft
	 */

	public static void sortDraft(Draft draft) {
		for (int i = 0; i < draft.numberOfIdSortedDominos(); i++) {
			for (int j = 0; j < draft.numberOfIdSortedDominos() - i - 1; j++) {
				if (draft.getIdSortedDomino(j).getId() > draft.getIdSortedDomino(j + 1).getId()) {
					Domino temp = draft.getIdSortedDomino(j);
					draft.addIdSortedDominoAt(draft.getIdSortedDomino(j + 1), j);
					draft.addIdSortedDominoAt(temp, j + 1);
				}
			}
		}
	}

	/**
	 * Feature 10 - Choose Next Domino
	 *
	 * @author ricky
	 */

	static void chooseNextDomino(Draft nextdraft, Domino d, Player p) {

		for (int i = 0; i < Draft.maximumNumberOfIdSortedDominos(); i++) {
			if (nextdraft.getIdSortedDomino(i).getId() == d.getId()) {
				if (!nextdraft.getIdSortedDomino(i).hasDominoSelection()) {

					new DominoSelection(p, d, nextdraft);
					if(getStateMachine() != null) triggerSelectionComplete();
					
				}
			}
		}
	}

	/**
	 * Clears the game options to default.
	 * 
	 * @param game The game to be cleared.
	 * @return The game.
	 */
	public static Game clearGameOptions(Game game) {
		game.setNumberOfPlayers(4);
		for (BonusOption bonus : game.getSelectedBonusOptions()) {
			game.removeSelectedBonusOption(bonus);
		}
		return game;
	}

	/**
	 * Sets all game options.
	 * 
	 * @param game                 The game.
	 * @param numPlayers           The number of players.
	 * @param isUsingHarmony       True if using bonus rule harmony.
	 * @param isUsingMiddleKingdom True if using bonus rule Middle Kingdom.
	 * @return The game.
	 */
	public static Game setGameOptions(int numPlayers, boolean isUsingHarmony, boolean isUsingMiddleKingdom) {
		Game game = getGame();
		if(game == null) game = new Game(48, KingdominoApplication.getKingdomino());
		setGame(game);
		setNumPlayers(game, numPlayers);
		if (isUsingHarmony)
			setBonusOption("Harmony");
		if (isUsingMiddleKingdom)
			setBonusOption("MiddleKingdom");
		
		return game;
	}

	/**
	 * Sets the number of players for a game.
	 * 
	 * @param game       The game.
	 * @param numPlayers The number of players.
	 * @return The game.
	 */
	public static Game setNumPlayers(Game game, int numPlayers) {

		game.setNumberOfPlayers(numPlayers);
		return game;

	}

	/**
	 * Adds a bonus option to the game.
	 * 
	 * @param game The game.
	 * @param name The name of the bonus option.
	 * @return The game.
	 */
	public static Game setBonusOption(String name) {
		Game game = getGame();
		if(game == null) game = new Game(48, KingdominoApplication.getKingdomino());
		setGame(game);
		BonusOption option;
		option = getBonusOption(game.getKingdomino(), name);
		if (option == null) {
			option = game.getKingdomino().addBonusOption(name);
			game.addSelectedBonusOption(option);
		}
		game.addSelectedBonusOption(option);
		return game;
	}

	/**
	 * Finds an existing bonus option by name.
	 * 
	 * @param kingdomino      The Kingdomino instance.
	 * @param bonusOptionName The name of the bonus option.
	 * @return The BonusOption. Null if not found.
	 */
	public static BonusOption getBonusOption(Kingdomino kingdomino, String bonusOptionName) {

		for (BonusOption option : kingdomino.getBonusOptions()) {
			if (option.getOptionName().equals(bonusOptionName))
				return option;
		}

		return null;
	}

	static void UserintoPlayer(String p1, String p2, String p3, String p4) {

		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();

		Player player1=game.addPlayer();
		Player player2=game.addPlayer();
		Player player3=game.addPlayer();
		Player player4=game.addPlayer();

		player1.setUser(new User(p1, kingdomino));
		player2.setUser(new User(p2, kingdomino));
		player3.setUser(new User(p3, kingdomino));
		player4.setUser(new User(p4, kingdomino));

		player1.setColor(Player.PlayerColor.Blue);
		player2.setColor(Player.PlayerColor.Yellow);
		player3.setColor(Player.PlayerColor.Pink);
		player4.setColor(Player.PlayerColor.Green);
	}

	public static void startGame(Game game) {

		Random rand = new Random();
		List<Player> clone = new ArrayList<Player>(game.getPlayers());
		for (Player p : clone) {
			int index = rand.nextInt(game.getPlayers().size());
			game.addOrMovePlayerAt(p, index);
		}
		createAllDominoes(game);
		//createFirstDraft();

		for (Player p : game.getPlayers()) {
			Kingdom k = new Kingdom(p);
			new Castle(0, 0, k, p);
			p.setPropertyScore(0);
			p.setBonusScore(0);
		}

		setStateMachine();
	}
	
	public static void startGame() {
		startGame(getGame());
	}

	static void makeEmptyGame() {
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		kingdomino.setCurrentGame(game);
		KingdominoApplication.setKingdomino(kingdomino);


		for (int i = 0; i < 4; i++) {
			Player player = new Player(KingdominoApplication.getKingdomino().getCurrentGame());
			player.setColor(Player.PlayerColor.values()[i]);
		}

		Controller.setBonusOption("Harmony");
		Controller.setBonusOption("MiddleKingdom");
	}


	public static void revealFirstDraft(Game game) {

		Draft draft = game.getCurrentDraft();
		if (draft != null)
			draft.setDraftStatus(DraftStatus.FaceUp);

	}

	public static boolean loadGame(String path) {

		Game game = setGameOptions(4, true, true);
		// Random rand = new Random();
		createAllDominoes(game);
		List<Domino> used = new ArrayList<Domino>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line = "";
			String delimiters = "[:\\+()@, ]+";

			line = br.readLine();
			String[] current = line.split(delimiters);
			Domino[] claimed = new Domino[4];
			for (int i = 1; i < 5; i++) {
				claimed[i - 1] = game.getAllDomino(Integer.decode(current[i]) - 1);
				game.addPlayer();
			}

			line = br.readLine();
			current = line.split(delimiters);
			Domino[] unclaimed = new Domino[current.length - 1];
			for (int i = 1; i <= unclaimed.length; i++) {
				unclaimed[i - 1] = game.getAllDomino(Integer.decode(current[i]) - 1);
			}

			Draft c = new Draft(DraftStatus.FaceUp, game);
			for (int i = 1; i <= unclaimed.length; i++) {
				c.addIdSortedDomino(claimed[claimed.length - i]);
				new DominoSelection(game.getPlayer(claimed.length - i), claimed[claimed.length - i], c);
				
			}

			Draft n = new Draft(DraftStatus.FaceUp, game);
			for (int i = 0; i < claimed.length - unclaimed.length; i++) {
				n.addIdSortedDomino(claimed[i]);
				new DominoSelection(game.getPlayer(i), claimed[i], n);
			}
			
			Controller.sortDraft(c);
			Controller.sortDraft(n);

			for (int i = 0; i < unclaimed.length; i++) {
				n.addIdSortedDomino(unclaimed[i]);
			}

			for (Domino d : claimed) {
				used.add(d);
			}

			for (Domino d : unclaimed) {
				used.add(d);
			}

			game.setCurrentDraft(c);
			game.setNextDraft(n);
			game.setNextPlayer(game.getCurrentDraft().getIdSortedDomino(0).getDominoSelection().getPlayer());

			for (Player p : game.getPlayers()) {
				Kingdom k = new Kingdom(p);
				new Castle(0, 0, k, p);
				p.setPropertyScore(0);
				p.setBonusScore(0);
				line = br.readLine();
				current = line.split(delimiters);
				for (int i = 1; i < current.length; i += 4) {
					int x = Integer.decode(current[i + 1]);
					int y = Integer.decode(current[i + 2]);
					Domino domino = game.getAllDomino(Integer.decode(current[i]) - 1);
					DirectionKind direction = getDirection(current[i + 3]);
					if (verifyNoOverlapping(p.getKingdom(), domino.getId(), x, y, direction) && Math.abs(x) < 5
							&& Math.abs(y) < 5 && !used.contains(domino)) {
						DominoInKingdom d = new DominoInKingdom(x, y, k, domino);
						d.setDirection(direction);
						used.add(d.getDomino());
					} else
						return false;
				}
				
				for(KingdomTerritory t : p.getKingdom().getTerritories()) {
					if(t instanceof DominoInKingdom) {
						if(!verifyNeighborAdjacency((DominoInKingdom)t)) return false;
					}
				}

			}


			// shuffle dominoes

			for (Domino d : used) {
				game.addOrMoveAllDominoAt(d, 0);
			}
			game.setTopDominoInPile(game.getAllDomino(used.size()));

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new java.lang.IllegalArgumentException(
					"Error occured while trying to read save file: " + e.getMessage());
		}

		KingdominoApplication.getKingdomino().setCurrentGame(game);
		return true;
	}

	public static void saveGame(Game game, String path, boolean overwrite) {

		File file = new File(path);
		if (file.exists() && !overwrite)
			return;
		saveGame(game, path);

	}

	public static void saveGame(Game game, String path) {
		try {
			File fout = new File(path);
			FileOutputStream fos = new FileOutputStream(fout);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			int[] c = new int[4];
			int[] u = new int[4];
			int cCount = 0;
			int uCount = 0;
			for (Domino d : game.getNextDraft().getIdSortedDominos()) {
				if (d.hasDominoSelection()) {
					c[cCount] = d.getId();
					cCount++;
				} else {
					u[uCount] = d.getId();
					uCount++;
				}
			}

			for (int i = cCount; i < 4; i++) {
				c[i] = game.getCurrentDraft().getIdSortedDomino(i).getId();
			}

			bw.write("C: ");
			for (int i = 0; i < 3; i++) {
				bw.write(c[i] + ", ");
			}
			bw.write(c[3]);
			bw.newLine();

			bw.write("U: ");
			for (int i = 0; i < uCount - 1; i++) {
				bw.write(u[i] + ", ");
			}
			bw.write(u[uCount - 1]);
			bw.newLine();

			cCount = 1;
			for (Player p : game.getPlayers()) {
				bw.write("P" + cCount + ": ");
				boolean first = true;
				for (KingdomTerritory t : p.getKingdom().getTerritories()) {

					if (t instanceof DominoInKingdom) {
						DominoInKingdom d = (DominoInKingdom) t;
						if (!first) {
							bw.write(", ");
							first = false;
						}
						bw.write(d.getDomino().getId() + "@(" + d.getX() + "," + d.getY() + ","
								+ getDirectionString(d.getDirection()) + ")");
					}
				}
				bw.newLine();

			}
			bw.close();

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	static String getDirectionString(DirectionKind direction) {
		switch (direction) {
		case Up:
			return "U";
		case Down:
			return "D";
		case Left:
			return "L";
		case Right:
			return "R";
		default:
			return "U";
		}
	}

	static DirectionKind getDirection(String direction) {

		switch (direction) {
		case "U":
			return DirectionKind.Up;
		case "D":
			return DirectionKind.Down;
		case "L":
			return DirectionKind.Left;
		case "R":
			return DirectionKind.Right;
		default:
			throw new RuntimeException("Invalid direction string was provided: " + direction);
		}
	}

	static void createAllDominoes(Game game) {
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
	
	static boolean placeDomino(DominoInKingdom domino) {

		if(domino.getDomino().getStatus() == DominoStatus.CorrectlyPreplaced) {
			domino.getDomino().setStatus(DominoStatus.PlacedInKingdom);
			triggerPlacementComplete();
			return true;		
		}
		
		return false;
		
	}

	static TerrainType getTerrainType(String terrain) {
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
	
	public static DominoInKingdom getDominoInKingdom(Player player, int id) {
		for(KingdomTerritory t : player.getKingdom().getTerritories()) {
			if(t instanceof DominoInKingdom) {
				if(((DominoInKingdom)t).getDomino().getId() == id) return (DominoInKingdom)t;
			}
		}
		return null;
	}
	static Color PlayerColortoColor(Player.PlayerColor p) {

		if (p == Player.PlayerColor.Blue) {
			return Color.blue;
		}
		if (p == Player.PlayerColor.Pink) {
			return Color.pink;
		}
		if (p == Player.PlayerColor.Green) {
			return Color.green;
		}
		if (p == Player.PlayerColor.Yellow) {
			return Color.orange;
		}

		return null;
	}
	
	public static List<Player> getRankedPlayers(){
	List<Player> rankedPlayers =CalculateRanking();
	return rankedPlayers;
	}
	
	public static int GetPlayerRanks(int n) {
		
		int rank = getRankedPlayers().get(n).getCurrentRanking();
		
		return rank;	
	}
	
	public static int GetPropertyScores(int n) {
		
		int pscore = getRankedPlayers().get(n).getPropertyScore();
		
		return pscore;	
	}
	
	public static int GetBonusScores(int n) {
		
		int bscore = getRankedPlayers().get(n).getBonusScore();
		
		return bscore;	
	}
	
	public static int GetTotalScores(int n) {
		
		int tscore = getRankedPlayers().get(n).getTotalScore();
		
		return tscore;	
	}
	
	public static String GetNames(int n) {
	
		String name = getRankedPlayers().get(n).getUser().getName();
		
		return name;	
	}
	
	public static Color GetColors(int n) {
		
		Color color = PlayerColortoColor(getRankedPlayers().get(n).getColor());
		
		return color;	
	}

	static void changePlayerOrder(Color[] colors) {
		for (int i = 0; i < 4; i++) {
			getGame().addOrMovePlayerAt(getGame().getCurrentDraft().getIdSortedDomino(i).getDominoSelection().getPlayer(), i);
		}
	}

	


}


