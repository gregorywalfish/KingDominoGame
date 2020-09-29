/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;
import java.util.*;

/**
 * Players, users
 */
// line 29 "../../../../../Kingdomino.ump"
public class User
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, User> usersByName = new HashMap<String, User>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //User Attributes
  private String name;
  private int playedGames;
  private int wonGames;

  //User Associations
  private List<Player> playerInGames;
  private Kingdomino kingdomino;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public User(String aName, Kingdomino aKingdomino)
  {
    playedGames = 0;
    wonGames = 0;
    if (!setName(aName))
    {
      throw new RuntimeException("Cannot create due to duplicate name");
    }
    playerInGames = new ArrayList<Player>();
    boolean didAddKingdomino = setKingdomino(aKingdomino);
    if (!didAddKingdomino)
    {
      throw new RuntimeException("Unable to create user due to kingdomino");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    String anOldName = getName();
    if (hasWithName(aName)) {
      return wasSet;
    }
    name = aName;
    wasSet = true;
    if (anOldName != null) {
      usersByName.remove(anOldName);
    }
    usersByName.put(aName, this);
    return wasSet;
  }

  public boolean setPlayedGames(int aPlayedGames)
  {
    boolean wasSet = false;
    playedGames = aPlayedGames;
    wasSet = true;
    return wasSet;
  }

  public boolean setWonGames(int aWonGames)
  {
    boolean wasSet = false;
    wonGames = aWonGames;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }
  /* Code from template attribute_GetUnique */
  public static User getWithName(String aName)
  {
    return usersByName.get(aName);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithName(String aName)
  {
    return getWithName(aName) != null;
  }

  public int getPlayedGames()
  {
    return playedGames;
  }

  public int getWonGames()
  {
    return wonGames;
  }
  /* Code from template association_GetMany */
  public Player getPlayerInGame(int index)
  {
    Player aPlayerInGame = playerInGames.get(index);
    return aPlayerInGame;
  }

  public List<Player> getPlayerInGames()
  {
    List<Player> newPlayerInGames = Collections.unmodifiableList(playerInGames);
    return newPlayerInGames;
  }

  public int numberOfPlayerInGames()
  {
    int number = playerInGames.size();
    return number;
  }

  public boolean hasPlayerInGames()
  {
    boolean has = playerInGames.size() > 0;
    return has;
  }

  public int indexOfPlayerInGame(Player aPlayerInGame)
  {
    int index = playerInGames.indexOf(aPlayerInGame);
    return index;
  }
  /* Code from template association_GetOne */
  public Kingdomino getKingdomino()
  {
    return kingdomino;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPlayerInGames()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addPlayerInGame(Player aPlayerInGame)
  {
    boolean wasAdded = false;
    if (playerInGames.contains(aPlayerInGame)) { return false; }
    User existingUser = aPlayerInGame.getUser();
    if (existingUser == null)
    {
      aPlayerInGame.setUser(this);
    }
    else if (!this.equals(existingUser))
    {
      existingUser.removePlayerInGame(aPlayerInGame);
      addPlayerInGame(aPlayerInGame);
    }
    else
    {
      playerInGames.add(aPlayerInGame);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePlayerInGame(Player aPlayerInGame)
  {
    boolean wasRemoved = false;
    if (playerInGames.contains(aPlayerInGame))
    {
      playerInGames.remove(aPlayerInGame);
      aPlayerInGame.setUser(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPlayerInGameAt(Player aPlayerInGame, int index)
  {  
    boolean wasAdded = false;
    if(addPlayerInGame(aPlayerInGame))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayerInGames()) { index = numberOfPlayerInGames() - 1; }
      playerInGames.remove(aPlayerInGame);
      playerInGames.add(index, aPlayerInGame);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePlayerInGameAt(Player aPlayerInGame, int index)
  {
    boolean wasAdded = false;
    if(playerInGames.contains(aPlayerInGame))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayerInGames()) { index = numberOfPlayerInGames() - 1; }
      playerInGames.remove(aPlayerInGame);
      playerInGames.add(index, aPlayerInGame);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPlayerInGameAt(aPlayerInGame, index);
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
      existingKingdomino.removeUser(this);
    }
    kingdomino.addUser(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    usersByName.remove(getName());
    while( !playerInGames.isEmpty() )
    {
      playerInGames.get(0).setUser(null);
    }
    Kingdomino placeholderKingdomino = kingdomino;
    this.kingdomino = null;
    if(placeholderKingdomino != null)
    {
      placeholderKingdomino.removeUser(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "playedGames" + ":" + getPlayedGames()+ "," +
            "wonGames" + ":" + getWonGames()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "kingdomino = "+(getKingdomino()!=null?Integer.toHexString(System.identityHashCode(getKingdomino())):"null");
  }
}