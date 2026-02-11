package sanguine.model;

import sanguine.model.cell.Cell;
import sanguine.model.cell.EmptyCell;

/**
 * The class that represents the board of the Sanguine game.
 * Said board is made up of a 2D array of cells.
 */
public final class Board {
  private final int rows;
  private final int cols;
  private final Cell[][] grid;

  /**
   * Simple constructor for the Board.
   *
   * @param rows (int) the number of rows the board should contain.
   * @param cols (int) the number of columns the board should contain.
   */
  public Board(int rows, int cols) {
    this.rows = rows;
    this.cols = cols;
    this.grid = new Cell[rows][cols];
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        grid[row][col] = new EmptyCell();
      }
    }
  }

  /**
   * The number of rows the board contains.
   *
   * @return (int) the number of rows.
   */
  public int rows() {
    return rows;
  }

  /**
   * The number of columns the board contains.
   *
   * @return (int) the number of columns.
   */
  public int cols() {
    return cols;
  }

  /**
   * Seeds the pawns on the board.
   */
  public void seedPawns() {
    for (int row = 0; row < rows; row++) {
      grid[row][0] = grid[row][0].pawnIncrement(PlayerId.RED);
      grid[row][cols - 1] = grid[row][cols - 1].pawnIncrement(PlayerId.BLUE);
    }
  }

  /**
   * Determines whether the given position is within bounds of the board.
   *
   * @param position (Position) the row, column position given.
   * @return (boolean) whether said position is in bounds.
   */
  public boolean inBounds(Position position) {
    if (position == null) {
      return false;
    }
    int xpos = position.col();
    int ypos = position.row();
    return xpos >= 0 && xpos < cols && ypos >= 0 && ypos < rows;
  }

  /**
   * Obtains the cell at the given position.
   *
   * @param position (Position) the given position.
   * @return (cell) the cell at the position.
   */
  public Cell getCell(Position position) {
    if (!inBounds(position)) {
      throw new IllegalArgumentException("Invalid position");
    }
    return grid[position.row()][position.col()];
  }

  /**
   * Takes in a cell and position, and places that cell at the position of this board.
   *
   * @param position (Position) the position to place the cell at.
   * @param cell (cell) the cell to place on the board.
   */
  public void setCell(Position position, Cell cell) {
    grid[position.row()][position.col()] = cell;
  }

  /**
   * Applies the correct influence mask for the given player around the given position.
   *
   * @param position (Position) the position to apply the influence mask.
   * @param player (PlayerId) the player applying the influence mask.
   */
  public void applyInfluenceMask(Position position, PlayerId player) {
    Cell currentCell = getCell(position);
    switch (currentCell.kind()) {
      case EMPTY, PAWNS -> setCell(position, currentCell.pawnIncrement(player));
      case CARD -> {}
      default -> throw new IllegalStateException("Unexpected value: " + currentCell.kind());
    }
  }

}
