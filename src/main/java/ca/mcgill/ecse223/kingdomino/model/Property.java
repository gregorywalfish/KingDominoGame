/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;
import java.util.*;

/**
 * Scoring
 */
// line 103 "../../../../../Kingdomino.ump"
public class Property
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Property Attributes
  private TerrainType propertyType;
  private TerrainType leftTile;
  private int score;
  private int size;
  private int crowns;

  //Property Associations
  private List<Domino> includedDominos;
  private Kingdom kingdom;

  //Helper Variables
  private boolean canSetPropertyType;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Property(Kingdom aKingdom)
  {
    canSetPropertyType = true;
    score = 0;
    size = 0;
    crowns = 0;
    includedDominos = new ArrayList<Domino>();
    boolean didAddKingdom = setKingdom(aKingdom);
    if (!didAddKingdom)
    {
      throw new RuntimeException("Unable to create property due to kingdom");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template attribute_SetImmutable */
  public boolean setPropertyType(TerrainType aPropertyType)
  {
    boolean wasSet = false;
    if (!canSetPropertyType) { return false; }
    canSetPropertyType = false;
    propertyType = aPropertyType;
    wasSet = true;
    return wasSet;
  }

  public boolean setLeftTile(TerrainType aLeftTile)
  {
    boolean wasSet = false;
    leftTile = aLeftTile;
    wasSet = true;
    return wasSet;
  }

  public boolean setScore(int aScore)
  {
    boolean wasSet = false;
    score = aScore;
    wasSet = true;
    return wasSet;
  }

  public boolean setSize(int aSize)
  {
    boolean wasSet = false;
    size = aSize;
    wasSet = true;
    return wasSet;
  }

  public boolean setCrowns(int aCrowns)
  {
    boolean wasSet = false;
    crowns = aCrowns;
    wasSet = true;
    return wasSet;
  }

  public TerrainType getPropertyType()
  {
    return propertyType;
  }

  public TerrainType getLeftTile()
  {
    return leftTile;
  }

  public int getScore()
  {
    return score;
  }

  public int getSize()
  {
    return size;
  }

  public int getCrowns()
  {
    return crowns;
  }
  /* Code from template association_GetMany */
  public Domino getIncludedDomino(int index)
  {
    Domino aIncludedDomino = includedDominos.get(index);
    return aIncludedDomino;
  }

  public List<Domino> getIncludedDominos()
  {
    List<Domino> newIncludedDominos = Collections.unmodifiableList(includedDominos);
    return newIncludedDominos;
  }

  public int numberOfIncludedDominos()
  {
    int number = includedDominos.size();
    return number;
  }

  public boolean hasIncludedDominos()
  {
    boolean has = includedDominos.size() > 0;
    return has;
  }

  public int indexOfIncludedDomino(Domino aIncludedDomino)
  {
    int index = includedDominos.indexOf(aIncludedDomino);
    return index;
  }
  /* Code from template association_GetOne */
  public Kingdom getKingdom()
  {
    return kingdom;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfIncludedDominos()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addIncludedDomino(Domino aIncludedDomino)
  {
    boolean wasAdded = false;
    if (includedDominos.contains(aIncludedDomino)) { return false; }
    includedDominos.add(aIncludedDomino);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeIncludedDomino(Domino aIncludedDomino)
  {
    boolean wasRemoved = false;
    if (includedDominos.contains(aIncludedDomino))
    {
      includedDominos.remove(aIncludedDomino);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addIncludedDominoAt(Domino aIncludedDomino, int index)
  {  
    boolean wasAdded = false;
    if(addIncludedDomino(aIncludedDomino))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfIncludedDominos()) { index = numberOfIncludedDominos() - 1; }
      includedDominos.remove(aIncludedDomino);
      includedDominos.add(index, aIncludedDomino);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveIncludedDominoAt(Domino aIncludedDomino, int index)
  {
    boolean wasAdded = false;
    if(includedDominos.contains(aIncludedDomino))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfIncludedDominos()) { index = numberOfIncludedDominos() - 1; }
      includedDominos.remove(aIncludedDomino);
      includedDominos.add(index, aIncludedDomino);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addIncludedDominoAt(aIncludedDomino, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setKingdom(Kingdom aKingdom)
  {
    boolean wasSet = false;
    if (aKingdom == null)
    {
      return wasSet;
    }

    Kingdom existingKingdom = kingdom;
    kingdom = aKingdom;
    if (existingKingdom != null && !existingKingdom.equals(aKingdom))
    {
      existingKingdom.removeProperty(this);
    }
    kingdom.addProperty(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    includedDominos.clear();
    Kingdom placeholderKingdom = kingdom;
    this.kingdom = null;
    if(placeholderKingdom != null)
    {
      placeholderKingdom.removeProperty(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "score" + ":" + getScore()+ "," +
            "size" + ":" + getSize()+ "," +
            "crowns" + ":" + getCrowns()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "propertyType" + "=" + (getPropertyType() != null ? !getPropertyType().equals(this)  ? getPropertyType().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "leftTile" + "=" + (getLeftTile() != null ? !getLeftTile().equals(this)  ? getLeftTile().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "kingdom = "+(getKingdom()!=null?Integer.toHexString(System.identityHashCode(getKingdom())):"null");
  }
}