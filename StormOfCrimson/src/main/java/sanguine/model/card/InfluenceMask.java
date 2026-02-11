package sanguine.model.card;

import java.util.Arrays;
import sanguine.model.PlayerId;

/**
 * An influence mask is a grid of booleans that represent the tiles that
 * its owner influences around it. Every true boolean is a tile that
 * is influenced by this InfluenceMask.
 */
public final class InfluenceMask {
  private final boolean[][] mask;

  /**
   * A simple constructor for the InfluenceMask.
   *
   * @param mask (boolean[][]) a 2D array that represents the influenced tiles.
   */
  public InfluenceMask(boolean[][] mask) {
    if (mask.length != 5) {
      throw new IllegalArgumentException();
    }
    for (boolean[] row : mask) {
      if (row.length != 5) {
        throw new IllegalArgumentException();
      }
    }

    this.mask = deepCopy(mask);
  }

  /**
   * A method that reverses the influenceMask for each player's perspective.
   *
   * @param player (PlayerId) an enum that represents the red or blue player.
   * @return (int[][]) a 2D array with 1s for each true boolean offset in the center.
   */
  public int[][] reverseFor(PlayerId player) {
    // reverses the influenceMask for each player's perspective
    int center = 2;

    int count = 0;
    for (int r = 0; r < 5; r++) {
      for (int c = 0; c < 5; c++) {
        if (mask[r][c]) {
          count++;
        }
      }
    }

    int[][] offsets = new int[count][2];
    int index = 0;

    for (int r = 0; r < 5; r++) {
      for (int c = 0; c < 5; c++) {
        if (mask[r][c]) {
          int dr = r - center;
          int dc = c - center;

          // If the player is BLUE, mirror
          if (player == PlayerId.BLUE) {
            dc = -dc;
          }

          offsets[index][0] = dr;
          offsets[index][1] = dc;
          index++;
        }
      }
    }
    return offsets;
  }

  /**
   * Produces a deep copy of the original influence grid.
   *
   * @param original (boolean[][]) the original influence grid.
   * @return (boolean[][]) a deep copy of the same influence grid.
   */
  private static boolean[][] deepCopy(boolean[][] original) {

    final boolean[][] result = new boolean[original.length][];
    for (int i = 0; i < original.length; i++) {
      result[i] = Arrays.copyOf(original[i], original[i].length);
    }
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof InfluenceMask other)) {
      return false;
    }
    return Arrays.deepEquals(mask, other.mask);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(mask);
  }
}
