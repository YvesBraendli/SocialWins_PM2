package ch.zhaw.pm2.socialWins;

import java.awt.Color;

import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;

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
	
	public static final String WELCOME_TEXT = "Willkommen beim SocialWins, viel Spass beim Spiel. Um Hilfe zu erhalten, den Button links oben klicken.";
	public static final String WRONG_QUEUE_TEXT = "Bitte w�hle eine andere Spalte, diese ist schon gef�llt.";
	public static final String INFORMATION_TEXT = "Bitte klicke auf ein Feld, welches sich in der Kolone befindet, in der du ein Spielstein hinzuf�gen m�chtest.";
	public static final javafx.scene.paint.Color DEFAULT_BACKGROUND_COLOR_OF_GAMEFIELD = javafx.scene.paint.Color.WHITE;
	public static final BorderStroke DEFAULT_BORDERSTROKE = new BorderStroke(javafx.scene.paint.Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1));
	public static final int INDICATOR_FOR_MULTIPLE_DIGIT_NUMBER = 10;
	public static final String PLAYER_PROMPT_TEXT_FRONT = "Spieler ";
	public static final String PLAYER_PROMPT_TEXT_BACK = " ist an der Reihe.";
	public static final String WINNING_QUEUE_TEXT_FRONT = "Erstelle eine Reihe von ";
	public static final String WINNING_QUEUE_TEXT_BACK = " Chips aneinander, um das Spiel zu gewinnen.";
	public static final String HELP_TEXT = "Dieser Text muss noch genauer definiert werden";
	
	public static final String SETUP_TITEL = "SocialWins - New Game";
	public static final String GAME_TITEL= "Willkommen beim SocialWins - Viel Spass";
	public static final String HELP_WINDOW_TITEL = "Help Window";
	
	
	// AI Configurations
	public static final int CENTER_COLUMS_SCORE = 4;
	public static final int TWO_IN_A_ROW_SCORE = 2;
	public static final int THREE_IN_A_ROW_SCORE = 6;
	public static final int FOUR_IN_A_ROW_SCORE = 500;
	public static final int OPPONENT_TWO_IN_A_ROW_PENALTY = -2;
	public static final int OPPONENT_THREE_IN_A_ROW_PENALTY = -100;
	public static final int OPPONENT_FOUR_IN_A_ROW_PENALTY = -200;
	
	public static final int POINT_BLOCK_SIZE = 4;



}
