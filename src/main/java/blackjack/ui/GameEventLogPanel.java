package blackjack.ui;

import javax.swing.*;

/**

 Panel displaying a scrollable text area for game event messages.
 */
public class GameEventLogPanel extends JScrollPane {
	private JTextArea eventLogArea;

	/**

	 Constructs the game event log panel and initializes default text.
	 */
	public GameEventLogPanel() {
		eventLogArea = new JTextArea("Welcome to Blackjack!\nPress the Deal button to begin.\n");
		eventLogArea.setColumns(20);
		eventLogArea.setEditable(false);
		this.setViewportView(eventLogArea);
	}
	/**

	 Appends a message to the game log.
	 @param message the event description
	 */
	public void logMessage(Object message) {
		eventLogArea.append(message + "\n");
	}
	/**

	 Clears the game log and resets it to the welcome message.
	 */
	public void resetLog() {
		eventLogArea.setText("Welcome to Blackjack!\nPress the Deal button to begin.\n");
	}
}