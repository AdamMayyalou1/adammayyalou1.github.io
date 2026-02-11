package testcontroller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import sanguine.controller.strategy.ControlBoardStrategy;
import sanguine.controller.strategy.FillFirstStrategy;
import sanguine.controller.strategy.MaximizeRowScoreStrategy;
import sanguine.controller.strategy.MinimaxStrategy;
import sanguine.controller.strategy.Move;
import sanguine.controller.strategy.MoveGenerator;
import sanguine.controller.strategy.Strategy;
import sanguine.model.BasicSanguineModel;
import sanguine.model.Board;
import sanguine.model.PlayerId;
import sanguine.model.Position;
import sanguine.model.cell.Cell;
import sanguine.model.deck.Deck;
import sanguine.model.deck.DeckLoader;
import sanguine.model.game.GameState;
import sanguine.model.game.Scoring;

/**
 * A class for testing the different strategies that a computer player may utilize.
 * Also includes tests for the classes associated with it.
 */
public class TestStrategies {

  private BasicSanguineModel model;

  /**
   * Simple setup that reads in a deck config and prepares the model.
   */
  @Before
  public void setUp() {
    String path = "docs" + File.separator + "example.deck";
    Deck deck = DeckLoader.readConfig(new File(path));
    model = new BasicSanguineModel(3, 3, deck, deck, 5);
    model.start();
  }

  @Test
  public void testFillFirstStrategy() {
    Strategy strat = new FillFirstStrategy();
    GameState state = model.getState();

    List<Move> moves = strat.choose(state);

    assertFalse(moves.isEmpty());
    Move first = moves.getFirst();

    // Fill-first should choose row-major first valid placement.
    // Row 0, Col 0 should be a pawn cell owned by RED after start().
    assertEquals(0, first.pos().row());
    assertEquals(0, first.pos().col());

    // Hand index must be in range.
    assertTrue(first.handIndex() >= 0);
    // Next state is not null.
    assertNotNull(first.nextState());
  }


  @Test
  public void testMaximizeRowScoreStrategyTieBreaking() {
    Strategy strat = new MaximizeRowScoreStrategy();
    GameState state = model.getState();

    List<Move> moves = strat.choose(state);

    // If multiple winning moves exist in the first scoring row,
    // they MUST all belong to that row.
    if (!moves.isEmpty()) {
      int targetRow = moves.getFirst().pos().row();

      for (Move m : moves) {
        assertEquals(targetRow, m.pos().row());
      }
    }
  }


  @Test
  public void testControlBoardStrategy() {
    Strategy strat = new ControlBoardStrategy();
    GameState state = model.getState();

    List<Move> moves = strat.choose(state);

    assertFalse(moves.isEmpty());

    Move chosen = moves.getFirst();

    assertNotNull(chosen.pos());
    assertNotNull(chosen.nextState());
    // Determine that the hand index is valid
    assertTrue(chosen.handIndex() >= 0);

    // Check that board control increased.
    int beforeCount = countOwned(state.board());
    int afterCount = countOwned(chosen.nextState().board());

    assertTrue(afterCount >= beforeCount);
  }

  /**
   * Helper method that counts the amount of cells owned by a given player.
   *
   * @param board (Board) the board being examined.
   * @return (int) the number of cells they own.
   */
  private int countOwned(Board board) {
    int count = 0;
    for (int r = 0; r < board.rows(); r++) {
      for (int c = 0; c < board.cols(); c++) {
        Cell cell = board.getCell(new Position(r, c));
        if (cell.owner() == PlayerId.RED) {
          count++;
        }
      }
    }
    return count;
  }


  @Test
  public void testControlBoardStrategyTieBreaking() {
    Strategy strat = new ControlBoardStrategy();
    GameState state = model.getState();

    List<Move> moves = strat.choose(state);

    // If ties exist, first move must be top-left-most
    Move first = moves.getFirst();

    for (Move m : moves) {
      boolean valid =
          m.pos().row() > first.pos().row()
              || (m.pos().row() == first.pos().row() && m.pos().col() >= first.pos().col());

      // Tie-breaking will pick the upper-left most move.
      assertTrue(valid);
    }
  }


  @Test
  public void testMoveGeneratorProducesValidMoves() {
    GameState state = model.getState();
    List<Move> moves = MoveGenerator.allValidMoves(state);

    assertFalse("There should be possible moves at start of game.", moves.isEmpty());

    for (Move m : moves) {
      assertTrue("handIndex must reference a card in hand",
          m.handIndex() >= 0);
      assertNotNull("position must be non-null", m.pos());
      assertNotNull("nextState must be non-null", m.nextState());
    }
  }

  @Test
  public void testMoveGeneratorRespectsPawnOwnership() {
    GameState state = model.getState();
    List<Move> moves = MoveGenerator.allValidMoves(state);

    for (Move m : moves) {
      Position p = m.pos();
      Cell before = state.board().getCell(p);

      assertEquals("Moves should only target pawn cells owned by current player.",
          state.current(), before.owner());
    }
  }

  @Test
  public void testMoveGeneratorNoIllegalMoves() {
    GameState state = model.getState();
    List<Move> moves = MoveGenerator.allValidMoves(state);

    for (Move m : moves) {
      Position p = m.pos();
      assertTrue("Generated moves must be within board bounds.",
          p.row() >= 0 && p.row() < state.board().rows());
      assertTrue(p.col() >= 0 && p.col() < state.board().cols());
    }
  }


  @Test
  public void testMinimaxReturnsMove() {
    model.start();
    GameState state = model.getState();

    Strategy minimax = new MinimaxStrategy(1, new ControlBoardStrategy());

    List<Move> moves = minimax.choose(state);
    assertNotNull(moves);
    assertFalse(moves.isEmpty());

    for (Move m : moves) {
      int row = m.pos().row();
      int col = m.pos().col();
      assertTrue(row >= 0 && row < model.rows());
      assertTrue(col >= 0 && col < model.cols());
    }
  }

  @Test
  public void testMinimaxChoosesBestMove() {
    model.start();
    GameState state = model.getState();

    Strategy minimax = new MinimaxStrategy(2, new ControlBoardStrategy());
    List<Move> moves = minimax.choose(state);

    assertNotNull(moves);
    assertFalse(moves.isEmpty());

    // Calculate best score manually and then compare.
    int bestScore = Integer.MIN_VALUE;
    for (Move m : moves) {
      int score = Scoring.total(m.nextState().board(), state.current())
          - Scoring.total(m.nextState().board(), state.current().opposite());
      bestScore = Math.max(bestScore, score);
    }

    for (Move m : moves) {
      int score = Scoring.total(m.nextState().board(), state.current())
          - Scoring.total(m.nextState().board(), state.current().opposite());
      assertEquals(bestScore, score);
    }
  }

  @Test
  public void testMinimaxHandlesTieMoves() {
    model.start();
    GameState state = model.getState();

    Strategy minimax = new MinimaxStrategy(1, new ControlBoardStrategy());
    List<Move> moves = minimax.choose(state);

    assertFalse(moves.isEmpty());

    // Verify all returned moves have equal evaluation
    int eval = Scoring.total(moves.getFirst().nextState().board(), state.current())
        - Scoring.total(moves.getFirst().nextState().board(), state.current().opposite());
    for (Move m : moves) {
      int e = Scoring.total(m.nextState().board(), state.current())
          - Scoring.total(m.nextState().board(), state.current().opposite());
      assertEquals(eval, e);
    }
  }
}

