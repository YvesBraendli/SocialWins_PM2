package ch.zhaw.pm2.socialWins;

import java.awt.Color;

/**
 * This class is adds constants used for the default game settings
 * 
 * @author Moser Nadine, Meier Robin and Braendli Yves.
 *
 */
public class Config {
	
	public static final int DEFAULT_NUMBER_OF_PLAYERS = 1; 
	public static final int MAX_NUMBER_OF_PLAYERS = 8;
	public static final int DEFAULT_WINNINGROW = 4;
	public static final int LOWEST_POSSIBLE_WINNINGROW = 3;
	public static final int HIGHEST_POSSIBLE_WINNINGROW = 6;
	public static final double BOARDSIZE_MULTIPLICATOR = 1.5;
	
	public static final String ALLOWED_PLAYERNAME_PATTERN = "^\\w+(-\\w+)*$";
	
	public static final Color SINGLEPLAYER_USERCOLOR = Color.RED;
	public static final Color SINGLEPLAYER_COMPUTERCOLOR = Color.BLUE;
	public static final String SINGLEPLAYER_COMPUTERNAME = "George";
	
	
	
	// AI Configurations
	public static final int CENTER_COLUMS_SCORE = 4;
	public static final int TWO_IN_A_ROW_SCORE = 2;
	public static final int THREE_IN_A_ROW_SCORE = 6;
	public static final int FOUR_IN_A_ROW_SCORE = 200;
	public static final int OPPONENT_TWO_IN_A_ROW_PENALTY = -2;
	public static final int OPPONENT_THREE_IN_A_ROW_PENALTY = -100;
	
	public static final int POINT_BLOCK_SIZE = 4;
}
