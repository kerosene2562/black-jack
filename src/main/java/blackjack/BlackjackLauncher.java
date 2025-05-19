package blackjack;

import blackjack.engine.BlackjackGameEngine;
import blackjack.ui.BlackjackGameWindow;

/**
 * Launcher class for starting the Blackjack game with GUI.
 */
public class BlackjackLauncher
{

	public static void main(String[] args)
	{
		BlackjackGameEngine engine = new BlackjackGameEngine();
		BlackjackGameWindow gui = new BlackjackGameWindow(engine);
		engine.addObserver(gui);
		engine.runGameLoop();
	}
}
