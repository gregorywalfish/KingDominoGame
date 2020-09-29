/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;
import java.util.*;

// line 6 "../../../../../Kingdomino.ump"
public class Kingdomino
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Kingdomino Associations
  private List<User> users;
  private List<BonusOption> bonusOptions;
  private List<Game> allGames;
  private Game currentGame;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Kingdomino()
  {
    users = new ArrayList<User>();
    bonusOptions = new ArrayList<BonusOption>();
    allGames = new ArrayList<Game>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public User getUser(int index)
  {
    User aUser = users.get(index);
    return aUser;
  }

  public List<User> getUsers()
  {
    List<User> newUsers = Collections.unmodifiableList(users);
    return newUsers;
  }

  public int numberOfUsers()
  {
    int number = users.size();
    return number;
  }

  public boolean hasUsers()
  {
    boolean has = users.size() > 0;
    return has;
  }

  public int indexOfUser(User aUser)
  {
    int index = users.indexOf(aUser);
    return index;
  }
  /* Code from template association_GetMany */
  public BonusOption getBonusOption(int index)
  {
    BonusOption aBonusOption = bonusOptions.get(index);
    return aBonusOption;
  }

  public List<BonusOption> getBonusOptions()
  {
    List<BonusOption> newBonusOptions = Collections.unmodifiableList(bonusOptions);
    return newBonusOptions;
  }

  public int numberOfBonusOptions()
  {
    int number = bonusOptions.size();
    return number;
  }

  public boolean hasBonusOptions()
  {
    boolean has = bonusOptions.size() > 0;
    return has;
  }

  public int indexOfBonusOption(BonusOption aBonusOption)
  {
    int index = bonusOptions.indexOf(aBonusOption);
    return index;
  }
  /* Code from template association_GetMany */
  public Game getAllGame(int index)
  {
    Game aAllGame = allGames.get(index);
    return aAllGame;
  }

  public List<Game> getAllGames()
  {
    List<Game> newAllGames = Collections.unmodifiableList(allGames);
    return newAllGames;
  }

  public int numberOfAllGames()
  {
    int number = allGames.size();
    return number;
  }

  public boolean hasAllGames()
  {
    boolean has = allGames.size() > 0;
    return has;
  }

  public int indexOfAllGame(Game aAllGame)
  {
    int index = allGames.indexOf(aAllGame);
    return index;
  }
  /* Code from template association_GetOne */
  public Game getCurrentGame()
  {
    return currentGame;
  }

  public boolean hasCurrentGame()
  {
    boolean has = currentGame != null;
    return has;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfUsers()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public User addUser(String aName)
  {
    return new User(aName, this);
  }

  public boolean addUser(User aUser)
  {
    boolean wasAdded = false;
    if (users.contains(aUser)) { return false; }
    Kingdomino existingKingdomino = aUser.getKingdomino();
    boolean isNewKingdomino = existingKingdomino != null && !this.equals(existingKingdomino);
    if (isNewKingdomino)
    {
      aUser.setKingdomino(this);
    }
    else
    {
      users.add(aUser);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeUser(User aUser)
  {
    boolean wasRemoved = false;
    //Unable to remove aUser, as it must always have a kingdomino
    if (!this.equals(aUser.getKingdomino()))
    {
      users.remove(aUser);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addUserAt(User aUser, int index)
  {  
    boolean wasAdded = false;
    if(addUser(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveUserAt(User aUser, int index)
  {
    boolean wasAdded = false;
    if(users.contains(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addUserAt(aUser, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBonusOptions()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public BonusOption addBonusOption(String aOptionName)
  {
    return new BonusOption(aOptionName, this);
  }

  public boolean addBonusOption(BonusOption aBonusOption)
  {
    boolean wasAdded = false;
    if (bonusOptions.contains(aBonusOption)) { return false; }
    Kingdomino existingKingdomino = aBonusOption.getKingdomino();
    boolean isNewKingdomino = existingKingdomino != null && !this.equals(existingKingdomino);
    if (isNewKingdomino)
    {
      aBonusOption.setKingdomino(this);
    }
    else
    {
      bonusOptions.add(aBonusOption);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBonusOption(BonusOption aBonusOption)
  {
    boolean wasRemoved = false;
    //Unable to remove aBonusOption, as it must always have a kingdomino
    if (!this.equals(aBonusOption.getKingdomino()))
    {
      bonusOptions.remove(aBonusOption);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBonusOptionAt(BonusOption aBonusOption, int index)
  {  
    boolean wasAdded = false;
    if(addBonusOption(aBonusOption))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBonusOptions()) { index = numberOfBonusOptions() - 1; }
      bonusOptions.remove(aBonusOption);
      bonusOptions.add(index, aBonusOption);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBonusOptionAt(BonusOption aBonusOption, int index)
  {
    boolean wasAdded = false;
    if(bonusOptions.contains(aBonusOption))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBonusOptions()) { index = numberOfBonusOptions() - 1; }
      bonusOptions.remove(aBonusOption);
      bonusOptions.add(index, aBonusOption);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBonusOptionAt(aBonusOption, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAllGames()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Game addAllGame(int aMaxPileSize)
  {
    return new Game(aMaxPileSize, this);
  }

  public boolean addAllGame(Game aAllGame)
  {
    boolean wasAdded = false;
    if (allGames.contains(aAllGame)) { return false; }
    Kingdomino existingKingdomino = aAllGame.getKingdomino();
    boolean isNewKingdomino = existingKingdomino != null && !this.equals(existingKingdomino);
    if (isNewKingdomino)
    {
      aAllGame.setKingdomino(this);
    }
    else
    {
      allGames.add(aAllGame);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAllGame(Game aAllGame)
  {
    boolean wasRemoved = false;
    //Unable to remove aAllGame, as it must always have a kingdomino
    if (!this.equals(aAllGame.getKingdomino()))
    {
      allGames.remove(aAllGame);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAllGameAt(Game aAllGame, int index)
  {  
    boolean wasAdded = false;
    if(addAllGame(aAllGame))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAllGames()) { index = numberOfAllGames() - 1; }
      allGames.remove(aAllGame);
      allGames.add(index, aAllGame);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAllGameAt(Game aAllGame, int index)
  {
    boolean wasAdded = false;
    if(allGames.contains(aAllGame))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAllGames()) { index = numberOfAllGames() - 1; }
      allGames.remove(aAllGame);
      allGames.add(index, aAllGame);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAllGameAt(aAllGame, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setCurrentGame(Game aNewCurrentGame)
  {
    boolean wasSet = false;
    currentGame = aNewCurrentGame;
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    while (users.size() > 0)
    {
      User aUser = users.get(users.size() - 1);
      aUser.delete();
      users.remove(aUser);
    }
    
    while (bonusOptions.size() > 0)
    {
      BonusOption aBonusOption = bonusOptions.get(bonusOptions.size() - 1);
      aBonusOption.delete();
      bonusOptions.remove(aBonusOption);
    }
    
    while (allGames.size() > 0)
    {
      Game aAllGame = allGames.get(allGames.size() - 1);
      aAllGame.delete();
      allGames.remove(aAllGame);
    }
    
    currentGame = null;
  }

}