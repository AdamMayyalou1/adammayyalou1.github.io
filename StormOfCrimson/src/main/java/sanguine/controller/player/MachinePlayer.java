package sanguine.controller.player;

import java.util.List;
import sanguine.controller.strategy.Move;
import sanguine.controller.strategy.Strategy;
import sanguine.model.PlayerId;
import sanguine.model.game.GameState;
import sanguine.view.ViewCommands;

/**
 * The controller that represents the machine player during a Sanguine game.
 */
public class MachinePlayer implements Player {
  private final PlayerId playerId;
  private final Strategy strategy;
  private ViewCommands commands;

  /**
   * Simple constructor for the machine player.
   *
   * @param playerId (PlayerId) the player's color.
   * @param strategy (Strategy) the machine player's strategy to follow.
   */
  public MachinePlayer(PlayerId playerId, Strategy strategy) {
    this.playerId = playerId;
    this.strategy = strategy;
  }
  
  @Override
  public PlayerId id() {
    return playerId;
  }

  @Override
  public void thisTurn(GameState state) {
    if (commands == null) {
      return;
    }
    List<Move> moves = strategy.choose(state);

    if (moves.isEmpty()) {
      commands.pass();
    } else {
      Move best = moves.getFirst();
      commands.cardClicked(best.handIndex());
      commands.cellClicked(best.pos());
      commands.confirmMove();
    }

  }

  @Override
  public void addCommands(ViewCommands commands) {
    this.commands = commands;
  }
}
