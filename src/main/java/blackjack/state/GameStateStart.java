package blackjack.state;

import blackjack.engine.BlackjackGameEngine;

/**
 * State representing the beginning of the game.
 * Responsible for initial dealing and transitioning to player turn.
 */
public class GameStateStart implements GameState
{

	private BlackjackGameEngine gameEngineContext;

	/**
	 * Constructs the start state with a reference to the game engine.
	 * @param gameEngineContext main game engine
	 */
	public GameStateStart(BlackjackGameEngine gameEngineContext)
	{
		this.gameEngineContext = gameEngineContext;
	}

	/**
	 * Starts the game by dealing cards and transitioning to player state.
	 */
	@Override
	public void startGame()
	{
		gameEngineContext.executeCardDealSequence();
		gameEngineContext.updateGameState(gameEngineContext.getPlayerTurnState());
	}

	/**
	 * No-op for endPlayerTurn during start.
	 */
	@Override
	public void endPlayerTurn()
	{
		// Not applicable
	}

	/**
	 * No-op for endRound during start.
	 */
	@Override
	public void endRound()
	{
		// Not applicable
	}

	/**
	 * No-op for resetGame during start.
	 */
	@Override
	public void resetGame()
	{
		// Not applicable
	}
}