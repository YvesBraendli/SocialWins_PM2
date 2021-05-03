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
			FXMLLoader loader = new FXMLLoader(getClass().getResource("SetupView.fxml"));
			Pane rootPane = loader.load();
			Scene scene = new Scene(rootPane);
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
}