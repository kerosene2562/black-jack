package blackjack.ui;

import blackjack.engine.BlackjackGameEngine;
import blackjack.logging.GameLogger;
import blackjack.util.GameAdvisor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**

 Panel containing action buttons for the player (Deal, Hit, Stay, Reset).
 */
public class PlayerActionControlPanel extends JPanel
{
	private BlackjackGameEngine gameEngine;
	private GameEventLogPanel eventLogPanel;
	private JPanel mainLayoutContainer;

	private final JButton dealButton = new JButton("Deal");
    private final JButton requestCardButton = new JButton("Hit");
    private final JButton holdTurnButton = new JButton("Stay");
    private final JButton restartGameButton = new JButton("Reset");

	/**

	 Constructs the action button panel for the player.
	 @param gameEngine reference to the main game logic
	 @param eventLogPanel reference to the game event log display
	 @param mainLayoutContainer reference to the main UI container
	 */
	public PlayerActionControlPanel(BlackjackGameEngine gameEngine, GameEventLogPanel eventLogPanel, JPanel mainLayoutContainer)
	{
		this.gameEngine = gameEngine;
		this.eventLogPanel = eventLogPanel;
		this.mainLayoutContainer = mainLayoutContainer;

		initListeners();

		this.add(dealButton);
		this.add(requestCardButton);
		this.add(holdTurnButton);
		this.add(restartGameButton);
	}

	private void initListeners() {
		dealButton.addActionListener(new DealButtonHandler());
		requestCardButton.addActionListener(new HitButtonHandler());
		holdTurnButton.addActionListener(new StayButtonHandler());
		restartGameButton.addActionListener(new ResetButtonHandler());
	}

	private void logAdvice() {
		String advice = GameAdvisor.advisePlayer(gameEngine.getPlayer().getHandCards());
		eventLogPanel.logMessage(advice);
	}

	private void repaintUI() {
		mainLayoutContainer.repaint();
	}
	/**

	 Action triggered when the Deal button is pressed.
	 */
	private class DealButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			gameEngine.getCurrentGameState().startGame();
			if (gameEngine.getPlayer().hasBlackjackImmediately())
			{
				eventLogPanel.logMessage("Player has Blackjack!");
				eventLogPanel.logMessage("Dealer's Turn");
				gameEngine.getCurrentGameState().endPlayerTurn();
			}
			else
			{
				eventLogPanel.logMessage("Player's turn!");
				logAdvice();
				GameLogger.logEvent("New round started. Player cards: " + gameEngine.getPlayer().getHandCards());
			}
			repaintUI();
		}
	}
	/**

	 Action triggered when the Hit button is pressed.
	 */
	private class HitButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (gameEngine.getCurrentGameState().equals(gameEngine.getPlayerTurnState()))
			{
				eventLogPanel.logMessage("Player Hits!");
				gameEngine.drawCardForPlayer();

				logAdvice();

				GameLogger.logEvent("Player chose to Hit. Hand: " + gameEngine.getPlayer().getHandCards());

				repaintUI();
			}
		}
	}
	/**

	 Action triggered when the Stay button is pressed.
	 */
	private class StayButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (gameEngine.getCurrentGameState().equals(gameEngine.getPlayerTurnState()))
			{
				eventLogPanel.logMessage("Player Stays!");
				eventLogPanel.logMessage("Dealer's Turn");

				GameLogger.logEvent("Player chose to Stay.");

				gameEngine.getCurrentGameState().endPlayerTurn();
				repaintUI();
			}
		}
	}
	/**

	 Action triggered when the Reset button is pressed.
	 */
	private class ResetButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			GameLogger.logEvent("Game reset by player.");
			gameEngine.getCurrentGameState().resetGame();
			eventLogPanel.logMessage(gameEngine.getStatistics().getSummary());
			repaintUI();
		}
	}
}