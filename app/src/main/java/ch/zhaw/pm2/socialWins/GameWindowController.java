package ch.zhaw.pm2.socialWins;

import java.awt.TextArea;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Controller for the game window.
 * @author Meier Robin, Moser Nadine, Braendli Yves
 *
 */
public class GameWindowController {
	private Game game; 
	private Parent parentSceneGraph;
	
	@FXML private Button newGameButton;
	@FXML private Button helpButton;;
	

	/**
	 * Is called from GameUI to connect the Controller to the game model.
	 * @param model The current model of the game class.
	 */
	public void setModel(Game model) {
		game = model;
	}
	
	public void setParentSceneGraph(Parent parentSceneGraph) {
		this.parentSceneGraph = parentSceneGraph;
	}
	
	@FXML
	private void startNewGame() {
		game = new Game(5);
		Stage stage = (Stage) newGameButton.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	private void showHelp() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("HelpView.fxml"));
			Pane rootPane = loader.load();
			// fill in scene and stage setup
			Scene scene = new Scene(rootPane);
			// new stage for a new window
            Stage inputWindow = new Stage();
			// the controller has to know about the model and the stage
            HelpWindowController helpWindowController = loader.getController();
            helpWindowController.setModel(game);
            helpWindowController.setParentSceneGraph(parentSceneGraph);

            // configure and show stage
            inputWindow.setScene(scene);
            inputWindow.setMinWidth(300);
            inputWindow.setMinHeight(385);
            inputWindow.setTitle("Help Window");
            inputWindow.show();

		} catch (IOException e) {
		    System.err.println("Failed to load FXML resource: " + e.getMessage());
        }
		
	}

}
