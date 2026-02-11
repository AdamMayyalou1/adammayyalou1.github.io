package testmodel.testcell;

import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;
import org.junit.Test;
import sanguine.model.card.BaseCard;
import sanguine.model.card.Card;
import sanguine.model.card.InfluenceMask;
import sanguine.model.deck.Deck;
import sanguine.model.deck.DeckLoader;

/**
 * A class for testing DeckLoader and its ability to read in a deck correctly.
 */
public class TestDeckLoader {
  BaseCard security;
  BaseCard security2;
  BaseCard queen;
  BaseCard crab;
  BaseCard crab2;
  BaseCard wheel;
  BaseCard flame;
  BaseCard flame2;
  BaseCard chocomog;
  BaseCard grenade;
  BaseCard sweeper;
  BaseCard sweeper2;
  BaseCard quetz;
  BaseCard big;
  BaseCard rider;

  /**
   * Sets up the security card.
   *
   * @return (BaseCard) the security card.
   */
  public BaseCard setUpSecurity() {
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
    return new BaseCard("Security", 1, 1, mask);
  }

  /**
   * Sets up the queen card.
   *
   * @return (BaseCard) the queen card.
   */
  public BaseCard setUpQueen() {
    boolean[][] maskGrid = new boolean[5][5];
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        maskGrid[row][col] = false;
      }
    }
    maskGrid[0][2] = true;
    maskGrid[4][2] = true;
    InfluenceMask mask = new InfluenceMask(maskGrid);
    return new BaseCard("Queen", 1, 1, mask);
  }

  /**
   * Sets up the crab card.
   *
   * @return (BaseCard) the crab card.
   */
  public BaseCard setUpCrab() {
    boolean[][] maskGrid = new boolean[5][5];
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        maskGrid[row][col] = false;
      }
    }
    maskGrid[1][2] = true;
    maskGrid[2][1] = true;
    maskGrid[2][3] = true;
    InfluenceMask mask = new InfluenceMask(maskGrid);
    return new BaseCard("Crab", 1, 1, mask);
  }

  /**
   * Sets up the wheel card.
   *
   * @return (BaseCard) the wheel card.
   */
  public BaseCard setUpWheel() {
    boolean[][] maskGrid = new boolean[5][5];
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        maskGrid[row][col] = false;
      }
    }
    maskGrid[0][4] = true;
    maskGrid[4][4] = true;
    InfluenceMask mask = new InfluenceMask(maskGrid);
    return new BaseCard("Wheel", 1, 1, mask);
  }

  /**
   * Sets up the flame card.
   *
   * @return (BaseCard) the flame card.
   */
  public BaseCard setUpFlame() {
    boolean[][] maskGrid = new boolean[5][5];
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        maskGrid[row][col] = false;
      }
    }
    maskGrid[1][1] = true;
    maskGrid[1][2] = true;
    maskGrid[2][1] = true;
    maskGrid[3][1] = true;
    maskGrid[3][2] = true;
    InfluenceMask mask = new InfluenceMask(maskGrid);
    return new BaseCard("Flame", 1, 3, mask);
  }

  /**
   * Sets up the chocomog card.
   *
   * @return (BaseCard) the chocomog card.
   */
  public BaseCard setUpChocomog() {
    boolean[][] maskGrid = new boolean[5][5];
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        maskGrid[row][col] = false;
      }
    }
    maskGrid[1][2] = true;
    maskGrid[2][3] = true;
    maskGrid[3][2] = true;
    InfluenceMask mask = new InfluenceMask(maskGrid);
    return new BaseCard("Chocomog", 1, 1, mask);
  }

  /**
   * Sets up the grenade card.
   *
   * @return (BaseCard) the grenade card.
   */
  public BaseCard setUpGrenade() {
    boolean[][] maskGrid = new boolean[5][5];
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        maskGrid[row][col] = false;
      }
    }
    maskGrid[2][4] = true;
    InfluenceMask mask = new InfluenceMask(maskGrid);
    return new BaseCard("Grenade", 2, 1, mask);
  }

  /**
   * Sets up the sweeper card.
   *
   * @return (BaseCard) the sweeper card.
   */
  public BaseCard setUpSweeper() {
    boolean[][] maskGrid = new boolean[5][5];
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        maskGrid[row][col] = false;
      }
    }
    maskGrid[1][2] = true;
    maskGrid[1][3] = true;
    maskGrid[3][2] = true;
    maskGrid[3][3] = true;
    InfluenceMask mask = new InfluenceMask(maskGrid);
    return new BaseCard("Sweeper", 2, 2, mask);
  }

  /**
   * Sets up the quetz card.
   *
   * @return (BaseCard) the quetz card.
   */
  public BaseCard setUpQuetz() {
    boolean[][] maskGrid = new boolean[5][5];
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        maskGrid[row][col] = false;
      }
    }
    maskGrid[0][2] = true;
    maskGrid[1][3] = true;
    maskGrid[3][3] = true;
    maskGrid[4][2] = true;
    InfluenceMask mask = new InfluenceMask(maskGrid);
    return new BaseCard("Quetz", 2, 3, mask);
  }

  /**
   * Sets up the big card.
   *
   * @return (BaseCard) the big card.
   */
  public BaseCard setUpBig() {
    boolean[][] maskGrid = new boolean[5][5];
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        maskGrid[row][col] = false;
      }
    }
    maskGrid[0][2] = true;
    maskGrid[1][1] = true;
    maskGrid[1][3] = true;
    maskGrid[2][0] = true;
    maskGrid[2][4] = true;
    maskGrid[3][1] = true;
    maskGrid[3][3] = true;
    maskGrid[4][2] = true;
    InfluenceMask mask = new InfluenceMask(maskGrid);
    return new BaseCard("Big", 3, 5, mask);
  }

  /**
   * Sets up the rider card.
   *
   * @return (BaseCard) the rider card.
   */
  public BaseCard setUpRider() {
    boolean[][] maskGrid = new boolean[5][5];
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        maskGrid[row][col] = false;
      }
    }
    maskGrid[2][3] = true;
    maskGrid[3][1] = true;
    maskGrid[3][2] = true;
    maskGrid[3][3] = true;
    InfluenceMask mask = new InfluenceMask(maskGrid);
    return new BaseCard("Rider", 3, 5, mask);
  }

  @Test
  public void testDeckLoaderWithExample() {
    String path = "docs" + File.separator + "example.deck";
    File config;
    config = new File(path);
    security = setUpSecurity();
    security2 = setUpSecurity();
    queen = setUpQueen();
    crab = setUpCrab();
    crab2 = setUpCrab();
    wheel = setUpWheel();
    flame = setUpFlame();
    flame2 =  setUpFlame();
    chocomog = setUpChocomog();
    grenade = setUpGrenade();
    sweeper = setUpSweeper();
    sweeper2 = setUpSweeper();
    quetz = setUpQuetz();
    big = setUpBig();
    rider = setUpRider();
    List<Card> cards = List.of(security, security2, queen, crab, crab2,
        wheel, flame, flame2, chocomog, grenade, sweeper, sweeper2, quetz,
        big, rider);
    Deck deck = new Deck(cards);
    Deck deck2 = DeckLoader.readConfig(config);
    System.out.println(deck);
    System.out.println(deck2);
    assertEquals(deck, deck2);
  }
}
