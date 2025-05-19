package blackjack.state;

import blackjack.engine.BlackjackGameEngine;

/**
 * State representing the dealer's automated actions.
 * Transitions to round-end when appropriate.
 */
public class GameStateDealerTurn implements GameState {

	private BlackjackGameEngine gameEngineContext;

	public GameStateDealerTurn(BlackjackGameEngine gameEngineContext) {
		this.gameEngineContext = gameEngineContext;
	}

	@Override
	public void startGame() {
		// Not applicable
	}

	@Override
	public void endPlayerTurn() {
		// Not applicable — already in dealer turn
	}

	/**
	 * Ends the dealer turn and transitions to round-end state.
	 */
	@Override
	public void endRound() {
		gameEngineContext.updateGameState(gameEngineContext.getRoundEndState());
	}

	@Override
	public void resetGame() {
		// Not applicable in this state
	}
}