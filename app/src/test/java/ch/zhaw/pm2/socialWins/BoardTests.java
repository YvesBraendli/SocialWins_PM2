package ch.zhaw.pm2.socialWins;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

	/**
	 * Equivalence Partitioning C1<br>
	 * If column argument is invalid, the chip can't be added.<br>
	 * Expected result: returns false, chip is not added
	 */
	@Test
	public void addChip_InvalidColumnArgument_returnFalse() {
		// Act
		boolean result = testBoard.addChip(-1, Color.RED);

		// Assert
		assertFalse(result);
	}

	/**
	 * Equivalence Partitioning C1<br>
	 * If color argument is null, the chip can't be added.<br>
	 * Expected result: returns false, chip is not added
	 */
	@Test
	public void addChip_InvalidColorArgument_returnFalse() {
		// Act
		boolean result = testBoard.addChip(0, null);

		// Assert
		assertFalse(result);
	}

	/**
	 * Equivalence Partitioning C2, C3<br>
	 * If requested column is already full, the chip can't be added.<br>
	 * Expected result: returns false, chip is not added
	 */
	@Test
	public void addChip_columnIsFull_returnFalse() {
		// Arrange
		int column = 0;
		fillColumn(column, Color.RED);

		// Act
		boolean result = testBoard.addChip(column, Color.BLUE);

		// Assert
		assertFalse(result);
	}

	/**
	 * Equivalence Partitioning C3, C4<br>
	 * If requested column is empty, the chip can be added.<br>
	 * Expected result: returns true, chip is added
	 */
	@Test
	public void addChip_columnIsEmpty_returnTrue() {
		// Act
		boolean result = testBoard.addChip(0, Color.BLUE);

		// Assert
		assertTrue(result);
		assertEquals(Color.BLUE, testBoard.getBoard()[0][0].getColor());
	}

	/**
	 * Equivalence Partitioning C3, C5<br>
	 * If requested column is not empty but still has place for a chip, the chip can
	 * be added.<br>
	 * Expected result: returns true, chip is added
	 */
	@Test
	public void addChip_columnHasPlace_returnTrue() {
		// Arrange
		testBoard.addChip(0, Color.RED);
		testBoard.addChip(0, Color.RED);

		// Act
		boolean result = testBoard.addChip(0, Color.BLUE);

		// Assert
		assertTrue(result);
		assertEquals(Color.BLUE, testBoard.getBoard()[2][0].getColor());
	}

	/**
	 * Equivalence Partitioning G1<br>
	 * If number of chips Argument is invalid, no winner can be found.<br>
	 * Expected result: returns null, no winner found
	 */
	@ParameterizedTest
	@ValueSource(ints = { Integer.MIN_VALUE, -1, 0 })
	public void getColorWithChipsInARow_InvalidNumberOfChipsArgument_returnsNull(int number) {
		// Act
		Color result = testBoard.getColorWithChipsInARow(number);

		// Assert
		assertNull(result);
	}

	/**
	 * Equivalence Partitioning G2<br>
	 * If multiple chips with different color are in the board, but no row with
	 * chips with same color, no winner can be found.<br>
	 * Expected result: returns null, no winner found
	 */
	@Test
	public void getColorWithChipsInARow_MultipleColorNoWinner_returnsNull() {
		// Arrange
		testBoard.addChip(0, Color.RED);
		testBoard.addChip(0, Color.BLUE);
		testBoard.addChip(0, Color.RED);

		// Act
		Color result = testBoard.getColorWithChipsInARow(3);

		// Assert
		assertNull(result);
	}

	/**
	 * Equivalence Partitioning G3, G4<br>
	 * If chips with same color are in the board and build a horizontal line, no
	 * winner can be found.<br>
	 * Expected result: returns color from winner
	 */
	@Test
	public void getColorWithChipsInARow_OneColorHorizontal_returnsColor() {
		// Arrange
		Color winnerColor = Color.YELLOW;
		testBoard.addChip(0, winnerColor);
		testBoard.addChip(1, winnerColor);
		testBoard.addChip(2, winnerColor);

		// Act
		Color result = testBoard.getColorWithChipsInARow(3);

		// Assert
		assertEquals(winnerColor, result);
	}

	/**
	 * Equivalence Partitioning G3, G4<br>
	 * If chips with same color are in the board and build a vertical line, no
	 * winner can be found.<br>
	 * Expected result: returns color from winner
	 */
	@Test
	public void getColorWithChipsInARow_OneColorVertical_returnsColor() {
		// Arrange
		Color winnerColor = Color.YELLOW;
		testBoard.addChip(0, winnerColor);
		testBoard.addChip(0, winnerColor);
		testBoard.addChip(0, winnerColor);

		// Act
		Color result = testBoard.getColorWithChipsInARow(3);

		// Assert
		assertEquals(winnerColor, result);
	}

	/**
	 * Equivalence Partitioning G3, G4<br>
	 * If chips with same color are in the board and build a diagonal (from left top
	 * to right bottom) line, no winner can be found.<br>
	 * Expected result: returns color from winner
	 */
	@Test
	public void getColorWithChipsInARow_OneColorTopBottomDiagonal_returnsColor() {
		// Arrange
		Color winnerColor = Color.YELLOW;
		testBoard.addChip(0, Color.RED);
		testBoard.addChip(0, Color.RED);
		testBoard.addChip(0, winnerColor);
		testBoard.addChip(1, Color.RED);
		testBoard.addChip(1, winnerColor);
		testBoard.addChip(2, winnerColor);

		// Act
		Color result = testBoard.getColorWithChipsInARow(3);

		// Assert
		assertEquals(winnerColor, result);
	}

	/**
	 * Equivalence Partitioning G3, G4<br>
	 * If chips with same color are in the board and build a diagonal (from left
	 * bottom to right top) line, no winner can be found.<br>
	 * Expected result: returns color from winner
	 */
	@Test
	public void getColorWithChipsInARow_OneColorBottomTopDiagonal_returnsColor() {
		// Arrange
		Color winnerColor = Color.YELLOW;
		testBoard.addChip(0, winnerColor);
		testBoard.addChip(1, Color.RED);
		testBoard.addChip(1, winnerColor);
		testBoard.addChip(2, Color.RED);
		testBoard.addChip(2, Color.RED);
		testBoard.addChip(2, winnerColor);
		// Act
		Color result = testBoard.getColorWithChipsInARow(3);

		// Assert
		assertEquals(winnerColor, result);
	}
}
