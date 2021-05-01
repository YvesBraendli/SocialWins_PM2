package ch.zhaw.pm2.socialWins;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Game {
	private Player[] players;
	private SocialWinsBoard board;
	private int winningLineLength;
	private Player winner;
	private boolean isSinglePlay;
	private int currentPlayerIndex;

	/**
	 * Constructs a Game object for a social wins game.
	 * 
	 * @param winningLineLength indicates the number of chips that have to be in a
	 *                          row to win the game
	 * @param numberOfPlayers   number of the players
	 * @throws IllegalArgumentsException if winning line is not between 3 and 6 or
	 *                                   numer of players is not between 1 and 8
	 */
	public Game(int winningLineLength, int numberOfPlayers) {
		if (winningLineLength < 3 || winningLineLength > 6 || numberOfPlayers < 1 || numberOfPlayers > 8) {
			throw new IllegalArgumentException();
		}

		this.winningLineLength = winningLineLength;
		currentPlayerIndex = 0;
		
		if (numberOfPlayers == 1) {
			initializeSinglePlayerGame();
		}
		// multiplayer
		else {
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

	/**
	 * the start method starts the game if all player have been initialized and
	 * added correctly
	 * 
	 * @return true if the game has been started
	 */
	public boolean start() {
		for (int i = 0; i < players.length; i++) {
			if (players[i] == null) {
				return false;
			}
			if(!isSinglePlay && players[i] instanceof Computer) {
				return false;
			}
		}
		
		if(isSinglePlay) {
			if(!(players[0] instanceof HumanPlayer && players[1] instanceof Computer)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * this method does next move for current player. if the arguments are valid and
	 * position is free the chip gets added <br>
	 * if the game is started in single player modus, the computer will do its turn
	 * immediately afterwards.
	 * 
	 * @param row    row to throw chip in
	 * @param column column to throw chip in
	 * @return true if chip has been added to board
	 */
	public boolean nextMove(int row, int column) {
		boolean isAdded = board.addChip(row, column, players[currentPlayerIndex].getColor());

		if (!isAdded)
			return false;

		if (isSinglePlay) {
			((Computer) players[1]).nextMove();
		} else {
			switchToNextPlayer();
		}
		updateWinner();
		return true;
	}

	private void switchToNextPlayer() {
		if (currentPlayerIndex == (players.length - 1)) {
			currentPlayerIndex = 0;
			return;
		}

		currentPlayerIndex++;
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
				players[i] = new HumanPlayer(name, color);
				return true;
			}
		}

		return false;
	}

	/**
	 * AddComputer Method provides functionality to add a ComputerPlayer to the
	 * game. The Parameters must contain Values and can't be null.
	 * 
	 * @param name  name of the computer
	 * @param color name of the computer
	 * @param level level of the computer, level 1-3 are possible
	 * @return return true if computer has successfully been added
	 */
	public boolean addComputer(String name, Color color, int level) {
		if (name == null || name.isEmpty())
			return false;
		if (color == null)
			return false;

		if (level < 1 || level > 3)
			return false;

		if (!isSinglePlay)
			return false;

		// check if no other computer exists
		if (players[1] != null && players[1] instanceof Computer)
			return false;

		players[1] = new Computer(name, color, level);
		return true;
	}

	/**
	 * get Winner() returns the player that has won the game.
	 * 
	 * @return the Player that has won the game. returns null if no player has won
	 *         yet.
	 */
	public Player getWinner() {
		return winner;
	}

	private void updateWinner() {
		boolean hasWinner = board.hasChipsInARow(winningLineLength);
		Color winnerColor = null;
		if (hasWinner) {
			winnerColor = board.getColorWithChipsInARow(winningLineLength);
		}

		for (int i = 0; i < players.length; i++) {
			if (players[i] != null && players[i].getColor() == winnerColor) {
				winner = players[i];
				return;
			}
		}
	}
}
