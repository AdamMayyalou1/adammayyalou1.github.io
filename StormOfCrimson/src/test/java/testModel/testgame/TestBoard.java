package testmodel.testgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.File;
import org.junit.Before;
import org.junit.Test;
import sanguine.model.Board;
import sanguine.model.Position;
import sanguine.model.deck.Deck;
import sanguine.model.deck.DeckLoader;

/**
 * A class for testing Board and its associated methods.
 */
public class TestBoard {

  private Deck deck;
  private Board board;

  /**
   * A simple setup for each test.
   */
  @Before
  public void setUp() {
    String path = "docs" + File.separator + "example.deck";
    File config = new File(path);
    deck = DeckLoader.readConfig(config);
    board = new Board(3, 3);
  }

  @Test
  public void testInBoundMethod() {
    Position pos = null;
    assertFalse(board.inBounds(pos));
    Position pos2 = new Position(0, 0);
    assertTrue(board.inBounds(pos2));
  }

  @Test
  public void testGetCell() {
    Position pos = new Position(0, 0);
    assertEquals(board.getCell(pos), board.getCell(pos));

    Position pos2 = new Position(5, 5);
    assertThrows(IllegalArgumentException.class, () -> board.getCell(pos2));
  }
}
