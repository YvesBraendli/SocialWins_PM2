package ch.zhaw.pm2.socialWins.strategy;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import ch.zhaw.pm2.socialWins.Board;
import ch.zhaw.pm2.socialWins.Chip;
import ch.zhaw.pm2.socialWins.Config;

/**
 * This Class is used to evaluate current board states and assign each possible position a score.
 * It implements the MiniMax Algorithm to decide, which is the best possible move to make, 
 * by brute forcing each possibility down to a specified depth. 
 * The class is used for a Connect 4 Bot to decide, which move it will play.
 * 
 * Credit: This implementation is inspired by Keith Galli's Implementation in Python 
 * but was also modified to allow winning rows 3-6.
 * https://medium.com/analytics-vidhya/artificial-intelligence-at-play-connect-four-minimax-algorithm-explained-3b5fc32e4a4f
 * 
 * 
 * @author yves braendli, robin meier, nadine moser
 *
 */
public class MoveCalculator {
	private int numberOfColumns;
	private int numberOfRows;
	private int winningRowLength;
	
	private static final int EMPTY_SPACES_FOR_LOW_SCORE = 2;
	private static final int EMPTY_SPACES_FOR_MEDIUM_SCORE = 1;

	/**
	 *  Constructor of the Move Calculator Class
	 * @param numberOfRows of the board
	 * @param numberOfColumns of the board
	 * @param winningRowLength number of chips in a row needed to win (3-6)
	 */
	public MoveCalculator(int numberOfRows, int numberOfColumns, int winningRowLength) {
		this.numberOfColumns = numberOfColumns;
		this.numberOfRows = numberOfRows;
		this.winningRowLength = winningRowLength;
	}
	
	/**
	 * Implementation of the MiniMax Algorithm. 
	 * The Method gets recursively called to try out every possible move the bot and the opponent can make.
	 * The depth of the recursion is decided by the depth parameter. Warning: depth higher than 7 take a long time to calculate!
	 * After the lowest depth has been reached, the move gets evaluated and if it is better than the last, it gets stored in the return Move object.
	 * @param depth how many iteration of the minimax algorithm should be applied
	 * @param board the current state of the board
	 * @param isMaximizing boolean which decides if the current simulated turn is the computer (maximizing) or the player (minimizing)
	 * @param setColumn the column of the last chip that was set
	 * @return a Move object containing a score and the corresponding move
	 */
	public Move calculateComputerMove(int depth, Board board, Boolean isMaximizing, int setColumn) {
		if(depth == 0 || isGameEnding(board)) {
				return new Move(setColumn, evaluateState(board.getBoard(), Config.SINGLEPLAYER_COMPUTERCOLOR, setColumn));
		}
		ArrayList<Integer> validColumns = getValidColumns(board);
		if(isMaximizing) {
			Move move = new Move(setColumn, Integer.MIN_VALUE);
			for(int column: validColumns) {
				Board boardCopy = new Board(board.generateBoardCopy(board.getBoard()));
				boardCopy.addChip(column, Config.SINGLEPLAYER_COMPUTERCOLOR);
				Move nextMove = calculateComputerMove(depth-1, boardCopy, false, column);
				if(nextMove.getScore() > move.getScore()) {
					move = nextMove;
				}
			}
			return move;
		}
		else {
			Move move = new Move(setColumn, Integer.MAX_VALUE);
			for(int column: validColumns) {
				Board boardCopy = new Board(board.generateBoardCopy(board.getBoard()));
				boardCopy.addChip(column, Config.SINGLEPLAYER_USERCOLOR);
				Move nextMove = calculateComputerMove(depth-1, boardCopy, true, column);
				if(nextMove.getScore() < move.getScore()) {
					move = nextMove;
				}
			}
			return move;
		}
	}
	
	private boolean isGameEnding(Board board) {
		if(board.isBoardFull() || Config.SINGLEPLAYER_COMPUTERCOLOR.equals(board.getColorWithChipsInARow(winningRowLength)) 
		|| Config.SINGLEPLAYER_USERCOLOR.equals(board.getColorWithChipsInARow(winningRowLength))) {
			return true;
		}
		return false;
	}
	
	private ArrayList<Integer> getValidColumns(Board board) {
		ArrayList<Integer> validColums = new ArrayList<Integer>();
		for(int i = 0; i < numberOfColumns; i++) {
			if(!board.isColumnFull(i)) {
				validColums.add(i);
			}
		}
		
		return validColums;
	}
	
	/**
	 *  Evaluates the current board state
	 * @param board the board
	 * @param playedChipColor the color of the last chip played
	 * @param playedColumn the column that the last chip was played in. 
	 * This is only used for the center column check.
	 * 
	 * @return the score of the current board state
	 */
	private int evaluateState(Chip[][] board, Color playedChipColor, int playedColumn) {
		int score = 0;

		score += checkHorizontalBlocks(board, playedChipColor);
		score += checkVerticalBlocks(board, playedChipColor);
		score += checkDiagonalBlocks(board, playedChipColor);

		double halfOfBoardColumns = numberOfColumns / 2.00;
		int centerColumn = (int) Math.floor(halfOfBoardColumns);
		score += checkIfCenterColumn(board, playedColumn, centerColumn);
		if(halfOfBoardColumns % 1 == 0) {
			score += checkIfCenterColumn(board, playedColumn, centerColumn-1);
		}
		
		
		return score;
	}

	private int checkIfCenterColumn(Chip[][] board, int playerColumn, int centerColumn) {
		int score = 0;
		if (centerColumn == playerColumn) {
				return score += Config.CENTER_COLUMS_SCORE;
		}
		return score;
	}

	/**
	 *  generates all possible blocks in the two diagonal directions the size of the winning row length
	 * @param board the current board state
	 * @param playedChipColor the color of the last chip played
	 * @return score
	 */
	private int checkDiagonalBlocks(Chip[][] board, Color playedChipColor) {
		int score = 0;
		for (int i = 0; i < numberOfRows - (winningRowLength - 1); i++) {
			for (int j = 0; j < numberOfColumns - (winningRowLength - 1); j++) {
				Chip[] block = new Chip[winningRowLength];
				for (int k = 0; k < winningRowLength; k++) {	
					block[k] = board[i - k + (winningRowLength - 1)][j + k];
				}
				score += evaluateBlock(block, playedChipColor);
				block = new Chip[winningRowLength];
				for (int k = 0; k < winningRowLength; k++) {	
					block[k] = board[i + k][j + k];
				}
				score += evaluateBlock(block, playedChipColor);
			}
		}
		return score;
	}

	/**
	 * Generates all possible blocks in vertical direction the size of the winning row length
	 * @param board the current board state
	 * @param playedChipColor the color of the last chip played
	 * @return score
	 */
	private int checkVerticalBlocks(Chip[][] board, Color playedChipColor) {
		int score = 0;
		for (int i = 0; i < numberOfColumns; i++) {
			for (int j = 0; j < numberOfRows - (winningRowLength - 1); j++) {
				Chip[] block = new Chip[winningRowLength];
				for (int k = j; k < j+winningRowLength; k++) {
					block[k-j] = board[k][i];
				}
				score += evaluateBlock(block, playedChipColor);
			}
		}
		return score;
	}

	/**
	 * Generates all possible blocks in horizontal direction the size of the winning row length
	 * @param board the current board state
	 * @param playedChipColor the color of the last chip played
	 * @return score
	 */
	private int checkHorizontalBlocks(Chip[][] board, Color playedChipColor) {
		int score = 0;
		for (int i = 0; i < numberOfRows; i++) {
			for (int j = 0; j < numberOfColumns - (winningRowLength - 1); j++) {
				Chip[] block = new Chip[winningRowLength];
				for (int k = j; k < j+winningRowLength; k++) {
					block[k-j] = board[i][k];
				}
				score += evaluateBlock(block, playedChipColor);
			}
		}
		return score;
	}

	/**
	 *  Checks a block if the conditions for a low, 
	 *  medium or high score are met and adds the corresponding score.
	 * @param block the current block to check
	 * @param playedChipColor the color of the last chip played
	 * @return score
	 */
	private int evaluateBlock(Chip[] block, Color playedChipColor) {
		int score = 0;
		int elementsInBlock = checkForElementsInBlock(block, playedChipColor);
		int emptyElementsInBlock = checkForEmptyElements(block);

		if (winningRowLength > 3 && elementsInBlock == (winningRowLength-EMPTY_SPACES_FOR_LOW_SCORE) && emptyElementsInBlock == EMPTY_SPACES_FOR_LOW_SCORE) {
			if (isComputerColor(playedChipColor)) {
				score += Config.LOW_SCORE;
			} else {
				score += Config.OPPONENT_LOW_PENALTY;
			}
		}
		if (elementsInBlock == (winningRowLength-EMPTY_SPACES_FOR_MEDIUM_SCORE) && emptyElementsInBlock == EMPTY_SPACES_FOR_MEDIUM_SCORE) {
			if (isComputerColor(playedChipColor)) {
				score += Config.MEDIUM_SCORE;
			} else {
				score += Config.OPPONENT_MEDIUM_PENALTY;
			}
		}
		if (elementsInBlock == winningRowLength) {
			if (isComputerColor(playedChipColor)) {
				score += Config.HIGH_SCORE;
			} 
			else {
				score += Config.OPPONENT_HIGH_PENALTY;
			}
		}
		return score;
	}

	private boolean isComputerColor(Color playedChipColor) {
		return playedChipColor.equals(Config.SINGLEPLAYER_COMPUTERCOLOR);
	}

	private int checkForElementsInBlock(Chip[] block, Color chipColor) {
		int numberOfElements = 0;
		for (int i = 0; i < winningRowLength; i++) {
			if (!(block[i] == null) && block[i].getColor().equals(chipColor)) {
				numberOfElements += 1;
			}
		}
		return numberOfElements;
	}

	private int checkForEmptyElements(Chip[] block) {
		int numberOfElements = 0;
		for (int i = 0; i < winningRowLength; i++) {
			if (block[i] == null) {
				numberOfElements += 1;
			}
		}
		return numberOfElements;
	}
}
