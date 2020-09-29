/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;
import java.util.*;

/**
 * Dominos, pile, draft
 */
// line 47 "../../../../../Kingdomino.ump"
public class Kingdom
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Kingdom Associations
  private List<KingdomTerritory> territories;
  private List<Property> properties;
  private Player player;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Kingdom(Player aPlayer)
  {
    territories = new ArrayList<KingdomTerritory>();
    properties = new ArrayList<Property>();
    boolean didAddPlayer = setPlayer(aPlayer);
    if (!didAddPlayer)
    {
      throw new RuntimeException("Unable to create kingdom due to player");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public KingdomTerritory getTerritory(int index)
  {
    KingdomTerritory aTerritory = territories.get(index);
    return aTerritory;
  }

  public List<KingdomTerritory> getTerritories()
  {
    List<KingdomTerritory> newTerritories = Collections.unmodifiableList(territories);
    return newTerritories;
  }

  public int numberOfTerritories()
  {
    int number = territories.size();
    return number;
  }

  public boolean hasTerritories()
  {
    boolean has = territories.size() > 0;
    return has;
  }

  public int indexOfTerritory(KingdomTerritory aTerritory)
  {
    int index = territories.indexOf(aTerritory);
    return index;
  }
  /* Code from template association_GetMany */
  public Property getProperty(int index)
  {
    Property aProperty = properties.get(index);
    return aProperty;
  }

  public void clearProperty() {
    properties.clear();
  }

  public List<Property> getProperties()
  {
    List<Property> newProperties = Collections.unmodifiableList(properties);
    return newProperties;
  }

  public int numberOfProperties()
  {
    int number = properties.size();
    return number;
  }

  public boolean hasProperties()
  {
    boolean has = properties.size() > 0;
    return has;
  }

  public int indexOfProperty(Property aProperty)
  {
    int index = properties.indexOf(aProperty);
    return index;
  }
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTerritories()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */


  public boolean addTerritory(KingdomTerritory aTerritory)
  {
    boolean wasAdded = false;
    if (territories.contains(aTerritory)) { return false; }
    Kingdom existingKingdom = aTerritory.getKingdom();
    boolean isNewKingdom = existingKingdom != null && !this.equals(existingKingdom);
    if (isNewKingdom)
    {
      aTerritory.setKingdom(this);
    }
    else
    {
      territories.add(aTerritory);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTerritory(KingdomTerritory aTerritory)
  {
    boolean wasRemoved = false;
    //Unable to remove aTerritory, as it must always have a kingdom
    if (!this.equals(aTerritory.getKingdom()))
    {
      territories.remove(aTerritory);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTerritoryAt(KingdomTerritory aTerritory, int index)
  {  
    boolean wasAdded = false;
    if(addTerritory(aTerritory))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTerritories()) { index = numberOfTerritories() - 1; }
      territories.remove(aTerritory);
      territories.add(index, aTerritory);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTerritoryAt(KingdomTerritory aTerritory, int index)
  {
    boolean wasAdded = false;
    if(territories.contains(aTerritory))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTerritories()) { index = numberOfTerritories() - 1; }
      territories.remove(aTerritory);
      territories.add(index, aTerritory);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTerritoryAt(aTerritory, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfProperties()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Property addProperty()
  {
    return new Property(this);
  }

  public boolean addProperty(Property aProperty)
  {
    boolean wasAdded = false;
    if (properties.contains(aProperty)) { return false; }
    Kingdom existingKingdom = aProperty.getKingdom();
    boolean isNewKingdom = existingKingdom != null && !this.equals(existingKingdom);
    if (isNewKingdom)
    {
      aProperty.setKingdom(this);
    }
    else
    {
      properties.add(aProperty);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeProperty(Property aProperty)
  {
    boolean wasRemoved = false;
    //Unable to remove aProperty, as it must always have a kingdom
    if (!this.equals(aProperty.getKingdom()))
    {
      properties.remove(aProperty);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPropertyAt(Property aProperty, int index)
  {  
    boolean wasAdded = false;
    if(addProperty(aProperty))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfProperties()) { index = numberOfProperties() - 1; }
      properties.remove(aProperty);
      properties.add(index, aProperty);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePropertyAt(Property aProperty, int index)
  {
    boolean wasAdded = false;
    if(properties.contains(aProperty))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfProperties()) { index = numberOfProperties() - 1; }
      properties.remove(aProperty);
      properties.add(index, aProperty);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPropertyAt(aProperty, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setPlayer(Player aNewPlayer)
  {
    boolean wasSet = false;
    if (aNewPlayer == null)
    {
      //Unable to setPlayer to null, as kingdom must always be associated to a player
      return wasSet;
    }
    
    Kingdom existingKingdom = aNewPlayer.getKingdom();
    if (existingKingdom != null && !equals(existingKingdom))
    {
      //Unable to setPlayer, the current player already has a kingdom, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Player anOldPlayer = player;
    player = aNewPlayer;
    player.setKingdom(this);

    if (anOldPlayer != null)
    {
      anOldPlayer.setKingdom(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    while (territories.size() > 0)
    {
      KingdomTerritory aTerritory = territories.get(territories.size() - 1);
      aTerritory.delete();
      territories.remove(aTerritory);
    }
    
    while (properties.size() > 0)
    {
      Property aProperty = properties.get(properties.size() - 1);
      aProperty.delete();
      properties.remove(aProperty);
    }
    
    Player existingPlayer = player;
    player = null;
    if (existingPlayer != null)
    {
      existingPlayer.setKingdom(null);
    }
  }

}