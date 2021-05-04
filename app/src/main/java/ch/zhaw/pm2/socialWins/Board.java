package ch.zhaw.pm2.socialWins;

import java.awt.Color;

/**
 * This Board Class contains the current Board from a socialWins game and
 * provides following functionalities: <br>
 * - is board full / is column full (to check if there is still place to add a
 * chip) <br>
 * - add chip to the board <br>
 * - check if there is a color with a certain amount of chips with the same color in a row
 * 
 * @author yves braendli, robin meier, nadine moser
 *
 */
public class Board {
	private int rows;
	private int columns;
	private Chip[][] chips;

	/**
	 * Constructor to create a new Board instance. A board with requested rows and
	 * columns gets initialized.
	 * 
	 * @param rows    number of rows of the board
	 * @param columns number of columns of the board
	 * @throws IllegalArgumentException if rows or columns argument is lower than 1
	 */
	public Board(int rows, int columns) {
		if (!isValidBoard(rows, columns)) {
			throw new IllegalArgumentException();
		}
		chips = new Chip[rows][columns];
		this.rows = rows;
		this.columns = columns;
	}

	/**
	 * Getter for the Board. Return the current board ( no copy!)
	 * 
	 * @return current Board
	 */
	public Chip[][] getBoard() {
		return chips;
	}

	/**
	 * checks if board is full
	 * 
	 * @return true if it is full, false if not
	 */
	public boolean isBoardFull() {
		for (int i = 0; i < chips[0].length; i++) {
			if (!isColumnFull(i)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * checks if column is full.
	 * 
	 * @param column column to check
	 * @return true if column is full, false if not.
	 */
	public boolean isColumnFull(int column) {
		if (!isValidColumn(column)) {
			return false;
		}

		for (int row = 0; row < chips.length; row++) {
			if (chips[row][column] == null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * The chips with the asked amount and from the same color get detected and the
	 * color with the chips in a row is returned. if no color has a row, null will
	 * be returned.
	 * 
	 * @param amount amount of chips with same color in a row
	 * @return the color from the chip, if no row thenn null is returned
	 */
	public Color getColorWithChipsInARow(int amount) {
		if (!isPossibleAmount(amount)) {
			return null;
		}

		Color winnerColor = getHorizontalWinner(amount);
		if (winnerColor != null) {
			return winnerColor;
		}

		winnerColor = getVerticalWinner(amount);
		if (winnerColor != null) {
			return winnerColor;
		}

		winnerColor = getDiagonalDownUpWinner(amount);
		if (winnerColor != null) {
			return winnerColor;
		}

		winnerColor = getDiagonalUpDownWinner(amount);
		return winnerColor;
	}

	/**
	 * This method adds a Chip in the given Color to the board. The Chip gets added
	 * on the specified column and on the lowest row possible. (simulates gravity)
	 * If the chip could be added it returns true, if it wasn't possible it returns
	 * false.
	 * 
	 * @param column the column to add the chip
	 * @param color  the color of the added chip
	 * @return boolean, if chip could be added
	 */
	public boolean addChip(int column, Color color) {
		if (!isValidColumn(column) || color == null) {
			return false;
		}

		for (int row = 0; row < chips.length; row++) {
			if (chips[row][column] == null) {
				chips[row][column] = new Chip(color);
				return true;
			}
		}
		return false;
	}

	private boolean isPossibleAmount(int amount) {
		return !(amount > rows || amount > columns || amount < 1);
	}

	private Color getHorizontalWinner(int amount) {
		Color currentColor = null;
		int currentChipsCount = 0;

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				if (chips[r][c] == null) {
					currentChipsCount = 0;
					continue;
				}

				Color newColor = chips[r][c].getColor();

				if (newColor == currentColor) {
					currentChipsCount++;
				}

				if (currentColor == null || currentColor != newColor) {
					currentColor = newColor;
					currentChipsCount = 1;
				}

				if (currentChipsCount == amount) {
					return currentColor;
				}
			}
			currentChipsCount = 0;
			currentColor = null;
		}

		return null;
	}

	private Color getVerticalWinner(int amount) {

		Color currentColor = null;
		int currentChipsCount = 0;

		for (int c = 0; c < columns; c++) {
			for (int r = 0; r < rows; r++) {
				if (chips[r][c] == null) {
					currentChipsCount = 0;
					continue;
				}

				Color newColor = chips[r][c].getColor();

				if (newColor == currentColor) {
					currentChipsCount++;
				}

				if (currentColor == null || currentColor != newColor) {
					currentColor = newColor;
					currentChipsCount = 1;
				}

				if (currentChipsCount == amount) {
					return currentColor;
				}
			}

			currentChipsCount = 0;
			currentColor = null;
		}

		return null;
	}

	private Color getDiagonalDownUpWinner(int amount) {

		Color currentColor = null;
		int currentChipsCount = 0;

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				for (int i = 0; i < amount; i++) {

					if (isEdge(r + i, c + i)) {
						break;
					}
					if (chips[r + i][c + i] == null) {
						currentChipsCount = 0;
						break;
					}

					Color newColor = chips[r + i][c + i].getColor();

					if (newColor == currentColor) {
						currentChipsCount++;
					}

					if (currentColor == null || currentColor != newColor) {
						currentColor = newColor;
						currentChipsCount = 1;
					}

					if (currentChipsCount == amount) {
						return currentColor;
					}
				}

				currentColor = null;
				currentChipsCount = 0;
			}
		}

		return null;
	}

	private boolean isEdge(int row, int column) {
		if (row < 0 || column < 0 || row >= rows || column >= columns) {
			return true;
		}
		return false;
	}

	private Color getDiagonalUpDownWinner(int amount) {
		Color currentColor = null;
		int currentChipsCount = 0;

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				for (int i = 0; i < amount; i++) {

					if (isEdge(r - i, c + i)) {
						break;
					}

					if (chips[r - i][c + i] == null) {
						currentChipsCount = 0;
						break;
					}
					Color newColor = chips[r - i][c + i].getColor();

					if (newColor == currentColor) {
						currentChipsCount++;
					}

					if (currentColor == null || currentColor != newColor) {
						currentColor = newColor;
						currentChipsCount = 1;
					}

					if (currentChipsCount == amount) {
						return currentColor;
					}
				}

				currentColor = null;
				currentChipsCount = 0;
			}
		}

		return null;
	}

	private boolean isValidColumn(int column) {
		return column >= 0;
	}

	private boolean isValidBoard(int rows, int columns) {
		return rows > 0 && columns > 0;
	}
}
