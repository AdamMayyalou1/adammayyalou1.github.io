package testmodel.testcell;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static sanguine.model.PlayerId.BLUE;
import static sanguine.model.PlayerId.RED;
import static sanguine.model.cell.Cell.Kind.CARD;
import static sanguine.model.cell.Cell.Kind.EMPTY;
import static sanguine.model.cell.Cell.Kind.PAWNS;

import org.junit.Before;
import org.junit.Test;
import sanguine.model.card.BaseCard;
import sanguine.model.card.InfluenceMask;
import sanguine.model.cell.CardCell;
import sanguine.model.cell.Cell;
import sanguine.model.cell.EmptyCell;
import sanguine.model.cell.PawnCell;

/**
 * A class that tests all the methods for the classes that implement the cell interface.
 */
public class TestCell {
  Cell empty;
  Cell pawn1;
  Cell pawn2;
  Cell pawn3;
  Cell card;
  BaseCard security;

  /**
   * Simple setup before each test.
   */
  @Before
  public void setUp() {
    empty = new EmptyCell();
    pawn1 = new PawnCell(BLUE, 1);
    pawn2 = new PawnCell(RED, 2);
    pawn3 = new PawnCell(RED, 3);

    boolean[][] maskGrid = new boolean[5][5];
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        maskGrid[row][col] = false;
      }
    }
    maskGrid[1][2] = true;
    maskGrid[2][1] = true;
    maskGrid[2][3] = true;
    maskGrid[3][2] = true;
    InfluenceMask mask = new InfluenceMask(maskGrid);
    security = new BaseCard("Security", 1, 1, mask);
    card = new CardCell(RED, security);
  }

  @Test
  public void testKind() {
    assertEquals(EMPTY, empty.kind());
    assertEquals(PAWNS, pawn1.kind());
    assertEquals(PAWNS, pawn2.kind());
    assertEquals(PAWNS, pawn3.kind());
    assertEquals(CARD, card.kind());
  }

  @Test
  public void testOwner() {
    assertNull(empty.owner());
    assertEquals(BLUE, pawn1.owner());
    assertEquals(RED, pawn2.owner());
    assertEquals(RED, pawn3.owner());
    assertEquals(RED, card.owner());
  }

  @Test
  public void testIsOwned() {
    assertFalse(empty.isOwned());
    assertTrue(pawn1.isOwned());
    assertTrue(pawn2.isOwned());
    assertTrue(pawn3.isOwned());
    assertTrue(card.isOwned());
  }

  @Test
  public void testPawnCount() {
    assertEquals(0, empty.pawnCount());
    assertEquals(1, pawn1.pawnCount());
    assertEquals(2, pawn2.pawnCount());
    assertEquals(3, pawn3.pawnCount());
    assertEquals(0, card.pawnCount());
  }

  @Test
  public void testCard() {
    assertNull(empty.card());
    assertNull(pawn1.card());
    assertNull(pawn2.card());
    assertNull(pawn3.card());
    assertEquals(security, card.card());
  }

  @Test
  public void testPawnIncrement() {
    assertEquals(new PawnCell(RED, 1), empty.pawnIncrement(RED));
    assertEquals(card, card.pawnIncrement(BLUE));

    assertEquals(new PawnCell(RED, 1), pawn1.pawnIncrement(RED));
    assertEquals(new PawnCell(RED, 3), pawn2.pawnIncrement(RED));
    assertEquals(new PawnCell(RED, 3), pawn3.pawnIncrement(RED));
  }

  @Test
  public void testClaimedBy() {
    assertEquals(empty, empty.claimedBy(RED));
    assertEquals(card, card.claimedBy(RED));

    assertEquals(pawn1, pawn1.claimedBy(BLUE));
    assertEquals(new PawnCell(BLUE, 2), pawn2.claimedBy(BLUE));
    assertEquals(new PawnCell(RED, 3), pawn3.claimedBy(RED));
  }
}
