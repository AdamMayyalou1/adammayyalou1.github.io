package sanguine.controller;

import sanguine.model.Position;
import sanguine.model.SanguineModel;
import sanguine.view.SanguineView;
import sanguine.view.ViewCommands;

/**
 * Class for the Controller of the Sanguine game (stub).
 */
public class StubController implements ViewCommands {

  private final SanguineModel model;
  private final SanguineView view;

  private int cardChosen = -1;
  private int rowChosen = -1;
  private int colChosen = -1;

  /**
   * Simple constructor for the StubController.
   *
   * @param model (Model) the given model of the game.
   * @param view (View) the given view of the game.
   */
  public StubController(SanguineModel model, SanguineView view) {
    this.model = model;
    this.view = view;
    this.view.addCommands(this);
    this.view.refresh();
  }

  @Override
  public void cardClicked(int cardIndex) {
    System.out.println("card clicked: " + cardIndex + " Player: " + model.currentPlayer());
    if (cardChosen != -1 && cardChosen == cardIndex) {
      cardChosen = -1;
    } else {
      cardChosen = cardIndex;
    }
    view.refresh();
  }

  @Override
  public void cellClicked(Position position) {
    System.out.println("cell Clicked at: (" + position.row() + ", " + position.col() + ")");
    if (rowChosen != -1 && colChosen != -1
        && rowChosen == position.row() && colChosen == position.col()) {
      rowChosen = -1;
      colChosen = -1;
    } else {
      rowChosen = position.row();
      colChosen = position.col();
    }
    view.refresh();
  }

  @Override
  public void confirmMove() {
    System.out.println("Confirm Move Pressed");
    model.placeCard(cardChosen, new Position(rowChosen, colChosen));
    view.refresh();
  }

  @Override
  public void pass() {
    System.out.println("Pass Pressed");
    model.pass();
    view.refresh();
  }
}