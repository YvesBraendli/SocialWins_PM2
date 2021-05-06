package ch.zhaw.pm2.socialWins.strategy;

public class ComputerIntermediateStrategy implements Strategy{
	
	private MoveCalculator moveCalculator;
	
	public ComputerIntermediateStrategy(int numberOfRows, int numberOfColumns, int winningRowLength){
		moveCalculator = new MoveCalculator(numberOfRows, numberOfColumns, winningRowLength);	
	}

	@Override
	public void nextMove() {
		throw new UnsupportedOperationException();
	}
}
