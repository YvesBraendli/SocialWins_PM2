package ch.zhaw.pm2.socialWins.strategy;

import ch.zhaw.pm2.socialWins.Board;
import ch.zhaw.pm2.socialWins.Config;

public class ComputerIntermediateStrategy implements Strategy{
	
	private MoveCalculator moveCalculator;
	private int depth;
	
	public ComputerIntermediateStrategy(int numberOfRows, int numberOfColumns, int winningRowLength){
		moveCalculator = new MoveCalculator(numberOfRows, numberOfColumns, winningRowLength);
		depth = Config.BEGINNER_SEARCH_DEPTH+winningRowLength-Config.DEPTH_NEGATION_AMOUNT;
	}
	
	@Override
	public int nextMove(Board board) {
		return moveCalculator.calculateComputerMove(depth, board, true, 0).getColumn();
	}
}
