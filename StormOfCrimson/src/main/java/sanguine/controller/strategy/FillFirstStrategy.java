package sanguine.controller.strategy;

import java.util.List;
import sanguine.model.game.GameState;

/**
 * A class that represents the Fill First strategy.
 * Chooses the first card and location that can be played on and plays there.
 */
public class FillFirstStrategy implements Strategy {
  @Override
  public List<Move> choose(GameState state) {
    List<Move> moves = MoveGenerator.allValidMoves(state);
    return moves.isEmpty()
        ? List.of()
        : List.of(moves.getFirst());
  }
}