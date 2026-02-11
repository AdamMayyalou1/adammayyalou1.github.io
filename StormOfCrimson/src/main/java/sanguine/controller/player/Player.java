package sanguine.controller.player;

import sanguine.model.PlayerId;
import sanguine.model.game.GameState;
import sanguine.view.ViewCommands;

/**
 * An interface that represents a type of player that could be playing
 * the Sanguine game.
 */
public interface Player {

  /**
   * Getter for the player's color.
   *
   * @return (PlayerId) the player's color.
   */
  PlayerId id();

  /**
   * The player decides what they are doing this turn (primarily for the machine player).
   * The machine player will determine their commands to follow and strategy here/
   *
   * @param state (GameState) the game's current state this turn.
   */
  void thisTurn(GameState state);

  /**
   * Primarily for the machine player. Adds the given commands to their commands.
   *
   * @param commands (ViewCommands) the given commands.
   */
  void addCommands(ViewCommands commands);

}
