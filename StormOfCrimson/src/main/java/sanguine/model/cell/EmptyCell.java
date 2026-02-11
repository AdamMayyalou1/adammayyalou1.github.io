package sanguine.model.cell;

import sanguine.model.PlayerId;
import sanguine.model.card.Card;

/**
 * A record class that represents an empty cell with no card or pawns.
 */
public record EmptyCell() implements Cell {
  @Override
  public Kind kind() {
    return Kind.EMPTY;
  }

  @Override
  public boolean isOwned() {
    return false;
  }

  @Override
  public PlayerId owner() {
    return null;
  }

  @Override
  public int pawnCount() {
    return 0;
  }

  @Override
  public Card card() {
    return null;
  }

  @Override
  public Cell pawnIncrement(PlayerId playerId) {
    return new PawnCell(playerId, 1);
  }

  @Override
  public Cell claimedBy(PlayerId playerId) {
    return this;
  }
}
