package testcontroller;

import static org.junit.Assert.assertEquals;

import java.io.File;
import org.junit.Before;
import org.junit.Test;
import sanguine.controller.player.HumanPlayer;
import sanguine.controller.player.MachinePlayer;
import sanguine.controller.strategy.FillFirstStrategy;
import sanguine.model.BasicSanguineModel;
import sanguine.model.PlayerId;
import sanguine.model.deck.Deck;
import sanguine.model.deck.DeckLoader;

/**
 * A class that tests the classes that rely on the player interface.
 */
public class TestPlayer {
  HumanPlayer humanPlayer;
  MachinePlayer machinePlayer;
  private BasicSanguineModel model;

  /**
   * Simple method that sets up before each test.
   */
  @Before
  public void setup() {
    humanPlayer = new HumanPlayer(PlayerId.RED);
    machinePlayer = new MachinePlayer(PlayerId.BLUE,
        new FillFirstStrategy());
    String path = "docs" + File.separator + "example.deck";
    Deck deck = DeckLoader.readConfig(new File(path));
    model = new BasicSanguineModel(3, 3, deck, deck, 5);
    model.start();
  }

  @Test
  public void testPlayerId() {
    assertEquals(PlayerId.RED, humanPlayer.id());
    assertEquals(PlayerId.BLUE, machinePlayer.id());
  }
}
