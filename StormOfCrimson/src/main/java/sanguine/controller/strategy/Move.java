package sanguine.controller.strategy;

import sanguine.model.Position;
import sanguine.model.game.GameState;

/**
 * A record that represents a possible move that a player can make.
 *
 * @param handIndex (int) the index belonging to the card used in the move.
 * @param pos (Position) the move's position on the board.
 * @param nextState (GameState) the GameState created by the move.
 */
public record Move(int handIndex, Position pos, GameState nextState) {
}
