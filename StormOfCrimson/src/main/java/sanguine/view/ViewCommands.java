package sanguine.view;

import sanguine.model.Position;

/**
 * Interface to identify the Publisher Subscriber relationship between the View and the Controller
 * allows the view to publish events and the controller to make decisions.
 */
public interface ViewCommands {

  /**
   * Command to handle what happens to the model based on the User's input to the view.
   * Specifically, which card is clicked.
   *
   * @param cardIndex The card index of the card clicked
   */
  void cardClicked(int cardIndex);

  /**
   * Command to handle what happens to the model based on the User's input to the view.
   * Specifically, which cell is clicked.
   *
   * @param position Position (x, y) of the cell clicked.
   */
  void cellClicked(Position position);


  /**
   * Command to handle what happens to the model based on the User's input to the view.
   * Specifically, confirming the current move based on the cell and card clicked.
   */
  void confirmMove();

  /**
   * Command to handle what happens to the model based on the User's input to the view.
   * Specifically, passing the turn to the next player.
   */
  void pass();
}
