package blackjack.model;

/**
 * Represents a playing card used in the Blackjack game.
 * Each card has a code (like "AH", "2D") and can be face-down or face-up.
 */
public class PlayingCardRepresentation
{
	private String cardSymbolCode;
	private boolean isCardFaceDown;

	/**
	 * Constructs a new card with a given code.
	 * @param cardSymbolCode the symbol of the card (e.g. "AS" for Ace of Spades)
	 */
	public PlayingCardRepresentation(String cardSymbolCode)
	{
		this.cardSymbolCode = cardSymbolCode;
	}

	/**
	 * Gets the symbol code of the card.
	 * @return the symbol code
	 */
	public String getCardSymbolCode()
	{
		return cardSymbolCode;
	}

	/**
	 * Gets the file path to the card image, depending on whether it's face-down or face-up.
	 * @return path to card image
	 */
	public String retrieveCardImagePath()
	{
		String baseImageDirectory = "assets/img/";
		return isCardFaceDown ? baseImageDirectory + "CardBack.png" : baseImageDirectory + cardSymbolCode + ".png";
	}

	/**
	 * Checks if the card is face-down.
	 * @return true if face-down, false if face-up
	 */
	public boolean isCardCurrentlyFaceDown()
	{
		return isCardFaceDown;
	}

	/**
	 * Flips the card to face-down position.
	 */
	public void turnCardFaceDown()
	{
		isCardFaceDown = true;
	}

	/**
	 * Flips the card to face-up position.
	 */
	public void turnCardFaceUp()
	{
		isCardFaceDown = false;
	}

	/**
	 * Returns a string representation of the card.
	 * @return string with code and face-down state
	 */
	@Override
	public String toString()
	{
		return cardSymbolCode + " [Face down: " + isCardFaceDown + "]";
	}
}
