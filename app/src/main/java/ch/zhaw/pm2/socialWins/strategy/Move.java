package ch.zhaw.pm2.socialWins.strategy;

public class Move {
	private int score;
	private int column;
	
	public Move(int column, int score) {
		this.score = score;
		this.column = column;
	}
		
	public int getColumn() {
		return column;
	}
	
	public int getScore() {
		return score;
	}
	
	public void addScore(int additionalScore) {
		score += additionalScore;
	}
}
