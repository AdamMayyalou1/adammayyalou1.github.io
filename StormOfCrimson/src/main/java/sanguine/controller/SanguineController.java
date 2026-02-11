package sanguine.controller;

import sanguine.controller.player.Player;
import sanguine.model.PlayerId;
import sanguine.model.Position;
import sanguine.model.SanguineModel;
import sanguine.view.SanguineView;
import sanguine.view.ViewCommands;

/**
 * A class for the controller of the Sanguine game.
 * Handles the actions of the players during the course of the game.
 */
public class SanguineController implements ViewCommands, ModelStatusListener {

  private final SanguineModel model;
  private final Player player;
  private final SanguineView view;
  private boolean myTurn = false;

  private int cardChosen = -1;
  private int rowChosen = -1;
  private int colChosen = -1;

  /**
   * A constructor for the Sanguine controller.
   *
   * @param model (SanguineModel) the given Sanguine model.
   * @param player (Player) the type of player that this constructor handles requests from.
   * @param view (SanguineView) the view of the game.
   */
  public SanguineController(SanguineModel model, Player player, SanguineView view) {
    this.model = model;
    this.player = player;
    this.view = view;

    view.addCommands(this);

    model.addModelStatusListener(this);

    player.addCommands(this);
  }

  private void clearInputs() {
    cardChosen = -1;
    rowChosen = -1;
    colChosen = -1;
  }

  @Override
  public void onTurnChanged(PlayerId currentPlayer) {
    myTurn = (currentPlayer == player.id());
    view.setTurnActive(myTurn);

    if (myTurn) {
      player.thisTurn(model.getState());
    } else {
      clearInputs();
    }
  }

  @Override
  public void onGameOver(PlayerId winner, int redTotal, int blueTotal) {
    view.showGameOver(winner, redTotal, blueTotal);
  }


  @Override
  public void cardClicked(int cardIndex) {
    if (!myTurn) {
      return;
    }
    cardChosen = cardIndex;
    view.highlightCard(cardChosen);
  }

  @Override
  public void cellClicked(Position position) {
    if (!myTurn) {
      return;
    }
    rowChosen = position.row();
    colChosen = position.col();
    view.highlightCell(rowChosen, colChosen);
  }

  @Override
  public void confirmMove() {
    if (!myTurn) {
      return;
    }

    if (cardChosen == -1 || rowChosen == -1 || colChosen == -1) {
      view.showError("A Card and Cell must be chosen to make a move.");
      return;
    }

    try {
      model.placeCard(cardChosen, new Position(rowChosen, colChosen));
      clearInputs();
      view.clearHighlights();
      view.refresh();
    } catch (Exception e) {
      view.showError(e.getMessage());
    }
  }

  @Override
  public void pass() {
    if (!myTurn) {
      return;
    }

    clearInputs();
    model.pass();
    view.refresh();
  }
}

