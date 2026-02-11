package sanguine.controller.strategy;

import java.util.ArrayList;
import java.util.List;
import sanguine.model.PlayerId;
import sanguine.model.game.GameState;
import sanguine.model.game.Scoring;

/**
 * A class that represents the Minimax strategy.
 * Chooses the move that leaves their opponent in a situation with no good moves.
 */
public class MinimaxStrategy implements Strategy {

  private final int depth;
  private final Strategy opponentStrategy;

  /**
   * A simple constructor for the minimax strategy.
   *
   * @param depth (int).
   * @param opponentStrategy (Strategy) the estimated guess of the opponent's strategy.
   */
  public MinimaxStrategy(int depth, Strategy opponentStrategy) {
    this.depth = depth;
    this.opponentStrategy = opponentStrategy;
  }

  @Override
  public List<Move> choose(GameState state) {
    PlayerId me = state.current();
    Result best = minimax(state, depth, me);

    // Return ALL moves tied for best score.
    return best.bestMoves;
  }

  private record Result(int score, List<Move> bestMoves) {
  }

  private int evaluate(GameState state, PlayerId rootPlayer) {
    return Scoring.total(state.board(), rootPlayer)
        - Scoring.total(state.board(), rootPlayer.opposite());
  }

  private Result minimax(GameState state, int depthLeft, PlayerId rootPlayer) {

    // Terminal condition
    List<Move> moves = MoveGenerator.allValidMoves(state);
    if (depthLeft == 0 || moves.isEmpty()) {
      int score = evaluate(state, rootPlayer);
      return new Result(score, List.of());
    }

    PlayerId current = state.current();
    boolean maximizing = (current == rootPlayer);

    int bestScore = maximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
    List<Move> bestMoves = new ArrayList<>();

    for (Move m : moves) {

      GameState next = m.nextState();

      Result r;
      if (depthLeft == 1) {
        int score = evaluate(next, rootPlayer);
        r = new Result(score, List.of());
      } else {
        r = minimax(next, depthLeft - 1, rootPlayer);
      }

      int score = r.score;

      // Maximizer
      if (maximizing) {
        if (score > bestScore) {
          bestScore = score;
          bestMoves.clear();
          bestMoves.add(m);
        } else if (score == bestScore) {
          bestMoves.add(m);
        }

        // Minimizer
      } else {
        if (score < bestScore) {
          bestScore = score;
          bestMoves.clear();
          bestMoves.add(m);
        } else if (score == bestScore) {
          bestMoves.add(m);
        }
      }
    }

    return new Result(bestScore, bestMoves);
  }
}