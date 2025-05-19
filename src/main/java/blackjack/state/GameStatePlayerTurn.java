package blackjack.state;

import blackjack.engine.BlackjackGameEngine;

/**
 * State representing the player's turn.
 * Transitions to dealer when player ends their turn.
 */
public class GameStatePlayerTurn implements GameState
{

	private BlackjackGameEngine gameEngineContext;

	public GameStatePlayerTurn(BlackjackGameEngine gameEngineContext)
	{
		this.gameEngineContext = gameEngineContext;
	}

	@Override
	public void startGame()
	{
		// Not applicable
	}

	/**
	 * Player ends their turn — dealer cards are revealed, state switches.
	 */
	@Override
	public void endPlayerTurn()
	{
		gameEngineContext.revealAllDealerCards();
		gameEngineContext.updateGameState(gameEngineContext.getDealerTurnState());
	}

	@Override
	public void endRound()
	{
		gameEngineContext.updateGameState(gameEngineContext.getRoundEndState());
	}

	@Override
	public void resetGame()
	{
		// Not applicable
	}
}