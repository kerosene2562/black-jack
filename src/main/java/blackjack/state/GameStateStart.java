package blackjack.state;

import blackjack.engine.BlackjackGameEngine;

/**
 * State representing the beginning of the game.
 * Responsible for initial dealing and transitioning to player turn.
 */
public class GameStateStart implements GameState {

	private BlackjackGameEngine gameEngineContext;

	/**
	 * Constructs the start state with a reference to the game engine.
	 * @param gameEngineContext main game engine
	 */
	public GameStateStart(BlackjackGameEngine gameEngineContext) {
		this.gameEngineContext = gameEngineContext;
	}

	/**
	 * Starts the game by dealing cards and transitioning to player state.
	 */
	@Override
	public void startGame() {
		gameEngineContext.broadcastGameLogMessage("Starting new round. Dealing cards...");
		gameEngineContext.executeCardDealSequence();
		gameEngineContext.updateGameState(gameEngineContext.getPlayerTurnState());
	}

	/**
	 * Player turn hasn't started yet — can't end it.
	 */
	@Override
	public void endPlayerTurn() {
		gameEngineContext.broadcastGameLogMessage("Cannot end turn: game has not started yet.");
	}

	/**
	 * Round hasn't started yet — can't end it.
	 */
	@Override
	public void endRound() {
		gameEngineContext.broadcastGameLogMessage("Cannot end round: game hasn't started yet.");
	}

	/**
	 * Allows resetting even before game starts — useful for edge cases.
	 */
	@Override
	public void resetGame() {
		gameEngineContext.broadcastGameLogMessage("Resetting game from Start state.");
		gameEngineContext.fullyResetGame();
		gameEngineContext.broadcastGameLogReset();
		gameEngineContext.updateGameState(gameEngineContext.getStartState());
	}
}
