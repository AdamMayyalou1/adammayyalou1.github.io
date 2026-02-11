package testmodel.testcell;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import sanguine.model.card.BaseCard;
import sanguine.model.card.Card;
import sanguine.model.card.InfluenceMask;
import sanguine.model.deck.Hand;

/**
 * A class for testing Hand and its associated methods.
 */
public class TestHand {

  private Card card1;
  private Card card2;
  private Card card3;
  private Hand emptyHand;
  private Hand oneCardHand;

  /**
   * Simple setup before each test.
   */
  @Before
  public void setUp() {
    boolean[][] maskGrid = new boolean[5][5];
    maskGrid[2][2] = true;
    InfluenceMask mask = new InfluenceMask(maskGrid);

    card1 = new BaseCard("Card1", 1, 1, mask);
    card2 = new BaseCard("Card2", 2, 2, mask);
    card3 = new BaseCard("Card3", 3, 3, mask);

    emptyHand = new Hand(new ArrayList<>());
    oneCardHand = new Hand(List.of(card1));
  }

  @Test
  public void testSize() {
    assertEquals(0, emptyHand.size());
    assertEquals(1, oneCardHand.size());
  }

  @Test
  public void testAddCard() {
    Hand newHand = oneCardHand.add(card2);
    assertEquals(1, oneCardHand.size());
    assertEquals(2, newHand.size());
    assertTrue(newHand.cards().contains(card1));
    assertTrue(newHand.cards().contains(card2));
  }

  @Test
  public void testRemoveCard() {
    Hand handWithTwo = oneCardHand.add(card2);
    Hand newHand = handWithTwo.remove(card1);
    assertEquals(2, handWithTwo.size());
    assertEquals(1, newHand.size());
    assertTrue(newHand.cards().contains(card2));
    assertFalse(newHand.cards().contains(card1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveCardNotInHand() {
    emptyHand.remove(card1);
  }

  @Test
  public void testMultipleOperations() {
    Hand h1 = emptyHand.add(card1).add(card2);
    Hand h2;
    h2 = h1.remove(card1).add(card3);

    assertEquals(2, h1.size());
    assertTrue(h1.cards().contains(card1));
    assertTrue(h1.cards().contains(card2));

    assertEquals(2, h2.size());
    assertFalse(h2.cards().contains(card1));
    assertTrue(h2.cards().contains(card2));
    assertTrue(h2.cards().contains(card3));
  }
}