package ch.zhaw.pm2.socialWins;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.util.HashMap;
import java.util.stream.Stream;

public class GameTests {

	private Game testGame;

	static Stream<String> blankStrings() {
		return Stream.of("", "   ", null);
	}

	/**
	 * Equivalence Partitioning G1<br>
	 * if game constructor for SinglePlayer is called with invalid winninglinelength
	 * argument and exception is thrown.<br>
	 * Expected result: throws an illegal argument exception
	 */
	@ParameterizedTest
	@ValueSource(ints = { 2, 7 })
	public void game_SinglePlayerInvalidWinningLineThrowsIllegalArgumentException(int winningLine) {
		// Act & Assert
		assertThrows(IllegalArgumentException.class, () -> new Game(winningLine, "max", 2, 6, 6));
	}

	/**
	 * Equivalence Partitioning G1<br>
	 * if game constructor for SinglePlayer is called with invalid userName argument
	 * and exception is thrown.<br>
	 * Expected result: throws an illegal argument exception
	 */
	@ParameterizedTest
	@MethodSource("blankStrings")
	public void game_SinglePlayerInvalidUserName_ThrowsIllegalArgumentException(String name) {
		// Act & Assert
		assertThrows(IllegalArgumentException.class, () -> new Game(5, name, 2, 6, 6));
	}

	/**
	 * Equivalence Partitioning G1<br>
	 * if game constructor for SinglePlayer is called with invalid level argument
	 * and exception is thrown.<br>
	 * Expected result: throws an illegal argument exception
	 */
	@ParameterizedTest
	@ValueSource(ints = { 0, 4 })
	public void game_SinglePlayerInvalidLevel_ThrowsIllegalArgumentException(int level) {
		// Act & Assert
		assertThrows(IllegalArgumentException.class, () -> new Game(5, "max", level, 6, 6));
	}

	/**
	 * Equivalence Partitioning G3<br>
	 * if game constructor for SinglePlayer is called with valid arguments,
	 * everything is will be initialized and no exception is thrown. <br>
	 * Expected result : no exception is thrown.
	 */
	@Test
	public void game_SinglePlayerValidArguments_NoExceptionIsThrown() {
		// Act
		testGame = new Game(5, "max", 2, 6, 2);

		try {
			testGame = new Game(5, "max", 2, 6, 2);
		} catch (Exception e) {
			fail();
		}
	}

	/**
	 * Equivalence Partitioning G1<br>
	 * if game constructor for MultiPlayer is called with invalid winninglinelength
	 * argument and exception is thrown.<br>
	 * Expected result: throws an illegal argument exception
	 */
	@ParameterizedTest
	@ValueSource(ints = { 2, 7 })
	public void game_MultiPlayerInvalidWinningLineThrowsIllegalArgumentException(int winningLine) {
		// Act & Assert
		assertThrows(IllegalArgumentException.class, () -> new Game(winningLine, "max", 2, 6, 6));
	}

	/**
	 * Equivalence Partitioning G1<br>
	 * if game constructor for MultiPlayer is called with invalid userName argument
	 * and exception is thrown.<br>
	 * Expected result: throws an illegal argument exception
	 */
	@ParameterizedTest
	@MethodSource("blankStrings")
	public void game_MultiPlayerInvalidUserName_ThrowsIllegalArgumentException(String name) {
		HashMap<Color, String> players = new HashMap<>();
		players.put(Color.YELLOW, name);
		players.put(Color.BLUE, "max");
		players.put(Color.BLACK, "laura");
		players.put(Color.WHITE, "olga");
		
		// Act & Assert
		assertThrows(IllegalArgumentException.class, () -> new Game(5, players, 5, 5));
	}
	
	/**
	 * Equivalence Partitioning G2<br>
	 * if game constructor for MultiPlayer is called with invalid userName argument
	 * and exception is thrown.<br>
	 * Expected result: throws an illegal argument exception
	 */
	@Test
	public void game_MultiPlayerDublicateUserName_ThrowsIllegalArgumentException() {
		HashMap<Color, String> players = new HashMap<>();
		players.put(Color.YELLOW, "max");
		players.put(Color.BLUE, "max");
		players.put(Color.BLACK, "laura");
		players.put(Color.WHITE, "olga");
		
		// Act & Assert
		assertThrows(IllegalArgumentException.class, () -> new Game(5, players, 5, 5));
	}
	
	/**
	 * Equivalence Partitioning G1<br>
	 * if game constructor for MultiPlayer is called with invalid amount of users argument
	 * and exception is thrown.<br>
	 * Expected result: throws an illegal argument exception
	 */
	@Test
	public void game_MultiPlayerInvalidPlayerAmount_ThrowsIllegalArgumentException() {
		HashMap<Color, String> players = new HashMap<>();
		players.put(Color.YELLOW, "tim");
		
		// Act & Assert
		assertThrows(IllegalArgumentException.class, () -> new Game(5, players, 5, 5));
	}

	/**
	 * Equivalence Partitioning G3, G4<br>
	 * if game constructor for MulitPlayer is called with valid arguments,
	 * everything is will be initialized and no exception is thrown. <br>
	 * Expected result : no exception is thrown.
	 */
	@Test
	public void game_MultiPlayerValidArguments_NoExceptionIsThrown() {
		// Act
		HashMap<Color, String> players = new HashMap<>();
		players.put(Color.YELLOW, "tim");
		players.put(Color.BLUE, "max");
		players.put(Color.BLACK, "laura");
		players.put(Color.WHITE, "olga");

		try {
			testGame = new Game(5, players, 5, 5);
		} catch (Exception e) {
			fail();
		}
	}
	
	/**
	 * Equivalence Partitioning S3<br>
	 * if player added and method gets called, the return value is the color from
	 * the current player.<br>
	 * Expected result: return color from current player
	 */
	@Test
	public void getColorFromCurrentPlayer_PlayerAdded_returnColor() {
		// Arrange
		testGame = new Game(5, "max", 2, 6, 6);
		Color expectedColor = Color.RED; // TODO get form configFile

		// Act
		Color result = testGame.getColorFromCurrentPlayer();

		// Assert
		assertEquals(expectedColor, result);
	}

	/**
	 * Equivalence Partitioning S3<br>
	 * if player added and method gets called, the return value is the name from the
	 * current player.<br>
	 * Expected result: return name from current player
	 */
	@Test
	public void getNameFromCurrentPlayer_PlayerAdded_returnString() {
		// Arrange
		String name = "max";
		testGame = new Game(5, name, 2, 6, 6);

		// Act
		String result = testGame.getNameFromCurrentPlayer();

		// Assert
		assertTrue(name.equals(result));
	}

	/**
	 * Equivalence Partitioning M1<br>
	 * Chip can't be added to board if column is invalid. nextmove method terminates
	 * early.<br>
	 * Expected result: returns false, chip has not been added to board
	 */
	@Test
	public void nextMove_InvalidColumn_returnFalse() {
		// Arrange
		testGame = new Game(5, "max", 2, 6, 6);

		// Act
		boolean result = testGame.nextMove(-5);

		// Assert
		assertFalse(result);
	}

	/**
	 * Equivalence Partitioning M2<br>
	 * Chip can't be added to board if column is already full. nextmove method
	 * terminates early.<br>
	 * Expected result: returns false, chip has not been added to board
	 */
	@Test
	public void nextMove_BoardIsFull_returnFalse() {
		// Arrange
		testGame = new Game(5, "max", 2, 6, 6);

		// Assert
		testGame.nextMove(0);
		testGame.nextMove(0);
		testGame.nextMove(0);
		testGame.nextMove(0);
		testGame.nextMove(0);
		testGame.nextMove(0);
		testGame.nextMove(0);
		testGame.nextMove(0);
		testGame.nextMove(0);
		testGame.nextMove(0);

		// Act
		boolean result = testGame.nextMove(0);

		// Assert
		assertFalse(result);
	}

	/**
	 * Equivalence Partitioning M3, M4<br>
	 * Column argument is valid and board has still empty space in that column. The
	 * chip can be placed.<br>
	 * Expected result: returns true, chip has been added to board
	 */
	@Test
	public void nextMove_emptyBoard_returnTrue() {
		// Arrange
		HashMap<Color, String> players = new HashMap<>();
		players.put(Color.RED, "tim");
		players.put(Color.BLUE, "max");
		testGame = new Game(5, players, 6, 6);
		String oldPlayer = testGame.getNameFromCurrentPlayer();

		// Act
		boolean result = testGame.nextMove(0);
		String newPlayer = testGame.getNameFromCurrentPlayer();

		// Assert
		assertTrue(result);
		assertTrue(!oldPlayer.equals(newPlayer));
	}

	/**
	 * Equivalence Partitioning W1<br>
	 * return null if no player has requested number of chips in a row.<br>
	 * Expected result: returns null, no winner
	 */
	@Test
	public void getWinner_hasNoWinner_returnsNull() {
		// Arrange
		testGame = new Game(5, "max", 2, 6, 6);

		// Act
		Player winner = testGame.getWinner();

		// Assert
		assertNull(winner);
	}

	/**
	 * Equivalence Partitioning W2<br>
	 * return player if player has requested number of chips in a row.<br>
	 * Expected result: returns player, the winner
	 */
	@Test
	public void getWinner_hasWinner_returnsWinner() {
		// Arrange
		String name = "tim";
		Color color = Color.YELLOW;
		HashMap<Color, String> players = new HashMap<>();
		players.put(color, name);
		players.put(Color.BLUE, "max");
		players.put(Color.BLACK, "laura");
		players.put(Color.WHITE, "olga");
		testGame = new Game(5, players, 6, 6);

		for (int i = 0; i < 5; i++) {
			testGame.nextMove(0);
			testGame.nextMove(1);
			testGame.nextMove(2);
			testGame.nextMove(3);
		}

		// Act
		Player winner = testGame.getWinner();

		// Assert
		assertEquals(name, winner.getName());
		assertEquals(color, winner.getColor());
	}
}
