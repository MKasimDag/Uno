package playingCards;

public abstract class Cards {
	private String color;
	private int scoreVal;
	private String path;
	private int id;
	
	public Cards(String color, String path, int id) {
		super();
		this.color = color;
		this.path = path;
		this.id = id;
	}
	public abstract int isPlayable(Cards midCard);
	
	public int getScoreVal() {
		return scoreVal;
	}

	public void setScoreVal(int scoreVal) {
		this.scoreVal = scoreVal;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
