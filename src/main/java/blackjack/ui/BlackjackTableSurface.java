package blackjack.ui;

import blackjack.engine.BlackjackGameEngine;

import javax.swing.*;
import java.awt.*;

/**

 Main visual area where player and dealer cards are rendered.
 */
public class BlackjackTableSurface extends JPanel {
	private BlackjackGameEngine gameEngine;
	private CardVisualizationPanel dealerCardDisplay;
	private CardVisualizationPanel playerCardDisplay;

	/**

	 Constructs the table surface and sets up card panels.
	 @param gameEngine main game engine reference
	 */
	public BlackjackTableSurface(BlackjackGameEngine gameEngine) {
		this.gameEngine = gameEngine;
		this.setLayout(new GridLayout(2, 1));
		dealerCardDisplay = new CardVisualizationPanel(this.gameEngine.getDealersCards());
		playerCardDisplay = new CardVisualizationPanel(this.gameEngine.getPlayersCards());

		dealerCardDisplay.setBackground(new Color(7, 121, 7));
		playerCardDisplay.setBackground(new Color(7, 121, 7));

		this.add(dealerCardDisplay);
		this.add(playerCardDisplay);
	}
}
