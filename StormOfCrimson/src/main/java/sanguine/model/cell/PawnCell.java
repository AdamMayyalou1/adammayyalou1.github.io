package sanguine.model.cell;

import sanguine.model.PlayerId;
import sanguine.model.card.Card;

/**
 * A record class that represents a cell with pawns.
 *
 * @param owner (PlayerId) the player who owns this cell.
 * @param pawnCount (int) the number of pawns on this cell.
 */
public record PawnCell(PlayerId owner, int pawnCount) implements Cell {
  /**
   * Checks for any invalid inputs when being constructed.
   *
   * @param owner (PlayerId) the player who owns this cell.
   * @param pawnCount (int) the number of pawns on this cell.
   */
  public PawnCell {
    if (owner == null) {
      throw new IllegalArgumentException("Owner cannot be null");
    }
    if (pawnCount < 0 || pawnCount > 3) {
      throw new IllegalArgumentException("Pawn count must be between 0 and 3");
    }
  }

  @Override
  public Kind kind() {
    return Kind.PAWNS;
  }

  @Override
  public boolean isOwned() {
    return true;
  }

  @Override
  public Card card() {
    return null;
  }

  @Override
  public Cell pawnIncrement(PlayerId playerId) {
    if (playerId.equals(owner)) {
      if (pawnCount >= 3) {
        return new PawnCell(playerId, 3);
      }
      return new PawnCell(playerId, pawnCount + 1);
    }
    return new PawnCell(playerId, pawnCount);
  }

  @Override
  public Cell claimedBy(PlayerId playerId) {
    return new PawnCell(playerId, pawnCount);
  }
}
