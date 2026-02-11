package sanguine.model;

/**
 * A player id that represents whether the player is red or blue.
 */
public enum PlayerId {
  RED, BLUE;

  /**
   * Produces the player that is opposite of this player.
   *
   * @return (PlayerId) the opposing player.
   */
  public PlayerId opposite() {
    if (this == BLUE) {
      return RED;
    } else {
      return BLUE;
    }
  }
}
