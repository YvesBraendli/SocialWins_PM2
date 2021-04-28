package ch.zhaw.pm2.socialWins;

public class Board {
	private boolean isFull = false;
	private Chip[][] chips;
	
	private static int height = 8;
	
	public Board(int rows) {
		chips = new Chip[rows][height];
	}
}
