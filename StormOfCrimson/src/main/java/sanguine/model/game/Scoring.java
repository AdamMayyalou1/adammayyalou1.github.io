package sanguine.model.game;

import java.util.ArrayList;
import java.util.List;
import sanguine.model.Board;
import sanguine.model.PlayerId;
import sanguine.model.Position;
import sanguine.model.cell.Cell;

/**
 * Class to calculate the scoring mechanism of the game. Has two static methods, one to find the
 * total score necessary at the end of the game. The second to assess the row scores for each row.
 */
public class Scoring {

  /**
   * Gets every row score for the given board for both players. Returns it in a specific format
   * valuable to the row indices.
   *
   * @param board The board to find the score.
   * @return Returns a List of a set of integers {RedScore, BlueScore} with each index of the list
   *     being a different row. This allows for the same scoring guidelines as the instructions.
   */
  public static List<int[]> rowScores(Board board) {
    List<int[]> scores = new ArrayList<>();
    for (int row = 0; row < board.rows(); row++) {
      int redScore = 0;
      int blueScore = 0;
      for (int col = 0; col < board.cols(); col++) {
        Cell currentCell = board.getCell(new Position(row, col));
        if (currentCell.kind() == Cell.Kind.CARD) {
          int value = currentCell.card().value();
          if (currentCell.owner() == PlayerId.BLUE) {
            blueScore += value;
          } else if (currentCell.owner() == PlayerId.RED) {
            redScore += value;
          }
        }
      }
      scores.add(new int[]{redScore, blueScore});
    }
    return scores;
  }

  /**
   * Finds the total score based on the instructions for the assignment where the player with the
   * higher score for each row adds that to their total score. If even then the score is 0.
   *
   * @param board The board to calculate the score of.
   * @param player The player in which to score.
   * @return The total score for that player.
   */
  public static int total(Board board, PlayerId player) {
    int total = 0;
    List<int[]> rowScores = rowScores(board);
    for (int[] rowScore : rowScores) {
      int redScore = rowScore[0];
      int blueScore = rowScore[1];
      if (redScore > blueScore && player == PlayerId.RED) {
        total += redScore;
      } else if (redScore < blueScore && player == PlayerId.BLUE) {
        total += blueScore;
      }
    }
    return total;
  }
}
