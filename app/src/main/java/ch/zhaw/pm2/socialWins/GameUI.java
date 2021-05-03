package ch.zhaw.pm2.socialWins;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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

	private void showSetupWindow(Stage primaryStage) {
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
			primaryStage.setTitle("SocialWins - New Game");
			primaryStage.show();
		} catch (Exception e) {
			System.err.println("Error starting up UI" + e.getMessage());
		}
	}
	
	public void switchToGameWindow(Game game, int rowLength, int columnLength) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("GameView.fxml"));
			AnchorPane rootPane = loader.load();
			GameWindowController gameWindowController = loader.getController();
			gameWindowController.setUpGameView(game, rowLength, columnLength);
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
			inputWindow.setTitle("Willkommen beim SocialWins - Viel Spass");
			inputWindow.show();
		} catch (Exception e) {
			System.err.println("Error starting up UI" + e.getMessage());
		}
	}
	
	
//	/**
//	 * Starts up the GUI and shows the setup-screen.
//	 */
//	@Override
//	public void start(Stage primaryStage) {
//		game = new Game(5);
//		gameWindow(primaryStage);
//	}
//
//	private void gameWindow(Stage primaryStage) {
//		try {
//			FXMLLoader loader = new FXMLLoader(getClass().getResource("GameView.fxml"));
//			Pane rootPane = loader.load();
//			GameWindowController gameWindowController = loader.getController();
//			gameWindowController.setUpGameView(game);
//			// fill in scene and stage setup
//			Scene scene = new Scene(rootPane);
//			
//			// configure and show stage
//			primaryStage.setScene(scene);
//			primaryStage.setMinWidth(750);
//			primaryStage.setMinHeight(600);
//			primaryStage.setWidth(750);
//			primaryStage.setHeight(700);
//			primaryStage.setTitle("SocialWins - New Game");
//			primaryStage.show();
//		} catch (Exception e) {
//			System.err.println("Error starting up UI" + e.getMessage());
//		}
//	}
	
}