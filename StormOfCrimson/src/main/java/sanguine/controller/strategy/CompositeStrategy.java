package sanguine.controller.strategy;

import java.util.List;
import sanguine.model.game.GameState;

/**
 * A class that represents the Composite strategy.
 * Combines multiple strategies into a single one.
 */
public class CompositeStrategy implements Strategy {

  private final List<Strategy> chain;

  /**
   * Constructs a chain of strategies.
   *
   * @param strategies (Strategy) takes in a strategy and constructs a list.
   */
  public CompositeStrategy(List<Strategy> strategies) {
    this.chain = strategies;
  }

  @Override
  public List<Move> choose(GameState state) {
    for (Strategy s : chain) {
      List<Move> moves = s.choose(state);
      if (!moves.isEmpty()) {
        return moves;
      }
    }
    return List.of();
  }
}