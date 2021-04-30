package ch.zhaw.pm2.socialWins;

import java.awt.Color;

public interface Board {
	Chip[][] getBoard();	
	boolean isFull();
	boolean hasChipsInARow(int amount);
	Color getColorWithChipsInARow(int amount);
	boolean addChip(int x, int y, Color color);
}
