package ch.zhaw.pm2.socialWins;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map.Entry;

import ch.zhaw.pm2.socialWins.strategy.ComputerAdvancedStrategy;
import ch.zhaw.pm2.socialWins.strategy.ComputerBeginnerStrategy;
import ch.zhaw.pm2.socialWins.strategy.ComputerIntermediateStrategy;
import ch.zhaw.pm2.socialWins.strategy.Strategy;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Game class provides the logic for a social wins game. It works closely
 * together with the board class to update the current game board. the game
 * class also does the next move for the current player and switches to next
 * player or requests the next move from the computer.
 * 
 * @author yves braendli, robin meier, nadine moser
 *
 */
public class Game {
	private Player[] players;
	private Board board;
	private int winningLineLength;

	private boolean isSinglePlay;
	private int currentPlayerIndex;

	private IntegerProperty nextComputerMove = new SimpleIntegerProperty();
	private Player winner;

	/**
	 * Constructs a SinglePlayer Game for a social wins game.
	 * 
	 * @param winningLineLength indicates the number of chips that have to be in a
	 *                          row to win the game
	 * @param userName          name of the user
	 * @param level             difficulty for the computer strategy
	 * @param columns           width of the board
	 * @param rows              height of the board
	 * @throws InvalidLevelException 
	 * @throws IllegalArgumentsException if winning line is not between 3 and 6 or
	 *                                   userName is null or empty or computer level
	 *                                   is not between 1 and 3
	 */
	public Game(int winningLineLength, String userName, int level, int columns, int rows) {
		if (!isValidWinningLineLength(winningLineLength) || !isValidName(userName) || !isValidLevel(level)) {
			throw new IllegalArgumentException();
		}

		this.winningLineLength = winningLineLength;
		currentPlayerIndex = 0;

		players = new Player[2];
		board = new Board(rows, columns);
		isSinglePlay = true;

		Color userColor = Config.SINGLEPLAYER_USERCOLOR;
		Color computerColor = Config.SINGLEPLAYER_COMPUTERCOLOR;
		String computerName = Config.SINGLEPLAYER_COMPUTERNAME;
		addPlayer(userName, userColor);
		Strategy strategy = getStrategy(level, board.getNumberOfRows(), board.getNumberOfColumns(), winningLineLength);
		players[1] = new Computer(computerName, computerColor, strategy, board, winningLineLength);

	}

	private Strategy getStrategy(int level, int numberOfRows, int numberOfColumns, int winningRowLength) {
		switch (level) {
		case 1:
			return new ComputerBeginnerStrategy(numberOfRows, numberOfColumns, winningRowLength);
		case 2: 
			return new ComputerIntermediateStrategy(numberOfRows, numberOfColumns, winningRowLength);
		case 3:
			return new ComputerAdvancedStrategy(numberOfRows, numberOfColumns, winningRowLength);		
		default:
			return new ComputerIntermediateStrategy(numberOfRows, numberOfColumns, winningRowLength);
		}
	}
	
	/**
	 * Constructs a MultiPlayer game for a social wins game.
	 * 
	 * @param winningLineLength indicates the number of chips that have to be in a
	 *                          row to win the game
	 * @param users             contains all userColor and userNames from players
	 * @param columns           width of the board
	 * @param rows              height of the board
	 * @throws IllegalArgumentsException if winning line is not between 3 and 6 or
	 *                                   name from players are null, empty or aren't
	 *                                   unique
	 * 
	 */
	public Game(int winningLineLength, HashMap<Color, String> users, int columns, int rows) {
		if (!isValidWinningLineLength(winningLineLength) || !isValidPlayerAmount(users)) {
			throw new IllegalArgumentException();
		}
		players = new Player[users.size()];
		board = new Board(rows, columns);
		isSinglePlay = false;

		this.winningLineLength = winningLineLength;
		currentPlayerIndex = 0;

		for (Entry<Color, String> entry : users.entrySet()) {
			boolean isAdded = addPlayer(entry.getValue(), entry.getKey());
			if (!isAdded) {
				throw new IllegalArgumentException();
			}
		}

	}

	/**
	 * returns true if the game is a single play, false if multi player game
	 * 
	 * @return boolean if single play
	 */
	public boolean isSinglePlay() {
		return isSinglePlay;
	}

	/**
	 * Gets ComputerMove the computer did last.
	 * 
	 * @return The next Move that the computer did in this game.
	 */
	public int getNextComputerMove() {
		return this.nextComputerMove.get();
	}

	/**
	 * Gets the current state bound property of the nextComputerMove.
	 * 
	 * @return the current nextComputerMove bound property of this game.
	 */
	public IntegerProperty nextComputerMoveBoundProperty() {
		return nextComputerMove;
	}

	private boolean isValidWinningLineLength(int winningLineLength) {
		return !(winningLineLength < Config.LOWEST_POSSIBLE_WINNINGROW
				|| winningLineLength > Config.HIGHEST_POSSIBLE_WINNINGROW);
	}

	private boolean isValidLevel(int level) {
		return !(level < 1 || level > 3);
	}

	private boolean isValidName(String name) {
		return !(name == null || name.isEmpty() || name.isBlank());
	}

	private boolean isValidPlayerAmount(HashMap<Color, String> users) {
		return !(users.size() < 2 || users.size() > 8);
	}

	/**
	 * this method does next move for current player. if the arguments are valid and
	 * position is free the chip gets added <br>
	 * if the game is started in single player modus, the computer will do its turn
	 * immediately afterwards.
	 * 
	 * @param column column to throw chip in
	 * @return true if chip has been added to board
	 */
	public boolean nextMove(int column) {
		boolean isAdded = board.addChip(column, players[currentPlayerIndex].getColor());
		if (!isAdded) {
			return false;
		}

		if (!isSinglePlay) {
			switchToNextPlayer();
		}
		updateWinner();
		return true;
	}

	public void doComputerMove() {
		boolean isValidComputerTurn = false;
		while (!isValidComputerTurn) {
			int nextMove = ((Computer) players[1]).nextMove();
			isValidComputerTurn = board.addChip(nextMove, Config.SINGLEPLAYER_COMPUTERCOLOR);
			if (isValidComputerTurn) {
				nextComputerMove.set(nextMove);
				nextComputerMove.set(-1);
			}
		}
		updateWinner();
	}

	private void switchToNextPlayer() {
		if (currentPlayerIndex == (players.length - 1)) {
			currentPlayerIndex = 0;
			return;
		}

		currentPlayerIndex++;
	}

	private boolean addPlayer(String name, Color color) {
		if (!isValidName(name)) {
			return false;
		}

		for (int i = 0; i < players.length; i++) {
			if (players[i] != null && (players[i].getColor() == color || players[i].getName().equals(name))) {
				return false;
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
	 * getWinningLineLength() returns the number value of the line length, that is
	 * necessary for a player, to win the game.
	 * 
	 * @return the length of the line, which a player has to build to win the game.
	 */
	public int getWinningLineLength() {
		return winningLineLength;
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
		Color winnerColor = board.getColorWithChipsInARow(winningLineLength);
		if (winnerColor == null) {
			return;
		}

		for (int i = 0; i < players.length; i++) {
			if (players[i] != null && players[i].getColor() == winnerColor) {
				winner = players[i];
				return;
			}
		}
	}

	/**
	 * this method provides the color from the current player.
	 * 
	 * @return color from current player
	 */
	public Color getColorFromCurrentPlayer() {
		if (players[currentPlayerIndex] == null)
			return null;
		return players[currentPlayerIndex].getColor();
	}

	/**
	 * this method provides the name from the current player.
	 * 
	 * @return name from current player
	 */
	public String getNameFromCurrentPlayer() {
		if (players[currentPlayerIndex] == null)
			return null;
		return players[currentPlayerIndex].getName();
	}
}
