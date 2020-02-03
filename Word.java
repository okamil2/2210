
public class Word {
	//initializing the variables
	 private String word;
	 private int score;
	
	//here is the constructor
	public Word(String word, int score) {
		this.word = word;
		this.score = score;
	}
	public String getKey() {
		return word;
	}
	public int getValue() {
		return score;
	}
}
