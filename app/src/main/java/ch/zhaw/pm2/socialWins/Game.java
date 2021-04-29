package ch.zhaw.pm2.socialWins;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Game {
	private Player[] players;
	private SocialWinsBoard board;
	private int winningLineLength;
	private Player winner;
	
	public Game(int winningLineLength, int NumberOfPlayers) {
		// if winningLine is not in range throw exception
		// if numberofplayer is not in range throw exception
		
		this.winningLineLength = winningLineLength;
		players = new Player[NumberOfPlayers];
		int rows = 10; // refactor
		board = new SocialWinsBoard(rows);
	}
	
	public void start() {
		// have all players been set? otherwise stop.
	}
	
	public void addPlayer(String name, Color color) {
		
	}
	
	public Player getWinner() {
	}
}
