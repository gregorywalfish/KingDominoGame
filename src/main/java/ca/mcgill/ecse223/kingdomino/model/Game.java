/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;
import java.util.*;

// line 13 "../../../../../Kingdomino.ump"
public class Game
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Game Attributes
  private int numberOfPlayers;
  private int maxPileSize;

  //Game Associations
  private Gameplay gameplay;
  private List<Player> players;
  private List<Domino> allDominos;
  private List<Draft> allDrafts;
  private Player nextPlayer;
  private Draft currentDraft;
  private Draft nextDraft;
  private Domino topDominoInPile;
  private List<BonusOption> selectedBonusOptions;
  private Kingdomino kingdomino;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Game(int aMaxPileSize, Kingdomino aKingdomino)
  {
    numberOfPlayers = 4;
    maxPileSize = aMaxPileSize;
    players = new ArrayList<Player>();
    allDominos = new ArrayList<Domino>();
    allDrafts = new ArrayList<Draft>();
    selectedBonusOptions = new ArrayList<BonusOption>();
    boolean didAddKingdomino = setKingdomino(aKingdomino);
    if (!didAddKingdomino)
    {
      throw new RuntimeException("Unable to create allGame due to kingdomino");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setNumberOfPlayers(int aNumberOfPlayers)
  {
    boolean wasSet = false;
    numberOfPlayers = aNumberOfPlayers;
    wasSet = true;
    return wasSet;
  }

  public int getNumberOfPlayers()
  {
    return numberOfPlayers;
  }

  public int getMaxPileSize()
  {
    return maxPileSize;
  }
  /* Code from template association_GetOne */
  public Gameplay getGameplay()
  {
    return gameplay;
  }

  public boolean hasGameplay()
  {
    boolean has = gameplay != null;
    return has;
  }
  /* Code from template association_GetMany */
  public Player getPlayer(int index)
  {
    Player aPlayer = players.get(index);
    return aPlayer;
  }

  public List<Player> getPlayers()
  {
    List<Player> newPlayers = Collections.unmodifiableList(players);
    return newPlayers;
  }

  public int numberOfPlayers()
  {
    int number = players.size();
    return number;
  }

  public boolean hasPlayers()
  {
    boolean has = players.size() > 0;
    return has;
  }

  public int indexOfPlayer(Player aPlayer)
  {
    int index = players.indexOf(aPlayer);
    return index;
  }
  /* Code from template association_GetMany */
  public Domino getAllDomino(int index)
  {
    Domino aAllDomino = allDominos.get(index);
    return aAllDomino;
  }

  /**
   * sorted by Ids of dominos
   */
  public List<Domino> getAllDominos()
  {
    List<Domino> newAllDominos = Collections.unmodifiableList(allDominos);
    return newAllDominos;
  }

  public int numberOfAllDominos()
  {
    int number = allDominos.size();
    return number;
  }

  public boolean hasAllDominos()
  {
    boolean has = allDominos.size() > 0;
    return has;
  }

  public int indexOfAllDomino(Domino aAllDomino)
  {
    int index = allDominos.indexOf(aAllDomino);
    return index;
  }
  /* Code from template association_GetMany */
  public Draft getAllDraft(int index)
  {
    Draft aAllDraft = allDrafts.get(index);
    return aAllDraft;
  }

  /**
   * sorted by Ids of dominos
   */
  public List<Draft> getAllDrafts()
  {
    List<Draft> newAllDrafts = Collections.unmodifiableList(allDrafts);
    return newAllDrafts;
  }

  public int numberOfAllDrafts()
  {
    int number = allDrafts.size();
    return number;
  }

  public boolean hasAllDrafts()
  {
    boolean has = allDrafts.size() > 0;
    return has;
  }

  public int indexOfAllDraft(Draft aAllDraft)
  {
    int index = allDrafts.indexOf(aAllDraft);
    return index;
  }
  /* Code from template association_GetOne */
  public Player getNextPlayer()
  {
    return nextPlayer;
  }

  public boolean hasNextPlayer()
  {
    boolean has = nextPlayer != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Draft getCurrentDraft()
  {
    return currentDraft;
  }

  public boolean hasCurrentDraft()
  {
    boolean has = currentDraft != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Draft getNextDraft()
  {
    return nextDraft;
  }

  public boolean hasNextDraft()
  {
    boolean has = nextDraft != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Domino getTopDominoInPile()
  {
    return topDominoInPile;
  }

  public boolean hasTopDominoInPile()
  {
    boolean has = topDominoInPile != null;
    return has;
  }
  /* Code from template association_GetMany */
  public BonusOption getSelectedBonusOption(int index)
  {
    BonusOption aSelectedBonusOption = selectedBonusOptions.get(index);
    return aSelectedBonusOption;
  }

  public List<BonusOption> getSelectedBonusOptions()
  {
    List<BonusOption> newSelectedBonusOptions = Collections.unmodifiableList(selectedBonusOptions);
    return newSelectedBonusOptions;
  }

  public int numberOfSelectedBonusOptions()
  {
    int number = selectedBonusOptions.size();
    return number;
  }

  public boolean hasSelectedBonusOptions()
  {
    boolean has = selectedBonusOptions.size() > 0;
    return has;
  }

  public int indexOfSelectedBonusOption(BonusOption aSelectedBonusOption)
  {
    int index = selectedBonusOptions.indexOf(aSelectedBonusOption);
    return index;
  }
  /* Code from template association_GetOne */
  public Kingdomino getKingdomino()
  {
    return kingdomino;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setGameplay(Gameplay aNewGameplay)
  {
    boolean wasSet = false;
    if (gameplay != null && !gameplay.equals(aNewGameplay) && equals(gameplay.getGame()))
    {
      //Unable to setGameplay, as existing gameplay would become an orphan
      return wasSet;
    }

    gameplay = aNewGameplay;
    Game anOldGame = aNewGameplay != null ? aNewGameplay.getGame() : null;

    if (!this.equals(anOldGame))
    {
      if (anOldGame != null)
      {
        anOldGame.gameplay = null;
      }
      if (gameplay != null)
      {
        gameplay.setGame(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPlayers()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfPlayers()
  {
    return 4;
  }
  /* Code from template association_AddOptionalNToOne */
  public Player addPlayer()
  {
    if (numberOfPlayers() >= maximumNumberOfPlayers())
    {
      return null;
    }
    else
    {
      return new Player(this);
    }
  }

  public boolean addPlayer(Player aPlayer)
  {
    boolean wasAdded = false;
    if (players.contains(aPlayer)) { return false; }
    if (numberOfPlayers() >= maximumNumberOfPlayers())
    {
      return wasAdded;
    }

    Game existingGame = aPlayer.getGame();
    boolean isNewGame = existingGame != null && !this.equals(existingGame);
    if (isNewGame)
    {
      aPlayer.setGame(this);
    }
    else
    {
      players.add(aPlayer);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePlayer(Player aPlayer)
  {
    boolean wasRemoved = false;
    //Unable to remove aPlayer, as it must always have a game
    if (!this.equals(aPlayer.getGame()))
    {
      players.remove(aPlayer);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPlayerAt(Player aPlayer, int index)
  {  
    boolean wasAdded = false;
    if(addPlayer(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePlayerAt(Player aPlayer, int index)
  {
    boolean wasAdded = false;
    if(players.contains(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPlayerAt(aPlayer, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAllDominos()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Domino addAllDomino(int aId, TerrainType aLeftTile, TerrainType aRightTile, int aRightCrown)
  {
    return new Domino(aId, aLeftTile, aRightTile, aRightCrown, this);
  }

  public boolean addAllDomino(Domino aAllDomino)
  {
    boolean wasAdded = false;
    if (allDominos.contains(aAllDomino)) { return false; }
    Game existingGame = aAllDomino.getGame();
    boolean isNewGame = existingGame != null && !this.equals(existingGame);
    if (isNewGame)
    {
      aAllDomino.setGame(this);
    }
    else
    {
      allDominos.add(aAllDomino);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAllDomino(Domino aAllDomino)
  {
    boolean wasRemoved = false;
    //Unable to remove aAllDomino, as it must always have a game
    if (!this.equals(aAllDomino.getGame()))
    {
      allDominos.remove(aAllDomino);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAllDominoAt(Domino aAllDomino, int index)
  {  
    boolean wasAdded = false;
    if(addAllDomino(aAllDomino))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAllDominos()) { index = numberOfAllDominos() - 1; }
      allDominos.remove(aAllDomino);
      allDominos.add(index, aAllDomino);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAllDominoAt(Domino aAllDomino, int index)
  {
    boolean wasAdded = false;
    if(allDominos.contains(aAllDomino))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAllDominos()) { index = numberOfAllDominos() - 1; }
      allDominos.remove(aAllDomino);
      allDominos.add(index, aAllDomino);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAllDominoAt(aAllDomino, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAllDrafts()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Draft addAllDraft(Draft.DraftStatus aDraftStatus)
  {
    return new Draft(aDraftStatus, this);
  }

  public boolean addAllDraft(Draft aAllDraft)
  {
    boolean wasAdded = false;
    if (allDrafts.contains(aAllDraft)) { return false; }
    Game existingGame = aAllDraft.getGame();
    boolean isNewGame = existingGame != null && !this.equals(existingGame);
    if (isNewGame)
    {
      aAllDraft.setGame(this);
    }
    else
    {
      allDrafts.add(aAllDraft);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAllDraft(Draft aAllDraft)
  {
    boolean wasRemoved = false;
    //Unable to remove aAllDraft, as it must always have a game
    if (!this.equals(aAllDraft.getGame()))
    {
      allDrafts.remove(aAllDraft);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAllDraftAt(Draft aAllDraft, int index)
  {  
    boolean wasAdded = false;
    if(addAllDraft(aAllDraft))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAllDrafts()) { index = numberOfAllDrafts() - 1; }
      allDrafts.remove(aAllDraft);
      allDrafts.add(index, aAllDraft);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAllDraftAt(Draft aAllDraft, int index)
  {
    boolean wasAdded = false;
    if(allDrafts.contains(aAllDraft))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAllDrafts()) { index = numberOfAllDrafts() - 1; }
      allDrafts.remove(aAllDraft);
      allDrafts.add(index, aAllDraft);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAllDraftAt(aAllDraft, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setNextPlayer(Player aNewNextPlayer)
  {
    boolean wasSet = false;
    nextPlayer = aNewNextPlayer;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setCurrentDraft(Draft aNewCurrentDraft)
  {
    boolean wasSet = false;
    currentDraft = aNewCurrentDraft;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setNextDraft(Draft aNewNextDraft)
  {
    boolean wasSet = false;
    nextDraft = aNewNextDraft;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setTopDominoInPile(Domino aNewTopDominoInPile)
  {
    boolean wasSet = false;
    topDominoInPile = aNewTopDominoInPile;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfSelectedBonusOptions()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addSelectedBonusOption(BonusOption aSelectedBonusOption)
  {
    boolean wasAdded = false;
    if (selectedBonusOptions.contains(aSelectedBonusOption)) { return false; }
    selectedBonusOptions.add(aSelectedBonusOption);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeSelectedBonusOption(BonusOption aSelectedBonusOption)
  {
    boolean wasRemoved = false;
    if (selectedBonusOptions.contains(aSelectedBonusOption))
    {
      selectedBonusOptions.remove(aSelectedBonusOption);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addSelectedBonusOptionAt(BonusOption aSelectedBonusOption, int index)
  {  
    boolean wasAdded = false;
    if(addSelectedBonusOption(aSelectedBonusOption))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSelectedBonusOptions()) { index = numberOfSelectedBonusOptions() - 1; }
      selectedBonusOptions.remove(aSelectedBonusOption);
      selectedBonusOptions.add(index, aSelectedBonusOption);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveSelectedBonusOptionAt(BonusOption aSelectedBonusOption, int index)
  {
    boolean wasAdded = false;
    if(selectedBonusOptions.contains(aSelectedBonusOption))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSelectedBonusOptions()) { index = numberOfSelectedBonusOptions() - 1; }
      selectedBonusOptions.remove(aSelectedBonusOption);
      selectedBonusOptions.add(index, aSelectedBonusOption);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addSelectedBonusOptionAt(aSelectedBonusOption, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setKingdomino(Kingdomino aKingdomino)
  {
    boolean wasSet = false;
    if (aKingdomino == null)
    {
      return wasSet;
    }

    Kingdomino existingKingdomino = kingdomino;
    kingdomino = aKingdomino;
    if (existingKingdomino != null && !existingKingdomino.equals(aKingdomino))
    {
      existingKingdomino.removeAllGame(this);
    }
    kingdomino.addAllGame(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Gameplay existingGameplay = gameplay;
    gameplay = null;
    if (existingGameplay != null)
    {
      existingGameplay.delete();
    }
    while (players.size() > 0)
    {
      Player aPlayer = players.get(players.size() - 1);
      aPlayer.delete();
      players.remove(aPlayer);
    }
    
    while (allDominos.size() > 0)
    {
      Domino aAllDomino = allDominos.get(allDominos.size() - 1);
      aAllDomino.delete();
      allDominos.remove(aAllDomino);
    }
    
    while (allDrafts.size() > 0)
    {
      Draft aAllDraft = allDrafts.get(allDrafts.size() - 1);
      aAllDraft.delete();
      allDrafts.remove(aAllDraft);
    }
    
    nextPlayer = null;
    currentDraft = null;
    nextDraft = null;
    topDominoInPile = null;
    selectedBonusOptions.clear();
    Kingdomino placeholderKingdomino = kingdomino;
    this.kingdomino = null;
    if(placeholderKingdomino != null)
    {
      placeholderKingdomino.removeAllGame(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "numberOfPlayers" + ":" + getNumberOfPlayers()+ "," +
            "maxPileSize" + ":" + getMaxPileSize()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "gameplay = "+(getGameplay()!=null?Integer.toHexString(System.identityHashCode(getGameplay())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "nextPlayer = "+(getNextPlayer()!=null?Integer.toHexString(System.identityHashCode(getNextPlayer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "currentDraft = "+(getCurrentDraft()!=null?Integer.toHexString(System.identityHashCode(getCurrentDraft())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "nextDraft = "+(getNextDraft()!=null?Integer.toHexString(System.identityHashCode(getNextDraft())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "topDominoInPile = "+(getTopDominoInPile()!=null?Integer.toHexString(System.identityHashCode(getTopDominoInPile())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "kingdomino = "+(getKingdomino()!=null?Integer.toHexString(System.identityHashCode(getKingdomino())):"null");
  }
}