package blackjack.util;

import blackjack.model.PlayingCardRepresentation;

import java.util.ArrayList;

/**
 * Represents a participant in the Blackjack game (either player or dealer).
 * Maintains the hand of cards and provides methods to assess hand value.
 */
public class BlackjackParticipant {

	/** The cards currently held in hand */
	protected ArrayList<PlayingCardRepresentation> handOfCards;

	/** Constructs a new Blackjack participant with an empty hand */
	public BlackjackParticipant() {
		handOfCards = new ArrayList<>();
	}

	public boolean hasBlackjackImmediately() {
		return handOfCards.size() == 2 && doesHavePerfectBlackjack();
	}

	/**
	 * Adds a card to the participant's hand.
	 * @param card the card to add
	 */
	public void acquireCardIntoHand(PlayingCardRepresentation card) {
		handOfCards.add(card);
	}

	/**
	 * Clears the hand of all cards.
	 */
	public void discardAllCardsFromHand() {
		handOfCards.clear();
	}

	/**
	 * Calculates the total value of the hand, handling aces appropriately.
	 * @return total value of cards
	 */
	public int calculateTotalHandValue() {
		int totalValue = 0;
		int aceCount = 0;

		for (PlayingCardRepresentation card : handOfCards) {
			String symbol = card.getCardSymbolCode();

			if (symbol.contains("A")) aceCount++;
			else if (symbol.contains("2")) totalValue += 2;
			else if (symbol.contains("3")) totalValue += 3;
			else if (symbol.contains("4")) totalValue += 4;
			else if (symbol.contains("5")) totalValue += 5;
			else if (symbol.contains("6")) totalValue += 6;
			else if (symbol.contains("7")) totalValue += 7;
			else if (symbol.contains("8")) totalValue += 8;
			else if (symbol.contains("9")) totalValue += 9;
			else if (symbol.contains("0") || symbol.contains("J") || symbol.contains("Q") || symbol.contains("K")) {
				totalValue += 10;
			}
		}

		// Handle ace value choices
		for (int i = 0; i < aceCount; i++) {
			totalValue += (totalValue + 11 > 21) ? 1 : 11;
		}

		return totalValue;
	}

	/**
	 * Checks if the hand value is over 21.
	 * @return true if busted
	 */
	public boolean isHandBusted() {
		return calculateTotalHandValue() > 21;
	}

	/**
	 * Checks if the participant has exactly 21.
	 * @return true if hand is blackjack
	 */
	public boolean doesHavePerfectBlackjack() {
		return calculateTotalHandValue() == 21;
	}

	/**
	 * Returns the current hand.
	 * @return list of cards in hand
	 */
	public ArrayList<PlayingCardRepresentation> getHandCards() {
		return handOfCards;
	}

	/**
	 * Displays hand to console (debugging/logging).
	 */
	public void printHandToConsole() {
		System.out.print("Current Hand: ");
		for (PlayingCardRepresentation card : handOfCards) {
			System.out.print(card + ", ");
		}
		System.out.println();
	}

	/**
	 * Returns number of cards currently held.
	 * @return number of cards in hand
	 */
	public int getNumberOfCardsInHand() {
		return handOfCards.size();
	}
}
