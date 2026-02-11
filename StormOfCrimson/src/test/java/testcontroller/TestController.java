package testcontroller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import org.junit.Test;
import sanguine.controller.ModelStatusListener;
import sanguine.controller.SanguineController;
import sanguine.controller.player.Player;
import sanguine.model.Board;
import sanguine.model.PlayerId;
import sanguine.model.Position;
import sanguine.model.SanguineModel;
import sanguine.model.cell.Cell;
import sanguine.model.deck.Hand;
import sanguine.model.game.GameState;
import sanguine.view.SanguineView;
import sanguine.view.ViewCommands;

/**
 * A class that tests the Sanguine controller by mocking the model, view, and player.
 */
public class TestController {
  /**
   * Mock for the model.
   */
  private static class ModelMock implements SanguineModel {
    Appendable log;

    public ModelMock(Appendable log) {
      this.log = log;
    }

    private void write(String s) {
      try {
        log.append(s);
      } catch (Exception ignored) {
        // Ignored
      }
    }

    @Override
    public void addModelStatusListener(ModelStatusListener l) {
      write("addListener ");
    }

    @Override
    public void placeCard(int handIndex, Position p) {
      write("placeCard " + handIndex + " " + p.row() + " " + p.col() + " ");
    }

    @Override
    public void start() {
    }

    @Override
    public void pass() {
      write("pass ");
    }

    @Override
    public GameState getState() {
      return null;
    }

    @Override
    public int rows() {
      return 0;
    }

    @Override
    public int cols() {
      return 0;
    }

    @Override
    public PlayerId currentPlayer() {
      return null;
    }

    @Override
    public Board board() {
      return null;
    }

    @Override
    public Hand handOf(PlayerId p) {
      return null;
    }

    @Override
    public boolean isGameOver() {
      return false;
    }

    @Override
    public List<int[]> rowScores() {
      return List.of();
    }

    @Override
    public int totalScore(PlayerId player) {
      return 0;
    }

    @Override
    public PlayerId winner() {
      return null;
    }

    @Override
    public boolean isLegalMove(int cardIndex, Position position) {
      return false;
    }

    @Override
    public Cell cellAt(Position position) {
      return null;
    }

    @Override
    public PlayerId ownerAt(Position position) {
      return null;
    }
  }

  /**
   * Mock for the player.
   */
  private static class PlayerMock implements Player {
    Appendable log;
    private final PlayerId id;

    public PlayerMock(Appendable log, PlayerId id) {
      this.log = log;
      this.id = id;
    }

    private void write(String s) {
      try {
        log.append(s);
      } catch (Exception ignored) {
        // Ignored
      }
    }

    @Override
    public void addCommands(ViewCommands c) {
      write("addPlayerCommands ");
    }

    public void thisTurn(GameState state) {
      write("playerTurn ");
    }

    @Override
    public PlayerId id() {
      return id;
    }
  }

  /**
   * Mock for the Sanguine view.
   */
  private static class ViewMock implements SanguineView {
    Appendable log;

    public ViewMock(Appendable log) {
      this.log = log;
    }

    private void write(String s) {
      try {
        log.append(s);
      } catch (Exception ignored) {
        // Ignored
      }
    }

    @Override
    public void addCommands(ViewCommands c) {
      write("addViewCommands ");
    }

    @Override
    public void setTurnActive(boolean active) {
      write("turnActive " + active + " ");
    }

    @Override
    public void clearHighlights() {
      write("clearHighlights ");
    }

    @Override
    public void refresh() {
      write("refresh ");
    }

    @Override
    public void makeVisible() {
    }

    @Override
    public void highlightCard(Integer index) {
    }

    @Override
    public void highlightCell(Integer row, Integer col) {
    }

    @Override
    public void showError(String msg) {
      write("error " + msg + " ");
    }

    @Override
    public void showGameOver(PlayerId winner, int r, int b) {
      write("gameOver " + winner + " " + r + " " + b + " ");
    }
  }



  @Test
  public void testConfirmMoveCallsModelCorrectly() {
    StringBuilder log = new StringBuilder();

    ModelMock model = new ModelMock(log);
    PlayerMock player = new PlayerMock(log, PlayerId.RED);
    ViewMock view = new ViewMock(log);

    SanguineController controller = new SanguineController(model, player, view);

    // Simulate turn starting
    controller.onTurnChanged(PlayerId.RED);

    // Simulate UI actions
    controller.cardClicked(2);
    controller.cellClicked(new Position(1, 1));

    controller.confirmMove();

    String expected =
        "addViewCommands "
            + "addListener "
            + "addPlayerCommands "
            + "turnActive true "
            + "playerTurn "
            + "placeCard 2 1 1 "
            + "clearHighlights "
            + "refresh ";

    assertEquals(expected, log.toString());
  }

  @Test
  public void testConfirmMoveShowsErrorWhenInputsMissing() {
    StringBuilder log = new StringBuilder();

    ModelMock model = new ModelMock(log);
    PlayerMock player = new PlayerMock(log, PlayerId.RED);
    ViewMock view = new ViewMock(log);

    SanguineController controller = new SanguineController(model, player, view);

    controller.onTurnChanged(PlayerId.RED);

    controller.confirmMove();

    assertTrue(log.toString().contains("error A Card and Cell must be chosen"));
  }

  @Test
  public void testPassCallsModel() {
    StringBuilder log = new StringBuilder();

    ModelMock model = new ModelMock(log);
    PlayerMock player = new PlayerMock(log, PlayerId.RED);
    ViewMock view = new ViewMock(log);

    SanguineController controller = new SanguineController(model, player, view);

    controller.onTurnChanged(PlayerId.RED);
    controller.pass();

    assertTrue(log.toString().contains("pass "));
    assertTrue(log.toString().contains("refresh "));
  }

  @Test
  public void testControllerIgnoresCommands() {
    StringBuilder log = new StringBuilder();

    ModelMock model = new ModelMock(log);
    PlayerMock player = new PlayerMock(log, PlayerId.RED);
    ViewMock view = new ViewMock(log);

    SanguineController controller = new SanguineController(model, player, view);

    controller.onTurnChanged(PlayerId.BLUE);

    controller.cardClicked(1);
    controller.cellClicked(new Position(0, 0));
    controller.confirmMove();
    controller.pass();

    assertFalse(log.toString().contains("placeCard"));
    assertFalse(log.toString().contains("pass"));
    assertFalse(log.toString().contains("highlightCard"));
    assertFalse(log.toString().contains("highlightCell"));
  }
}

