package tests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import blackjack.model.StandardDeckCardManager;
import blackjack.model.PlayingCardRepresentation;

/**
 * Unit tests for StandardDeckCardManager singleton and iterator behavior.
 */
class TestStandardDeckCardManager {

	/**
	 * Tests that the Singleton instance always returns the same object.
	 */
	@Test
	void testGetInstance() {
		StandardDeckCardManager deck = StandardDeckCardManager.getInstance();
		StandardDeckCardManager anotherDeck = StandardDeckCardManager.getInstance();
		assertEquals(deck, anotherDeck, "Singleton instances should be the same.");
	}

	/**
	 * Tests that shuffling the deck changes the order of the cards.
	 */
	@Test
	void testShuffle() {
		StandardDeckCardManager deck = StandardDeckCardManager.getInstance();
		deck.shuffleDeck();
		PlayingCardRepresentation firstCard = deck.next();
		deck.shuffleDeck();
		PlayingCardRepresentation secondCard = deck.next();
		assertNotEquals(firstCard, secondCard, "First cards before and after shuffle should differ.");
	}

	/**
	 * Tests that the deck contains exactly 52 cards after shuffling and iteration.
	 */
	@Test
	void testDeckIterator() {
		StandardDeckCardManager deck = StandardDeckCardManager.getInstance();
		deck.shuffleDeck(); // Ensure full deck order
		ArrayList<PlayingCardRepresentation> cards = new ArrayList<>();

		while (deck.hasNext()) {
			cards.add(deck.next());
		}

		assertEquals(52, cards.size(), "Deck should contain 52 cards.");
	}
}
