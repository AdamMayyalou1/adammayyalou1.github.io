package testmodel.testcell;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import sanguine.model.card.BaseCard;
import sanguine.model.card.InfluenceMask;
import sanguine.model.deck.Deck;

/**
 * A class for testing deck and its associated methods.
 */
public class TestDeck {
  BaseCard card1;
  BaseCard card2;
  BaseCard card3;
  Deck deckSingle;
  Deck deckMultiple;
  Deck emptyDeck;

  /**
   * Simple setup before each test.
   */
  @Before
  public void setup() {
    boolean[][] maskGrid = new boolean[5][5];
    maskGrid[2][2] = true;
    InfluenceMask mask = new InfluenceMask(maskGrid);

    card1 = new BaseCard("Security", 1, 1, mask);
    card2 = new BaseCard("Queen", 1, 2, mask);
    card3 = new BaseCard("Crab", 2, 3, mask);

    deckSingle = new Deck(List.of(card1));
    deckMultiple = new Deck(List.of(card1, card2, card3));
    emptyDeck = new Deck(new ArrayList<>());
  }

  @Test
  public void testSize() {
    assertEquals(0, emptyDeck.size());
    assertEquals(1, deckSingle.size());
    assertEquals(3, deckMultiple.size());
  }

  @Test
  public void testIsEmpty() {
    assertTrue(emptyDeck.isEmpty());
    assertFalse(deckSingle.isEmpty());
    assertFalse(deckMultiple.isEmpty());
  }

  @Test
  public void testPeek() {
    assertEquals(card1, deckMultiple.peek());
    assertEquals(card1, deckSingle.peek());
    assertNull(emptyDeck.peek());
  }

  @Test
  public void testPop() {
    Deck popped = deckMultiple.pop();
    assertEquals(2, popped.size());
    assertEquals(card3, popped.peek());

    Deck poppedOnce = deckSingle.pop();
    assertNotNull(poppedOnce);
    assertTrue(poppedOnce.isEmpty());

    assertNull(emptyDeck.pop());
  }

  @Test
  public void testDraw() {
    Deck.Drawn drawn = deckMultiple.draw();

    assertEquals(card1, drawn.card());
    assertEquals(2, drawn.deck().size());
    assertEquals(card3, drawn.deck().peek());
  }

  @Test
  public void testEqualsAndHashCode() {
    Deck another = new Deck(List.of(card1, card2, card3));
    Deck reversed = new Deck(List.of(card3, card2, card1));

    assertEquals(deckMultiple, another);
    assertEquals(deckMultiple.hashCode(), another.hashCode());

    assertNotEquals(deckMultiple, reversed);
    assertNotEquals(deckMultiple.hashCode(), reversed.hashCode());

    assertNotEquals(deckMultiple, deckSingle);
    assertNotEquals(null, deckMultiple);
  }
}