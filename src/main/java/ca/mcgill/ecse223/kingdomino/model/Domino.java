/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;

// line 52 "../../../../../Kingdomino.ump"
public class Domino
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum DominoStatus { Excluded, InPile, InNextDraft, InCurrentDraft, CorrectlyPreplaced, ErroneouslyPreplaced, PlacedInKingdom, Discarded }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Domino Attributes
  private int id;
  private TerrainType leftTile;
  private TerrainType rightTile;
  private int leftCrown;
  private int rightCrown;
  private DominoStatus status;

  //Domino Associations
  private Domino nextDomino;
  private Game game;
  private Domino prevDomino;
  private DominoSelection dominoSelection;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Domino(int aId, TerrainType aLeftTile, TerrainType aRightTile, int aRightCrown, Game aGame)
  {
    id = aId;
    leftTile = aLeftTile;
    rightTile = aRightTile;
    leftCrown = 0;
    rightCrown = aRightCrown;
    status = DominoStatus.InPile;
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create allDomino due to game");
    }
    if (aId<1||aId>48)
    {
      throw new RuntimeException("Please provide a valid id [id>=1&&id<=48]");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStatus(DominoStatus aStatus)
  {
    boolean wasSet = false;
    status = aStatus;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }

  public TerrainType getLeftTile()
  {
    return leftTile;
  }

  public TerrainType getRightTile()
  {
    return rightTile;
  }

  public int getLeftCrown()
  {
    return leftCrown;
  }

  public int getRightCrown()
  {
    return rightCrown;
  }

  public DominoStatus getStatus()
  {
    return status;
  }
  /* Code from template association_GetOne */
  public Domino getNextDomino()
  {
    return nextDomino;
  }

  public boolean hasNextDomino()
  {
    boolean has = nextDomino != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_GetOne */
  public Domino getPrevDomino()
  {
    return prevDomino;
  }

  public boolean hasPrevDomino()
  {
    boolean has = prevDomino != null;
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
  /* Code from template association_SetOptionalOneToOptionalOne */
  public boolean setNextDomino(Domino aNewNextDomino)
  {
    boolean wasSet = false;
    if (aNewNextDomino == null)
    {
      Domino existingNextDomino = nextDomino;
      nextDomino = null;
      
      if (existingNextDomino != null && existingNextDomino.getPrevDomino() != null)
      {
        existingNextDomino.setPrevDomino(null);
      }
      wasSet = true;
      return wasSet;
    }

    Domino currentNextDomino = getNextDomino();
    if (currentNextDomino != null && !currentNextDomino.equals(aNewNextDomino))
    {
      currentNextDomino.setPrevDomino(null);
    }

    nextDomino = aNewNextDomino;
    Domino existingPrevDomino = aNewNextDomino.getPrevDomino();

    if (!equals(existingPrevDomino))
    {
      aNewNextDomino.setPrevDomino(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    if (aGame == null)
    {
      return wasSet;
    }

    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      existingGame.removeAllDomino(this);
    }
    game.addAllDomino(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOptionalOne */
  public boolean setPrevDomino(Domino aNewPrevDomino)
  {
    boolean wasSet = false;
    if (aNewPrevDomino == null)
    {
      Domino existingPrevDomino = prevDomino;
      prevDomino = null;
      
      if (existingPrevDomino != null && existingPrevDomino.getNextDomino() != null)
      {
        existingPrevDomino.setNextDomino(null);
      }
      wasSet = true;
      return wasSet;
    }

    Domino currentPrevDomino = getPrevDomino();
    if (currentPrevDomino != null && !currentPrevDomino.equals(aNewPrevDomino))
    {
      currentPrevDomino.setNextDomino(null);
    }

    prevDomino = aNewPrevDomino;
    Domino existingNextDomino = aNewPrevDomino.getNextDomino();

    if (!equals(existingNextDomino))
    {
      aNewPrevDomino.setNextDomino(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setDominoSelection(DominoSelection aNewDominoSelection)
  {
    boolean wasSet = false;
    if (dominoSelection != null && !dominoSelection.equals(aNewDominoSelection) && equals(dominoSelection.getDomino()))
    {
      //Unable to setDominoSelection, as existing dominoSelection would become an orphan
      return wasSet;
    }

    dominoSelection = aNewDominoSelection;
    Domino anOldDomino = aNewDominoSelection != null ? aNewDominoSelection.getDomino() : null;

    if (!this.equals(anOldDomino))
    {
      if (anOldDomino != null)
      {
        anOldDomino.dominoSelection = null;
      }
      if (dominoSelection != null)
      {
        dominoSelection.setDomino(this);
      }
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    if (nextDomino != null)
    {
      nextDomino.setPrevDomino(null);
    }
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removeAllDomino(this);
    }
    if (prevDomino != null)
    {
      prevDomino.setNextDomino(null);
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
            "id" + ":" + getId()+ "," +
            "leftCrown" + ":" + getLeftCrown()+ "," +
            "rightCrown" + ":" + getRightCrown()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "leftTile" + "=" + (getLeftTile() != null ? !getLeftTile().equals(this)  ? getLeftTile().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "rightTile" + "=" + (getRightTile() != null ? !getRightTile().equals(this)  ? getRightTile().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "status" + "=" + (getStatus() != null ? !getStatus().equals(this)  ? getStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "dominoSelection = "+(getDominoSelection()!=null?Integer.toHexString(System.identityHashCode(getDominoSelection())):"null");
  }
}