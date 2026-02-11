package sanguine.model;

//import java.util.List;

//import sanguine.model.deck.Hand;

import sanguine.controller.ModelStatusListener;
import sanguine.model.game.GameState;

/**
 * Interface for the implementations of Sanguine Model. Holds all the necessary data and
 * move set for the GUI and the Controller.
 */
public interface SanguineModel extends ReadOnlySanguineModel {


  /**
   * Start the given game and prepare for User input.
   */
  void start();

  /**
   * Pass the turn to the next player.
   */
  void pass();

  /**
   * Attempts to place the card in the given destination from the current player's hand.
   *
   * @param handIndex The index of the card wanted to play.
   * @param destination The destination of the card to place.
   */
  void placeCard(int handIndex, Position destination);

  /**
   * Getter that returns the state of the game.
   *
   * @return (GameState) the current state of the game.
   */
  GameState getState();

  /**
   * Adds a ModelStatusListener to the model.
   *
   * @param listener (ModelStatusListener) the given listener.
   */
  void addModelStatusListener(ModelStatusListener listener);
}
