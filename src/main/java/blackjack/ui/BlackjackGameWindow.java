package blackjack.ui;

import blackjack.engine.BlackjackGameEngine;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**

 Main game window responsible for assembling all UI components and displaying them.
 */
public class BlackjackGameWindow extends JFrame implements Observer
{
	private BlackjackGameEngine gameEngine;
	private JPanel mainLayoutContainer;
	private BlackjackTableSurface tableSurface;
	private GameEventLogPanel eventLogPanel;
	private JPanel playerControlsPanel;

	/**

	 Constructs the main window of the blackjack game.
	 @param gameEngine reference to the game engine
	 */
	public BlackjackGameWindow(BlackjackGameEngine gameEngine)
	{
		this.gameEngine = gameEngine;
		mainLayoutContainer = new JPanel(new BorderLayout());
		tableSurface = new BlackjackTableSurface(this.gameEngine);
		eventLogPanel = new GameEventLogPanel();
		playerControlsPanel = new PlayerActionControlPanel(this.gameEngine, eventLogPanel, mainLayoutContainer);

		mainLayoutContainer.add(tableSurface, BorderLayout.CENTER);
		mainLayoutContainer.add(playerControlsPanel, BorderLayout.PAGE_END);
		mainLayoutContainer.add(eventLogPanel, BorderLayout.LINE_END);

		this.setTitle("Blackjack Game");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1000, 600);
		this.setContentPane(mainLayoutContainer);
		this.setVisible(true);
	}
	public JPanel getMainLayoutContainer()
	{
		return this.mainLayoutContainer;
	}

	public GameEventLogPanel getEventLogPanel()
	{
		return this.eventLogPanel;
	}

	/**

	 Updates UI components based on observable events from the game engine.
	 */
	@Override
	public void update(Observable o, Object arg)
	{
		if (arg.equals("repaint"))
			this.mainLayoutContainer.repaint();
		else if (arg.equals("reset game log"))
			this.eventLogPanel.resetLog();
		else
			this.eventLogPanel.logMessage(arg);
	}
}