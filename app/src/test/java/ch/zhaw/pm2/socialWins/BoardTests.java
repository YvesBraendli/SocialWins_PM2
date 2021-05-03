package ch.zhaw.pm2.socialWins;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

public class BoardTests {
	private Board testBoard;

	@BeforeEach
	public void setup() {
		testBoard = new Board(5, 4);
	}

	private void fillColumn(int column, Color color) {
		for (int row = 0; row < testBoard.getBoard().length; row++) {
			testBoard.addChip(column, color);
		}
	}

	private void fillBoard(Color color) {
		for (int column = 0; column < testBoard.getBoard()[0].length; column++) {
			fillColumn(column, color);
		}
	}

	/**
	 * Equivalence Partitioning F2<br>
	 * If board is empty and the method gets called, it returns false.<br>
	 * Expected result: returns false
	 */
	@Test
	public void isBoardFull_emptyBoard_returnsFalse() {
		// Act
		boolean result = testBoard.isBoardFull();

		// Assert
		assertFalse(result);
	}

	/**
	 * Equivalence Partitioning F5<br>
	 * If board has only one full column (rest is empty) and the method gets called,
	 * it returns false.<br>
	 * Expected result: returns false
	 */
	@Test
	public void isBoardFull_onlyOneColumnIsFull_returnsFalse() {
		// Arrange
		int column = 0;
		fillColumn(column, Color.RED);

		// Act
		boolean result = testBoard.isBoardFull();

		// Assert
		assertFalse(result);
	}

	/**
	 * Equivalence Partitioning F6<br>
	 * If board is full and the method gets called, it returns true.<br>
	 * Expected result: returns true
	 */
	@Test
	public void isBoardFull_BoardIsFull_returnsTrue() {
		// Arrange
		fillBoard(Color.RED);

		// Act
		boolean result = testBoard.isBoardFull();

		// Assert
		assertTrue(result);
	}

	/**
	 * Equivalence Partitioning F1<br>
	 * If the method gets called with an argument that is not in the board, it
	 * returns false.<br>
	 * Expected result: returns false
	 */
	@Test
	public void isColumnFull_invalidArgument_returnsFalse() {
		// Act
		boolean result = testBoard.isColumnFull(-1);

		// Assert
		assertFalse(result);
	}

	/**
	 * Equivalence Partitioning F2, F4<br>
	 * If board is empty and the method gets called, it returns false.<br>
	 * Expected result: returns false
	 */
	@ParameterizedTest
	@ValueSource(ints = { 0, 1, 2, 3 })
	public void isColumnFull_emptyBoard_returnsFalse(int column) {
		// Act
		boolean result = testBoard.isColumnFull(column);

		// Assert
		assertFalse(result);
	}

	/**
	 * Equivalence Partitioning F4, F5<br>
	 * If the requested column is full, it returns true.<br>
	 * Expected result: returns true
	 */
	@Test
	public void isColumnFull_OnlyArgumentColumnIsFull_returnsTrue() {
		// Arrange
		int column = 0;
		fillColumn(column, Color.RED);
		// Act
		boolean result = testBoard.isColumnFull(column);

		// Assert
		assertTrue(result);
	}

	/**
	 * Equivalence Partitioning F3, F4<br>
	 * If the requested column is empty, it returns false.<br>
	 * Expected result: returns false
	 */
	@Test
	public void isColumnFull_OnlyArgumentColumnIsEmpty_returnsFalse() {
		// Arrange
		for (int i = 1; i < testBoard.getBoard()[0].length; i++) {
			fillColumn(i, Color.RED);
		}

		// Act
		boolean result = testBoard.isColumnFull(0);

		// Assert
		assertFalse(result);
	}

	/**
	 * Equivalence Partitioning F6<br>
	 * If board is full and the method gets called, it returns true.<br>
	 * Expected result: returns true
	 */
	@ParameterizedTest
	@ValueSource(ints = { 0, 1, 2, 3 })
	public void isColumnFull_BoardIsFull_returnsTrue(int column) {
		// Arrange
		fillBoard(Color.RED);

		// Act
		boolean result = testBoard.isColumnFull(column);

		// Assert
		assertTrue(result);
	}
}
