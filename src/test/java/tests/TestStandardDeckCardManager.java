package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import blackjack.model.PlayingCardRepresentation;
import org.junit.jupiter.api.Test;

import blackjack.model.StandardDeckCardManager;

class TestStandardDeckCardManager {

	@Test
	void testGetInstance() {
		StandardDeckCardManager deck = StandardDeckCardManager.getInstance();
		StandardDeckCardManager anotherDeck = StandardDeckCardManager.getInstance();
		assertEquals(deck, anotherDeck);
	}
	
	@Test
	void testShuffle() {
		StandardDeckCardManager deck = StandardDeckCardManager.getInstance();
		deck.shuffle(); // reset the deck iterator to zero
		
		PlayingCardRepresentation playingCardRepresentationPreShuffle = deck.next();
		deck.shuffle();
		PlayingCardRepresentation playingCardRepresentationPostShuffle = deck.next();
		
		assertNotSame(playingCardRepresentationPreShuffle, playingCardRepresentationPostShuffle);
	}
	
	@Test
	void testDeckIterator() {
		StandardDeckCardManager deck = StandardDeckCardManager.getInstance();
		ArrayList<PlayingCardRepresentation> playingCardRepresentations = new ArrayList<PlayingCardRepresentation>();
		
		while(deck.hasNext()) {
			playingCardRepresentations.add(deck.next());
		}
		
		assertEquals(playingCardRepresentations.size(), 52);
	}

}
