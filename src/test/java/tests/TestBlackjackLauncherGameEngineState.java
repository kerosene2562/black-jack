package tests;

import blackjack.engine.BlackjackGameEngine;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for verifying correct state transitions in BlackjackGameEngine.
 */
class TestBlackjackLauncherGameEngineState {

	BlackjackGameEngine blackjackGameEngine = new BlackjackGameEngine();

	/**
	 * Verifies that the initial state is the Start State.
	 */
	@Test
	void testInitialStateCorrect() {
		assertEquals(blackjackGameEngine.getCurrentGameState(), blackjackGameEngine.getStartState());
	}

	/**
	 * Verifies that state can be manually updated.
	 */
	@Test
	void testSetState() {
		blackjackGameEngine.updateGameState(blackjackGameEngine.getPlayerTurnState());
		assertEquals(blackjackGameEngine.getCurrentGameState(), blackjackGameEngine.getPlayerTurnState());
	}

	/**
	 * Verifies behavior of the Start state transitions.
	 */
	@Test
	void testStartState() {
		blackjackGameEngine.updateGameState(blackjackGameEngine.getStartState());

		// Methods that should do nothing in Start state
		blackjackGameEngine.getCurrentGameState().endPlayerTurn();
		assertEquals(blackjackGameEngine.getCurrentGameState(), blackjackGameEngine.getStartState());

		blackjackGameEngine.getCurrentGameState().resetGame();
		assertEquals(blackjackGameEngine.getCurrentGameState(), blackjackGameEngine.getStartState());

		blackjackGameEngine.getCurrentGameState().endRound();
		assertEquals(blackjackGameEngine.getCurrentGameState(), blackjackGameEngine.getStartState());

		// Start game should transition to PlayerTurn
		blackjackGameEngine.getCurrentGameState().startGame();
		assertEquals(blackjackGameEngine.getCurrentGameState(), blackjackGameEngine.getPlayerTurnState());
	}

	/**
	 * Verifies PlayerTurn state behavior.
	 */
	@Test
	void testPlayerTurnState() {
		blackjackGameEngine.updateGameState(blackjackGameEngine.getPlayerTurnState());

		blackjackGameEngine.getCurrentGameState().startGame();
		assertEquals(blackjackGameEngine.getCurrentGameState(), blackjackGameEngine.getPlayerTurnState());

		blackjackGameEngine.getCurrentGameState().resetGame();
		assertEquals(blackjackGameEngine.getCurrentGameState(), blackjackGameEngine.getPlayerTurnState());

		blackjackGameEngine.getCurrentGameState().endPlayerTurn();
		assertEquals(blackjackGameEngine.getCurrentGameState(), blackjackGameEngine.getDealerTurnState());

		// Return to player turn for testing endRound()
		blackjackGameEngine.updateGameState(blackjackGameEngine.getPlayerTurnState());
		blackjackGameEngine.getCurrentGameState().endRound();
		assertEquals(blackjackGameEngine.getCurrentGameState(), blackjackGameEngine.getRoundEndState());
	}

	/**
	 * Verifies DealerTurn state behavior.
	 */
	@Test
	void testDealerTurnState() {
		blackjackGameEngine.updateGameState(blackjackGameEngine.getDealerTurnState());

		blackjackGameEngine.getCurrentGameState().endPlayerTurn();
		assertEquals(blackjackGameEngine.getCurrentGameState(), blackjackGameEngine.getDealerTurnState());

		blackjackGameEngine.getCurrentGameState().resetGame();
		assertEquals(blackjackGameEngine.getCurrentGameState(), blackjackGameEngine.getDealerTurnState());

		blackjackGameEngine.getCurrentGameState().startGame();
		assertEquals(blackjackGameEngine.getCurrentGameState(), blackjackGameEngine.getDealerTurnState());

		blackjackGameEngine.getCurrentGameState().endRound();
		assertEquals(blackjackGameEngine.getCurrentGameState(), blackjackGameEngine.getRoundEndState());
	}

	/**
	 * Verifies RoundEnd state behavior.
	 */
	@Test
	void testRoundEndState() {
		blackjackGameEngine.updateGameState(blackjackGameEngine.getRoundEndState());

		blackjackGameEngine.getCurrentGameState().endPlayerTurn();
		assertEquals(blackjackGameEngine.getCurrentGameState(), blackjackGameEngine.getRoundEndState());

		blackjackGameEngine.getCurrentGameState().endRound();
		assertEquals(blackjackGameEngine.getCurrentGameState(), blackjackGameEngine.getRoundEndState());

		blackjackGameEngine.getCurrentGameState().startGame();
		assertEquals(blackjackGameEngine.getCurrentGameState(), blackjackGameEngine.getRoundEndState());

		blackjackGameEngine.getCurrentGameState().resetGame();
		assertEquals(blackjackGameEngine.getCurrentGameState(), blackjackGameEngine.getStartState());
	}
}
