package blackjack.stats;

/**
 * Tracks win/loss/draw statistics for a player or dealer.
 */
public class PlayerStatisticsTracker
{

    private int wins;
    private int losses;
    private int draws;

    public void recordWin()
    {
        wins++;
    }

    public void recordLoss()
    {
        losses++;
    }

    public void recordDraw()
    {
        draws++;
    }

    public void resetStats()
    {
        wins = 0;
        losses = 0;
        draws = 0;
    }

    
    public String getSummary()
    {
        return "Wins: " + wins + ", Losses: " + losses + ", Draws: " + draws;
    }
}
