package playingCards;

public class WildCard extends Cards {
    private String type;
    private String settedColor;
    private boolean doesAffectedGame;

    public WildCard(String color, String path, int id, String type) {
        super(color, path, id);
        this.type = type;
        this.setScoreVal(50);
        this.doesAffectedGame = false;
    }

    @Override
    public int isPlayable(Cards midCard) {
        return 1; // Wild cards are always playable
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSettedColor() {
        return settedColor;
    }

    public void setSettedColor(String settedColor) {
        this.settedColor = settedColor;
    }

    public boolean isDoesAffectedGame() {
        return doesAffectedGame;
    }

    public void setDoesAffectedGame(boolean doesAffectedGame) {
        this.doesAffectedGame = doesAffectedGame;
    }
}