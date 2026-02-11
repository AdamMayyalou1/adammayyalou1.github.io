package sanguine.model;


import java.util.List;
import sanguine.model.cell.Cell;
import sanguine.model.deck.Hand;

/**
 * Read Only model for the purpose of the View. contains only the methods that are able to view
 * the model without the functions to mutate the game.
 */
public interface ReadOnlySanguineModel {

  /**
   * Returns the number of Rows.
   *
   * @return number of Rows.
   */
  int rows();

  /**
   * Returns the number of Columns.
   *
   * @return number of Columns.
   */
  int cols();


  /**
   * Returns the current Player of the game.
   *
   * @return current Player
   */
  PlayerId currentPlayer();

  /**
   * Returns the current board.
   *
   * @return the board
   */
  Board board();


  /**
   * Takes a player and returns their current hand.
   *
   * @param p the given player
   * @return the hand of the player p
   */
  Hand handOf(PlayerId p);

  /**
   * Method to see whether the current state of the game is over or not.
   *
   * @return true if the game is over, false otherwise.
   */
  boolean isGameOver();

  /**
   * Find the current score of each row given as a list of Rows of {RedScore, BlueScore}.
   *
   * @return the List of Row Score for each player for each Row.
   */
  List<int[]> rowScores();

  /**
   * Finds the total score based on the instructions given. For each row the player with the
   * highest row score wins.
   *
   * @param player the given Player
   * @return an int given as the total score based on the given instructions
   */
  int totalScore(PlayerId player);

  // New Methods to add based on Checklist

  /**
   * Gives back the winner of the game if the game is over.
   *
   * @return the Winner or Null if there is no winner.
   */
  PlayerId winner();

  /**
   * Given a player's card in the hand and a position on the board, the model assesses whether
   * the move is valid or not.
   *
   * @param cardIndex The index of the card in the player's hand.
   * @param position The position on the board to place the card.
   * @return a boolean if the move is legal or not
   */
  boolean isLegalMove(int cardIndex, Position position);

  /**
   * Checks a cell on the board and returns the given cell.
   *
   * @param position The position on the board to inspect.
   * @return The cell at the given position or null if the position is invalid
   */
  Cell cellAt(Position position);

  /**
   * Gives the owner of the cell on the board. Also checks if there is an owner.
   *
   * @param position The position of the board to inspect.
   * @return The owner of the given position or null if no owner
   */
  PlayerId ownerAt(Position position);





}
