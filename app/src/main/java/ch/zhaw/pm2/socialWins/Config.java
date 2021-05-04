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
	
	public static final String WELCOME_TEXT = "Willkommen beim SocialWins, viel Spass beim Spiel. Um Hilfe zu erhalten, den Button links oben clicken.";
	public static final String WRONG_QUEUE_TEXT = "Bitte wähle eine andere Spalte, diese ist schon gefüllt.";
	public static final String INFORMATION_TEXT = "Bitte clicke auf ein Feld, welches sich in der Kolone befindet, in der du ein Spielstein hinzufügen möchtest.";
}
