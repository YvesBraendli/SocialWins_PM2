package ch.zhaw.pm2.socialWins.strategy;



import java.awt.Color;

import ch.zhaw.pm2.socialWins.Board;
import ch.zhaw.pm2.socialWins.Chip;
import ch.zhaw.pm2.socialWins.Config;
import javafx.util.Pair;

public class MoveCalculator {
	public Pair<Integer,Integer> calculateComputerMove(int depth, Board board){
		
		return new Pair(0,0);
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
		
		for(int i = 0; i < numberOfRows; i++) {		
			for(int j = 0; j < numberOfColumns-(Config.POINT_BLOCK_SIZE-1); j++) {
				Chip[] block = new Chip[Config.POINT_BLOCK_SIZE];
				for(int k = 0; k< Config.POINT_BLOCK_SIZE; k++) {
					block[k] = board[i][j];
				}
				evaluateBlock(block);
			}
		}
		
		for(int i = 0; i < numberOfColumns; i++) {		
			for(int j = 0; j < numberOfRows-(Config.POINT_BLOCK_SIZE-1); j++) {
				Chip[] block = new Chip[Config.POINT_BLOCK_SIZE];
				for(int k = 0; k< Config.POINT_BLOCK_SIZE; k++) {
					block[k] = board[j][i];
				}
				evaluateBlock(block);
			}
		}
			
		return score;
	}
	
	private int evaluateBlock(Chip[] block) {
		int score = 0;
		return score;
	}
}
