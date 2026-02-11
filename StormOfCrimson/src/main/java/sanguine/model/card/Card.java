package sanguine.model.card;

/**
 * An interface that represents a card that must contain a:
 * name,
 * cost,
 * value,
 * and grid of influence.
 */
public interface Card {

  /**
   * Getter for the name of the card.
   *
   * @return The given name stored.
   */
  String name();

  /**
   * Getter for the name of the card.
   *
   * @return The number for the cost of the card.
   */
  int cost();

  /**
   * Getter for the value of the card.
   *
   * @return The number for the value of the card.
   */
  int value();

  /**
   * Getter for the influenceMask.
   *
   * @return the associated InfluenceMask.
   */
  InfluenceMask influenceMask();
}
