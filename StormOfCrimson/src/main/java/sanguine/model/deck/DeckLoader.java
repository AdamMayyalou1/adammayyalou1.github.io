package sanguine.model.deck;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import sanguine.model.card.BaseCard;
import sanguine.model.card.Card;
import sanguine.model.card.InfluenceMask;

/**
 * The DeckLoader class that contains the method to read config files.
 */
public class DeckLoader {
  /**
   * A util method that takes in a given config file and creates a new
   * deck of cards.
   *
   * @param file (File) the given config file.
   * @return (deck) the translated deck of cards.
   */
  public static Deck readConfig(File file) {
    List<Card> cards = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = br.readLine()) != null) {
        line = line.trim();
        if (line.isEmpty()) {
          continue;
        }

        // Obtain the card's name, cost, and value
        String[] parts = line.split("\\s+");
        String name = parts[0];
        int cost = Integer.parseInt(parts[1]);
        int value = Integer.parseInt(parts[2]);

        // read the influence grid
        boolean[][] influenceGrid = new boolean[5][5];
        for (int r = 0; r < 5; r++) {
          String row = br.readLine().trim();
          for (int c = 0; c < 5; c++) {
            char charAt =  row.charAt(c);
            influenceGrid[r][c] = (charAt == 'I');
          }
        }

        BaseCard card = new BaseCard(name, cost, value, new InfluenceMask(influenceGrid));
        cards.add(card);
      }

    } catch (IOException e) {
      throw new RuntimeException("Error reading config file", e);
    }

    return new Deck(cards);
  }
}