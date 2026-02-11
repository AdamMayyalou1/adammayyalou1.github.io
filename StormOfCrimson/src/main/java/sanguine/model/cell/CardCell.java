package sanguine.model.cell;

import sanguine.model.PlayerId;
import sanguine.model.card.Card;

/**
 * A record class that represents a cell containing a placed card.
 *
 * @param owner (PlayerId) the player who owns this cell.
 * @param card (card) the card placed on the cell.
 */
public record CardCell(PlayerId owner, Card card) implements Cell {

  /**
   * Checks for invalid inputs when constructing the tile.
   *
   * @param owner (PlayerId) the player who owns this cell.
   * @param card (card) the card placed on the cell.
   */
  public CardCell {
    if (owner == null || card == null) {
      throw new IllegalArgumentException("inputs cannot be null");
    }
  }

  @Override
  public Kind kind() {
    return Kind.CARD;
  }

  @Override
  public boolean isOwned() {
    return true;
  }

  @Override
  public int pawnCount() {
    return 0;
  }

  @Override
  public Cell pawnIncrement(PlayerId playerId) {
    return this;
  }

  @Override
  public Cell claimedBy(PlayerId playerId) {
    return this;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(card.name()).append("\n");
    builder.append("Cost: ").append(card.cost()).append("\n");
    builder.append("Value: ").append(card.value()).append("\n");
    int[][] mask = card.influenceMask().reverseFor(owner);

    boolean[][] grid = new boolean[5][5];
    for (int[] offset : mask) {
      int row = offset[0] + 2;
      int col = offset[1] + 2;
      grid[row][col] = true;
    }

    for (int row = 0; row < grid.length; row++) {
      for (int col = 0; col < grid.length; col++) {
        if (row == 2 && col == 2) {
          builder.append("C");
        } else if (grid[row][col]) {
          builder.append("I");
        } else {
          builder.append("X");
        }
      }
      builder.append("\n");
    }
    return builder.toString();
  }
}
