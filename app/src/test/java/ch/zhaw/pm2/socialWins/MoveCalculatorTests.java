package ch.zhaw.pm2.socialWins;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.zhaw.pm2.socialWins.strategy.Move;
import ch.zhaw.pm2.socialWins.strategy.MoveCalculator;

public class MoveCalculatorTests {
	private MoveCalculator testCalculator;
	private Board board;
	private int depth;
	
	
	@Test
	public void calculateComputerMove_bestColumnEmptyBoard_FourWinningRow() {
		// Arrange
		setupFourWinningRow();
		int expectedBestColumn = 3;
		depth = 1;
		
		// Act
		Move actualMove = testCalculator.calculateComputerMove(depth, board, true, 0);
		
		// Assert
		assertEquals(expectedBestColumn, actualMove.getColumn());
	}
	
	@Test
	public void calculateComputerMove_bestColumnEmptyBoard_ThreeWinningRow() {
		// Arrange
		setupThreeWinningRow();
		int expectedBestColumn = 2;
		depth = 1;
		
		// Act
		Move actualMove = testCalculator.calculateComputerMove(depth, board, true, 0);
		
		// Assert
		assertEquals(expectedBestColumn, actualMove.getColumn());
	}
	
	@Test
	public void calculateComputerMove_bestColumnEmptyBoard_FiveWinningRow() {
		// Arrange
		setupFiveWinningRow();
		int expectedBestColumn = 4;
		depth = 1;
		
		// Act
		Move actualMove = testCalculator.calculateComputerMove(depth, board, true, 0);
		
		// Assert
		assertEquals(expectedBestColumn, actualMove.getColumn());
	}
	
	@Test
	public void calculateComputerMove_bestColumnEmptyBoard_SixWinningRow() {
		// Arrange
		setupSixWinningRow();
		int expectedBestColumn = 4;
		depth = 1;
		
		// Act
		Move actualMove = testCalculator.calculateComputerMove(depth, board, true, 0);
		
		// Assert
		assertEquals(expectedBestColumn, actualMove.getColumn());
	}
	
	@Test
	public void evaluateState_twoInARow() {
		
	}
	
	@Test
	public void evaluateState_threeInARow() {
		
	}
	
	@Test
	public void evaluateState_fourInARow() {
		
	}
	
	@Test
	public void evaluateState_lastChipCenterRow() {
		
	}
	
	private void setupSixWinningRow() {
		board = new Board(9,10);
		testCalculator = new MoveCalculator(9, 10, 6);
	}
	
	private void setupFiveWinningRow() {
		board = new Board(8,9);
		testCalculator = new MoveCalculator(8, 9, 5);
	}
	
	private void setupFourWinningRow() {
		board = new Board(6,7);
		testCalculator = new MoveCalculator(6, 7, 4);
	}
	
	private void setupThreeWinningRow() {
		board = new Board(5,6);
		testCalculator = new MoveCalculator(5, 6, 3);
	}
	

}
