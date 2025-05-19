package blackjack.logging;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Logs game events to an external file for record-keeping or debugging.
 */
public class GameLogger {

    private static final String LOG_FILE = "blackjack.log";

    public static void logEvent(String event) {
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write("[" + LocalDateTime.now() + "] " + event + "\n");
        } catch (IOException e) {
            System.err.println("Logging error: " + e.getMessage());
        }
    }
}
