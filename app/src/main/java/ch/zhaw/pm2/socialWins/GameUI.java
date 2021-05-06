package ch.zhaw.pm2.socialWins;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * This class creates and shows the different GUI's, when it's necessary.
 * 
 * @author Moser Nadine, Meier Robin and Braendli Yves.
 *
 */
public class GameUI extends Application {

	/**
	 * Starts up the GUI and shows the setup-screen.
	 */
	@Override
	public void start(Stage primaryStage) {
		showSetupWindow(primaryStage);
	}

	/**
	 * Sets up and shows the setup window for the social-wins game.
	 * 
	 * @param primaryStage the primary stage for all GUI's of the game.
	 */
	public void showSetupWindow(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("SetupView.fxml"));
			Pane rootPane = loader.load();
			SetupWindowController setupWindowController = loader.getController();
			setupWindowController.setGameUI(this);
			Scene scene = new Scene(rootPane);

			// configure and show stage
			primaryStage.setScene(scene);
			primaryStage.setMinWidth(328);
			primaryStage.setMinHeight(310);
			primaryStage.setMaxWidth(328);
			primaryStage.setMaxHeight(310);
			primaryStage.setTitle(Config.SETUP_TITEL);
			primaryStage.show();
		} catch (Exception e) {
			System.err.println("Error starting up UI" + e.getMessage());
		}
	}

	/**
	 * Sets up and shows the game window for the social-wins game.
	 * 
	 * @param game         the current Game-Object of the social-wins game.
	 * @param rowLength    the calculated number of rows for a social-wins game.
	 * @param columnLength the calculated number of colums for a social-wins game.
	 */
	public void switchToGameWindow(Game game, int rowLength, int columnLength) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("GameView.fxml"));
			AnchorPane rootPane = loader.load();
			GameWindowController gameWindowController = loader.getController();
			gameWindowController.setUpGameView(game, rowLength, columnLength);
			gameWindowController.setGameUI(this);
			// fill in scene and stage setup
			Scene scene = new Scene(rootPane);
			// new stage for new window
			Stage inputWindow = new Stage();
			// configure and show stage
			inputWindow.setScene(scene);
			inputWindow.setMinWidth(750);
			inputWindow.setMinHeight(600);
			inputWindow.setWidth(750);
			inputWindow.setHeight(700);
			inputWindow.setTitle(Config.GAME_TITEL);
			inputWindow.show();
		} catch (Exception e) {
			System.err.println("Error starting up UI" + e.getMessage());
		}
	}

	/**
	 * Sets up and shows the game window for the social-wins game.
	 * 
	 * @param buttonsOnGameField the ArrayList with all the buttons in the
	 *                           social-wins game.
	 * @param numberOfRows       the calculated number of rows for a social-wins
	 *                           game.
	 * @param numberOfColumns    the calculated number of colums for a social-wins
	 *                           game.
	 * @param game               the current object of the Game class of a
	 *                           social-wins game.
	 */
	public void switchToWinningView(ArrayList<Button> buttonsOnGameField, int numberOfRows, int numberOfColumns,
			Game game) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("WinningView.fxml"));
			Pane rootPane = loader.load();
			WinningViewController winningViewController = loader.getController();
			winningViewController.setUpWinningView(game, numberOfRows, numberOfColumns, buttonsOnGameField, this);
			Scene scene = new Scene(rootPane);
			Stage winningStage = new Stage();
			winningStage.setScene(scene);
			winningStage.setMinWidth(600);
			winningStage.setMinHeight(430);
			winningStage.setTitle("SocialWins - Game ended");
			winningStage.show();
		} catch (Exception e) {
			System.err.println("Error starting up UI" + e.getMessage());
		}
	}
}