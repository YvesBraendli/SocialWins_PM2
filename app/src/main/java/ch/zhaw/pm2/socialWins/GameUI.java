package ch.zhaw.pm2.socialWins;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * This class creates and shows the different GUI's, when it's necessary.
 * 
 * @author Moser Nadine, Meier Robin and Braendli Yves.
 *
 */
public class GameUI extends Application {
	
	private Game game;

	/**
	 * Starts up the GUI and shows the setup-screen.
	 */
	@Override
	public void start(Stage primaryStage) {
		game = new Game(5); // Add method game.getWinningLineLength
		showWinningViewWindow(primaryStage);
	}

	private void showWinningViewWindow(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("WinningView.fxml"));
			Pane rootPane = loader.load();
			WinningViewController winningViewController = loader.getController();
			winningViewController.setUpWinningView(game);
			Scene scene = new Scene(rootPane);
			primaryStage.setScene(scene);
			primaryStage.setMinWidth(420);
			primaryStage.setMinHeight(250);
			primaryStage.setTitle("SocialWins - Game ended");
			primaryStage.show();
		} catch (Exception e) {
			System.err.println("Error starting up UI" + e.getMessage());
		}
	}
}