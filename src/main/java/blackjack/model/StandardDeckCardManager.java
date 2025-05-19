package blackjack.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Singleton class responsible for managing a standard deck of playing cards.
 * Provides shuffling and iteration using the Iterator pattern.
 */
public class StandardDeckCardManager implements Iterator<PlayingCardRepresentation>
{

	private static final String[] DEFAULT_CARD_SYMBOLS =
			{
			"AC", "AD", "AH", "AS", "2C", "2D", "2H", "2S",
			"3C", "3D", "3H", "3S", "4C", "4D", "4H", "4S",
			"5C", "5D", "5H", "5S", "6C", "6D", "6H", "6S",
			"7C", "7D", "7H", "7S", "8C", "8D", "8H", "8S",
			"9C", "9D", "9H", "9S", "0C", "0D", "0H", "0S",
			"JC", "JD", "JH", "JS", "QC", "QD", "QH", "QS",
			"KC", "KD", "KH", "KS"
		};

	private static final int MAXIMUM_CARD_COUNT = 52;

	private int currentDeckPosition = 0;
	private ArrayList<PlayingCardRepresentation> cardCollection;

	private static StandardDeckCardManager instance;

	/**
	 * Private constructor initializes the deck with all standard cards and shuffles them.
	 */
	private StandardDeckCardManager()
	{
		cardCollection = new ArrayList<>();
		initializeStandardDeck();
		shuffleDeck();
	}

	/**
	 * Initializes the deck with 52 standard cards.
	 */
	private void initializeStandardDeck()
	{
		for (String symbol : DEFAULT_CARD_SYMBOLS)
		{
			cardCollection.add(new PlayingCardRepresentation(symbol));
		}
	}

	/**
	 * Returns the singleton instance of this deck manager.
	 * @return instance of deck manager
	 */
	public static StandardDeckCardManager getInstance()
	{
		if (instance == null)
		{
			instance = new StandardDeckCardManager();
		}

		return instance;
	}

	/**
	 * Randomly shuffles the cards and resets iterator position.
	 */
	public void shuffleDeck()
	{
		Collections.shuffle(cardCollection);
		currentDeckPosition = 0;
	}

	/**
	 * Checks if there are more cards in the deck to iterate through.
	 * @return true if more cards are available
	 */
	@Override
	public boolean hasNext()
	{
		return currentDeckPosition < MAXIMUM_CARD_COUNT;
	}

	/**
	 * Returns the next card from the deck.
	 * @return the next PlayingCardRepresentation
	 */
	@Override
	public PlayingCardRepresentation next()
	{
		PlayingCardRepresentation nextCard = cardCollection.get(currentDeckPosition);
		currentDeckPosition++;
		return nextCard;
	}

	/**
	 * Allows inspection of remaining card count for debug or game logic.
	 * @return number of cards left
	 */
	public int getRemainingCardCount()
	{
		return MAXIMUM_CARD_COUNT - currentDeckPosition;
	}

	/**
	 * Returns a copy of all cards in the deck.
	 * @return list of all cards
	 */
	public ArrayList<PlayingCardRepresentation> getAllCardsSnapshot()
	{
		return new ArrayList<>(cardCollection);
	}
} 