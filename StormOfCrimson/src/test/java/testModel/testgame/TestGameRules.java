package testmodel.testgame;

import static org.junit.Assert.assertThrows;

import org.junit.Test;
import sanguine.model.game.GameRules;

/**
 * A testing class for the GameRules and its associated methods.
 */
public class TestGameRules {

  @Test
  public void testValid() {
    GameRules.validateGame(3, 5, 20, 5);
  }

  @Test
  public void testInvalidRowsCols() {
    assertThrows(IllegalArgumentException.class, () -> GameRules.validateGame(0, 5, 20, 5));
    assertThrows(IllegalArgumentException.class, () -> GameRules.validateGame(3, 4, 20, 5));
    assertThrows(IllegalArgumentException.class, () -> GameRules.validateGame(3, 0, 20, 5));
  }

  @Test
  public void testInvalidDeckSize() {
    assertThrows(IllegalArgumentException.class, () -> GameRules.validateGame(3, 5, 14, 5));
  }

  @Test
  public void testInvalidStartHand() {
    assertThrows(IllegalArgumentException.class, () -> GameRules.validateGame(3, 5, 15, 6));
    assertThrows(IllegalArgumentException.class, () -> GameRules.validateGame(3, 5, 15, 0));
  }
}
