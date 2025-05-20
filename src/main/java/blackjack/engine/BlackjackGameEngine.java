package blackjack.engine;

import blackjack.state.GameStateStart;
import blackjack.state.GameStatePlayerTurn;
import blackjack.state.GameStateDealerTurn;
import blackjack.state.GameStateRoundEnded;

import blackjack.model.PlayingCardRepresentation;
import blackjack.model.StandardDeckCardManager;
import blackjack.state.GameState;
import blackjack.stats.PlayerStatisticsTracker;
import blackjack.util.BlackjackDealer;
import blackjack.util.BlackjackParticipant;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Core game engine for running a single instance of Blackjack.
 * Manages states, transitions, players, and card drawing logic.
 */
public class BlackjackGameEngine extends Observable
{

	// Game states
	private GameState gameStartState;
	private GameState gamePlayerTurnState;
	private GameState gameDealerTurnState;
	private GameState gameRoundEndState;
	private GameState currentGameState;

	// Game statistics tracker
	private PlayerStatisticsTracker statsTracker;

	// Game participants
	public BlackjackParticipant primaryPlayer;
	public BlackjackDealer mainDealer;

	// Card deck
	private StandardDeckCardManager centralDeckManager;

	/**
	 * Initializes game engine, state machine, and participants.
	 */
	public BlackjackGameEngine()
	{
		gameStartState = new GameStateStart(this);
		gamePlayerTurnState = new GameStatePlayerTurn(this);
		gameDealerTurnState = new GameStateDealerTurn(this);
		gameRoundEndState = new GameStateRoundEnded(this);
		currentGameState = gameStartState;
		statsTracker = new PlayerStatisticsTracker();

		primaryPlayer = new BlackjackParticipant();
		mainDealer = new BlackjackDealer();
		centralDeckManager = StandardDeckCardManager.getInstance();
	}

	/**
	 * Updates the game to a new state.
	 */
	public void updateGameState(GameState newState)
	{
		currentGameState = newState;
	}

	/**
	 * Returns the current active game state.
	 */
	public GameState getCurrentGameState()
	{
		return currentGameState;
	}

	/**
	 * Returns the initial game state (before deal).
	 */
	public GameState getStartState()
	{
		return gameStartState;
	}

	/**
	 * Returns the player turn game state.
	 */
	public GameState getPlayerTurnState()
	{
		return gamePlayerTurnState;
	}

	/**
	 * Returns the dealer turn game state.
	 */
	public GameState getDealerTurnState()
	{
		return gameDealerTurnState;
	}

	/**
	 * Returns the round end game state.
	 */
	public GameState getRoundEndState()
	{
		return gameRoundEndState;
	}

	/**
	 * Returns the player participant object.
	 */
	public BlackjackParticipant getPlayer()
	{
		return primaryPlayer;
	}

	/**
	 * Returns cards currently held by the player.
	 */
	public ArrayList<PlayingCardRepresentation> getPlayersCards()
	{
		return getCardsHeldByPlayer();
	}

	/**
	 * Returns cards currently held by the dealer.
	 */
	public ArrayList<PlayingCardRepresentation> getDealersCards()
	{
		return getCardsHeldByDealer();
	}

	/**
	 * Starts the main game loop (used for dealer's automatic actions).
	 */
	public void runGameLoop() {
		beginGameLoopExecution();
	}

	/**
	 * Notifies observers (e.g., UI) with a log message.
	 */
	public void broadcastGameLogMessage(String messageContent)
	{
		setChanged();
		notifyObservers(messageContent);
	}

	/**
	 * Requests the UI to repaint (refresh display).
	 */
	public void broadcastUIRepaint()
	{
		setChanged();
		notifyObservers("repaint");
	}

	/**
	 * Notifies observers to reset the game log (typically clears event log panel).
	 */
	public void broadcastGameLogReset()
	{
		setChanged();
		notifyObservers("reset game log");
	}

	/**
	 * Executes the game loop, mainly handling dealer AI actions and transitions.
	 */
	public void beginGameLoopExecution()
	{
		boolean engineRunning = true;
		while (engineRunning)
		{
			/**
			 * Player turn logic
			 */
			if (currentGameState.equals(gamePlayerTurnState))
			{
				if (primaryPlayer.isHandBusted())
				{
					broadcastGameLogMessage("Player Busted!\n" + determineWinnerAnnouncement());
					currentGameState.endRound();
				}
				if (primaryPlayer.doesHavePerfectBlackjack())
				{
					broadcastGameLogMessage("Player hits 21!\n" + determineWinnerAnnouncement());
					currentGameState.endRound();
				}
			}
			/**
			 * Dealer turn logic
			 */
			if (currentGameState.equals(gameDealerTurnState))
			{
				if (isDealerTurnFinished())
				{
					if (mainDealer.doesHavePerfectBlackjack())
					{
						broadcastGameLogMessage("Dealer hits 21!\n" + determineWinnerAnnouncement());
					}
					else if (mainDealer.isHandBusted())
					{
						broadcastGameLogMessage("Dealer Busted!\n" + determineWinnerAnnouncement());
					}
					else
					{
						broadcastGameLogMessage("Dealer Holds.\n" + determineWinnerAnnouncement());
					}
					currentGameState.endRound();
				}

				if (!isDealerTurnFinished())
				{
					broadcastGameLogMessage("Dealer draws a card.");
					drawCardForDealer(false);
					if (mainDealer.doesHavePerfectBlackjack())
					{
						broadcastGameLogMessage("Dealer hits 21!\n" + determineWinnerAnnouncement());
						currentGameState.endRound();
					}
					if (mainDealer.isHandBusted())
					{
						broadcastGameLogMessage("Dealer Busted!\n" + determineWinnerAnnouncement());
						currentGameState.endRound();
					}
				}
			}

			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}

			broadcastUIRepaint();
		}
	}

	/**
	 * Fully resets the game by reshuffling the deck and clearing all hands.
	 */
	public void fullyResetGame()
	{
		reshuffleDeck();
		primaryPlayer.discardAllCardsFromHand();
		mainDealer.revealAllDealerCardsToPlayer();
		mainDealer.discardAllCardsFromHand();
	}

	/**
	 * Deals two cards to each participant. First dealer card is hidden.
	 */
	public void executeCardDealSequence()
	{
		for (int i = 0; i < 2; i++)
		{
			drawCardForPlayer();
			drawCardForDealer(i == 0);
		}
	}

	/**
	 * Draws a card for the dealer. Can be hidden (face-down).
	 * @param shouldHideCard whether the card should be turned face-down
	 */
	public void drawCardForDealer(boolean shouldHideCard)
	{
		if (centralDeckManager.hasNext())
		{
			PlayingCardRepresentation drawn = centralDeckManager.next();
			if (shouldHideCard)
			{
				drawn.turnCardFaceDown();
			}
			mainDealer.acquireCardIntoHand(drawn);
		}
	}

	/**
	 * Draws a visible card for the player.
	 */
	public void drawCardForPlayer()
	{
		if (centralDeckManager.hasNext())
		{
			PlayingCardRepresentation drawn = centralDeckManager.next();
			primaryPlayer.acquireCardIntoHand(drawn);
		}
	}

	/**
	 * Shuffles the central deck and resets iterator.
	 */
	public void reshuffleDeck()
	{
		centralDeckManager.shuffleDeck();
	}

	/**
	 * Returns the player's current hand.
	 */
	public ArrayList<PlayingCardRepresentation> getCardsHeldByPlayer()
	{
		return primaryPlayer.getHandCards();
	}

	/**
	 * Returns the dealer's current hand.
	 */
	public ArrayList<PlayingCardRepresentation> getCardsHeldByDealer()
	{
		return mainDealer.getHandCards();
	}

	/**
	 * Checks if the dealer is done drawing cards.
	 * @return true if dealer should stop drawing, false otherwise
	 */
	public boolean isDealerTurnFinished()
	{
		int dealerTotal = mainDealer.calculateTotalHandValue();
		int playerTotal = primaryPlayer.calculateTotalHandValue();

		return mainDealer.doesHavePerfectBlackjack()
				|| mainDealer.isHandBusted()
				|| (dealerTotal >= 17 && dealerTotal >= playerTotal);
	}

	/**
	 * Reveals all hidden cards in the dealer's hand.
	 */
	public void revealAllDealerCards()
	{
		mainDealer.revealAllDealerCardsToPlayer();
	}

	/**
	 * Returns the game statistics tracker (wins/losses/draws).
	 */
	public PlayerStatisticsTracker getStatistics()
	{
		return statsTracker;
	}

	/**
	 * Determines the winner based on hand values and bust conditions.
	 * Updates statistics accordingly.
	 * @return result message for the game log
	 */
	public String determineWinnerAnnouncement()
	{
		int playerTotal = primaryPlayer.calculateTotalHandValue();
		int dealerTotal = mainDealer.calculateTotalHandValue();

		if (primaryPlayer.isHandBusted() && !mainDealer.isHandBusted())
		{
			statsTracker.recordLoss();
			return "Dealer wins!\nClick Reset to try again.";
		}
		else if (!primaryPlayer.isHandBusted() && mainDealer.isHandBusted())
		{
			statsTracker.recordWin();
			return "Player wins!\nClick Reset to try again.";
		}
		else if (playerTotal > dealerTotal)
		{
			statsTracker.recordWin();
			return "Player wins!\nClick Reset to try again.";
		}
		else if (playerTotal < dealerTotal)
		{
			statsTracker.recordLoss();
			return "Dealer wins!\nClick Reset to try again.";
		}
		else
		{
			statsTracker.recordDraw();
			return "It's a tie!\nClick Reset to try again.";
		}
	}
}
