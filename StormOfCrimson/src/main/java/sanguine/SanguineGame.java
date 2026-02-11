package sanguine;

import java.io.File;
import sanguine.controller.SanguineController;
import sanguine.controller.player.HumanPlayer;
import sanguine.controller.player.MachinePlayer;
import sanguine.controller.player.Player;
import sanguine.controller.strategy.ControlBoardStrategy;
import sanguine.controller.strategy.FillFirstStrategy;
import sanguine.controller.strategy.MaximizeRowScoreStrategy;
import sanguine.model.BasicSanguineModel;
import sanguine.model.PlayerId;
import sanguine.model.deck.Deck;
import sanguine.model.deck.DeckLoader;
import sanguine.view.SanguineSwingView;
import sanguine.view.SanguineView;

/**
 * Basic Main Method for the Sanguine game using the StubController, SanguineSwingView, and Basic
 * Sanguine Model.
 */
public class SanguineGame {
  /**
   * The main used to run the model and view.
   *
   * @param args the user given arguments.
   */
  public static void main(String[] args) {
    if (args.length < 6) {
      throw new IllegalArgumentException("Not enough arguments");
    }

    int rows = Integer.parseInt(args[0]);
    int cols = Integer.parseInt(args[1]);
    String redDeckPath = args[2];
    String blueDeckPath = args[3];
    String redStrat = args[4];
    String blueStrat = args[5];

    Deck redDeck = DeckLoader.readConfig(new File(redDeckPath));
    Deck blueDeck = DeckLoader.readConfig(new File(blueDeckPath));

    BasicSanguineModel model = new BasicSanguineModel(rows, cols, redDeck, blueDeck, 5);

    SanguineView redView = new SanguineSwingView(model, PlayerId.RED);
    SanguineView blueView = new SanguineSwingView(model, PlayerId.BLUE);

    Player redPlayer = makePlayer(PlayerId.RED, redStrat);
    Player bluePlayer = makePlayer(PlayerId.BLUE, blueStrat);

    SanguineController redController = new SanguineController(model, redPlayer, redView);
    SanguineController blueController = new SanguineController(model, bluePlayer, blueView);

    model.start();

    if (redPlayer instanceof HumanPlayer) {
      redView.makeVisible();
    }
    if (bluePlayer instanceof HumanPlayer) {
      blueView.makeVisible();
    }

  }

  private static Player makePlayer(PlayerId playerId, String strat) {
    return switch (strat.toLowerCase()) {
      case "human" -> new HumanPlayer(playerId);
      case "controlboard" -> new MachinePlayer(playerId, new ControlBoardStrategy());
      case "fillfirst" -> new MachinePlayer(playerId, new FillFirstStrategy());
      case "maxrowscore" -> new MachinePlayer(playerId, new MaximizeRowScoreStrategy());
      default -> throw new IllegalArgumentException("Unknown strategy: " + strat);
    };
  }
}
