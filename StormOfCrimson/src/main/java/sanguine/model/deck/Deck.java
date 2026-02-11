package sanguine.model.deck;

import java.util.List;
import sanguine.model.card.Card;

/**
 * A class that represents a deck of cards that belongs to a player.
 */
public class Deck {
  List<Card> cards;


  /**
   * A simple constructor for a deck.
   *
   * @param cards List of cards the given list of cards for the deck.
   */
  public Deck(List<Card> cards) {
    this.cards = List.copyOf(cards).reversed();
  }

  /**
   * Produces the size of the deck of cards.
   *
   * @return (int) number of cards within the deck.
   */
  public int size() {
    return cards.size();
  }

  /**
   * Determines whether the deck of cards has no remaining cards.
   *
   * @return (boolean) whether the deck is empty or not.
   */
  public boolean isEmpty() {
    return cards.isEmpty();
  }

  /**
   * Peeks at the deck of cards and produces the card at the top of the deck.
   *
   * @return (card) the last card within the deck.
   */
  public Card peek() {
    if (isEmpty()) {
      return null;
    }
    return cards.getLast();
  }

  /**
   * Pops off the last card in the deck and keeps the rest of the cards.
   *
   * @return (deck) the new deck with the last element removed.
   */
  public Deck pop() {
    if (isEmpty()) {
      return null;
    }
    return new Deck(cards.subList(0, size() - 1));
  }

  /**
   * A record class that represents a drawn card and the resulting deck.
   *
   * @param card (card) the card that was drawn from the deck.
   * @param deck (deck) the rest of the deck after the card is drawn.
   */
  public record Drawn(Card card, Deck deck) {}

  /**
   * The method that produces the drawn record using the current deck.
   *
   * @return (Drawn) the drawn card and its resulting deck.
   */
  public Drawn draw() {
    return new Drawn(this.peek(), this.pop());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Deck other)) {
      return false;
    }
    return cards.equals(other.cards);
  }

  @Override
  public int hashCode() {
    return cards.hashCode();
  }
}
