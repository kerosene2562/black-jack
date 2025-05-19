package blackjack.util;

import blackjack.model.PlayingCardRepresentation;

import java.util.List;

/**
 * Provides simple strategic suggestions to the player based on their hand value.
 */
public class GameAdvisor
{

    public static String advisePlayer(List<PlayingCardRepresentation> hand)
    {
        int value = calculateHandValue(hand);
        if (value <= 11) return "Recommendation: Hit.";
        if (value >= 17) return "Recommendation: Stay.";
        return "Use your judgment!";
    }

    private static int calculateHandValue(List<PlayingCardRepresentation> hand)
    {
        int value = 0;
        int aces = 0;

        for (PlayingCardRepresentation card : hand)
        {
            String symbol = card.getCardSymbolCode();
            if (symbol.contains("A")) aces++;
            else if (symbol.contains("2")) value += 2;
            else if (symbol.contains("3")) value += 3;
            else if (symbol.contains("4")) value += 4;
            else if (symbol.contains("5")) value += 5;
            else if (symbol.contains("6")) value += 6;
            else if (symbol.contains("7")) value += 7;
            else if (symbol.contains("8")) value += 8;
            else if (symbol.contains("9")) value += 9;
            else value += 10;
        }

        for (int i = 0; i < aces; i++)
        {
            value += (value + 11 > 21) ? 1 : 11;
        }

        return value;
    }

}
