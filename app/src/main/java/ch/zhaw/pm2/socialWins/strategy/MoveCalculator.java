package ch.zhaw.pm2.socialWins.strategy;

import java.awt.Color;
import java.util.Arrays;

import ch.zhaw.pm2.socialWins.Board;
import ch.zhaw.pm2.socialWins.Chip;
import ch.zhaw.pm2.socialWins.Config;
import javafx.util.Pair;

public class MoveCalculator {
	public void calculateComputerMove() { // int depth, Board board

		Chip[][] testBoard = new Chip[6][7];
		evaluateState(testBoard, new Chip(Color.RED));
	}

	private int evaluateState(Chip[][] board, Chip playedChip) {
		int score = 0;
		int numberOfColumns = board[0].length;
		int numberOfRows = board.length;
		int centerColumn = (int) Math.floor(numberOfColumns / 2);

		score += checkIfCenterColumn(board, playedChip, numberOfColumns, centerColumn);
		score += checkHorizontalBlocks(board, playedChip, numberOfColumns, numberOfRows);
		score += checkVerticalBlocks(board, playedChip, numberOfColumns, numberOfRows);
		score += checkDiagonalBlocks(board, playedChip, numberOfColumns, numberOfRows);

		return score;
	}

	private int checkIfCenterColumn(Chip[][] board, Chip playedChip, int numberOfColumns, int centerColumn) {
		int score = 0;
		for (int i = 0; i < numberOfColumns; i++) {
			if (board[i][centerColumn].equals(playedChip)) {
				score += Config.CENTER_COLUMS_SCORE;
			}
		}
		return score;
	}

	private int checkDiagonalBlocks(Chip[][] board, Chip playedChip, int numberOfColumns, int numberOfRows) {
		int score = 0;
		for (int i = 0; i < numberOfRows - (Config.POINT_BLOCK_SIZE - 1); i++) {
			for (int j = 0; j < numberOfColumns - (Config.POINT_BLOCK_SIZE - 1); j++) {

				for (int k = 0; k < Config.POINT_BLOCK_SIZE; k++) {
					Chip[] block = new Chip[Config.POINT_BLOCK_SIZE];
					block[k] = board[i - k + (Config.POINT_BLOCK_SIZE - 1)][j + k];
					if (Arrays.asList(block).contains(playedChip)) {
						score += evaluateBlock(block, playedChip);
					}

					block = new Chip[Config.POINT_BLOCK_SIZE];
					block[k] = board[i + k][j + k];
					if (Arrays.asList(block).contains(playedChip)) {
						score += evaluateBlock(block, playedChip);
					}
				}
			}
		}
		return score;
	}

	private int checkVerticalBlocks(Chip[][] board, Chip playedChip, int numberOfColumns, int numberOfRows) {
		int score = 0;
		for (int i = 0; i < numberOfColumns; i++) {
			for (int j = 0; j < numberOfRows - (Config.POINT_BLOCK_SIZE - 1); j++) {
				Chip[] block = new Chip[Config.POINT_BLOCK_SIZE];
				for (int k = 0; k < Config.POINT_BLOCK_SIZE; k++) {
					block[k] = board[j][i];
				}
				if (Arrays.asList(block).contains(playedChip)) {
					score += evaluateBlock(block, playedChip);
				}
			}
		}
		return score;
	}

	private int checkHorizontalBlocks(Chip[][] board, Chip playedChip, int numberOfColumns, int numberOfRows) {
		int score = 0;
		for (int i = 0; i < numberOfRows; i++) {
			for (int j = 0; j < numberOfColumns - (Config.POINT_BLOCK_SIZE - 1); j++) {
				Chip[] block = new Chip[Config.POINT_BLOCK_SIZE];
				for (int k = 0; k < Config.POINT_BLOCK_SIZE; k++) {
					block[k] = board[i][j];
				}
				if (Arrays.asList(block).contains(playedChip)) {
					score += evaluateBlock(block, playedChip);
				}
			}
		}
		return score;
	}

	private int evaluateBlock(Chip[] block, Chip playedChip) {
		int score = 0;
		int elementsInBlock = checkForElementsInBlock(block, playedChip.getColor());
		int emptyElementsInBlock = checkForEmptyElements(block);

		if (elementsInBlock == 2 && emptyElementsInBlock == 2) {
			if (isComputerColor(playedChip)) {
				score += Config.TWO_IN_A_ROW_SCORE;
			} else {
				score += Config.OPPONENT_TWO_IN_A_ROW_PENALTY;
			}
		} else if (elementsInBlock == 3 && emptyElementsInBlock == 1) {
			if (isComputerColor(playedChip)) {
				score += Config.THREE_IN_A_ROW_SCORE;
			} else {
				score += Config.OPPONENT_THREE_IN_A_ROW_PENALTY;
			}
		} else if (elementsInBlock == 4) {
			if (isComputerColor(playedChip)) {
				score += Config.FOUR_IN_A_ROW_SCORE;
			} else {
				score += Config.OPPONENT_FOUR_IN_A_ROW_PENALTY;
			}
		}
		return score;
	}

	private boolean isComputerColor(Chip playedChip)) {
		return playedChip.getColor().equals(Config.SINGLEPLAYER_COMPUTERCOLOR);
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
