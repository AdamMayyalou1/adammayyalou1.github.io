package sanguine.model;

/**
 * The row, column coordinate position on a board.
 *
 * @param row (int) the row of the position.
 * @param col (int) the column of the position.
 */
public record Position(int row, int col) {
  /**
   * Constructor for a position.
   *
   * @param row (int) the row of the position.
   * @param col (int) the column of the position.
   */
  public Position {
    if (row < 0 || col < 0) {
      throw new IllegalArgumentException("Negative Position");
    }
  }
}
