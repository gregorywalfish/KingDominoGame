/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;
import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.Controller;

// line 3 "../../../../../Gameplay.ump"
public class Gameplay
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Gameplay State Machines
  public enum Gamestatus { Initializing, InProcess, Ending }
  public enum GamestatusInitializing { Null, CreatingFirstDraft, SelectingFirstDomino }
  public enum GamestatusInProcess { Null, CreatingNextDraft, PlacingDomino, SelectingDomino }
  public enum GamestatusEnding { Null, PlacingLastDomino, ShowingResults }
  private Gamestatus gamestatus;
  private GamestatusInitializing gamestatusInitializing;
  private GamestatusInProcess gamestatusInProcess;
  private GamestatusEnding gamestatusEnding;

  //Gameplay Associations
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Gameplay(Game aGame)
  {
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create gameplay due to game");
    }
    setGamestatusInitializing(GamestatusInitializing.Null);
    setGamestatusInProcess(GamestatusInProcess.Null);
    setGamestatusEnding(GamestatusEnding.Null);
    setGamestatus(Gamestatus.Initializing);
  }
  
  public Gameplay(Game aGame, String status)
  {
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create gameplay due to game");
    }
    setGamestatus(status);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getGamestatusFullName()
  {
    String answer = gamestatus.toString();
    if (gamestatusInitializing != GamestatusInitializing.Null) { answer += "." + gamestatusInitializing.toString(); }
    if (gamestatusInProcess != GamestatusInProcess.Null) { answer += "." + gamestatusInProcess.toString(); }
    if (gamestatusEnding != GamestatusEnding.Null) { answer += "." + gamestatusEnding.toString(); }
    return answer;
  }

  public Gamestatus getGamestatus()
  {
    return gamestatus;
  }

  public GamestatusInitializing getGamestatusInitializing()
  {
    return gamestatusInitializing;
  }

  public GamestatusInProcess getGamestatusInProcess()
  {
    return gamestatusInProcess;
  }

  public GamestatusEnding getGamestatusEnding()
  {
    return gamestatusEnding;
  }

  public boolean draftReady()
  {
    boolean wasEventProcessed = false;
    
    GamestatusInitializing aGamestatusInitializing = gamestatusInitializing;
    GamestatusInProcess aGamestatusInProcess = gamestatusInProcess;
    switch (aGamestatusInitializing)
    {
      case CreatingFirstDraft:
        exitGamestatusInitializing();
        // line 14 "../../../../../Gameplay.ump"
        generateInitialPlayerOrder(); resetCurrentPlayer();
        setGamestatusInitializing(GamestatusInitializing.SelectingFirstDomino);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    switch (aGamestatusInProcess)
    {
      case CreatingNextDraft:
        exitGamestatusInProcess();
        // line 28 "../../../../../Gameplay.ump"
        revealNextDraft(); resetCurrentPlayer();
        setGamestatusInProcess(GamestatusInProcess.PlacingDomino);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean selectionComplete()
  {
    boolean wasEventProcessed = false;
    
    GamestatusInitializing aGamestatusInitializing = gamestatusInitializing;
    GamestatusInProcess aGamestatusInProcess = gamestatusInProcess;
    switch (aGamestatusInitializing)
    {
      case SelectingFirstDomino:
        if (!(isCurrentPlayerTheLastInTurn()))
        {
          exitGamestatusInitializing();
        // line 18 "../../../../../Gameplay.ump"
          updateCurrentPlayer();
          setGamestatusInitializing(GamestatusInitializing.SelectingFirstDomino);
          wasEventProcessed = true;
          break;
        }
        if (isCurrentPlayerTheLastInTurn())
        {
          exitGamestatus();

          revealNextDraft(); 

          setGamestatusInProcess(GamestatusInProcess.PlacingDomino);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    switch (aGamestatusInProcess)
    {
      case SelectingDomino:
        if (!(isCurrentPlayerTheLastInTurn()))
        {
          exitGamestatusInProcess();
        // line 37 "../../../../../Gameplay.ump"
          updateCurrentPlayer();
          setGamestatusInProcess(GamestatusInProcess.PlacingDomino);
          wasEventProcessed = true;
          break;
        }
        if (isCurrentPlayerTheLastInTurn()&&!(isCurrentTurnTheLastInGame()))
        {
          exitGamestatusInProcess();
          setGamestatusInProcess(GamestatusInProcess.CreatingNextDraft);
          wasEventProcessed = true;
          break;
        }
        if (isCurrentPlayerTheLastInTurn()&&isCurrentTurnTheLastInGame())
        {
          exitGamestatus();
        // line 39 "../../../../../Gameplay.ump"
          resetCurrentPlayer();
          setGamestatusEnding(GamestatusEnding.PlacingLastDomino);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean placementComplete()
  {
    boolean wasEventProcessed = false;
    
    GamestatusInProcess aGamestatusInProcess = gamestatusInProcess;
    GamestatusEnding aGamestatusEnding = gamestatusEnding;
    switch (aGamestatusInProcess)
    {
      case PlacingDomino:
        exitGamestatusInProcess();
        setGamestatusInProcess(GamestatusInProcess.SelectingDomino);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    switch (aGamestatusEnding)
    {
      case PlacingLastDomino:
        if (!(isCurrentPlayerTheLastInTurn()))
        {
          exitGamestatusEnding();
        // line 48 "../../../../../Gameplay.ump"
          updateCurrentPlayer();
          setGamestatusEnding(GamestatusEnding.PlacingLastDomino);
          wasEventProcessed = true;
          break;
        }
        if (isCurrentPlayerTheLastInTurn())
        {
          exitGamestatusEnding();
          setGamestatusEnding(GamestatusEnding.ShowingResults);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void exitGamestatus()
  {
    switch(gamestatus)
    {
      case Initializing:
        exitGamestatusInitializing();
        break;
      case InProcess:
        exitGamestatusInProcess();
        break;
      case Ending:
        exitGamestatusEnding();
        break;
    }
  }

  private void setGamestatus(Gamestatus aGamestatus)
  {
    gamestatus = aGamestatus;

    // entry actions and do activities
    switch(gamestatus)
    {
      case Initializing:
        if (gamestatusInitializing == GamestatusInitializing.Null) { setGamestatusInitializing(GamestatusInitializing.CreatingFirstDraft); }
        break;
      case InProcess:
        if (gamestatusInProcess == GamestatusInProcess.Null) { setGamestatusInProcess(GamestatusInProcess.CreatingNextDraft); }
        break;
      case Ending:
        if (gamestatusEnding == GamestatusEnding.Null) { setGamestatusEnding(GamestatusEnding.PlacingLastDomino); }
        break;
    }
  }

  private void exitGamestatusInitializing()
  {
    switch(gamestatusInitializing)
    {
      case CreatingFirstDraft:
        setGamestatusInitializing(GamestatusInitializing.Null);
        break;
      case SelectingFirstDomino:
        setGamestatusInitializing(GamestatusInitializing.Null);
        break;
    }
  }

  private void setGamestatusInitializing(GamestatusInitializing aGamestatusInitializing)
  {
    gamestatusInitializing = aGamestatusInitializing;
    if (gamestatus != Gamestatus.Initializing && aGamestatusInitializing != GamestatusInitializing.Null) { setGamestatus(Gamestatus.Initializing); }

    // entry actions and do activities
    switch(gamestatusInitializing)
    {
      case CreatingFirstDraft:
        // line 13 "../../../../../Gameplay.ump"
        shuffleDominoPile(); createNextDraft(); orderNextDraft();
        break;
    }
  }

  private void exitGamestatusInProcess()
  {
    switch(gamestatusInProcess)
    {
      case CreatingNextDraft:
        setGamestatusInProcess(GamestatusInProcess.Null);
        break;
      case PlacingDomino:
        // line 32 "../../../../../Gameplay.ump"
        updateScoresAndRankings();
        setGamestatusInProcess(GamestatusInProcess.Null);
        break;
      case SelectingDomino:
        setGamestatusInProcess(GamestatusInProcess.Null);
        break;
    }
  }

  private void setGamestatusInProcess(GamestatusInProcess aGamestatusInProcess)
  {
    gamestatusInProcess = aGamestatusInProcess;
    if (gamestatus != Gamestatus.InProcess && aGamestatusInProcess != GamestatusInProcess.Null) { setGamestatus(Gamestatus.InProcess); }

    // entry actions and do activities
    switch(gamestatusInProcess)
    {
      case CreatingNextDraft:
        // line 27 "../../../../../Gameplay.ump"
        createNextDraft(); orderNextDraft();
        break;
    }
  }

  private void exitGamestatusEnding()
  {
    switch(gamestatusEnding)
    {
      case PlacingLastDomino:
        // line 47 "../../../../../Gameplay.ump"
        updateScoresAndRankings();
        setGamestatusEnding(GamestatusEnding.Null);
        break;
      case ShowingResults:
        setGamestatusEnding(GamestatusEnding.Null);
        break;
    }
  }

  private void setGamestatusEnding(GamestatusEnding aGamestatusEnding)
  {
    gamestatusEnding = aGamestatusEnding;
    if (gamestatus != Gamestatus.Ending && aGamestatusEnding != GamestatusEnding.Null) { setGamestatus(Gamestatus.Ending); }
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setGame(Game aNewGame)
  {
    boolean wasSet = false;
    if (aNewGame == null)
    {
      //Unable to setGame to null, as gameplay must always be associated to a game
      return wasSet;
    }
    
    Gameplay existingGameplay = aNewGame.getGameplay();
    if (existingGameplay != null && !equals(existingGameplay))
    {
      //Unable to setGame, the current game already has a gameplay, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Game anOldGame = game;
    game = aNewGame;
    game.setGameplay(this);

    if (anOldGame != null)
    {
      anOldGame.setGameplay(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Game existingGame = game;
    game = null;
    if (existingGame != null)
    {
      existingGame.setGameplay(null);
    }
  }


  /**
   * Setter for test setup
   */
  // line 63 "../../../../../Gameplay.ump"
   public void setGamestatus(String status){
    switch (status) {
		case "CreatingFirstDraft":
			gamestatus = Gamestatus.Initializing;
			gamestatusInitializing = GamestatusInitializing.CreatingFirstDraft;
			break;
		case "SelectingFirstDomino":
			gamestatus = Gamestatus.Initializing;
			gamestatusInitializing = GamestatusInitializing.SelectingFirstDomino;
			break;
		case "SelectingDomino":
			gamestatus = Gamestatus.InProcess;
			gamestatusInProcess = GamestatusInProcess.SelectingDomino;
			gamestatusInitializing = GamestatusInitializing.Null;
			break;
		case "DiscardingDomino":
			gamestatus = Gamestatus.InProcess;
			gamestatusInProcess = GamestatusInProcess.PlacingDomino;
			gamestatusInitializing = GamestatusInitializing.Null;
			break;
		case "DiscardingLastDomino":
			gamestatus = Gamestatus.Ending;
			gamestatusEnding = GamestatusEnding.PlacingLastDomino;
			gamestatusInitializing = GamestatusInitializing.Null;
			break;
		case "PlacingDomino":
			gamestatus = Gamestatus.InProcess;
			gamestatusInProcess = GamestatusInProcess.PlacingDomino;
			gamestatusInitializing = GamestatusInitializing.Null;
			break;
		case "PlacingLastDomino":
			gamestatus = Gamestatus.Ending;
			gamestatusEnding = GamestatusEnding.PlacingLastDomino;
			gamestatusInitializing = GamestatusInitializing.Null;
			break;
		
		// TODO add further cases here to set desired state
		default:
			throw new RuntimeException("Invalid gamestatus string was provided: " + status);
		}
  }


  /**
   * This method checks if the current player is the last in the turn.
   *
   * @return boolean TRUE if it is the last player in the turn. FALSE ow
   */
  // line 115 "../../../../../Gameplay.ump"
   public boolean isCurrentPlayerTheLastInTurn(){
    Game currentGame = KingdominoApplication.getKingdomino().getCurrentGame();
    	/*if(currentGame.getCurrentDraft().getIdSortedDomino(currentGame.getNumberOfPlayers() - 1).getDominoSelection() != null) {
    		return currentGame.getCurrentDraft().getIdSortedDomino(currentGame.getNumberOfPlayers() - 1)
				.getDominoSelection().getPlayer().equals(currentGame.getNextPlayer());
   	 	}
		if(currentGame.getNextDraft().getIdSortedDomino(currentGame.getNumberOfPlayers() - 1).getDominoSelection() != null) {
    		return currentGame.getNextDraft().getIdSortedDomino(currentGame.getNumberOfPlayers() - 1)
				.getDominoSelection().getPlayer().equals(currentGame.getNextPlayer());
   	 	}
   	 	return false;*/
	   return currentGame.getPlayer(currentGame.getNumberOfPlayers() - 1) == currentGame.getNextPlayer();
  }


  /**
   * This method checks if current turn is the last in the game.
   * 
   * @return boolean    TRUE if it is the last turn. FALSE ow
   */
  // line 134 "../../../../../Gameplay.ump"
   public boolean isCurrentTurnTheLastInGame(){
    return KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft() == null;
  }


  /**
   * This method shuffles the domino pile when called!
   * 
   */
  // line 146 "../../../../../Gameplay.ump"
   public void shuffleDominoPile(){
    Controller.shuffleDominoes(KingdominoApplication.getKingdomino().getCurrentGame().getAllDominos());
  }


  /**
   * This method does not do anything, like me! (We have already implemented it successfully)
   * 
   */
  // line 154 "../../../../../Gameplay.ump"
   public void generateInitialPlayerOrder(){
    // implemented in Controller.startGame()
  }


  /**
   *  This method creates the next draft by calling the corresponding controller method.
   *
   */
  // line 162 "../../../../../Gameplay.ump"
   public void createNextDraft(){
    Game currentGame = KingdominoApplication.getKingdomino().getCurrentGame();
		Controller.CreateNextDraft(currentGame.getAllDominos());
  }


  /**
   * This method puts the domino in the next draft in order of ids by calling the corresponding controller method.
   * 
   */
  // line 171 "../../../../../Gameplay.ump"
   public void orderNextDraft(){
    Controller.OrderDraft(KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft());
		draftReady();
  }


  /**
   * This method reveals the next draft after a turn by calling the corresponding controller method.
   *
   */
  // line 180 "../../../../../Gameplay.ump"
   public void revealNextDraft(){
    Controller.RevealDraft(KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft());
  }


  /**
   * This method updates the scores and rankings in real time.
   * 
   */
  // line 192 "../../../../../Gameplay.ump"
   public void updateScoresAndRankings(){
    Controller.CalculateRanking();
  }


  /**
   * This method resets the current player for domino selection.
   *
   */
  // line 200 "../../../../../Gameplay.ump"
   public void resetCurrentPlayer(){
    Game currentGame = KingdominoApplication.getKingdomino().getCurrentGame();
		currentGame.setNextPlayer(currentGame.getPlayer(0));
  }


  /**
   * This method updates the current player for domino selection.
   * 
   */
  // line 209 "../../../../../Gameplay.ump"
   public void updateCurrentPlayer(){
    Game currentGame = KingdominoApplication.getKingdomino().getCurrentGame();
    
    	int index = currentGame.getPlayers().indexOf(currentGame.getNextPlayer()) + 1;
    
		if(currentGame.getNextPlayer() == null || index >= currentGame.getNumberOfPlayers()) {
			currentGame.setNextPlayer(currentGame.getPlayer(0));
			
		}
		else currentGame.setNextPlayer(currentGame.getPlayer(index));
  }

}