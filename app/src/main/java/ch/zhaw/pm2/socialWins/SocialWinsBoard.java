package ch.zhaw.pm2.socialWins;

import java.awt.Color;

public class SocialWinsBoard implements Board{
	private boolean isFull = false;
	private Chip[][] chips;
	
	public SocialWinsBoard(int columns, int rows) {
		chips = new Chip[columns][rows];
	}

	@Override
	public Chip[][] getBoard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isFull() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasChipsInARow(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Color getColorWithChipsInARow(int amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addChip(int y, Color color) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
