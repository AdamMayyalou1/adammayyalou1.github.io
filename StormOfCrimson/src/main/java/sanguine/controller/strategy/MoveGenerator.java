package sanguine.controller.strategy;

import java.util.ArrayList;
import java.util.List;
import sanguine.controller.BoardUtils;
import sanguine.model.Board;
import sanguine.model.PlayerId;
import sanguine.model.Position;
import sanguine.model.deck.Hand;
import sanguine.model.game.GameState;
import sanguine.model.game.RuleKeeper;

/**
 * A class that determines every legal move that can be made in a GameState through simulation.
 */
public final class MoveGenerator {

  /**
   * Simulates placing a card on the board to ensure it is a legal move.
   *
   * @param state (GameState) current state of the game.
   * @param handIndex (int) the index of the card for the move.
   * @param pos (Position) the position of the move on the board.
   * @return (GameState) the new GameState created by this legal move.
   */
  public static GameState simulatePlace(GameState state, int handIndex, Position pos) {
    Board copy = BoardUtils.copyBoard(state.board());
    GameState tmp = new GameState(
        copy,
        state.redDeck(), state.redHand(),
        state.blueDeck(), state.blueHand(),
        state.current(),
        state.consecutiveSkips()
    );

    tmp = RuleKeeper.place(tmp, handIndex, pos);
    tmp = RuleKeeper.turnTryDraw(tmp);
    return tmp;
  }

  /**
   * Returns all possible legal moves that a player can make.
   *
   * @param state (GameState) the game's current state.
   * @return (List) a list of possible moves.
   */
  public static List<Move> allValidMoves(GameState state) {
    List<Move> result = new ArrayList<>();
    PlayerId player = state.current();
    Hand hand = (player == PlayerId.RED) ? state.redHand() : state.blueHand();

    for (int cardIndex = 0; cardIndex < hand.size(); cardIndex++) {
      for (int r = 0; r < state.board().rows(); r++) {
        for (int c = 0; c < state.board().cols(); c++) {
          Position pos = new Position(r, c);
          try {
            GameState next = simulatePlace(state, cardIndex, pos);
            result.add(new Move(cardIndex, pos, next));
          } catch (IllegalArgumentException ignored) {
            // Not a legal move, skip
          }
        }
      }
    }

    return result;
  }
}