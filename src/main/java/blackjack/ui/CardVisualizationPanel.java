package blackjack.ui;

import blackjack.model.PlayingCardRepresentation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**

 Custom panel that renders a list of cards as images horizontally.
 */
public class CardVisualizationPanel extends JPanel {
	private int cardWidth = 113;
	private int cardHeight = 157;
	private ArrayList<PlayingCardRepresentation> cardsToDisplay;

	/**

	 Constructs the card panel with a list of cards to display.
	 @param cards list of cards from player or dealer
	 */
	public CardVisualizationPanel(ArrayList<PlayingCardRepresentation> cards) {
		this.cardsToDisplay = cards;
	}
	/**

	 Paints all card images side by side on the panel.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			for (int i = 0; i < cardsToDisplay.size(); i++) {
				BufferedImage cardImg = ImageIO.read(new File(cardsToDisplay.get(i).retrieveCardImagePath()));
				g.drawImage(cardImg, (cardWidth + 3) * i, 50, cardWidth, cardHeight, this);
			}
		} catch (IOException ex) {
			System.out.println("Error loading card image: " + ex.getMessage());
		}
	}
}