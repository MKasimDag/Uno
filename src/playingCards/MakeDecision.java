package playingCards;

import java.util.LinkedList;
import java.util.Random;
import java.util.TreeMap;

public class MakeDecision {
    public static Cards playCard(TreeMap<String, LinkedList<Cards>> decks, String botName, Cards midCard) {

        Random rand = new Random();
        LinkedList<Cards> botDeck = decks.get(botName);
        LinkedList<Cards> playableCards = new LinkedList<>();
        String[] colorList = {"red", "blue", "yellow", "green"};
        
        for (Cards aCard: botDeck) {
            if (aCard.isPlayable(midCard) == 1) {
                playableCards.add(aCard);
            }
        }
        
        if (!playableCards.isEmpty()) {
            Cards playedCard = playableCards.get(rand.nextInt(playableCards.size()));
            if(playedCard instanceof WildCard) {
                ((WildCard) playedCard).setSettedColor(colorList[rand.nextInt(4)]);
            }
            return playedCard;
        }
        return null;
    }
}