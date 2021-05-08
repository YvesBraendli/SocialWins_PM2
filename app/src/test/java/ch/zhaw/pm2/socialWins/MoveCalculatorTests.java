package ch.zhaw.pm2.socialWins;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import ch.zhaw.pm2.socialWins.strategy.Move;
import ch.zhaw.pm2.socialWins.strategy.MoveCalculator;

public class MoveCalculatorTests {
	private MoveCalculator testCalculator;
	private Board board;
	private int depth;
	
	
	/**
	 * Equivalence Partitioning: eS4
	 * Checks for the uneven board sizes if the returned score for the center column is correct.
	 * Expected result: 4
	 */
	@ParameterizedTest
	@ValueSource(ints = {3,4})
	public void evaluateState_centerColumnScore_unevenBoards(int centerColumn) {
		// Arrange
		switch(centerColumn) {
		case 3:
			setupFourWinningRow();
			break;
		case 4:
			setupFiveWinningRow();
			break;
		}
		int expectedScore = Config.CENTER_COLUMS_SCORE;
		
		depth = 0;
		board.addChip(centerColumn, Config.SINGLEPLAYER_COMPUTERCOLOR);
		
		// Act
		Move move = testCalculator.calculateComputerMove(depth, board, true, centerColumn);
		
		// Assert
		assertEquals(expectedScore, move.getScore());	
	}
	
	/**
	 * Equivalence Partitioning: eS4
	 * Checks for a board sized for a 3 winning row if the returned score for the
	 * two center column is correct.
	 * Expected result: 4
	 */
	@ParameterizedTest
	@ValueSource(ints = {2,3})
	public void evaluateState_centerColumnScore_threeWinningRowBoardSize(int centerColumn) {
		// Arrange
		setupThreeWinningRow();
		int expectedScore = Config.CENTER_COLUMS_SCORE;
		
		depth = 0;
		board.addChip(centerColumn, Config.SINGLEPLAYER_COMPUTERCOLOR);
		
		// Act
		Move move = testCalculator.calculateComputerMove(depth, board, true, centerColumn);
		
		// Assert
		assertEquals(expectedScore, move.getScore());	
	}
	
	/**
	 * Equivalence Partitioning: eS4
	 * Checks for a board sized for a 6 winning row if the returned score for the
	 * two center column is correct.
	 * Expected result: 4
	 */
	@ParameterizedTest
	@ValueSource(ints = {4,5})
	public void evaluateState_centerColumnScore_sixWinningRowBoardSize(int centerColumn) {
		// Arrange
		setupSixWinningRow();
		int expectedScore = Config.CENTER_COLUMS_SCORE;
		
		depth = 0;
		board.addChip(centerColumn, Config.SINGLEPLAYER_COMPUTERCOLOR);
		
		// Act
		Move move = testCalculator.calculateComputerMove(depth, board, true, centerColumn);
		
		// Assert
		assertEquals(expectedScore, move.getScore());	
	}

	/**
	 * Equivalence Partitioning: eS1
	 * Checks if the returned score for a low score row is correct.
	 * Expected result: 2
	 */
	@ParameterizedTest
	@ValueSource(ints = {1,2,3,4})
	public void evaluateState_lowScore(int numberOfChipsToPlace) {
		// Arrange
		switch(numberOfChipsToPlace) {
		case 1:
			setupThreeWinningRow();
			break;
		case 2:
			setupFourWinningRow();
			break;
		case 3:
			setupFiveWinningRow();
			break;
		case 4:
			setupSixWinningRow();
			break;
		}
		int expectedScore = Config.LOW_SCORE;
		
		for(int i = 0; i < numberOfChipsToPlace; i++) {
			board.addChip(1, Config.SINGLEPLAYER_COMPUTERCOLOR);
		}
		depth = 0;
		
		// Act
		Move move = testCalculator.calculateComputerMove(depth, board, true, 0);
		
		// Assert
		assertEquals(expectedScore, move.getScore());		
	}
	
	/**
	 * Equivalence Partitioning: eS2
	 * Checks if the returned score for a medium score row is correct.
	 * Expected result: 2+6
	 */
	@ParameterizedTest
	@ValueSource(ints = {2,3,4,5})
	public void evaluateState_mediumScore(int numberOfChipsToPlace) {
		// Arrange
		switch(numberOfChipsToPlace) {
		case 2:
			setupThreeWinningRow();
			break;
		case 3:
			setupFourWinningRow();
			break;
		case 4:
			setupFiveWinningRow();
			break;
		case 5:
			setupSixWinningRow();
			break;
		}
		int expectedScore = Config.MEDIUM_SCORE+Config.LOW_SCORE;
		
		for(int i = 0; i < numberOfChipsToPlace; i++) {
			board.addChip(1, Config.SINGLEPLAYER_COMPUTERCOLOR);
		}
		depth = 0;
		
		// Act
		Move move = testCalculator.calculateComputerMove(depth, board, true, 0);
		
		// Assert
		assertEquals(expectedScore, move.getScore());		
	}
	
	/**
	 * Equivalence Partitioning: eS3
	 * Checks if the returned score for a high score row is correct.
	 * Expected result: 2+6+500
	 */
	@ParameterizedTest
	@ValueSource(ints = {3,4,5,6})
	public void evaluateState_highScore(int numberOfChipsToPlace) {
		// Arrange
		switch(numberOfChipsToPlace) {
		case 3:
			setupThreeWinningRow();
			break;
		case 4:
			setupFourWinningRow();
			break;
		case 5:
			setupFiveWinningRow();
			break;
		case 6:
			setupSixWinningRow();
			break;
		}
		int expectedScore = Config.HIGH_SCORE+Config.MEDIUM_SCORE+Config.LOW_SCORE;
		
		for(int i = 0; i < numberOfChipsToPlace; i++) {
			board.addChip(1, Config.SINGLEPLAYER_COMPUTERCOLOR);
		}
		depth = 0;
		
		// Act
		Move move = testCalculator.calculateComputerMove(depth, board, true, 0);
		
		// Assert
		assertEquals(expectedScore, move.getScore());		
	}
	
	/**
	 * Equivalence Partitioning: ccM4
	 * Checks if the correct column gets returned in an empty board. This should be the center column.
	 * For even boards the first of the two center columns is prefered.
	 * Expected results:
	 * Winning Row 3 = 2
	 * Winning Row 4 = 3
	 * Winning Row 5 = 4
	 */
	@ParameterizedTest
	@ValueSource(ints = {2,3,4})
	public void calculateBestMove_emptyBoard(int expectedBestColumn) {
		// Arrange
		switch(expectedBestColumn) {
		case 2:
			setupThreeWinningRow();
			break;
		case 3:
			setupFourWinningRow();
			break;
		case 4:
			setupFiveWinningRow();
			break;
		}
	
		depth = 1;
		
		// Act
		Move actualMove = testCalculator.calculateComputerMove(depth, board, true, 0);
		
		// Assert
		assertEquals(expectedBestColumn, actualMove.getColumn());	
	}
	
	/**
	 * Equivalence Partitioning: ccM4
	 * Checks if the correct column gets returned in an empty board. This should be the center column.
	 * For even boards the first of the two center columns is prefered.
	 * Expected results:
	 * Winning Row 6 = 4
	 */
	@Test
	public void calculateBestMove_emptyBoard_sixWinningRowBoardSize() {
		// Arrange
		setupSixWinningRow();
		depth = 1;
		int expectedBestColumn = 4;
		
		// Act
		Move actualMove = testCalculator.calculateComputerMove(depth, board, true, 0);
		
		// Assert
		assertEquals(expectedBestColumn, actualMove.getColumn());	
		
	}
	
	/**
	 * Equivalence Partitioning: ccM1
	 * Checks if full columns are ignored during move selection
	 * Expected result: column 0
	 */
	@ParameterizedTest
	@ValueSource(ints = {3,4,5,6})
	public void calculateBestMove_allColumnsFullExpectOne(int boardSize) {
		// Arrange
		switch(boardSize) {
		case 3:
			setupThreeWinningRow();
			for(int i = 1; i<6; i++) {
				for(int j = 0; j<5; j++) {
					board.addChip(i, Config.SINGLEPLAYER_COMPUTERCOLOR);
				}
			}
			break;
		case 4:
			setupFourWinningRow();
			for(int i = 1; i<7; i++) {
				for(int j = 0; j<6; j++) {
					board.addChip(i, Config.SINGLEPLAYER_COMPUTERCOLOR);
				}
			}
			break;
		case 5:
			setupFiveWinningRow();
			for(int i = 1; i<9; i++) {
				for(int j = 0; j<8; j++) {
					board.addChip(i, Config.SINGLEPLAYER_COMPUTERCOLOR);
				}
			}
			break;
		case 6:
			setupSixWinningRow();
			for(int i = 1; i<10; i++) {
				for(int j = 0; j<9; j++) {
					board.addChip(i, Config.SINGLEPLAYER_COMPUTERCOLOR);
				}
			}
			break;
		}
		
		depth = 1;
		int expectedBestColumn = 0;
		
		// Act
		Move actualMove = testCalculator.calculateComputerMove(depth, board, true, 0);
		
		// Assert
		assertEquals(expectedBestColumn, actualMove.getColumn());
		
	}
	
	/**
	 * Equivalence Partitioning: ccM2
	 * Checks if the computer prioritizes stopping other player from winning, if they themself can't win
	 * Expected result: Column 3
	 */
	@ParameterizedTest
	@ValueSource(ints = {3,4,5,6})
	public void calculateBestMove_StopOtherPlayerFromWinning(int boardSize) {
		// Arrange
		switch(boardSize) {
		case 3:
			setupThreeWinningRow();
			break;
		case 4:
			setupFourWinningRow();
			board.addChip(0, Config.SINGLEPLAYER_COMPUTERCOLOR);
			board.addChip(6, Config.SINGLEPLAYER_USERCOLOR);
			break;
		case 5:
			setupFiveWinningRow();
			board.addChip(8, Config.SINGLEPLAYER_COMPUTERCOLOR);
			board.addChip(0, Config.SINGLEPLAYER_COMPUTERCOLOR);
			board.addChip(6, Config.SINGLEPLAYER_USERCOLOR);
			board.addChip(7, Config.SINGLEPLAYER_USERCOLOR);
			break;
		case 6:
			setupSixWinningRow();
			board.addChip(9, Config.SINGLEPLAYER_COMPUTERCOLOR);
			board.addChip(0, Config.SINGLEPLAYER_COMPUTERCOLOR);
			board.addChip(0, Config.SINGLEPLAYER_COMPUTERCOLOR);
			board.addChip(6, Config.SINGLEPLAYER_USERCOLOR);
			board.addChip(7, Config.SINGLEPLAYER_USERCOLOR);
			board.addChip(8, Config.SINGLEPLAYER_USERCOLOR);
			break;
		}
		board.addChip(0, Config.SINGLEPLAYER_COMPUTERCOLOR);
		board.addChip(4, Config.SINGLEPLAYER_USERCOLOR);
		board.addChip(5, Config.SINGLEPLAYER_USERCOLOR);
		depth = 1;
		int expectedBestColumn = 3;
		
		// Act
		Move actualMove = testCalculator.calculateComputerMove(depth, board, true, 0);
		
		// Assert
		assertEquals(expectedBestColumn, actualMove.getColumn());
	}
	
	/**
	 * Equivalence Partitioning: ccM3
	 * Checks if the computer tries to select a winning opportunity if it presents itself.
	 * For Board sizes with winning rows 3 and 4.
	 * Expected results: Column 3
	 */
	@ParameterizedTest
	@ValueSource(ints = {3,4})
	public void calculateBestMove_WinnableOptionAvailable_WinningRowSizeThreeAndFour(int boardSize) {
		// Arrange
		switch(boardSize) {
		case 3:
			setupThreeWinningRow();
			break;
		case 4:
			setupFourWinningRow();
			board.addChip(6, Config.SINGLEPLAYER_COMPUTERCOLOR);
			break;
		}
		board.addChip(4, Config.SINGLEPLAYER_COMPUTERCOLOR);
		board.addChip(5, Config.SINGLEPLAYER_COMPUTERCOLOR);
		depth = 1;
		int expectedBestColumn = 3;
		
		// Act
		Move actualMove = testCalculator.calculateComputerMove(depth, board, true, 0);
		
		// Assert
		assertEquals(expectedBestColumn, actualMove.getColumn());	
	}
	
	
	/**
	 * Equivalence Partitioning: ccM3
	 * Checks if the computer tries to select a winning opportunity if it presents itself. 
	 * For Board sizes with winning rows 5 and 6.
	 * Expected results: Column 4
	 */
	@ParameterizedTest
	@ValueSource(ints = {5,6})
	public void calculateBestMove_WinnableOptionAvailable_WinningRowSizeFiveAndSix(int boardSize) {
		// Arrange
		switch(boardSize) {
		case 5:
			setupFiveWinningRow();
			break;
		case 6:
			setupSixWinningRow();
			board.addChip(9, Config.SINGLEPLAYER_COMPUTERCOLOR);
			break;
		}
		board.addChip(5, Config.SINGLEPLAYER_COMPUTERCOLOR);
		board.addChip(6, Config.SINGLEPLAYER_COMPUTERCOLOR);
		board.addChip(7, Config.SINGLEPLAYER_COMPUTERCOLOR);
		board.addChip(8, Config.SINGLEPLAYER_COMPUTERCOLOR);
		depth = 1;
		int expectedBestColumn = 4;
		
		// Act
		Move actualMove = testCalculator.calculateComputerMove(depth, board, true, 0);
		
		// Assert
		assertEquals(expectedBestColumn, actualMove.getColumn());	
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
