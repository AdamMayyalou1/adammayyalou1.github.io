package sanguine.controller.strategy;

import java.util.ArrayList;
import java.util.List;
import sanguine.model.PlayerId;
import sanguine.model.game.GameState;
import sanguine.model.game.Scoring;

/**
 * A class that represents the Maximize Row Score strategy.
 * Chooses a card and location that will allow the current player to win a particular
 * row by making their row-score higher than the opponent's row-score.
 */
public class MaximizeRowScoreStrategy implements Strategy {
  @Override
  public List<Move> choose(GameState state) {
    List<Move> all = MoveGenerator.allValidMoves(state);
    List<int[]> beforeScores = Scoring.rowScores(state.board());
    PlayerId me = state.current();
    PlayerId opp = me.opposite();

    // Map enum to scoring index
    int myIndex  = (me == PlayerId.RED) ? 0 : 1;
    int oppIndex = (opp == PlayerId.RED) ? 0 : 1;

    List<Move> results = new ArrayList<>();

    // Iterate rows from top to bottom
    for (int row = 0; row < beforeScores.size(); row++) {
      int myBefore  = beforeScores.get(row)[myIndex];
      int oppBefore = beforeScores.get(row)[oppIndex];

      // If already winning this row, ignore it
      if (myBefore > oppBefore) {
        continue;
      }

      // Try all moves that affect this row
      for (Move m : all) {
        if (m.pos().row() != row) {
          continue;
        }

        List<int[]> afterScores = Scoring.rowScores(m.nextState().board());
        int myAfter  = afterScores.get(row)[myIndex];
        int oppAfter = afterScores.get(row)[oppIndex];

        // Found a move that gives me a lead
        if (myAfter > oppAfter) {
          results.add(m);
        }
      }

      // If any move improves this row, stop and return them
      if (!results.isEmpty()) {
        return results;
      }
    }

    // No score-improving moves for any row
    return List.of();
  }
}