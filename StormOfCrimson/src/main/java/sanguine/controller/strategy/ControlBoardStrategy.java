package sanguine.controller.strategy;

import java.util.ArrayList;
import java.util.List;
import sanguine.model.Board;
import sanguine.model.PlayerId;
import sanguine.model.Position;
import sanguine.model.cell.Cell;
import sanguine.model.game.GameState;

/**
 * A class that represents the Control Board strategy.
 * Chooses a card and location that will give the current
 * player ownership of the most cells.
 */
public class ControlBoardStrategy implements Strategy {

  @Override
  public List<Move> choose(GameState state) {
    List<Move> moves = MoveGenerator.allValidMoves(state);
    if (moves.isEmpty()) {
      return List.of();
    }

    PlayerId me = state.current();

    int best = -1;
    List<Move> bestMoves = new ArrayList<>();

    for (Move m : moves) {
      int score = countOwnedCells(m.nextState().board(), me);

      if (score > best) {
        best = score;
        bestMoves.clear();
        bestMoves.add(m);
      } else if (score == best) {
        bestMoves.add(m);
      }
    }

    return bestMoves;
  }

  private int countOwnedCells(Board b, PlayerId me) {
    int count = 0;
    for (int r = 0; r < b.rows(); r++) {
      for (int c = 0; c < b.cols(); c++) {
        Cell cell = b.getCell(new Position(r, c));
        if (cell.owner() == me) {
          count++;
        }
      }
    }
    return count;
  }
}