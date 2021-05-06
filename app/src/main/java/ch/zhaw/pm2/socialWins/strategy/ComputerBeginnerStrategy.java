package ch.zhaw.pm2.socialWins.strategy;

import ch.zhaw.pm2.socialWins.Board;
import ch.zhaw.pm2.socialWins.Config;

public class ComputerBeginnerStrategy implements Strategy {
	
	private MoveCalculator moveCalculator;
	private int depth;
	
	public ComputerBeginnerStrategy(int numberOfRows, int numberOfColumns, int winningRowLength){
		moveCalculator = new MoveCalculator(numberOfRows, numberOfColumns, winningRowLength);
		depth = Config.BEGINNER_SEARCH_DEPTH;
	}
	
	@Override
	public int nextMove(Board board) {
		return moveCalculator.calculateComputerMove(depth, board, true, 0).getColumn();
	}
}
