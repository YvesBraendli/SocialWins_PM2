package ch.zhaw.pm2.socialWins;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Game {
	private Player[] players;
	private SocialWinsBoard board;
	private int winningLineLength;
	private Player winner;
	privaet boolean isSinglePlay;
	
	public Game(int winningLineLength, int numberOfPlayers) {
		// if winningLine is not in range throw exception
		// if numberofplayer is not in range throw exception

		this.winningLineLength = winningLineLength;
		
		if(numberOfPlayers == 1) {
			initializeSinglePlayerGame();
		}
		//multiplayer
		else{
			players = new Player[numberOfPlayers];
			int rows = 10; // refactor
			board = new SocialWinsBoard(rows);
			isSinglePlay = false;
		}
	}

	private void initializeSinglePlayerGame() {
		players = new Player[2]; // 2 is magic number
		isSinglePlay = true;
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
	 * @return return true if player has been successfully been added
	 */
	public boolean addPlayer(String name, Color color) {
		if (name == null || name.isEmpty())
			return false;
		if (color == null)
			return false;

		for (int i = 0; i < players.length; i++) {
			if (players[i] != null && (players[i].getColor() == color || players[i].getName().equals(name))) {
				return false; // because color or name already exists
			}
		}
		
		for (int i = 0; i < players.length; i++) {
			if (players[i] == null) {
				players[i] = new RealPlayer(name, color);
				return true;
			}
		}

		return false;
	}

	/**
	 * AddComputer Method provides functionality to add a ComputerPlayer to the game.
	 * The Parameters must contain Values and can't be null.
	 * @param name	name of the computer
	 * @param color	name of the computer
	 * @param level	level of the computer, level 1-3 are possible
	 * @return return true if computer has successfully been added
	 */
	public boolean addComputer(String name, Color color, int level) {
		if (name == null || name.isEmpty())
			return false;
		if (color == null)
			return false;

		// check level.
		if(!isSinglePlay) return false;
		
		// check if no other computer exists
		if(players[2] != null && players[2] instanceof Computer) return false;
		
		players[2] = new Computer(name, color, level);
		
	}
	
	/**
	 * get Winner() returns the player that has won the game.
	 * @return 	the Player that has won the game. 
	 * 			returns null if no player has won yet.
	 */
	public Player getWinner() {
		return winner;
	}

	private boolean hasWinner() {
		return false;
	}
}
