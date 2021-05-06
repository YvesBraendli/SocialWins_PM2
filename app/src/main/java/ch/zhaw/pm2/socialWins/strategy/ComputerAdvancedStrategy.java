package ch.zhaw.pm2.socialWins.strategy;

public class ComputerAdvancedStrategy implements Strategy{
	
	private MoveCalculator moveCalculator;
	
	public ComputerBeginnerStrategy(int numberOfRows, int numberOfColumns, int winningRowLength){
		moveCalculator = new MoveCalculator(numberOfRows, numberOfColumns, winningRowLength);	
	}
	
	@Override
	public void nextMove() {
		throw new UnsupportedOperationException();
	}
}
