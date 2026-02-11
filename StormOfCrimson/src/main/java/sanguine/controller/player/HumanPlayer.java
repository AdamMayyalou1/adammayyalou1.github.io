package sanguine.controller.player;

import sanguine.model.PlayerId;
import sanguine.model.game.GameState;
import sanguine.view.ViewCommands;

/**
 * The controller that represents a human player playing the Sanguine game.
 */
public class HumanPlayer implements Player {
  private final PlayerId playerId;

  /**
   * Simple constructor for a human player.
   *
   * @param playerId (PlayerId) the player's color.
   */
  public HumanPlayer(PlayerId playerId) {
    this.playerId = playerId;
  }

  @Override
  public PlayerId id() {
    return playerId;
  }

  @Override
  public void thisTurn(GameState state) {
  }

  @Override
  public void addCommands(ViewCommands commands) {
    
  }
}
