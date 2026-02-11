package sanguine;

import java.io.File;
import java.io.IOException;
import sanguine.model.BasicSanguineModel;
import sanguine.model.Position;
import sanguine.model.card.Card;
import sanguine.model.deck.Deck;
import sanguine.model.deck.DeckLoader;
import sanguine.model.game.GameRules;
import sanguine.view.textualview.SanguineTextualView;

/**
 * Sanguine Class is the main class to showcase the work of the model and the view.
 * Both work together to show how both sides move together 1 by 1.
 */
public class Sanguine {
  /**
   * The main used to run the model and view.
   *
   * @param args the user given arguments.
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.print("No arguments found");
      return;
    }
    String filePath = args[0];

    if (filePath == null || filePath.isEmpty()) {
      System.out.println("File name invalid or cannot be found");
      return;
    }
    File config = new File(filePath);
    Deck deck;
    try {
      deck = DeckLoader.readConfig(config);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return;
    }
    int rows = 3;
    int cols = 5;
    int startHand = 5;

    GameRules.validateGame(rows, cols, deck.size(), startHand);
    BasicSanguineModel model = new BasicSanguineModel(rows, cols, deck, deck, startHand);
    SanguineTextualView view = new SanguineTextualView(model);

    model.start();
    try {
      view.render();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    boolean moved = tryMove(model, new Position(1, 0));
    if (!moved) {
      model.pass();
    }


    try {
      view.render();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    boolean moved2 = tryMove(model, new Position(1, 4));
    if (!moved2) {
      model.pass();
    }

    try {
      view.render();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static boolean tryMove(BasicSanguineModel model, Position pos) {
    for (int index = 0; index < model.handOf(model.currentPlayer()).cards().size(); index++) {
      Card card = model.handOf(model.currentPlayer()).cards().get(index);
      if (card.cost() == 1) {
        model.placeCard(index, pos);
        return true;
      }
    }
    return false;
  }
}
