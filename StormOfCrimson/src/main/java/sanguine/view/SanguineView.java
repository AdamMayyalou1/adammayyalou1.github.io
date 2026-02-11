package sanguine.view;

import sanguine.model.PlayerId;

/**
 * Interface for the Publisher view with the identifying commands and necessary methods to update
 * the view.
 */
public interface SanguineView {

  /**
   * Setter to add the necessary commands to control the changes in view based on the controller.
   *
   * @param commands Commands Implementation to add to the Publisher.
   */
  void addCommands(ViewCommands commands);

  /**
   * Refreshes the view for the next move.
   */
  void refresh();

  /**
   * Makes the view visible at the start of the game.
   */
  void makeVisible();

  /**
   * Highlights the selected card.
   *
   * @param index (Integer) the selected card's position.
   */
  void highlightCard(Integer index);  // null = clear

  /**
   * Highlights the selected cell.
   *
   * @param row (Integer) the selected cell's row.
   * @param col (Integer) the selected cell's column.
   */
  void highlightCell(Integer row, Integer col); // nulls = clear

  /**
   * Clears any elements that are highlighted.
   */
  void clearHighlights();

  /**
   * Simple setter that determines whether a turn is active.
   *
   * @param active (boolean) whether the turn is active.
   */
  void setTurnActive(boolean active); // maybe gray out / change title

  /**
   * Displays the given missage as an error message.
   *
   * @param message (String) the given error message.
   */
  void showError(String message);

  /**
   * Displays the game over screen with the condition of the game at the end.
   *
   * @param winner (PlayerId) the player that won the game.
   * @param redScore (int) the red player's score.
   * @param blueScore (int) the blue player's score.
   */
  void showGameOver(PlayerId winner, int redScore, int blueScore);


}
