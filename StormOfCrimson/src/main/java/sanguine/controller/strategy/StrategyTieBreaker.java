package sanguine.controller.strategy;

import java.util.Comparator;
import java.util.List;

/**
 * A class that breaks ties brought about by differing strategies.
 */
public final class StrategyTieBreaker {
  /**
   * Picks the best strategy of the given list.
   *
   * @param moves (List) a list of possible moves to perform.
   * @return (Move) the best possible move to perform.
   */
  public static Move pickBest(List<Move> moves) {
    return moves.stream()
        .min(Comparator
            .comparingInt((Move m) -> m.pos().row())
            .thenComparingInt(m -> m.pos().col())
            .thenComparingInt(Move::handIndex))
        .orElse(null);
  }
}