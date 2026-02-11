package testmodel.testcell;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;
import sanguine.model.card.BaseCard;
import sanguine.model.card.InfluenceMask;

/**
 * A class for testing BaseCard and its associated methods.
 */
public class TestBaseCard {
  BaseCard dragon;
  BaseCard turtle;
  boolean[][] maskGridDragon;
  boolean[][] maskGridTurtle;

  /**
   * Simple setup before each test.
   */
  @Before
  public void setUp() {
    maskGridDragon = new boolean[5][5];
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        maskGridDragon[row][col] = false;
      }
    }
    maskGridDragon[2][3] = true;
    InfluenceMask dragonMask = new InfluenceMask(maskGridDragon);
    dragon = new BaseCard("dragon", 1, 1, dragonMask);

    maskGridTurtle = new boolean[5][5];
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        maskGridTurtle[row][col] = false;
      }
    }
    maskGridTurtle[2][3] = true;
    maskGridTurtle[3][2] = true;
    maskGridTurtle[1][2] = true;
    maskGridTurtle[2][1] = true;
    InfluenceMask turtleMask = new InfluenceMask(maskGridTurtle);
    turtle = new BaseCard("turtle", 1, 2, turtleMask);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullException() {
    new BaseCard(null, 1, 1, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNameWithSpaces() {
    new BaseCard("security drone", 1, 1, new InfluenceMask(maskGridDragon));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCost() {
    new BaseCard("security", 8, 1, new InfluenceMask(maskGridDragon));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidValue() {
    new BaseCard("security", 1, -5, new InfluenceMask(maskGridDragon));
  }

  @Test
  public void testEquals() {
    InfluenceMask dragonMask2 = new InfluenceMask(maskGridDragon);
    BaseCard dragon2 = new BaseCard("dragon", 1, 1, dragonMask2);

    assertEquals(dragon, dragon2);
    assertNotEquals(dragon, turtle);
  }
}
