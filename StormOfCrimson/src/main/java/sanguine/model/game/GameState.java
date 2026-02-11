package sanguine.model.game;

import sanguine.model.Board;
import sanguine.model.PlayerId;
import sanguine.model.deck.Deck;
import sanguine.model.deck.Hand;

/**
 * Record to hold the true state of the game after construction and in between every play.
 * The GameState always holds a legal state and is modified within the model by the RuleKeeper.
 *
 * @param board The saved Board
 * @param redDeck The available deck for the red player
 * @param redHand The current hand for the red player
 * @param blueDeck The available deck for the blue player
 * @param blueHand The current hand for the red player
 * @param current Current Player to play
 * @param consecutiveSkips number of consecutiveSkips as a win condition.
 */
public record GameState(Board board, Deck redDeck,
                        Hand redHand, Deck blueDeck,
                        Hand blueHand, PlayerId current,
                        int consecutiveSkips) {}
