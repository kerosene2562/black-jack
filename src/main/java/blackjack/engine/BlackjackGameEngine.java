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
public class BlackjackGameEngine extends Observable {

	private GameState gameStartState;
	private GameState gamePlayerTurnState;
	private GameState gameDealerTurnState;
	private GameState gameRoundEndState;
	private GameState currentGameState;
	private PlayerStatisticsTracker statsTracker;


	public BlackjackParticipant primaryPlayer;
	public BlackjackDealer mainDealer;
	private StandardDeckCardManager centralDeckManager;

	/**
	 * Initializes game engine, state machine, and participants.
	 */
	public BlackjackGameEngine() {
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
	 * Sets the current game state.
	 * @param newState the new state to enter
	 */
	public void updateGameState(GameState newState) {
		currentGameState = newState;
	}

	public GameState getCurrentGameState() {
		return currentGameState;
	}

	public GameState getStartState() {
		return gameStartState;
	}

	public GameState getPlayerTurnState() {
		return gamePlayerTurnState;
	}

	public GameState getDealerTurnState() {
		return gameDealerTurnState;
	}

	public GameState getRoundEndState() {
		return gameRoundEndState;
	}

	public BlackjackParticipant getPlayer() {
		return primaryPlayer;
	}

	public ArrayList<PlayingCardRepresentation> getPlayersCards() {
		return getCardsHeldByPlayer();
	}

	public ArrayList<PlayingCardRepresentation> getDealersCards() {
		return getCardsHeldByDealer();
	}

	public void runGameLoop() {
		beginGameLoopExecution();
	}


	public void broadcastGameLogMessage(String messageContent) {
		setChanged();
		notifyObservers(messageContent);
	}

	public void broadcastUIRepaint() {
		setChanged();
		notifyObservers("repaint");
	}

	public void broadcastGameLogReset() {
		setChanged();
		notifyObservers("reset game log");
	}

	/**
	 * Runs the game loop (used for dealer logic and repaint triggering).
	 */
	public void beginGameLoopExecution() {
		boolean engineRunning = true;
		while (engineRunning) {

			if (currentGameState.equals(gamePlayerTurnState)) {
				if (primaryPlayer.isHandBusted()) {
					broadcastGameLogMessage("Player Busted!\n" + determineWinnerAnnouncement());
					currentGameState.endRound();
				}

				if (primaryPlayer.doesHavePerfectBlackjack()) {
					broadcastGameLogMessage("Player hits 21!\n" + determineWinnerAnnouncement());
					currentGameState.endRound();
				}
			}

			if (currentGameState.equals(gameDealerTurnState)) {
				if (isDealerTurnFinished()) {
					if (mainDealer.doesHavePerfectBlackjack()) {
						broadcastGameLogMessage("Dealer hits 21!\n" + determineWinnerAnnouncement());
					} else if (mainDealer.isHandBusted()) {
						broadcastGameLogMessage("Dealer Busted!\n" + determineWinnerAnnouncement());
					} else {
						broadcastGameLogMessage("Dealer Holds.\n" + determineWinnerAnnouncement());
					}
					currentGameState.endRound();
				}

				if (!isDealerTurnFinished()) {
					broadcastGameLogMessage("Dealer draws a card.");
					drawCardForDealer(false);
					if (mainDealer.doesHavePerfectBlackjack()) {
						broadcastGameLogMessage("Dealer hits 21!\n" + determineWinnerAnnouncement());
						currentGameState.endRound();
					}
					if (mainDealer.isHandBusted()) {
						broadcastGameLogMessage("Dealer Busted!\n" + determineWinnerAnnouncement());
						currentGameState.endRound();
					}
				}
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			broadcastUIRepaint();
		}
	}

	/**
	 * Resets game state and hands.
	 */
	public void fullyResetGame() {
		reshuffleDeck();
		primaryPlayer.discardAllCardsFromHand();
		mainDealer.revealAllDealerCardsToPlayer();
		mainDealer.discardAllCardsFromHand();
	}

	/**
	 * Deals two cards each to player and dealer (first dealer card is hidden).
	 */
	public void executeCardDealSequence() {
		for (int i = 0; i < 2; i++) {
			drawCardForPlayer();
			drawCardForDealer(i == 0);
		}
	}

	public void drawCardForDealer(boolean shouldHideCard) {
		if (centralDeckManager.hasNext()) {
			PlayingCardRepresentation drawn = centralDeckManager.next();
			if (shouldHideCard) {
				drawn.turnCardFaceDown();
			}
			mainDealer.acquireCardIntoHand(drawn);
		}
	}

	public void drawCardForPlayer() {
		if (centralDeckManager.hasNext()) {
			PlayingCardRepresentation drawn = centralDeckManager.next();
			primaryPlayer.acquireCardIntoHand(drawn);
		}
	}

	public void reshuffleDeck() {
		centralDeckManager.shuffleDeck();
	}

	public ArrayList<PlayingCardRepresentation> getCardsHeldByPlayer() {
		return primaryPlayer.getHandCards();
	}

	public ArrayList<PlayingCardRepresentation> getCardsHeldByDealer() {
		return mainDealer.getHandCards();
	}

	public boolean isDealerTurnFinished() {
		int dealerTotal = mainDealer.calculateTotalHandValue();
		int playerTotal = primaryPlayer.calculateTotalHandValue();

		return mainDealer.doesHavePerfectBlackjack()
				|| mainDealer.isHandBusted()
				|| (dealerTotal >= 17 && dealerTotal >= playerTotal);
	}

	public void revealAllDealerCards() {
		mainDealer.revealAllDealerCardsToPlayer();
	}

	public PlayerStatisticsTracker getStatistics() {
		return statsTracker;
	}

	public String determineWinnerAnnouncement() {
		int playerTotal = primaryPlayer.calculateTotalHandValue();
		int dealerTotal = mainDealer.calculateTotalHandValue();

		if (primaryPlayer.isHandBusted() && !mainDealer.isHandBusted()) {
			statsTracker.recordLoss();
			return "Dealer wins!\nClick Reset to try again.";
		} else if (!primaryPlayer.isHandBusted() && mainDealer.isHandBusted()) {
			statsTracker.recordWin();
			return "Player wins!\nClick Reset to try again.";
		} else if (playerTotal > dealerTotal) {
			statsTracker.recordWin();
			return "Player wins!\nClick Reset to try again.";
		} else if (playerTotal < dealerTotal) {
			statsTracker.recordLoss();
			return "Dealer wins!\nClick Reset to try again.";
		} else {
			statsTracker.recordDraw();
			return "It's a tie!\nClick Reset to try again.";
		}
	}
} 