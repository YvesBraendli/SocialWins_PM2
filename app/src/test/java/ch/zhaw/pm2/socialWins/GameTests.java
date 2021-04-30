package ch.zhaw.pm2.socialWins;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.awt.Color;

public class GameTests {

	private Game testGame;

	@BeforeEach
	private void setup() {
		testGame = new Game(10, 4);
	}

	/**
	 * Equivalence Partitioning A1 invalid arguments This method tests if player is
	 * not added if argument name is null. Expected result: player should not be
	 * added
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
	 * Equivalence Partitioning A1 invalid arguments This method tests if player is
	 * not added if argument name is empty. Expected result: player should not be
	 * added
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
	 * Equivalence Partitioning A1 invalid arguments This method tests if player is
	 * not added if argument color is null. Expected result: player should not be
	 * added
	 *
	 */
	@Test
	public void addPlayer_invalidArgumentColorIsNull_returnsFalse() {
		// Act
		boolean result = testGame.addPlayer("max", null);

		// Assert
		assertFalse(result);
	}
}
