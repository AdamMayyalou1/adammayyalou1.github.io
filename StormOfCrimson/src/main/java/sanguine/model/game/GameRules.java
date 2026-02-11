package sanguine.model.game;

/**
 * GameRules class that has one static class to validate the GameState before initialization
 * of fields into the GameState Record. This makes sure that these invariants are kept true
 * during construction.
 */
public class GameRules {


  /**
   * Static method validateGame has one purpose which is to validate that the game is legal
   * during initialization. This checks rows, columns, deck size and the start hand before the game
   * is started.
   *
   * @param rows Rows to add to board.
   * @param cols Columns to add to board.
   * @param deckSize Size of deck to add to deck.
   * @param startHand Size of the hand for each player.
   */
  public static void validateGame(int rows, int cols, int deckSize, int startHand) {
    if (rows <= 0 || cols <= 0 || cols % 2 == 0) {
      throw new IllegalArgumentException("Invalid rows and cols values");
    }
    if (deckSize < rows * cols) {
      throw new IllegalArgumentException("deck is smaller than grid");
    }
    if (startHand <= 0 || startHand > deckSize / 3) {
      throw new IllegalArgumentException("Invalid starting hand size");
    }
  }
}
