// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a SWEN221 assignment.
// You may not distribute it in any other way without permission.
package swen221.cards.core;

import java.util.*;

/**
 * Represents a hand of cards held by a player. As the current round proceeds,
 * the number of cards in the hand will decrease. When the round is over, new
 * cards will be delt and added to this hand.
 *
 * @author David J. Pearce
 *
 */
public class Hand implements Cloneable, Iterable<Card> {
	private SortedSet<Card> cards = new TreeSet<>();

	@Override
	public Iterator<Card> iterator() {
		return cards.iterator();
	}

	/**
	 * Check whether a given card is contained in this hand, or not.
	 *
	 * @param card
	 * @return <code>true</code> if the card is contained in this hand;
	 *         <code>false</code> otherwise.
	 */
	public boolean contains(Card card) {
		return cards.contains(card);
	}

	/**
	 * Return all cards in this hand which match the given suit.
	 *
	 * @param suit
	 * @return The set of matching cards (if any).
	 */
	public Set<Card> matches(Card.Suit suit) {
		HashSet<Card> r = new HashSet<>();
		for(Card c : cards) {
			if(c.suit() == suit) {
				r.add(c);
			}
		}
		return r;
	}
	public Hand(){}
	public Hand(SortedSet<Card> cards) {
		this.cards = cards;
	}
	/**
	 * Add a card to the hand.
	 * @param card The card to be added.
	 */
	public void add(Card card) {
		cards.add(card);
	}

	/**
	 * Remove a card from the hand.
	 * @param card The card to be removed.
	 */
	public void remove(Card card) {
		cards.remove(card);
	}

	/**
	 * Get number of cards in this hand.
	 *
	 * @return The size of this hand.
	 */
	public int size() {
		return cards.size();
	}

	/**
	 * Remove all cards from this hand.
	 */
	public void clear() {
		cards.clear();
	}

	/**
	 * Method to get the highest rank Card in the deck
	 * FIXME edit to fix for trump suit
	 */
	public Card getBestCardInHand(Card.Suit trumps, boolean isLeader) {
		if(this.containsSuit(trumps)) {
			return this.getHighestCardInSuit(trumps);
		}
		if(isLeader) {
			Card.Rank maxRank = cards.stream().max(Comparator.comparing(Card::rank)).get().rank();
			return cards.stream().filter(card -> card.rank().equals(maxRank))
					.max(Card::compareTo).get();
		}
		return cards.stream().max(Card::compareTo).get();
	}

	/**
	 * Method to get highest Card in specific suit
	 */
	public Card getHighestCardInSuit(Card.Suit s) {
		return cards.stream().filter(card -> card.suit().equals(s))
				.max(Card::compareTo).get();
	}
	/**
	 * Method to conservatively get the lowest card above given card
	 */
	public Card getHighestCardHigherThan(Card toBeat) {
		return cards.stream().filter(card -> card.compareTo(toBeat) > 0)
				.min(Card::compareTo).get();
	}
	/**
	 * Method to get lowest Card in specific suit
	 */
	public Card getLowestCardInSuit(Card.Suit s) {
		return cards.stream().filter(card -> card.suit().equals(s))
				.min(Card::compareTo).get();
	}

	/**
	 * Method to get lowest Card in the deck.
	 */
	public Card getWorstCardInHand(Card.Suit trumps) {
		return cards.stream().min(Card::compareTo).get();
	}
	/**
	 * Method to check if in the current hand there is a Card of the same suit higher than arg
	 */
	public boolean maxOfSameSuitInHandHigherThan(Card s) {
		Card max = this.getHighestCardInSuit(s.suit());
		return max.compareTo(s) > 0;
	}

	/**
	 * Check that there is a specific suit in the current hand
	 */
	public boolean containsSuit(Card.Suit s) {
		return cards.stream().anyMatch(card -> card.suit().equals(s));
	}
	/** Helper method to copy (create a new instance) of this hand class but identical */
	public Hand copy() {
		SortedSet<Card> cardsCopy = new TreeSet<>();

		for(Card c: cards) {
			cardsCopy.add(c.copy());
		}
		return new Hand(cardsCopy);
	}
}
