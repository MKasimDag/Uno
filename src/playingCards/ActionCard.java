package playingCards;

public class ActionCard extends Cards {
    private String type;
    private boolean doesAffectedGame;

    public ActionCard(String color, String path, int id, String type) {
        super(color, path, id);
        this.type = type.split(".jpg")[0];
        this.setScoreVal(20);
        this.doesAffectedGame = false;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int isPlayable(Cards midCard) {
        int result = 0;

        if (midCard instanceof NumberCard) {
            if (this.getColor().equals(midCard.getColor())) {
                result = 1;
            }
        }
        if (midCard instanceof WildCard) {
            if (this.getColor().equals(((WildCard) midCard).getSettedColor())) {
                result = 1;
            }
        }
        if (midCard instanceof ActionCard) {
            if (this.getColor().equals(midCard.getColor()) || this.getType().equals(((ActionCard) midCard).getType())) {
                result = 1;
            }
        }
        return result;
    }

    public boolean isDoesAffectedGame() {
        return doesAffectedGame;
    }

    public void setDoesAffectedGame(boolean doesAffectedGame) {
        this.doesAffectedGame = doesAffectedGame;
    }
}