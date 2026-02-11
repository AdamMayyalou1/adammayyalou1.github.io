package testmodel.testcell;

import static org.junit.Assert.assertThrows;

import org.junit.Test;
import sanguine.model.card.InfluenceMask;

/**
 * A class for testing InfluenceMask and its construction.
 */
public class TestInfluenceMask {

  @Test
  public void testInfluenceMaskConstruction() {
    boolean[][] mask = new boolean[6][5];
    boolean[][] mask2 = new boolean[5][4];
    boolean[][] mask3 = new boolean[5][5];

    assertThrows(IllegalArgumentException.class, () ->
        new InfluenceMask(mask));
    assertThrows(IllegalArgumentException.class, () -> new InfluenceMask(mask2));
    new InfluenceMask(mask3);
  }

}

