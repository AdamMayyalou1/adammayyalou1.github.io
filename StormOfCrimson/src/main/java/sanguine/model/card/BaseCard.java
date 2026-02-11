package sanguine.model.card;

/**
 * A base card class that represents a basic type of card.
 * Can be used in the Queen's blood game.
 *
 * @param name (String) the name of the card.
 * @param cost (int) the number of pawns needed to use the card.
 * @param value (int) the score value of the card.
 * @param influenceMask (InfluenceMask) the tiles that the card influences.
 */
public record BaseCard(String name, int cost, int value, InfluenceMask influenceMask)
    implements Card {
  /**
   * Checks for exceptions when being constructed.
   *
   * @param name (String) the name of the card.
   * @param cost (int) the number of pawns needed to use the card.
   * @param value (int) the score value of the card.
   * @param influenceMask (InfluenceMask) the tiles that the card influences.
   */
  public BaseCard {
    if (name == null) {
      throw new IllegalArgumentException("name is null");
    } else if (name.contains(" ")) {
      throw new IllegalArgumentException("name cannot contain spaces");
    } else if (cost < 1 || cost > 3) {
      throw new IllegalArgumentException("cost must be between 1 and 3 inclusive");
    } else if (value <= 0) {
      throw new IllegalArgumentException("value must be greater than 0");
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BaseCard card = (BaseCard) o;
    return cost == card.cost && value == card.value
        && influenceMask.equals(card.influenceMask) && name.equals(card.name);
  }

  @Override
  public int hashCode() {
    int result = name.hashCode();
    result = 31 * result + cost;
    result = 31 * result + value;
    result = 31 * result + influenceMask.hashCode();
    return result;
  }

}
