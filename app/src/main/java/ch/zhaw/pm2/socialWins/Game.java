package ch.zhaw.pm2.socialWins;

import java.util.ArrayList;
import java.util.List;

public class Game {
	private List<Player> players = new ArrayList<>();
	private SocialWinsBoard board;
	private int winningLineLength;
	
	public Game(int winningLineLength) {
		this.winningLineLength = winningLineLength;
		int rows = 10; // refactor
		board = new SocialWinsBoard(rows);
	}
}
