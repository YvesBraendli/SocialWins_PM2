package ch.zhaw.pm2.socialWins;

import java.awt.Color;

public class Board {
	private boolean isFull = false;
	private Chip[][] chips;

	public Board(int rows, int columns) {
		chips = new Chip[rows][columns];
	}

	public Chip[][] getBoard() {
		return chips;
	}

	public boolean isBoardFull() {
		for (int row = 0; row < chips.length; row++) {
			for (int column = 0; column < chips[row].length; column++) {
				if(chips[row][column] == null) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean isColumnFull(int column) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean hasChipsInARow(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	public Color getColorWithChipsInARow(int amount) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean addChip(int column, Color color) {
		// TODO Auto-generated method stub
		return false;
	}

}
