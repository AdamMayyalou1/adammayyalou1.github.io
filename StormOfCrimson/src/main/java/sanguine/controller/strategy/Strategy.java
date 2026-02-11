package sanguine.controller.strategy;

import java.util.List;
import sanguine.model.game.GameState;

/**
 * An interface that represents a possible strategy that a computer player may use.
 */
public interface Strategy {
  /**
   * A method that represents the selection of this strategy.
   *
   * @param state (GameState) the current state of the game.
   * @return (List) a list of moves to be made by the strategy.
   */
  List<Move> choose(GameState state);
}
