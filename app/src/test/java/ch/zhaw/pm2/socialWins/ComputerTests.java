package ch.zhaw.pm2.socialWins;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import ch.zhaw.pm2.socialWins.strategy.Strategy;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.awt.Color;

public class ComputerTests {
	private Computer testComputer;
	
	private String name;
	private Color color;
	private @Mock Strategy strategy;
	private Board board;
	private int winningRow;

	@BeforeEach
	public void setup() {
		//MockitoAnnotations.initMocks(this);
		strategy = mock(Strategy.class);
		name = "tim";
		color = Color.RED;
		board = new Board(6, 6);
		winningRow = 5;
	}

	/**
	 * Equivalence Partitioning C1<br>
	 * Instantiate computer with invalid arguments : name is null<br>
	 * Expected result: throws IllegalArgumentException
	 */
	@Test
	public void computer_invalidArgumentsNameIsNull_IllegalArgumentException() {

		assertThrows(IllegalArgumentException.class, () -> new Computer(null, color, strategy, board, winningRow));
	}

	/**
	 * Equivalence Partitioning C1<br>
	 * Instantiate computer with invalid arguments : color is null<br>
	 * Expected result: throws IllegalArgumentException
	 */
	@Test
	public void computer_invalidArgumentsColorIsNull_IllegalArgumentException() {

		assertThrows(IllegalArgumentException.class, () -> new Computer(name, null, strategy, board, winningRow));
	}
	
	/**
	 * Equivalence Partitioning C1<br>
	 * Instantiate computer with invalid arguments : strategy is null<br>
	 * Expected result: throws IllegalArgumentException
	 */
	@Test
	public void computer_invalidArgumentsStrategyIsNull_IllegalArgumentException() {

		assertThrows(IllegalArgumentException.class, () -> new Computer(name, color, null, board, winningRow));
	}
	
	/**
	 * Equivalence Partitioning C1<br>
	 * Instantiate computer with invalid arguments : board is null<br>
	 * Expected result: throws IllegalArgumentException
	 */
	@Test
	public void computer_invalidArgumentsBoardIsNull_IllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> new Computer(name, color, strategy, null, winningRow));
	}
	
	/**
	 * Equivalence Partitioning C1<br>
	 * Instantiate computer with invalid arguments : wining row is lower than 3 and higher than 6<br>
	 * Expected result: throws IllegalArgumentException
	 */
	@ParameterizedTest
	@ValueSource(ints = { 1, 2, 7, 8 })
	public void computer_invalidArgumentsWinningRowIsNull_IllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> new Computer(name, color, null, board, winningRow));
	}
	
	/**
	 * Equivalence Partitioning C2<br>
	 * Instantiate computer with valid arguments <br>
	 * Expected result: throws IllegalArgumentException
	 */
	@Test
	public void nextMove_ValidArguments_strategyMethodGetsCalled() {
		// Arrange
		testComputer = new Computer(name, color, strategy, board, winningRow);
		
		// Act
		testComputer.nextMove();
		
		// Assert
    	verify(testComputer, times(1)).nextMove();
	}
	
}
