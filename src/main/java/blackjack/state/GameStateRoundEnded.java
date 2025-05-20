package blackjack.state;

import blackjack.engine.BlackjackGameEngine;

/**
 * State representing the end of the round.
 * Handles full reset logic.
 */
public class GameStateRoundEnded implements GameState
{

	private BlackjackGameEngine gameEngineContext;

	public GameStateRoundEnded(BlackjackGameEngine gameEngineContext)
	{
		this.gameEngineContext = gameEngineContext;
	}

	/**
	 * Starting a new game is not valid until reset is pressed.
	 */
	@Override
	public void startGame()
	{
		gameEngineContext.broadcastGameLogMessage("Cannot start new game. Press Reset to continue.");
	}

	/**
	 * No player turn to end in this state.
	 */
	@Override
	public void endPlayerTurn()
	{
		gameEngineContext.broadcastGameLogMessage("Player turn already ended. Press Reset to play again.");
	}

	/**
	 * Round is already ended.
	 */
	@Override
	public void endRound()
	{
		gameEngineContext.broadcastGameLogMessage("Round already ended. Press Reset to continue.");
	}

	/**
	 * Resets game, clears UI log, and returns to Start state.
	 */
	@Override
	public void resetGame()
	{
		gameEngineContext.broadcastGameLogMessage("Game reset. New round starting...");
		gameEngineContext.fullyResetGame();
		gameEngineContext.broadcastGameLogReset();
		gameEngineContext.updateGameState(gameEngineContext.getStartState());
	}
}
