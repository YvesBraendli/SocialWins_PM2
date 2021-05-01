package ch.zhaw.pm2.socialWins;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;

public class GameTests {

	private Game testGame;

	@BeforeEach
	private void setup() {
		testGame = new Game(5, 4);
	}

	/**
	 * Equivalence Partitioning A1<br>
	 * This method tests if player is not added if argument name is null. <br>
	 * Expected result: player should not be added
	 *
	 */
	@Test
	public void addPlayer_invalidArgumentNameIsNull_returnsFalse() {
		// Act
		boolean result = testGame.addPlayer(null, Color.BLUE);

		// Assert
		assertFalse(result);
	}

	/**
	 * Equivalence Partitioning A1 <br>
	 * This method tests if player is not added if argument name is empty. <br>
	 * Expected result: player should not be added
	 *
	 */
	@Test
	public void addPlayer_invalidArgumentNameIsEmpty_returnsFalse() {
		// Act
		boolean result = testGame.addPlayer("", Color.BLUE);

		// Assert
		assertFalse(result);
	}

	/**
	 * Equivalence Partitioning A1 <br>
	 * This method tests if player is not added if argument color is null. <br>
	 * Expected result: player should not be added
	 *
	 */
	@Test
	public void addPlayer_invalidArgumentColorIsNull_returnsFalse() {
		// Act
		boolean result = testGame.addPlayer("max", null);

		// Assert
		assertFalse(result);
	}

	/**
	 * Equivalence Partitioning A2 <br>
	 * This method tests if player is not added if player with same name already
	 * exists. <br>
	 * Expected result: player should not be added
	 *
	 */
	@Test
	public void addPlayer_NameAlreadyExists_returnsFalse() {
		// Arrange
		String name = "max";
		testGame.addPlayer(name, Color.YELLOW);

		// Act
		boolean result = testGame.addPlayer(name, Color.BLUE);

		// Assert
		assertFalse(result);
	}

	/**
	 * Equivalence Partitioning A2 <br>
	 * This method tests if player is not added if player with same color already
	 * exists. <br>
	 * Expected result: player should not be added
	 *
	 */
	@Test
	public void addPlayer_ColorAlreadyExists_returnsFalse() {
		// Arrange
		testGame.addPlayer("tim", Color.BLUE);

		// Act
		boolean result = testGame.addPlayer("max", Color.BLUE);

		// Assert
		assertFalse(result);
	}

	/**
	 * Equivalence Partitioning A3 <br>
	 * This method tests if player is not added if the number of players have
	 * already been added<br>
	 * Expected result: player should not be added
	 *
	 */
	@Test
	public void addPlayer_AllPlayerHaveBeenAddedNoMoreSpace_returnsFalse() {
		// Arrange
		testGame.addPlayer("tim", Color.YELLOW);
		testGame.addPlayer("karl", Color.RED);
		testGame.addPlayer("gaby", Color.GREEN);
		testGame.addPlayer("klöschen", Color.WHITE);

		// Act
		boolean result = testGame.addPlayer("helga", Color.BLUE);

		// Assert
		assertFalse(result);
	}

	/**
	 * Equivalence Partitioning A4, A5 <br>
	 * This method tests if player is added if the arguments are correct and all
	 * arguments are unique<br>
	 * Expected result: player should be added
	 *
	 */
	@Test
	public void addPlayer_CorrectAndUniqueArguments_returnsTrue() {

		// Act
		boolean result = testGame.addPlayer("max", Color.BLUE);

		// Assert
		assertTrue(result);
	}

	/**
	 * Equivalence Partitioning A1<br>
	 * This method tests if computer is not added if argument name is null. <br>
	 * Expected result: computer should not be added
	 *
	 */
	@Test
	public void addComputer_invalidArgumentNameIsNull_returnsFalse() {
		// Arrange
		testGame = new Game(5, 1);

		// Act
		boolean result = testGame.addComputer(null, Color.BLUE, 1);

		// Assert
		assertFalse(result);
	}

	/**
	 * Equivalence Partitioning A1 <br>
	 * This method tests if computer is not added if argument name is empty. <br>
	 * Expected result: computer should not be added
	 *
	 */
	@Test
	public void addComputer_invalidArgumentNameIsEmpty_returnsFalse() {
		// Arrange
		testGame = new Game(5, 1);

		// Act
		boolean result = testGame.addComputer("", Color.BLUE, 1);

		// Assert
		assertFalse(result);
	}

	/**
	 * Equivalence Partitioning A1 <br>
	 * This method tests if computer is not added if argument color is null. <br>
	 * Expected result: computer should not be added
	 *
	 */
	@Test
	public void addComputer_invalidArgumentColorIsNull_returnsFalse() {
		// Arrange
		testGame = new Game(5, 1);

		// Act
		boolean result = testGame.addComputer("max", null, 1);

		// Assert
		assertFalse(result);
	}

	/**
	 * Equivalence Partitioning A1 <br>
	 * This method tests if computer is not added if argument level is not between 1
	 * and 3. <br>
	 * Expected result: computer should not be added
	 *
	 */
	@Test
	public void addComputer_invalidArgumentLevelToHigh_returnsFalse() {
		// Arrange
		testGame = new Game(5, 1);

		// Act
		boolean result = testGame.addComputer("max", Color.BLUE, 4);

		// Assert
		assertFalse(result);
	}

	/**
	 * Equivalence Partitioning A1 <br>
	 * This method tests if computer is not added if argument level is not between 1
	 * and 3. <br>
	 * Expected result: computer should not be added
	 *
	 */
	@Test
	public void addComputer_invalidArgumentLevelToLow_returnsFalse() {
		// Arrange
		testGame = new Game(5, 1);

		// Act
		boolean result = testGame.addComputer("max", Color.BLUE, 0);

		// Assert
		assertFalse(result);
	}

	/**
	 * Equivalence Partitioning A2 <br>
	 * This method tests if computer is not added if other computers already
	 * added.<br>
	 * Expected result: computer should not be added
	 *
	 */
	@Test
	public void addComputer_OtherCoputerAlreadyExists_returnsFalse() {
		// Arrange
		testGame = new Game(5, 1);
		testGame.addComputer("max", Color.YELLOW, 1);

		// Act
		boolean result = testGame.addComputer("tim", Color.BLUE, 1);

		// Assert
		assertFalse(result);
	}

	/**
	 * Equivalence Partitioning A3 <br>
	 * This method tests if computer is not added if modus is not set to single
	 * player<br>
	 * Expected result: computer should not be added
	 *
	 */
	@Test
	public void addComputer_NoSinglePlayerModus_returnsFalse() {
		// Arrange
		testGame = new Game(5, 2);

		// Act
		boolean result = testGame.addComputer("max", Color.BLUE, 2);

		// Assert
		assertFalse(result);
	}

	/**
	 * Equivalence Partitioning A4, A5 <br>
	 * This method tests if computer is added if the arguments are correct and all
	 * arguments are unique<br>
	 * Expected result: computer should be added
	 *
	 */
	@Test
	public void addComputer_CorrectAndUniqueArguments_returnsTrue() {
		// Arrange
		testGame = new Game(5, 1);

		// Act
		boolean result = testGame.addComputer("max", Color.BLUE, 2);

		// Assert
		assertTrue(result);
	}

	/**
	 * Equivalence Partitioning S1<br>
	 * Not expected number of players are added, game can't be started. <br>
	 * Expected result: return false, game not started
	 */
	@Test
	public void start_MulitPlayerNotAllPlayerAdded_returnFalse() {
		// Act
		boolean result = testGame.start();

		// Assert
		assertFalse(result);
	}

	/**
	 * Equivalence Partitioning S2<br>
	 * Not correct type of players are added, game can't be started. <br>
	 * Expected result: return false, game not started
	 */
	@Test
	public void start_SinglePlayOnlyHumanPlayerAdded_returnFalse() {
		// Arrange
		testGame = new Game(5, 1);
		testGame.addPlayer("tim", Color.RED);
		testGame.addPlayer("max", Color.YELLOW);

		// Act
		boolean result = testGame.start();

		// Assert
		assertFalse(result);
	}

	/**
	 * Equivalence Partitioning S2<br>
	 * Not correct type of players are added, game can't be started. <br>
	 * Expected result: return false, game not started
	 */
	@Test
	public void start_MulitPlayerComputerAdded_returnFalse() {
		// Arrange
		testGame.addPlayer("tim", Color.RED);
		testGame.addComputer("tim", Color.BLACK, 2);
		testGame.addPlayer("max", Color.YELLOW);
		testGame.addPlayer("tim", Color.ORANGE);

		// Act
		boolean result = testGame.start();

		// Assert
		assertFalse(result);
	}

	/**
	 * Equivalence Partitioning S3<br>
	 * Correct number of and correct type of players are added. in multi player only
	 * humanPlayer instances are allowed. Expected result: return false, game not
	 * started
	 */
	@Test
	public void start_MulitPlayOnlyHumanPlayerAdded_returnTrue() {
		// Arrange
		testGame.addPlayer("tim", Color.RED);
		testGame.addPlayer("moritz", Color.GREEN);
		testGame.addPlayer("max", Color.YELLOW);
		testGame.addPlayer("franz", Color.ORANGE);

		// Act
		boolean result = testGame.start();

		// Assert
		assertTrue(result);
	}

	/**
	 * Equivalence Partitioning S2<br>
	 * Correct number of and correct type of players are added. in single player
	 * only one humanPlayer instance and one computer instance are allowed.<br>
	 * Expected result: return false, game not started
	 */
	@Test
	public void start_SinglePlayerComputerAdded_returnTrue() {
		// Arrange
		testGame = new Game(6, 1);
		testGame.addPlayer("tim", Color.RED);
		testGame.addComputer("tim", Color.BLACK, 2);

		// Act
		boolean result = testGame.start();

		// Assert
		assertTrue(result);
	}
	
	/**
	 * Equivalence Partitioning S1<br>
	 * if no player is added yet and method gets called, the return value is null<br>
	 * Expected result: return null
	 */
	@Test
	public void getColorFromCurrentPlayer_NoPlayerAdded_returnNull() {
		// Act
		Color result = testGame.getColorFromCurrentPlayer();
		
		// Assert
		assertNull(result);
	}
	
	/**
	 * Equivalence Partitioning S3<br>
	 * if player added  and method gets called, the return value is the color from the current player.<br>
	 * Expected result: return color from current player
	 */
	@Test
	public void getColorFromCurrentPlayer_PlayerAdded_returnColor() {
		// Arrange
		testGame.addPlayer("max", Color.YELLOW);
		// Act
		Color result = testGame.getColorFromCurrentPlayer();
		
		// Assert
		assertEquals(Color.YELLOW, result);
	}
	
	/**
	 * Equivalence Partitioning S1<br>
	 * if no player is added yet and method gets called, the return value is null<br>
	 * Expected result: return null
	 */
	@Test
	public void getNameFromCurrentPlayer_NoPlayerAdded_returnNull() {
		// Act
		String result = testGame.getNameFromCurrentPlayer();
		
		// Assert
		assertNull(result);
	}

	/**
	 * Equivalence Partitioning S3<br>
	 * if player added  and method gets called, the return value is the name from the current player.<br>
	 * Expected result: return name from current player
	 */
	@Test
	public void getColorFromCurrentPlayer_PlayerAdded_returnString() {
		// Arrange
		String name = "max";
		testGame.addPlayer(name, Color.YELLOW);
		
		// Act
		String result = testGame.getNameFromCurrentPlayer();
		
		// Assert
		assertTrue(name.equals(result));
	}
}
