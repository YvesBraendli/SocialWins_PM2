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

	/**
	 * AddPlayer Method provides the functionality to add Players to the game.
	 * Parameters must contain values and can't be null.
	 * 
	 * @param name  name of the player
	 * @param color color of the player
	 * @return return boolean if player has been successfully added
	 */
	public boolean addPlayer(String name, Color color) {
		if (name == null || name.isEmpty())
			return false;
		if (color == null)
			return false;

		for (int i = 0; i < players.length; i++) {
			if (players[i].getColor() == color) {
				return false; // because color allready exists
			}
		}
		for (int i = 0; i < players.length; i++) {
			if (players[i] == null) {
				players[i] = new Player(name, color);
				return true;
			}
		}

		return false;
	}

	public Player getWinner() {
		return winner;
	}

	private boolean hasWinner() {
		return false;
	}
}
