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
		for (int i = 0; i < chips[0].length; i++) {
			if(!isColumnFull(i)) {
				return false;
			}
		}
		return true;
	}

	public boolean isColumnFull(int column) {
		for(int row = 0; row < chips.length; row++) {
			if(chips[row][column] == null) {
				return false;
			}
		}
		return true;
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
		for(int row = 0; row < chips.length; row++) {
			if(chips[row][column] == null) {
				chips[row][column] = new Chip(color);
				return true;
			}
		}
		return false;
	}

}
