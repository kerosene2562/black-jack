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

	@Override
	public void startGame()
	{
		// Not applicable
	}

	@Override
	public void endPlayerTurn()
	{
		// Not applicable
	}

	@Override
	public void endRound()
	{
		// Not applicable
	}

	/**
	 * Resets game, UI and state to new round.
	 */
	@Override
	public void resetGame()
	{
		gameEngineContext.fullyResetGame();
		gameEngineContext.broadcastGameLogReset();
		gameEngineContext.updateGameState(gameEngineContext.getStartState());
	}
}
