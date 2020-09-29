/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;
import java.util.*;

/**
 * Draft = Next group of dominos available for selection
 */
// line 88 "../../../../../Kingdomino.ump"
public class Draft
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum DraftStatus { FaceDown, Sorted, FaceUp }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Draft Attributes
  private DraftStatus draftStatus;

  //Draft Associations
  private List<Domino> idSortedDominos;
  private List<DominoSelection> selections;
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Draft(DraftStatus aDraftStatus, Game aGame)
  {
    draftStatus = aDraftStatus;
    idSortedDominos = new ArrayList<Domino>();
    selections = new ArrayList<DominoSelection>();
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create allDraft due to game");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDraftStatus(DraftStatus aDraftStatus)
  {
    boolean wasSet = false;
    draftStatus = aDraftStatus;
    wasSet = true;
    return wasSet;
  }

  public DraftStatus getDraftStatus()
  {
    return draftStatus;
  }
  /* Code from template association_GetMany */
  public Domino getIdSortedDomino(int index)
  {
    Domino aIdSortedDomino = idSortedDominos.get(index);
    return aIdSortedDomino;
  }

  public List<Domino> getIdSortedDominos()
  {
    List<Domino> newIdSortedDominos = Collections.unmodifiableList(idSortedDominos);
    return newIdSortedDominos;
  }

  public int numberOfIdSortedDominos()
  {
    int number = idSortedDominos.size();
    return number;
  }

  public boolean hasIdSortedDominos()
  {
    boolean has = idSortedDominos.size() > 0;
    return has;
  }

  public int indexOfIdSortedDomino(Domino aIdSortedDomino)
  {
    int index = idSortedDominos.indexOf(aIdSortedDomino);
    return index;
  }
  /* Code from template association_GetMany */
  public DominoSelection getSelection(int index)
  {
    DominoSelection aSelection = selections.get(index);
    return aSelection;
  }

  public List<DominoSelection> getSelections()
  {
    List<DominoSelection> newSelections = Collections.unmodifiableList(selections);
    return newSelections;
  }

  public int numberOfSelections()
  {
    int number = selections.size();
    return number;
  }

  public boolean hasSelections()
  {
    boolean has = selections.size() > 0;
    return has;
  }

  public int indexOfSelection(DominoSelection aSelection)
  {
    int index = selections.indexOf(aSelection);
    return index;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfIdSortedDominos()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfIdSortedDominos()
  {
    return 4;
  }
  /* Code from template association_AddUnidirectionalOptionalN */
  public boolean addIdSortedDomino(Domino aIdSortedDomino)
  {
    boolean wasAdded = false;
    if (idSortedDominos.contains(aIdSortedDomino)) { return false; }
    if (numberOfIdSortedDominos() < maximumNumberOfIdSortedDominos())
    {
      idSortedDominos.add(aIdSortedDomino);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean removeIdSortedDomino(Domino aIdSortedDomino)
  {
    boolean wasRemoved = false;
    if (idSortedDominos.contains(aIdSortedDomino))
    {
      idSortedDominos.remove(aIdSortedDomino);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_SetUnidirectionalOptionalN */
  public boolean setIdSortedDominos(Domino... newIdSortedDominos)
  {
    boolean wasSet = false;
    ArrayList<Domino> verifiedIdSortedDominos = new ArrayList<Domino>();
    for (Domino aIdSortedDomino : newIdSortedDominos)
    {
      if (verifiedIdSortedDominos.contains(aIdSortedDomino))
      {
        continue;
      }
      verifiedIdSortedDominos.add(aIdSortedDomino);
    }

    if (verifiedIdSortedDominos.size() != newIdSortedDominos.length || verifiedIdSortedDominos.size() > maximumNumberOfIdSortedDominos())
    {
      return wasSet;
    }

    idSortedDominos.clear();
    idSortedDominos.addAll(verifiedIdSortedDominos);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addIdSortedDominoAt(Domino aIdSortedDomino, int index)
  {  
    boolean wasAdded = false;
    if(addIdSortedDomino(aIdSortedDomino))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfIdSortedDominos()) { index = numberOfIdSortedDominos() - 1; }
      idSortedDominos.remove(aIdSortedDomino);
      idSortedDominos.add(index, aIdSortedDomino);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveIdSortedDominoAt(Domino aIdSortedDomino, int index)
  {
    boolean wasAdded = false;
    if(idSortedDominos.contains(aIdSortedDomino))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfIdSortedDominos()) { index = numberOfIdSortedDominos() - 1; }
      idSortedDominos.remove(aIdSortedDomino);
      idSortedDominos.add(index, aIdSortedDomino);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addIdSortedDominoAt(aIdSortedDomino, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfSelections()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfSelections()
  {
    return 4;
  }
  /* Code from template association_AddOptionalNToOne */
  public DominoSelection addSelection(Player aPlayer, Domino aDomino)
  {
    if (numberOfSelections() >= maximumNumberOfSelections())
    {
      return null;
    }
    else
    {
      return new DominoSelection(aPlayer, aDomino, this);
    }
  }

  public boolean addSelection(DominoSelection aSelection)
  {
    boolean wasAdded = false;
    if (selections.contains(aSelection)) { return false; }
    if (numberOfSelections() >= maximumNumberOfSelections())
    {
      return wasAdded;
    }

    Draft existingDraft = aSelection.getDraft();
    boolean isNewDraft = existingDraft != null && !this.equals(existingDraft);
    if (isNewDraft)
    {
      aSelection.setDraft(this);
    }
    else
    {
      selections.add(aSelection);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeSelection(DominoSelection aSelection)
  {
    boolean wasRemoved = false;
    //Unable to remove aSelection, as it must always have a draft
    if (!this.equals(aSelection.getDraft()))
    {
      selections.remove(aSelection);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addSelectionAt(DominoSelection aSelection, int index)
  {  
    boolean wasAdded = false;
    if(addSelection(aSelection))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSelections()) { index = numberOfSelections() - 1; }
      selections.remove(aSelection);
      selections.add(index, aSelection);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveSelectionAt(DominoSelection aSelection, int index)
  {
    boolean wasAdded = false;
    if(selections.contains(aSelection))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSelections()) { index = numberOfSelections() - 1; }
      selections.remove(aSelection);
      selections.add(index, aSelection);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addSelectionAt(aSelection, index);
    }
    return wasAdded;
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
      existingGame.removeAllDraft(this);
    }
    game.addAllDraft(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    idSortedDominos.clear();
    while (selections.size() > 0)
    {
      DominoSelection aSelection = selections.get(selections.size() - 1);
      aSelection.delete();
      selections.remove(aSelection);
    }
    
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removeAllDraft(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "draftStatus" + "=" + (getDraftStatus() != null ? !getDraftStatus().equals(this)  ? getDraftStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }
}