package blackjack.stats;

/**
 * Tracks win/loss/draw statistics for a player or dealer.
 */
public class PlayerStatisticsTracker
{
    private int wins;
    private int losses;
    private int draws;

    /**
     * Increments the win counter by 1.
     */
    public void recordWin()
    {
        wins++;
    }

    /**
     * Increments the loss counter by 1.
     */
    public void recordLoss()
    {
        losses++;
    }

    /**
     * Increments the draw (tie) counter by 1.
     */
    public void recordDraw()
    {
        draws++;
    }

    /**
     * Resets all statistics (wins, losses, draws) to zero.
     */
    public void resetStats()
    {
        wins = 0;
        losses = 0;
        draws = 0;
    }

    /**
     * Returns a formatted summary of the current statistics.
     * @return a string in format: "Wins: X, Losses: Y, Draws: Z"
     */
    public String getSummary()
    {
        return "Wins: " + wins + ", Losses: " + losses + ", Draws: " + draws;
    }
}
