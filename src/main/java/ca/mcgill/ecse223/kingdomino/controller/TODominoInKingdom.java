/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.controller;
import ca.mcgill.ecse223.kingdomino.view.*;

// line 3 "../../../../../TransferObjects.ump"
public class TODominoInKingdom
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum DirectionKind { Up, Down, Left, Right }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TODominoInKingdom Attributes
  private DirectionKind direction;
  private int x;
  private int y;
  private int id;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TODominoInKingdom(int aX, int aY, int aId)
  {
    direction = DirectionKind.Up;
    x = aX;
    y = aY;
    id = aId;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDirection(DirectionKind aDirection)
  {
    boolean wasSet = false;
    direction = aDirection;
    wasSet = true;
    return wasSet;
  }

  public boolean setX(int aX)
  {
    boolean wasSet = false;
    x = aX;
    wasSet = true;
    return wasSet;
  }

  public boolean setY(int aY)
  {
    boolean wasSet = false;
    y = aY;
    wasSet = true;
    return wasSet;
  }

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public DirectionKind getDirection()
  {
    return direction;
  }

  public int getX()
  {
    return x;
  }

  public int getY()
  {
    return y;
  }

  public int getId()
  {
    return id;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "x" + ":" + getX()+ "," +
            "y" + ":" + getY()+ "," +
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "direction" + "=" + (getDirection() != null ? !getDirection().equals(this)  ? getDirection().toString().replaceAll("  ","    ") : "this" : "null");
  }
}