package sanguine.model.deck;

import java.util.ArrayList;
import java.util.List;
import sanguine.model.card.Card;


/**
 * Represents the set of cards a player currently holds.
 */
public record Hand(List<Card> cards) {
  /**
   * A simple constructor for the hand.
   *
   * @param cards The given list of cards for the hand.
   */
  public Hand(List<Card> cards) {
    this.cards = List.copyOf(cards);
  }

  /**
   * Returns the cards currently in the hand as an unmodifiable list.
   */
  @Override
  public List<Card> cards() {
    return cards;
  }

  /**
   * Returns the number of cards in the hand.
   */
  public int size() {
    return cards.size();
  }

  /**
   * Adds a card to the hand, returning a new Hand instance.
   */
  public Hand add(Card card) {
    List<Card> newCards = new ArrayList<>(cards);
    newCards.add(card);
    return new Hand(newCards);
  }

  /**
   * Removes a card from the hand, returning a new Hand instance.
   * Throws IllegalArgumentException if the card is not in hand.
   */
  public Hand remove(Card card) {
    if (!cards.contains(card)) {
      throw new IllegalArgumentException("card not found in hand");
    }
    List<Card> newCards = new ArrayList<>(cards);
    newCards.remove(card);
    return new Hand(newCards);
  }
}