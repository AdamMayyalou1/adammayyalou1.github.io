package testview;

//import static org.junit.Assert.assertEquals;

import java.io.File;
//import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import sanguine.model.BasicSanguineModel;
import sanguine.model.Board;
import sanguine.model.PlayerId;
import sanguine.model.Position;
import sanguine.model.card.BaseCard;
import sanguine.model.card.InfluenceMask;
import sanguine.model.cell.CardCell;
import sanguine.model.deck.Deck;
import sanguine.model.deck.DeckLoader;
import sanguine.view.textualview.SanguineTextualView;

/**
 * A test class for the textual view of the Sanguine game.
 */
public class TestTextualView {

  /**
   * A simple setUp for each test.
   */
  @Before
  public void setUp() {
    String path = "docs" + File.separator + "example.deck";
    File config = new File(path);
    Deck deck = DeckLoader.readConfig(config);
    BasicSanguineModel model = new BasicSanguineModel(3, 3, deck, deck, 3);
    Board board = model.board();

    boolean[][] mask = new boolean[5][5];
    mask[2][2] = true;
    InfluenceMask influence = new InfluenceMask(mask);
    BaseCard redCard = new BaseCard("Red", 1, 1, influence);
    BaseCard blueCard = new BaseCard("Blue", 1, 1, influence);

    board.setCell(new Position(0, 1), new CardCell(PlayerId.RED, redCard));
    board.setCell(new Position(2, 2), new CardCell(PlayerId.BLUE, blueCard));
  }

  /*@Test
  public void testRender() throws IOException {
    StringBuilder appendable = new StringBuilder();
    SanguineTextualView testview = new SanguineTextualView(model) {
      @Override
      public void render() throws IOException {
        super.render();
        appendable.append("2 R 0\n  3\n");
      }
    };

    testview.render();

    String expected =
        "1 _R_ 0\n" +
            "0 ___ 0\n" +
            "0 __B 1\n";

    assertEquals(expected, appendable.toString());
  }*/

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorRejectsNullModel() {
    new SanguineTextualView(null);
  }
}