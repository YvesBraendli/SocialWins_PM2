package ch.zhaw.pm2.socialWins;

import java.awt.Color;

import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;

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
	public static final int INDICATOR_FOR_MULTIPLE_DIGIT_NUMBER = 10;

	public static final double BOARDSIZE_MULTIPLICATOR = 1.5;

	public static final String ALLOWED_PLAYERNAME_PATTERN = "^\\w+(-\\w+)*$";
	public static final String SINGLEPLAYER_COMPUTERNAME = "George";
	public static final String WRONG_QUEUE_TEXT = "Bitte wähle eine andere Spalte, diese ist schon gefüllt.";
	public static final String INFORMATION_TEXT = "Bitte klicke auf ein Feld, welches sich in der Kolone befindet, in der du ein Spielstein hinzufügen möchtest.";
	public static final String HELP_TEXT = "Anleitung:\r\n"
			+ "\r\n"
			+ "Ziel des Spiels ist es eine bestimmt Anzahl an Chips in einer Reihe zu platzieren. Die Reihe kann vertikal, horizontal oder diagonal auf dem Feld liegen.\r\n"
			+ "\r\n"
			+ "Abwechslungsweise wählt jeder Spieler eine Spalte aus, in welcher der Chip in der Farbe des Spielers gesetzt werden soll. Der Chip selbst wird an der untersten möglichen Stelle in der Spalte platziert.\r\n"
			+ "\r\n"
			+ "Der erste Spieler, welcher die zu Beginn festgelegte Anzahl an Chips in einer Reihe hat, gewinnt.\r\n"
			+ "\r\n"
			+ "Programm:\r\n"
			+ "\r\n"
			+ "Neues Spiel : mit einem Klick auf \"Neues Spiel\" kann ein neues Spiel mit neuen Einstellungen gestartet werden. Der aktuelle Spielstand geht dabei verloren.\r\n"
			+ "\r\n"
			+ "Der Text oben rechts zeigt an, welcher Spieler an der Reihe ist um einen Chip zu platzieren.\r\n"
			+ "\r\n"
			+ "Der Text rechts in der mitte gibt eine Hilfestellung was als nächstes gemacht werden soll.\r\n"
			+ "\r\n"
			+ "Der Text unten rechts zeigt an wieviele Chips in einer Reihe platziert werden müssen, um das Spiel für sich zu entscheiden.\r\n"
			+ "\r\n"
			+ "Viel Spass beim Spielen!";
	public static final String SETUP_TITEL = "SocialWins - New Game";
	public static final String GAME_TITEL = "Willkommen beim SocialWins - Viel Spass";
	public static final String HELP_WINDOW_TITEL = "Help Window";
	public static final String WINNING_TITEL = "SocialWins - Game ended";
	public static final String WELCOME_TEXT = "Willkommen beim SocialWins, viel Spass beim Spiel. Um Hilfe zu erhalten, den Button links oben klicken.";
	public static final Color SINGLEPLAYER_USERCOLOR = Color.RED;
	public static final Color SINGLEPLAYER_COMPUTERCOLOR = Color.BLUE;
	public static final javafx.scene.paint.Color DEFAULT_BACKGROUND_COLOR_OF_GAMEFIELD = javafx.scene.paint.Color.WHITE;

	public static final BorderStroke DEFAULT_BORDERSTROKE = new BorderStroke(javafx.scene.paint.Color.BLACK,
			BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1));

	public static String createPlayerPromptText(String playerName) {
		return "Spieler " + playerName + " ist an der Reihe.";
	}

	public static String createWinningQueueText(int winningQueueLength) {
		return "Erstelle eine Reihe von " + winningQueueLength + " Chips aneinander, um das Spiel zu gewinnen.";
	}

	public static String createWinningText(String nameOfWinningPlayer) {
		return "Spieler " + nameOfWinningPlayer + " hat das Spiel gewonnen. Herzliche Gratulation!";
	}
	
	// AI Configurations
	public static final int CENTER_COLUMS_SCORE = 4;
	public static final int LOW_SCORE = 2;
	public static final int MEDIUM_SCORE = 6;
	public static final int HIGH_SCORE = 500;
	public static final int OPPONENT_LOW_PENALTY = -2;
	public static final int OPPONENT_MEDIUM_PENALTY = -100;
	public static final int OPPONENT_HIGH_PENALTY = -200;
	
	public static final int BEGINNER_SEARCH_DEPTH = 1;
	public static final int INTERMEDIATE_SEARCH_DEPTH = 3;
	public static final int ADVANCED_SEARCH_DEPTH = 5;
	public static final int DEPTH_NEGATION_AMOUNT = 4;
}
