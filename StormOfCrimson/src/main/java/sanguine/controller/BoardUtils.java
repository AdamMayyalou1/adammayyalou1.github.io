package sanguine.controller;

import sanguine.model.Board;
import sanguine.model.Position;

/**
 * A utils class that contains additional helpful methods for Board.
 */
public final class BoardUtils {
  /**
   * Returns a copy of the given board.
   *
   * @param original (Board) the original board.
   * @return (Board) the constructed copy/
   */
  public static Board copyBoard(Board original) {
    Board b = new Board(original.rows(), original.cols());
    for (int r = 0; r < original.rows(); r++) {
      for (int c = 0; c < original.cols(); c++) {
        b.setCell(new Position(r, c), original.getCell(new Position(r, c)));
      }
    }
    return b;
  }
}
