package ch.zhaw.pm2.socialWins;

import java.awt.Color;

import ch.zhaw.pm2.socialWins.strategy.Strategy;

/**
 * A computer is a Player that uses a strategy to calculate the next move.
 * 
 * @author robin meier, yves braendli, nadine moser
 *
 */
public class Computer extends Player {
	private Strategy strategy;
	private Board board;

	/**
	 * Constructor to instantiate a computer with the following arguments.
	 * 
	 * @param name             name of the computer
	 * @param color            color of the computer
	 * @param strategy         strategy for the computer
	 * @param board            board with current game state
	 * @param winningRowLength number to win the game
	 * @throws IllegalArgumentException if the arguments are null or the winning
	 *                                  length is lower than 3 or higher than 6
	 */
	public Computer(String name, Color color, Strategy strategy, Board board, int winningRowLength) {
		super(name, color);
		if (name == null || color == null || strategy == null || board == null
				|| winningRowLength < Config.LOWEST_POSSIBLE_WINNINGROW
				|| winningRowLength > Config.HIGHEST_POSSIBLE_WINNINGROW) {
			throw new IllegalArgumentException();
		}
		this.board = board;
		this.strategy = strategy;
	}

	public int nextMove() {
		return strategy.nextMove(board);
	}
}
