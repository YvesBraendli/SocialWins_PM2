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

	/**
	 * Starts up the GUI and shows the setup-screen.
	 */
	@Override
	public void start(Stage primaryStage) {
		setupWindow(primaryStage);
	}

	private void setupWindow(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("SetupViewA.fxml"));
			Pane rootPane = loader.load();
			Scene scene = new Scene(rootPane);
			primaryStage.setScene(scene);
			primaryStage.setMinWidth(420);
			primaryStage.setMinHeight(250);
			primaryStage.setTitle("SocialWins - New Game");
			primaryStage.show();
		} catch (Exception e) {
			System.err.println("Error starting up UI" + e.getMessage());
		}
	}
}