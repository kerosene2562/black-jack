package tests;

import blackjack.BlackjackGameEngine;
import org.junit.jupiter.api.Test;

class TestBlackjackLauncherGameEngineState {
	
	BlackjackGameEngine blackjackGameEngine;
	
	TestBlackjackLauncherGameEngineState() {
		blackjackGameEngine = new BlackjackGameEngine();
	}

	@Test
	void testInititalStateCorrect() {
		assertEquals(blackjackGameEngine.getCurrentState(), blackjackGameEngine.getStartState());
	}
	
	@Test
	void testSetState() {
		blackjackGameEngine.setState(blackjackGameEngine.getPlayerTurnState());
		assertEquals(blackjackGameEngine.getCurrentState(), blackjackGameEngine.getPlayerTurnState());
	}
	
	@Test 
	void testStartState() {
		blackjackGameEngine.setState(blackjackGameEngine.getStartState());
		
		blackjackGameEngine.getCurrentState().endPlayerTurn();
		assertEquals(blackjackGameEngine.getCurrentState(), blackjackGameEngine.getStartState());
		
		blackjackGameEngine.getCurrentState().resetGame();
		assertEquals(blackjackGameEngine.getCurrentState(), blackjackGameEngine.getStartState());
		
		blackjackGameEngine.getCurrentState().endRound();
		assertEquals(blackjackGameEngine.getCurrentState(), blackjackGameEngine.getStartState());
		
		blackjackGameEngine.getCurrentState().startGame();
		assertEquals(blackjackGameEngine.getCurrentState(), blackjackGameEngine.getPlayerTurnState());
	}
	
	@Test 
	void testPlayerTurnState() {
		blackjackGameEngine.setState(blackjackGameEngine.getPlayerTurnState());
		
		blackjackGameEngine.getCurrentState().startGame();
		assertEquals(blackjackGameEngine.getCurrentState(), blackjackGameEngine.getPlayerTurnState());
		
		blackjackGameEngine.getCurrentState().resetGame();
		assertEquals(blackjackGameEngine.getCurrentState(), blackjackGameEngine.getPlayerTurnState());
		
		blackjackGameEngine.getCurrentState().endPlayerTurn();
		assertEquals(blackjackGameEngine.getCurrentState(), blackjackGameEngine.getDealerTurnState());
		
		blackjackGameEngine.setState(blackjackGameEngine.getPlayerTurnState());
		
		blackjackGameEngine.getCurrentState().endRound();
		assertEquals(blackjackGameEngine.getCurrentState(), blackjackGameEngine.getRoundEndState());
		
				
	}
	
	@Test 
	void testDealerTurnState() {
		blackjackGameEngine.setState(blackjackGameEngine.getDealerTurnState());
		
		blackjackGameEngine.getCurrentState().endPlayerTurn();
		assertEquals(blackjackGameEngine.getCurrentState(), blackjackGameEngine.getDealerTurnState());
		
		blackjackGameEngine.getCurrentState().resetGame();
		assertEquals(blackjackGameEngine.getCurrentState(), blackjackGameEngine.getDealerTurnState());
		
		blackjackGameEngine.getCurrentState().startGame();
		assertEquals(blackjackGameEngine.getCurrentState(), blackjackGameEngine.getDealerTurnState());
		
		blackjackGameEngine.getCurrentState().endRound();
		assertEquals(blackjackGameEngine.getCurrentState(), blackjackGameEngine.getRoundEndState());
	}
	
	@Test 
	void testRoundEndState() {
		blackjackGameEngine.setState(blackjackGameEngine.getRoundEndState());
		
		blackjackGameEngine.getCurrentState().endPlayerTurn();
		assertEquals(blackjackGameEngine.getCurrentState(), blackjackGameEngine.getRoundEndState());
		
		blackjackGameEngine.getCurrentState().endRound();
		assertEquals(blackjackGameEngine.getCurrentState(), blackjackGameEngine.getRoundEndState());
		
		blackjackGameEngine.getCurrentState().startGame();
		assertEquals(blackjackGameEngine.getCurrentState(), blackjackGameEngine.getRoundEndState());
		
		blackjackGameEngine.getCurrentState().resetGame();
		assertEquals(blackjackGameEngine.getCurrentState(), blackjackGameEngine.getStartState());
	}

}
