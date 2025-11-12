package playingCards;

public class NumberCard extends Cards {
    private int number;

    public NumberCard(String color, String path, int id, int number) {
        super(color, path, id);
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int isPlayable(Cards midCard) {
        int result = 0;

        if (midCard instanceof NumberCard) {
            if (this.getColor().equals(midCard.getColor()) || this.getNumber() == ((NumberCard) midCard).getNumber()) {
                result = 1;
            }
        }
        if (midCard instanceof WildCard) {
            if (this.getColor().equals(((WildCard) midCard).getSettedColor())) {
                result = 1;
            }
        }
        if (midCard instanceof ActionCard) {
            if (this.getColor().equals(midCard.getColor())) {
                result = 1;
            }
        }
        return result;
    }
}