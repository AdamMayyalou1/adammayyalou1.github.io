package testmodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThrows;
//import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import sanguine.model.BasicSanguineModel;
import sanguine.model.Board;
import sanguine.model.PlayerId;
import sanguine.model.Position;
import sanguine.model.cell.Cell;
import sanguine.model.deck.Deck;
import sanguine.model.deck.DeckLoader;
import sanguine.model.deck.Hand;
import sanguine.model.game.Scoring;

/**
 * A test for the BasicModel and its associated methods.
 */
public class TestBasicModel {
  private BasicSanguineModel model;

  /**
   * A simple setup for each test.
   */
  @Before
  public void setUp() {
    String path = "docs" + File.separator + "example.deck";
    File config = new File(path);
    Deck deck = DeckLoader.readConfig(config);
    model = new BasicSanguineModel(3, 3, deck, deck, 5);
  }

  @Test
  public void testModelInitialization() {
    assertNotNull(model);
  }

  @Test
  public void testRows() {
    assertEquals(3, model.rows());
  }

  @Test
  public void testCols() {
    assertEquals(3, model.cols());
  }

  @Test
  public void testBoardNotNull() {
    Board board = model.board();
    assertNotNull(board);
  }

  @Test
  public void testCurrentPlayerBeforeStart() {
    PlayerId player = model.currentPlayer();
    assertEquals(PlayerId.RED, player);
  }

  @Test
  public void testHandOfRedPlayer() {
    Hand redHand = model.handOf(PlayerId.RED);
    assertNotNull(redHand);
  }

  @Test
  public void testHandOfBluePlayer() {
    Hand blueHand = model.handOf(PlayerId.BLUE);
    assertNotNull(blueHand);
  }

  @Test
  public void testGameNotOverInitially() {
    assertFalse(model.isGameOver());
  }

  @Test
  public void testStartGame() {
    model.start();
    Hand redHand = model.handOf(PlayerId.RED);
    assertNotNull(redHand);
  }

  @Test
  public void testTotalScoreInitially() {
    int redScore = model.totalScore(PlayerId.RED);
    int blueScore = model.totalScore(PlayerId.BLUE);
    assertEquals(0, redScore);
    assertEquals(0, blueScore);
  }

  @Test
  public void testRowScoreInitially() {
    List<int[]> scores = Scoring.rowScores(model.board());
    assertEquals(0,  scores.getFirst()[0]);
    assertEquals(0,  scores.getFirst()[1]);
  }

  @Test
  public void testPassSwitchesPlayer() {
    model.start();
    PlayerId firstPlayer = model.currentPlayer();
    model.pass();
    PlayerId secondPlayer = model.currentPlayer();
    assertEquals(firstPlayer.opposite(), secondPlayer);
  }

  @Test
  public void testMultiplePasses() {
    model.start();
    PlayerId initialPlayer = model.currentPlayer();
    model.pass();
    model.pass();
    PlayerId afterTwoPasses = model.currentPlayer();
    assertEquals(initialPlayer, afterTwoPasses);
  }

  @Test
  public void testPlaceCardChangesBoard() {
    model.start();
    Board boardBefore = model.board();
    Position pos = new Position(1, 0);

    Cell cellBefore = boardBefore.getCell(pos);

    model.placeCard(0, pos);
    Board boardAfter = model.board();
    Cell cellAfter = boardAfter.getCell(pos);

    assertNotSame(Cell.Kind.EMPTY, cellAfter.kind());
  }

  @Test
  public void testPlaceCardReducesHandSize() {
    model.start();
    PlayerId currentPlayer = model.currentPlayer();
    Hand handBefore = model.handOf(currentPlayer);
    int sizeBefore = handBefore.size();

    Position pos = new Position(1, 0);  // RED's pawn position
    model.placeCard(0, pos);
    
    Hand handAfter = model.handOf(currentPlayer);
    int sizeAfter = handAfter.size();
    
    assertEquals(sizeBefore - 1, sizeAfter);
  }

  @Test
  public void testPlaceCardAndPass() {
    model.start();
    PlayerId firstPlayer = model.currentPlayer();

    Position pos = new Position(1, 0);
    model.placeCard(0, pos);

    model.pass();

    PlayerId secondPlayer = model.currentPlayer();
    assertEquals(firstPlayer, secondPlayer);
  }

  @Test
  public void testPlaceCardIllegalPosition() {
    model.start();
    Position pos = new Position(1, 1);
    assertThrows(IllegalArgumentException.class, () -> model.placeCard(0, pos));
  }

  @Test
  public void testPlaceCardIllegalCost() {
    model.start();
    Position pos = new Position(1, 0);
    model.placeCard(0, pos);
    Position pos2 = new Position(1, 1);
    assertThrows(IllegalArgumentException.class, () -> model.placeCard(4, pos));
  }


  @Test
  public void testRowScoreAfterCard() {
    model.start();
    Position pos = new Position(0, 0);
    model.placeCard(0, pos);

    assertEquals(1, model.rowScores().getFirst()[0]);

  }

  @Test
  public void testTotalScoreAfter2Cards() {
    model.start();
    Position pos = new Position(0, 0);
    model.placeCard(0, pos);
    Position pos2 = new Position(1, 2);
    model.placeCard(0, pos2);

    assertEquals(1, model.totalScore(PlayerId.RED));
    assertEquals(1, model.totalScore(PlayerId.BLUE));
  }

}
