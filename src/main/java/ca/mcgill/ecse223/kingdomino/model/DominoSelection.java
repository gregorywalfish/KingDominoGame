/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;

/**
 * This can also be an association class
 */
// line 96 "../../../../../Kingdomino.ump"
public class DominoSelection
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //DominoSelection Associations
  private Player player;
  private Domino domino;
  private Draft draft;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DominoSelection(Player aPlayer, Domino aDomino, Draft aDraft)
  {
    boolean didAddPlayer = setPlayer(aPlayer);
    if (!didAddPlayer)
    {
      throw new RuntimeException("Unable to create dominoSelection due to player");
    }
    boolean didAddDomino = setDomino(aDomino);
    if (!didAddDomino)
    {
      throw new RuntimeException("Unable to create dominoSelection due to domino");
    }
    boolean didAddDraft = setDraft(aDraft);
    if (!didAddDraft)
    {
      throw new RuntimeException("Unable to create selection due to draft");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
  }
  /* Code from template association_GetOne */
  public Domino getDomino()
  {
    return domino;
  }
  /* Code from template association_GetOne */
  public Draft getDraft()
  {
    return draft;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setPlayer(Player aNewPlayer)
  {
    boolean wasSet = false;
    if (aNewPlayer == null)
    {
      //Unable to setPlayer to null, as dominoSelection must always be associated to a player
      return wasSet;
    }
    
    DominoSelection existingDominoSelection = aNewPlayer.getDominoSelection();
    if (existingDominoSelection != null && !equals(existingDominoSelection))
    {
      //Unable to setPlayer, the current player already has a dominoSelection, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Player anOldPlayer = player;
    player = aNewPlayer;
    player.setDominoSelection(this);

    if (anOldPlayer != null)
    {
      anOldPlayer.setDominoSelection(null);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setDomino(Domino aNewDomino)
  {
    boolean wasSet = false;
    if (aNewDomino == null)
    {
      //Unable to setDomino to null, as dominoSelection must always be associated to a domino
      return wasSet;
    }
    
    DominoSelection existingDominoSelection = aNewDomino.getDominoSelection();
    if (existingDominoSelection != null && !equals(existingDominoSelection))
    {
      //Unable to setDomino, the current domino already has a dominoSelection, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Domino anOldDomino = domino;
    domino = aNewDomino;
    domino.setDominoSelection(this);

    if (anOldDomino != null)
    {
      anOldDomino.setDominoSelection(null);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setDraft(Draft aDraft)
  {
    boolean wasSet = false;
    //Must provide draft to selection
    if (aDraft == null)
    {
      return wasSet;
    }

    //draft already at maximum (4)
    if (aDraft.numberOfSelections() >= Draft.maximumNumberOfSelections())
    {
      return wasSet;
    }
    
    Draft existingDraft = draft;
    draft = aDraft;
    if (existingDraft != null && !existingDraft.equals(aDraft))
    {
      boolean didRemove = existingDraft.removeSelection(this);
      if (!didRemove)
      {
        draft = existingDraft;
        return wasSet;
      }
    }
    draft.addSelection(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Player existingPlayer = player;
    player = null;
    if (existingPlayer != null)
    {
      existingPlayer.setDominoSelection(null);
    }
    Domino existingDomino = domino;
    domino = null;
    if (existingDomino != null)
    {
      existingDomino.setDominoSelection(null);
    }
    Draft placeholderDraft = draft;
    this.draft = null;
    if(placeholderDraft != null)
    {
      placeholderDraft.removeSelection(this);
    }
  }

}