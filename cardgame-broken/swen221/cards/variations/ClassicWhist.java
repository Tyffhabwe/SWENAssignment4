// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a SWEN221 assignment.
// You may not distribute it in any other way without permission.
package swen221.cards.variations;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import swen221.cards.core.Card;
import swen221.cards.core.CardGame;
import swen221.cards.core.Player;
import swen221.cards.core.Trick;
import swen221.cards.util.AbstractCardGame;

/**
 * An implementation of the "classical" rules of Whist.
 *
 * @author David J. Pearce
 *
 */
public class ClassicWhist extends AbstractCardGame {

	/**
	 * Construct a new game of classic whist.
	 */
	public ClassicWhist() {
	}
	public ClassicWhist(Map<Player.Direction, Player> players, Map<Player.Direction,Integer> tricks,
						Map<Player.Direction,Integer> scores, Card.Suit trumps, Trick currentTrick) {
		super(players, tricks, scores, trumps, currentTrick);
	}

	@Override
	public CardGame getCardCopyNewConstructor(Map<Player.Direction, Player> players,
											  Map<Player.Direction, Integer> tricks,
											  Map<Player.Direction, Integer> scores,
											  Card.Suit trumps, Trick currentTrick) {

		return new ClassicWhist(players, tricks, scores, trumps, currentTrick);
	}

	@Override
	public String getName() {
		return "Classic Whist";
	}

	@Override
	public boolean isGameFinished() {
		for (Player.Direction d : Player.Direction.values()) {
			if (scores.get(d) == 5) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void deal(List<Card> deck) {
		currentTrick = null;
		for (Player.Direction d : Player.Direction.values()) {
			players.get(d).getHand().clear();
		}
		Player.Direction d = Player.Direction.NORTH;
		for (int i = 0; i < deck.size(); ++i) {
			Card card = deck.get(i);
			players.get(d).getHand().add(card);
			d = d.next();
		}
	}
}
