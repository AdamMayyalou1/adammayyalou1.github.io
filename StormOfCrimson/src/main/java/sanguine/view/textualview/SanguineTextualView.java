package sanguine.view.textualview;

import java.io.IOException;
import java.util.List;
import sanguine.model.BasicSanguineModel;
import sanguine.model.Board;
import sanguine.model.Position;
import sanguine.model.cell.Cell;

/**
 * A class that represents a textual view of a Sanguine Queen's Blood game.
 */
public class SanguineTextualView implements TextualView {
  private final BasicSanguineModel model;
  private final Appendable appendable;

  /**
   * Constructs a textual view of the given Sanguine model.
   *
   * @param model The Sanguine model given to the textual view
   */
  public SanguineTextualView(BasicSanguineModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
    this.appendable = new StringBuilder();
  }

  @Override
  public void render() throws IOException {
    StringBuilder sb = new StringBuilder();

    Board board = model.board();
    List<int[]> scores = model.rowScores();

    for (int row = 0; row < board.rows(); row++) {
      // left-side red score
      sb.append(scores.get(row)[0]).append(" ");

      for (int col = 0; col < board.cols(); col++) {
        Cell cell = board.getCell(new Position(row, col));

        switch (cell.kind()) {
          case CARD -> {
            switch (cell.owner()) {
              case RED -> sb.append("R");
              case BLUE -> sb.append("B");
              default -> sb.append("X");
            }
          }
          case PAWNS -> {
            int count = cell.pawnCount();
            sb.append(count > 0 ? count : "_");
          }
          case EMPTY -> sb.append("_");
          default -> throw new IllegalStateException("Unexpected value: " + cell.kind());
        }
      }
      // right-side blue score
      sb.append(" ").append(scores.get(row)[1]).append("\n");
    }

    appendable.append(sb.toString());
    System.out.print(sb);
  }
}
