// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a SWEN221 assignment.
// You may not distribute it in any other way without permission.
package swen221.cards.util;

import swen221.cards.core.Card;
import swen221.cards.core.Player;
import swen221.cards.core.Trick;

/**
 * Implements a simple computer player who plays the highest card available when
 * the trick can still be won, otherwise discards the lowest card available. In
 * the special case that the player must win the trick (i.e. this is the last
 * card in the trick), then the player conservatively plays the least card
 * needed to win.
 *
 * @author David J. Pearce
 *
 */
public class SimpleComputerPlayer extends AbstractComputerPlayer {

	/**
	 * Construct a new (simple) computer player with the given player information.
	 *
	 * @param player Key player informmation.
	 */
	public SimpleComputerPlayer(Player player) {
		super(player);
	}

	@Override
	public Card getNextCard(Trick trick) {
		// TODO: you need to implement this!
		boolean playerIsLead = trick.getCardsPlayed().size() == 0;
		Card.Suit trumpSuit = trick.getTrumps();

		if(playerIsLead) {
			return player.getHand().getBestCardInHand(trumpSuit, playerIsLead);
		}

		//Am not the lead
		Card.Suit leadSuit = trick.getLeadSuit();
		boolean playerHasLeadSuit = player.getHand().containsSuit(leadSuit);

		if(playerHasLeadSuit) {
			boolean playerCanWinThisGame = playerCanWinThisGame(trick);
			if(playerCanWinThisGame) {
				boolean playIsLastToPlay = trick.thisIsTheFinalMove();
				if(playIsLastToPlay) {
					Card highestCardOfLeadSuitPlayed = trick.getHighestCardOfSuit(leadSuit);
					return player.getHand().getHighestCardHigherThan(highestCardOfLeadSuitPlayed);
				}
				//Play highest card in lead suit
				return player.getHand().getHighestCardInSuit(leadSuit);
			}
			//Player cannot win this game
			//Play(return) lowest available card in suit
			return player.getHand().getLowestCardInSuit(leadSuit);
		}

		//Do not have the lead suit

		boolean playerHasTrumpSuit = player.getHand().containsSuit(trumpSuit);
		if(playerHasTrumpSuit) {
			boolean playerCanWinThisGame = playerCanWinThisGame(trick);
			if(playerCanWinThisGame) {
				//Play highest card in trump suit
				boolean playIsLastToPlay = trick.thisIsTheFinalMove();
				if(playIsLastToPlay) {
					Card highestCardOfTrumpSuitPlayed = trick.getHighestCardOfSuit(trumpSuit);
					return player.getHand().getHighestCardHigherThan(highestCardOfTrumpSuitPlayed);
				}
				return player.getHand().getHighestCardInSuit(trumpSuit);
			}
			//Cannot win the game
			//Play the lowest card in deck
			return player.getHand().getWorstCardInHand(trumpSuit);
		}

		//Do not have trump suit
		//Play the literal lowest card in the deck
		return player.getHand().getWorstCardInHand(trumpSuit);
	}

	private boolean playerCanWinThisGame(Trick trick) {
		Card.Suit leadSuit = trick.getLeadSuit();
		Card maxCardOfLeadSuit = trick.getHighestCardOfSuit(leadSuit);

		Card.Suit trumpSuit = trick.getTrumps();


		boolean playerHasLeadSuit = player.getHand().containsSuit(leadSuit);

		if(playerHasLeadSuit) {
			boolean trumpSuitHasBeenPlayed = trick.trumpSuitHasBeenPlayed();
			if(trumpSuitHasBeenPlayed && trumpSuit != leadSuit) {
				//cannot win cause u have to play lead card
				return false;
			}
			//Trump suit not been played
			boolean playerMaxOfLeadSuitIsHigherThanCurrentLeadCard =
					player.getHand().maxOfSameSuitInHandHigherThan(maxCardOfLeadSuit);
			if(playerMaxOfLeadSuitIsHigherThanCurrentLeadCard) {
				return true;
			}
			//player max of the lead suit is lower than of the current lead card
			return false;
		}
		//Does not have lead suit
		boolean playerHasTrumpSuit = player.getHand().containsSuit(trumpSuit);
		if(playerHasTrumpSuit) {
			boolean trumpSuitHasBeenPlayed = trick.trumpSuitHasBeenPlayed();
			if(trumpSuitHasBeenPlayed) {
				Card maxCardOfTrumpSuit = trick.getHighestCardOfSuit(trumpSuit);
				boolean playerMaxTrumpSuitIsHigherThanWhatHasBeenPlayed =
						player.getHand().maxOfSameSuitInHandHigherThan(maxCardOfTrumpSuit);

				if(playerMaxTrumpSuitIsHigherThanWhatHasBeenPlayed) {
					return true;
				}
				//do not have a trump card higher than what has been player
				return false;
			}
			//trump suit not played yet
			//Just play a trump card
			return true;
		}

		//Does not have trump suit
		//Cannot win
		return false;
	}
}
