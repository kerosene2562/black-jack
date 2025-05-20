package blackjack.state;

import blackjack.engine.BlackjackGameEngine;

/**
 * State representing the dealer's automated actions.
 * Transitions to round-end when appropriate.
 */
public class GameStateDealerTurn implements GameState
{

	private BlackjackGameEngine gameEngineContext;

	public GameStateDealerTurn(BlackjackGameEngine gameEngineContext)
	{
		this.gameEngineContext = gameEngineContext;
	}

	/**
	 * Starting a game is not valid during dealer turn.
	 */
	@Override
	public void startGame()
	{
		gameEngineContext.broadcastGameLogMessage("Cannot start game: dealer is currently playing.");
	}

	/**
	 * Ending the player's turn is irrelevant in dealer's turn.
	 */
	@Override
	public void endPlayerTurn()
	{
		gameEngineContext.broadcastGameLogMessage("Player turn already ended. Dealer is acting.");
	}

	/**
	 * Ends the dealer's turn and transitions to the round-end state.
	 */
	@Override
	public void endRound()
	{
		gameEngineContext.broadcastGameLogMessage("Dealer turn finished. Ending round.");
		gameEngineContext.updateGameState(gameEngineContext.getRoundEndState());
	}

	/**
	 * Resets the game manually from dealer state (not usual).
	 */
	@Override
	public void resetGame()
	{
		gameEngineContext.broadcastGameLogMessage("Game reset during dealer's turn.");
		gameEngineContext.fullyResetGame();
		gameEngineContext.updateGameState(gameEngineContext.getStartState());
	}
}
