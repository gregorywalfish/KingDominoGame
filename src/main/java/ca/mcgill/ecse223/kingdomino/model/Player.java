/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;

// line 36 "../../../../../Kingdomino.ump"
public class Player
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum PlayerColor { Blue, Green, Yellow, Pink }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Player Attributes
  private PlayerColor color;
  private int currentRanking;
  private int bonusScore;
  private int propertyScore;

  //Player Associations
  private Kingdom kingdom;
  private Game game;
  private User user;
  private DominoSelection dominoSelection;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(Game aGame)
  {
    currentRanking = 1;
    bonusScore = 0;
    propertyScore = 0;
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create player due to game");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setColor(PlayerColor aColor)
  {
    boolean wasSet = false;
    color = aColor;
    wasSet = true;
    return wasSet;
  }

  public boolean setCurrentRanking(int aCurrentRanking)
  {
    boolean wasSet = false;
    currentRanking = aCurrentRanking;
    wasSet = true;
    return wasSet;
  }

  public boolean setBonusScore(int aBonusScore)
  {
    boolean wasSet = false;
    bonusScore = aBonusScore;
    wasSet = true;
    return wasSet;
  }

  public boolean setPropertyScore(int aPropertyScore)
  {
    boolean wasSet = false;
    propertyScore = aPropertyScore;
    wasSet = true;
    return wasSet;
  }

  public PlayerColor getColor()
  {
    return color;
  }

  public int getCurrentRanking()
  {
    return currentRanking;
  }

  public int getBonusScore()
  {
    return bonusScore;
  }

  public int getPropertyScore()
  {
    return propertyScore;
  }

  public int getTotalScore()
  {
    return bonusScore + propertyScore;
  }
  /* Code from template association_GetOne */
  public Kingdom getKingdom()
  {
    return kingdom;
  }

  public boolean hasKingdom()
  {
    boolean has = kingdom != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_GetOne */
  public User getUser()
  {
    return user;
  }

  public boolean hasUser()
  {
    boolean has = user != null;
    return has;
  }
  /* Code from template association_GetOne */
  public DominoSelection getDominoSelection()
  {
    return dominoSelection;
  }

  public boolean hasDominoSelection()
  {
    boolean has = dominoSelection != null;
    return has;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setKingdom(Kingdom aNewKingdom)
  {
    boolean wasSet = false;
    if (kingdom != null && !kingdom.equals(aNewKingdom) && equals(kingdom.getPlayer()))
    {
      //Unable to setKingdom, as existing kingdom would become an orphan
      return wasSet;
    }

    kingdom = aNewKingdom;
    Player anOldPlayer = aNewKingdom != null ? aNewKingdom.getPlayer() : null;

    if (!this.equals(anOldPlayer))
    {
      if (anOldPlayer != null)
      {
        anOldPlayer.kingdom = null;
      }
      if (kingdom != null)
      {
        kingdom.setPlayer(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    //Must provide game to player
    if (aGame == null)
    {
      return wasSet;
    }

    //game already at maximum (4)
    if (aGame.numberOfPlayers() >= Game.maximumNumberOfPlayers())
    {
      return wasSet;
    }
    
    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      boolean didRemove = existingGame.removePlayer(this);
      if (!didRemove)
      {
        game = existingGame;
        return wasSet;
      }
    }
    game.addPlayer(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setUser(User aUser)
  {
    boolean wasSet = false;
    User existingUser = user;
    user = aUser;
    if (existingUser != null && !existingUser.equals(aUser))
    {
      existingUser.removePlayerInGame(this);
    }
    if (aUser != null)
    {
      aUser.addPlayerInGame(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setDominoSelection(DominoSelection aNewDominoSelection)
  {
    boolean wasSet = false;
    if (dominoSelection != null && !dominoSelection.equals(aNewDominoSelection) && equals(dominoSelection.getPlayer()))
    {
      //Unable to setDominoSelection, as existing dominoSelection would become an orphan
      return wasSet;
    }

    dominoSelection = aNewDominoSelection;
    Player anOldPlayer = aNewDominoSelection != null ? aNewDominoSelection.getPlayer() : null;

    if (!this.equals(anOldPlayer))
    {
      if (anOldPlayer != null)
      {
        anOldPlayer.dominoSelection = null;
      }
      if (dominoSelection != null)
      {
        dominoSelection.setPlayer(this);
      }
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Kingdom existingKingdom = kingdom;
    kingdom = null;
    if (existingKingdom != null)
    {
      existingKingdom.delete();
      existingKingdom.setPlayer(null);
    }
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removePlayer(this);
    }
    if (user != null)
    {
      User placeholderUser = user;
      this.user = null;
      placeholderUser.removePlayerInGame(this);
    }
    DominoSelection existingDominoSelection = dominoSelection;
    dominoSelection = null;
    if (existingDominoSelection != null)
    {
      existingDominoSelection.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "currentRanking" + ":" + getCurrentRanking()+ "," +
            "bonusScore" + ":" + getBonusScore()+ "," +
            "propertyScore" + ":" + getPropertyScore()+ "," +
            "totalScore" + ":" + getTotalScore()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "color" + "=" + (getColor() != null ? !getColor().equals(this)  ? getColor().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "kingdom = "+(getKingdom()!=null?Integer.toHexString(System.identityHashCode(getKingdom())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "user = "+(getUser()!=null?Integer.toHexString(System.identityHashCode(getUser())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "dominoSelection = "+(getDominoSelection()!=null?Integer.toHexString(System.identityHashCode(getDominoSelection())):"null");
  }
}