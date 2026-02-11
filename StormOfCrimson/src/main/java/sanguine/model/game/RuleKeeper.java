package sanguine.model.game;

import sanguine.model.Board;
import sanguine.model.PlayerId;
import sanguine.model.Position;
import sanguine.model.card.Card;
import sanguine.model.cell.CardCell;
import sanguine.model.cell.Cell;
import sanguine.model.deck.Deck;
import sanguine.model.deck.Hand;

/**
 * RuleKeeper class to alter the Sanguine game based on the instructed rules of Homework 1.
 */
public final class RuleKeeper {

  /**
   * Play game method to instantiate the GameState record with all the fields.
   *
   * @param game The game to start.
   * @param startHand The number of cards to place in starting hand.
   * @return A new gameState with the starting state
   */
  public static GameState playGame(GameState game, int startHand) {
    Board board = game.board();
    board.seedPawns();
    Deck redDeck = game.redDeck();
    Deck blueDeck = game.blueDeck();
    Hand redHand = game.redHand();
    Hand blueHand = game.blueHand();
    for (int cardNum = 0; cardNum < startHand; cardNum++) {
      Deck.Drawn redDraw = redDeck.draw();
      redHand = redHand.add(redDraw.card());
      redDeck = redDraw.deck();
      Deck.Drawn blueDraw = blueDeck.draw();
      blueHand = blueHand.add(blueDraw.card());
      blueDeck = blueDraw.deck();
    }
    return new GameState(board, redDeck, redHand, blueDeck,
        blueHand, PlayerId.RED, 0);
  }

  /**
   * Pass the turn to the next player.
   * Adds 1 to the pass Streak & Changes the current player.
   *
   * @param game The game to alter.
   * @return a new GameState with the passed move.
   */
  public static GameState pass(GameState game) {
    int totalPass = game.consecutiveSkips() + 1;
    return new GameState(game.board(), game.redDeck(), game.redHand(), game.blueDeck(),
        game.blueHand(), game.current().opposite(), totalPass);
  }

  /**
   * Attempts to place a card in the wanted Position.
   * Places the card & Applies the correct Influence mask.
   * Throws an illegal argument exception if:
   * 1. The destination is out of bounds
   * 2. The cell is either the not a pawn type or the player is not the owner.
   * 3. The cost is too high compared to the number of pawns.
   *
   * @param game The game to alter.
   * @param cardNum The card Number within the player's hand to play
   * @param destination The destination position to play the card
   * @return The new game state with the move.
   */
  public static GameState place(GameState game, int cardNum, Position destination) {
    Board board = game.board();
    if (!board.inBounds(destination)) {
      throw new IllegalArgumentException("Destination position is invalid");
    }

    PlayerId current = game.current();
    Hand hand;
    if (current == PlayerId.RED) {
      hand = game.redHand();
    } else {
      hand = game.blueHand();
    }
    Card cardToPlace = hand.cards().get(cardNum);

    Cell cellToPlace = board.getCell(destination);
    if (cellToPlace.kind() != Cell.Kind.PAWNS || cellToPlace.owner() != current) {
      throw new IllegalArgumentException("Player does not own cell or is not a Pawn cell");
    } else if (cellToPlace.pawnCount() < cardToPlace.cost()) {
      throw new IllegalArgumentException("Not enough Pawns for card value");
    }

    board.setCell(destination, new CardCell(current, cardToPlace));

    for (int[] offset : cardToPlace.influenceMask().reverseFor(current)) {
      int offsetRow = destination.row() + offset[0];
      int offsetCol = destination.col() + offset[1];

      if (offsetRow >= 0 && offsetCol >= 0
          && offsetRow < board.rows() && offsetCol < board.cols()) {
        Position influencePosition = new Position(offsetRow, offsetCol);
        board.applyInfluenceMask(influencePosition, current);
      }
    }

    Hand nextHand = hand.remove(cardToPlace);
    if (current == PlayerId.RED) {
      return new GameState(board, game.redDeck(), nextHand, game.blueDeck(), game.blueHand(),
          current.opposite(), 0);
    } else {
      return new GameState(board, game.redDeck(), game.redHand(), game.blueDeck(), nextHand,
          current.opposite(), 0);
    }
  }

  /**
   * Called at the start of each turn, if no cards are in the deck,
   * then no card is drawn. No exception is thrown.
   *
   * @param game The game to alter.
   * @return the altered GameState.
   */
  public static GameState turnTryDraw(GameState game) {
    PlayerId current = game.current();
    if (current == PlayerId.RED) {
      if (game.redDeck().isEmpty()) {
        return game;
      }
      Deck.Drawn drawData = game.redDeck().draw();
      return new GameState(game.board(), drawData.deck(), game.redHand().add(drawData.card()),
          game.blueDeck(), game.blueHand(), current, game.consecutiveSkips());
    } else {
      if (game.blueDeck().isEmpty()) {
        return game;
      }
      Deck.Drawn drawData = game.blueDeck().draw();
      return new GameState(game.board(), game.redDeck(), game.redHand(), drawData.deck(),
          game.blueHand().add(drawData.card()), current, game.consecutiveSkips());
    }
  }
}
