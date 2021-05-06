package ch.zhaw.pm2.socialWins;

import java.awt.Color;

import ch.zhaw.pm2.socialWins.strategy.ComputerAdvancedStrategy;
import ch.zhaw.pm2.socialWins.strategy.ComputerBeginnerStrategy;
import ch.zhaw.pm2.socialWins.strategy.ComputerIntermediateStrategy;
import ch.zhaw.pm2.socialWins.strategy.Strategy;

public class Computer extends Player {
	private Strategy strategy;
	private Board board;

	public Computer(String name, Color color, int level, Board board, int winningRowLength) {
		super(name, color);
		this.board = board;
		setStrategy(level, board.getNumberOfRows(), board.getNumberOfColumns(), winningRowLength);
	}

	private void setStrategy(int level, int numberOfRows, int numberOfColumns, int winningRowLength) {
		switch (level) {
		case 1:
			strategy = new ComputerBeginnerStrategy(numberOfRows, numberOfColumns, winningRowLength);
			break;
		case 2: 
			strategy = new ComputerIntermediateStrategy(numberOfRows, numberOfColumns, winningRowLength);
			break;
		case 3:
			strategy = new ComputerAdvancedStrategy(numberOfRows, numberOfColumns, winningRowLength);
			break;
			
		default:
			// throw new exception, invalid level.
		}
	}
	
	public int nextMove() {
		return strategy.nextMove(board);
	}
}
