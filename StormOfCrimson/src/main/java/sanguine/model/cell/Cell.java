package sanguine.model.cell;

import sanguine.model.PlayerId;
import sanguine.model.card.Card;

/**
 * An interface that represents a cell on the board of the Queen's Blood game.
 */
public interface Cell {
  /**
   * Enums that represent whether a cell is empty, has pawns, or has a card.
   */
  enum Kind {
    EMPTY,
    PAWNS,
    CARD
  }

  /**
   * A getter that returns the kind of cell that it is.
   *
   * @return (Kind) an enum representing the cell's type.
   */
  Kind kind();

  /**
   * Determines whether the cell is currently owned or not.
   *
   * @return (boolean) is the cell owned?
   */
  boolean isOwned();

  /**
   * Returns the player who owns the cell.
   *
   * @return (PlayerId) the color of the cell's owner (null if there is no owner).
   */
  PlayerId owner();

  /**
   * Determines how many pawns the cell contains.
   *
   * @return (int) the number of pawns on this cell (0 is it is not a pawn cell).
   */
  int pawnCount();

  /**
   * Produces the card on this cell.
   *
   * @return (card) the cell's card (null if it is not a card cell).
   */
  Card card();

  /**
   * Increments the amount of pawns on this tile.
   *
   * @param playerId (PlayerId) the player who incremented this tile.
   * @return (cell) the new cell produced after being incremented.
   */
  Cell pawnIncrement(PlayerId playerId);

  /**
   * Returns a version of the cell that is now owned by the given player.
   *
   * @param playerId (PlayerId) the player who is claiming this cell
   * @return (cell) the new cell after being claimed.
   */
  Cell claimedBy(PlayerId playerId);
}
