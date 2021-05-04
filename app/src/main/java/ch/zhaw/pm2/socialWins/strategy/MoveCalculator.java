package ch.zhaw.pm2.socialWins.strategy;



import java.awt.Color;
import java.util.Arrays;

import ch.zhaw.pm2.socialWins.Board;
import ch.zhaw.pm2.socialWins.Chip;
import ch.zhaw.pm2.socialWins.Config;
import javafx.util.Pair;

public class MoveCalculator {
	public void calculateComputerMove(){ // int depth, Board board
		
		Chip[][] testBoard = new Chip[6][7];
		evaluateState(testBoard, new Chip(Color.RED));
	}
	
	
	private int evaluateState(Chip[][] board, Chip playedChip) {
		int score = 0;
		int numberOfColumns = board[0].length;
		int numberOfRows = board.length;
		int centerColumn = (int) Math.floor(numberOfColumns/2);
		
		for(int i = 0; i < numberOfColumns; i++ ) {
			if(board[i][centerColumn].equals(playedChip)) {
				score += Config.CENTER_COLUMS_SCORE;
			}
		}
		
		// horizontal blocks
		for(int i = 0; i < numberOfRows; i++) {		
			for(int j = 0; j < numberOfColumns-(Config.POINT_BLOCK_SIZE-1); j++) {
				Chip[] block = new Chip[Config.POINT_BLOCK_SIZE];
				for(int k = 0; k< Config.POINT_BLOCK_SIZE; k++) {
					block[k] = board[i][j];
				}
				if(Arrays.asList(block).contains(playedChip)) {
					evaluateBlock(block, playedChip);
				}
			}
		}
		
		// vertical blocks
		for(int i = 0; i < numberOfColumns; i++) {		
			for(int j = 0; j < numberOfRows-(Config.POINT_BLOCK_SIZE-1); j++) {
				Chip[] block = new Chip[Config.POINT_BLOCK_SIZE];
				for(int k = 0; k< Config.POINT_BLOCK_SIZE; k++) {
					block[k] = board[j][i];
				}
				if(Arrays.asList(block).contains(playedChip)) {
					evaluateBlock(block, playedChip);
				}
			}
		}
		
		// Diagonal blocks bottom left to top right
		for(int i = 0; i<numberOfRows-(Config.POINT_BLOCK_SIZE-1);i++) {
			for(int j = 0; j < numberOfColumns-(Config.POINT_BLOCK_SIZE-1); j++) {
				for(int k = 0; k< Config.POINT_BLOCK_SIZE; k++) {
					Chip[] block = new Chip[Config.POINT_BLOCK_SIZE];
					block[k] = board[i-k+(Config.POINT_BLOCK_SIZE-1)][j+k];
					if(Arrays.asList(block).contains(playedChip)) {
						evaluateBlock(block, playedChip);
					}
				}
			}
		}
		
		// Diagonal blocks top left to bottom right
		for(int i = 0; i<numberOfRows-(Config.POINT_BLOCK_SIZE-1);i++) {
			for(int j = 0; j < numberOfColumns-(Config.POINT_BLOCK_SIZE-1); j++) {
				for(int k = 0; k< Config.POINT_BLOCK_SIZE; k++) {
					Chip[] block = new Chip[Config.POINT_BLOCK_SIZE];
					block[k] = board[i+k][j+k];
					if(Arrays.asList(block).contains(playedChip)) {
						evaluateBlock(block, playedChip);
					}
				}
			}
		}
		return score;
	}
	
	private int evaluateBlock(Chip[] block, Chip playedChip) {
		int score = 0;
		return score;
	}
}
