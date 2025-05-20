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

	/**
	 * Starting a new game is invalid during the player's turn.
	 */
	@Override
	public void startGame()
	{
		gameEngineContext.broadcastGameLogMessage("Cannot start a new game: player turn in progress.");
	}

	/**
	 * Player ends their turn — dealer cards are revealed, state switches to dealer turn.
	 */
	@Override
	public void endPlayerTurn()
	{
		gameEngineContext.broadcastGameLogMessage("Player ends turn. Revealing dealer's cards.");
		gameEngineContext.revealAllDealerCards();
		gameEngineContext.updateGameState(gameEngineContext.getDealerTurnState());
	}

	/**
	 * Ends the round immediately and transitions to round-end state.
	 */
	@Override
	public void endRound()
	{
		gameEngineContext.broadcastGameLogMessage("Ending round from player turn.");
		gameEngineContext.updateGameState(gameEngineContext.getRoundEndState());
	}

	/**
	 * Allows manual reset during player's turn (not typical, but supported).
	 */
	@Override
	public void resetGame()
	{
		gameEngineContext.broadcastGameLogMessage("Game reset during player's turn.");
		gameEngineContext.fullyResetGame();
		gameEngineContext.updateGameState(gameEngineContext.getStartState());
	}
}
