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

	//TODO überall wo aktuell Config.POINT_BLOCK_SIZE müssti winning row stah
	//TODO ich ha kei ahnig wie ich aktuell central row positions prüefe sött, machi spöter
	
	public Move calculateComputerMove(int depth, Board board, Boolean isMaximizing, int setColumn) {
		numberOfColumns = board.getBoard()[0].length;
		numberOfRows = board.getBoard().length;
		ArrayList<Integer> validColumns = getValidColumns(board);
		
		System.out.println("Working in depth: "+depth);
		if(depth == 0 || board.isBoardFull() || Config.SINGLEPLAYER_COMPUTERCOLOR.equals(board.getColorWithChipsInARow(Config.POINT_BLOCK_SIZE)) || Config.SINGLEPLAYER_USERCOLOR.equals(board.getColorWithChipsInARow(Config.POINT_BLOCK_SIZE))) {
				return new Move(setColumn, evaluateState(board.getBoard(), Config.SINGLEPLAYER_COMPUTERCOLOR));
		}
		
		if(isMaximizing) {
			Move move = new Move(setColumn,-100000);
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
			Move move = new Move(setColumn,100000);
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
	
	private int evaluateState(Chip[][] board, Color playedChipColor) {
		int score = 0;

		score += checkHorizontalBlocks(board, playedChipColor);
		score += checkVerticalBlocks(board, playedChipColor);
		score += checkDiagonalBlocks(board, playedChipColor);

		//int centerColumn = (int) Math.floor(numberOfColumns / 2);
		//score += checkIfCenterColumn(board, playedChip, centerColumn);
		
		return score;
	}

//	private int checkIfCenterColumn(Chip[][] board, Chip playedChip, int centerColumn) {
//		int score = 0;
//		for (int i = 0; i < numberOfRows; i++) {
//			if (board[i][centerColumn].equals(playedChip)) {
//				return score += Config.CENTER_COLUMS_SCORE;
//			}
//		}
//		return score;
//	}

	private int checkDiagonalBlocks(Chip[][] board, Color playedChipColor) {
		int score = 0;
		for (int i = 0; i < numberOfRows - (Config.POINT_BLOCK_SIZE - 1); i++) {
			for (int j = 0; j < numberOfColumns - (Config.POINT_BLOCK_SIZE - 1); j++) {
				Chip[] block = new Chip[Config.POINT_BLOCK_SIZE];
				for (int k = 0; k < Config.POINT_BLOCK_SIZE; k++) {	
					block[k] = board[i - k + (Config.POINT_BLOCK_SIZE - 1)][j + k];
				}
				score += evaluateBlock(block, playedChipColor);
				block = new Chip[Config.POINT_BLOCK_SIZE];
				for (int k = 0; k < Config.POINT_BLOCK_SIZE; k++) {	
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
			for (int j = 0; j < numberOfRows - (Config.POINT_BLOCK_SIZE - 1); j++) {
				Chip[] block = new Chip[Config.POINT_BLOCK_SIZE];
				for (int k = j; k < Config.POINT_BLOCK_SIZE; k++) {
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
			for (int j = 0; j < numberOfColumns - (Config.POINT_BLOCK_SIZE - 1); j++) {
				Chip[] block = new Chip[Config.POINT_BLOCK_SIZE];
				for (int k = j; k < Config.POINT_BLOCK_SIZE; k++) {
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
			} 
			else {
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
			if (!(block[i] == null) && block[i].getColor().equals(chipColor)) {
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
		return numberOfElements;
	}
}
