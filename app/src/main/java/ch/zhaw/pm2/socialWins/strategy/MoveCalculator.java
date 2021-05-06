package ch.zhaw.pm2.socialWins.strategy;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import ch.zhaw.pm2.socialWins.Board;
import ch.zhaw.pm2.socialWins.Chip;
import ch.zhaw.pm2.socialWins.Config;

public class MoveCalculator {
	int numberOfColumns;
	int numberOfRows;
	int winningRowLength;
	
	public static final int EMPTY_SPACES_FOR_LOW_SCORE = 2;
	public static final int EMPTY_SPACES_FOR_MEDIUM_SCORE = 1;

	public MoveCalculator(int numberOfRows, int numberOfColumns, int winningRowLength) {
		this.numberOfColumns = numberOfColumns;
		this.numberOfRows = numberOfRows;
		this.winningRowLength = winningRowLength;
	}
	
	public Move calculateComputerMove(int depth, Board board, Boolean isMaximizing, int setColumn) {
		ArrayList<Integer> validColumns = getValidColumns(board);
		if(depth == 0 || board.isBoardFull() || Config.SINGLEPLAYER_COMPUTERCOLOR.equals(board.getColorWithChipsInARow(winningRowLength)) || Config.SINGLEPLAYER_USERCOLOR.equals(board.getColorWithChipsInARow(winningRowLength))) {
				return new Move(setColumn, evaluateState(board.getBoard(), Config.SINGLEPLAYER_COMPUTERCOLOR, setColumn));
		}
		
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
	
	
	private ArrayList<Integer> getValidColumns(Board board) {
		ArrayList<Integer> validColums = new ArrayList<Integer>();
		for(int i = 0; i < numberOfColumns; i++) {
			if(!board.isColumnFull(i)) {
				validColums.add(i);
			}
		}
		
		return validColums;
	}
	
	private int evaluateState(Chip[][] board, Color playedChipColor, int playedColumn) {
		int score = 0;

		score += checkHorizontalBlocks(board, playedChipColor);
		score += checkVerticalBlocks(board, playedChipColor);
		score += checkDiagonalBlocks(board, playedChipColor);

		int centerColumn = (int) Math.floor(numberOfColumns / 2);
		score += checkIfCenterColumn(board, playedColumn, centerColumn);
		
		return score;
	}

	private int checkIfCenterColumn(Chip[][] board, int playerColumn, int centerColumn) {
		int score = 0;
		if (centerColumn == playerColumn) {
				return score += Config.CENTER_COLUMS_SCORE;
		}
		return score;
	}

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

	private int checkVerticalBlocks(Chip[][] board, Color playedChipColor) {
		int score = 0;
		for (int i = 0; i < numberOfColumns; i++) {
			for (int j = 0; j < numberOfRows - (winningRowLength - 1); j++) {
				Chip[] block = new Chip[winningRowLength];
				for (int k = j; k < winningRowLength; k++) {
					block[k-j] = board[k][i];
				}
				score += evaluateBlock(block, playedChipColor);
			}
		}
		return score;
	}

	private int checkHorizontalBlocks(Chip[][] board, Color playedChipColor) {
		int score = 0;
		for (int i = 0; i < numberOfRows; i++) {
			for (int j = 0; j < numberOfColumns - (winningRowLength - 1); j++) {
				Chip[] block = new Chip[winningRowLength];
				for (int k = j; k < winningRowLength; k++) {
					block[k-j] = board[i][k];
				}
				score += evaluateBlock(block, playedChipColor);
			}
		}
		return score;
	}

	private int evaluateBlock(Chip[] block, Color playedChipColor) {
		int score = 0;
		int elementsInBlock = checkForElementsInBlock(block, playedChipColor);
		int emptyElementsInBlock = checkForEmptyElements(block);

		if (elementsInBlock == (winningRowLength-EMPTY_SPACES_FOR_LOW_SCORE) && emptyElementsInBlock == EMPTY_SPACES_FOR_LOW_SCORE) {
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
