package ch.zhaw.pm2.socialWins;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;

public class GameTests {

	private Game testGame;

	@BeforeEach
	private void setup() {
		testGame = new Game(10, 4);
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
	 * This method tests if player is not added if the number of players have already been added<br>
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

}
