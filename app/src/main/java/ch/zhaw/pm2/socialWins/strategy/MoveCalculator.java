package ch.zhaw.pm2.socialWins.strategy;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import ch.zhaw.pm2.socialWins.Board;
import ch.zhaw.pm2.socialWins.Chip;
import ch.zhaw.pm2.socialWins.Config;
import javafx.util.Pair;

public class MoveCalculator {
	int numberOfColumns;
	int numberOfRows;
	int resultColumn;
	
	
	public int calculateComputerMove(int depth, Board board, Boolean isMaximizing) {
		numberOfColumns = board.getBoard()[0].length;
		numberOfRows = board.getBoard().length;
	
		ArrayList<Integer> validColumns = getValidColumns(board);
		
		if(depth == 0) {
			return evaluateState(board.getBoard());
		}
		
		if(isMaximizing) {
			int score = -100000;
			for(int column: validColumns) {
				boardCopy = new Board(board.getBoard().clone());
				boardCopy.addChip(column);
				int newScore = calculateComputerMove(depth-1, boardCopy, false);
				if(newScore > score) {
					score = newScore;
				}
				return score;
			}
		}
		else {
			
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
	


	private int evaluateState(Chip[][] board, Color playedChipColor) {
		int score = 0;
		int centerColumn = (int) Math.floor(numberOfColumns / 2);

		score += checkIfCenterColumn(board, playedChipColor, numberOfColumns, centerColumn);
		score += checkHorizontalBlocks(board, playedChipColor, numberOfColumns, numberOfRows);
		score += checkVerticalBlocks(board, playedChipColor, numberOfColumns, numberOfRows);
		score += checkDiagonalBlocks(board, playedChipColor, numberOfColumns, numberOfRows);

		return score;
	}

	private int checkIfCenterColumn(Chip[][] board, Color playedChipColor, int centerColumn) {
		int score = 0;
		for (int i = 0; i < numberOfRows; i++) {
			if (board[i][centerColumn].getColor(playedChipColor)) {
				return score += Config.CENTER_COLUMS_SCORE;
			}
		}
		return score;
	}

	private int checkDiagonalBlocks(Chip[][] board, Color playedChipColor) {
		int score = 0;
		for (int i = 0; i < numberOfRows - (Config.POINT_BLOCK_SIZE - 1); i++) {
			for (int j = 0; j < numberOfColumns - (Config.POINT_BLOCK_SIZE - 1); j++) {

				for (int k = 0; k < Config.POINT_BLOCK_SIZE; k++) {
					Chip[] block = new Chip[Config.POINT_BLOCK_SIZE];
					block[k] = board[i - k + (Config.POINT_BLOCK_SIZE - 1)][j + k];
					score += evaluateBlock(block, playedChipColor);

					block = new Chip[Config.POINT_BLOCK_SIZE];
					block[k] = board[i + k][j + k];
					score += evaluateBlock(block, playedChipColor);
				}
			}
		}
		return score;
	}

	private int checkVerticalBlocks(Chip[][] board, Color playedChipColor) {
		int score = 0;
		for (int i = 0; i < numberOfColumns; i++) {
			for (int j = 0; j < numberOfRows - (Config.POINT_BLOCK_SIZE - 1); j++) {
				Chip[] block = new Chip[Config.POINT_BLOCK_SIZE];
				for (int k = 0; k < Config.POINT_BLOCK_SIZE; k++) {
					block[k] = board[j][i];
				}
				score += evaluateBlock(block, playedChipColor);
			}
		}
		return score;
	}

	private int checkHorizontalBlocks(Chip[][] board, Color playedChipColor) {
		int score = 0;
		for (int i = 0; i < numberOfRows; i++) {
			for (int j = 0; j < numberOfColumns - (Config.POINT_BLOCK_SIZE - 1); j++) {
				Chip[] block = new Chip[Config.POINT_BLOCK_SIZE];
				for (int k = 0; k < Config.POINT_BLOCK_SIZE; k++) {
					block[k] = board[i][j];
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

		if (elementsInBlock == 2 && emptyElementsInBlock == 2) {
			if (isComputerColor(playedChipColor)) {
				score += Config.TWO_IN_A_ROW_SCORE;
			} else {
				score += Config.OPPONENT_TWO_IN_A_ROW_PENALTY;
			}
		} else if (elementsInBlock == 3 && emptyElementsInBlock == 1) {
			if (isComputerColor(playedChipColor)) {
				score += Config.THREE_IN_A_ROW_SCORE;
			} else {
				score += Config.OPPONENT_THREE_IN_A_ROW_PENALTY;
			}
		} else if (elementsInBlock == 4) {
			if (isComputerColor(playedChipColor)) {
				score += Config.FOUR_IN_A_ROW_SCORE;
			} else {
				score += Config.OPPONENT_FOUR_IN_A_ROW_PENALTY;
			}
		}
		return score;
	}

	private boolean isComputerColor(Color playedChipColor) {
		return playedChipColor.equals(Config.SINGLEPLAYER_COMPUTERCOLOR);
	}

	private int checkForElementsInBlock(Chip[] block, Color chipColor) {
		int numberOfElements = 0;
		for (int i = 0; i < Config.POINT_BLOCK_SIZE; i++) {
			if (block[i].getColor().equals(chipColor)) {
				numberOfElements += 1;
			}
		}
		return numberOfElements;
	}

	private int checkForEmptyElements(Chip[] block) {
		int numberOfElements = 0;
		for (int i = 0; i < Config.POINT_BLOCK_SIZE; i++) {
			if (block[i] == null) {
				numberOfElements += 1;
			}
		}
		return 0;
	}
}
