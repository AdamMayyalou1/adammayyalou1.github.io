package sanguine.controller;

import sanguine.model.PlayerId;

/**
 * Listen and responds to the model status during a Sanguine game.
 */
public interface ModelStatusListener {
  /**
   * Responds to a turn changing during the game.
   *
   * @param currentPlayer (PlayerId) the current player.
   */
  void onTurnChanged(PlayerId currentPlayer);

  /**
   * Responds to the Sanguine game ending.
   *
   * @param winner (PlayerId) the player that won.
   * @param redTotal (int) the red player's score.
   * @param blueTotal (int) the blue player's score.
   */
  void onGameOver(PlayerId winner, int redTotal, int blueTotal);
}
