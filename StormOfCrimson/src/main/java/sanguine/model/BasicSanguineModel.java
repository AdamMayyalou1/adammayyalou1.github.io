package sanguine.model;

import java.util.ArrayList;
import java.util.List;
import sanguine.controller.ModelStatusListener;
import sanguine.model.card.Card;
import sanguine.model.cell.Cell;
import sanguine.model.cell.PawnCell;
import sanguine.model.deck.Deck;
import sanguine.model.deck.Hand;
import sanguine.model.game.GameRules;
import sanguine.model.game.GameState;
import sanguine.model.game.RuleKeeper;
import sanguine.model.game.Scoring;

/**
 * Basic Implementation of the Sanguine Model. Brings together the GameState, GameKeeper, Scoring
 * & GameRules to make a functional version of the game for 2 players.
 */
public class BasicSanguineModel implements SanguineModel {
  private final int rows;
  private final int cols;
  private final int startHand;
  private final List<ModelStatusListener> listeners = new ArrayList<ModelStatusListener>();
  private GameState state;
  private boolean gameStarted = false;
  private boolean gameOver = false;


  /**
   * Basic constructor for the BasicSanguineModel. Instantiates all fields of the GameState record.
   *
   * @param rows number of Rows.
   * @param cols number of Columns.
   * @param redDeck redDeck deck.
   * @param blueDeck blueDeck deck.
   * @param startHand Number of cards in starting hand.
   */
  public BasicSanguineModel(int rows, int cols, Deck redDeck, Deck blueDeck, int startHand) {
    GameRules.validateGame(rows, cols, redDeck.size(), startHand);
    GameRules.validateGame(rows, cols, blueDeck.size(), startHand);
    this.rows = rows;
    this.cols = cols;
    this.startHand = startHand;
    state = new GameState(new Board(rows, cols), redDeck, new Hand(List.of()), blueDeck,
        new Hand(List.of()), PlayerId.RED, 0);
  }

  private void turnChangePush() {
    for (ModelStatusListener listener : listeners) {
      listener.onTurnChanged(currentPlayer());
    }
  }

  private void gameOverPush() {
    for (ModelStatusListener listener : listeners) {
      listener.onGameOver(winner(), totalScore(PlayerId.RED), totalScore(PlayerId.BLUE));
    }
  }

  @Override
  public void start() {
    if (gameStarted) {
      return;
    }
    gameStarted = true;
    state = RuleKeeper.playGame(state, startHand);

    turnChangePush();

  }

  @Override
  public void pass() {
    state = RuleKeeper.pass(state);
    state = RuleKeeper.turnTryDraw(state);

    if (isGameOver()) {
      gameOver = true;
      gameOverPush();
    } else {
      turnChangePush();
    }
  }

  @Override
  public void placeCard(int handIndex, Position destination) {
    state = RuleKeeper.place(state, handIndex, destination);
    state = RuleKeeper.turnTryDraw(state);

    if (isGameOver()) {
      gameOver = true;
      gameOverPush();
    } else {
      turnChangePush();
    }
  }

  @Override
  public void addModelStatusListener(ModelStatusListener listener) {
    listeners.add(listener);
  }

  @Override
  public int rows() {
    return rows;
  }

  @Override
  public int cols() {
    return cols;
  }

  @Override
  public PlayerId currentPlayer() {
    return state.current();
  }

  @Override
  public Board board() {
    return state.board();
  }

  @Override
  public Hand handOf(PlayerId p) {
    return switch (p) {
      case RED -> state.redHand();
      case BLUE -> state.blueHand();
    };
  }

  @Override
  public boolean isGameOver() {
    if (state.consecutiveSkips() >= 2) {
      return true;
    }
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {

        if (state.board().getCell(new Position(row, col)).kind() != Cell.Kind.CARD) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public List<int[]> rowScores() {
    return Scoring.rowScores(state.board());
  }

  @Override
  public int totalScore(PlayerId player) {
    return Scoring.total(state.board(), player);
  }

  public GameState getState() {
    return state;
  }

  @Override
  public PlayerId winner() {
    if (isGameOver()) {
      if (totalScore(PlayerId.RED) > totalScore(PlayerId.BLUE)) {
        return PlayerId.RED;
      } else if (totalScore(PlayerId.BLUE) > totalScore(PlayerId.RED)) {
        return PlayerId.BLUE;
      } else {
        return null;
      }
    }
    return null;
  }

  @Override
  public boolean isLegalMove(int cardIndex, Position position) {
    if (!state.board().inBounds(position)) {
      return false;
    }
    Hand hand;
    if (state.current() == PlayerId.RED) {
      hand = state.redHand();
    } else {
      hand = state.blueHand();
    }
    if (cardIndex < 0 || cardIndex >= hand.size()) {
      return false;
    }

    Card chosenCard = hand.cards().get(cardIndex);
    Cell chosenCell = state.board().getCell(position);

    if (!(chosenCell instanceof PawnCell)) {
      return false;
    }

    if (chosenCell.owner() != currentPlayer()) {
      return false;
    }

    return chosenCell.pawnCount() >= chosenCard.cost();
  }

  @Override
  public Cell cellAt(Position position) {
    return state.board().getCell(position);
  }

  @Override
  public PlayerId ownerAt(Position position) {
    return cellAt(position).owner();
  }
}
