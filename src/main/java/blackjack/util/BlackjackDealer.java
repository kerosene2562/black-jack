package blackjack.util;

import blackjack.model.PlayingCardRepresentation;

/**
 * Represents the dealer in the Blackjack game. Inherits common behavior from BlackjackParticipant.
 * Provides logic to flip all hidden cards face-up at once.
 */
public class BlackjackDealer extends BlackjackParticipant
{

	/**
	 * Constructs a new dealer.
	 */
	public BlackjackDealer()
	{
		super();
	}

	/**
	 * Flips all cards currently in hand face-up.
	 * This is typically done at the end of the round.
	 */
	public void revealAllDealerCardsToPlayer()
	{
		for (PlayingCardRepresentation card : handOfCards)
		{
			card.turnCardFaceUp();
		}
	}

	/**
	 * Checks if dealer has at least one face-down card.
	 * @return true if any card is face-down
	 */
	public boolean isHidingAnyCards()
	{
		for (PlayingCardRepresentation card : handOfCards)
		{
			if (card.isCardCurrentlyFaceDown())
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Reveals only the first face-down card (used to simulate suspense).
	 */
	public void revealSingleFaceDownCard()
	{
		for (PlayingCardRepresentation card : handOfCards)
		{
			if (card.isCardCurrentlyFaceDown())
			{
				card.turnCardFaceUp();
				break;
			}
		}
	}
}
