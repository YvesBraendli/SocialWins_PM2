package ch.zhaw.pm2.socialWins;

import javafx.scene.control.TextArea;

import java.io.IOException;

import com.sun.prism.paint.Color;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Controller for the game window.
 * 
 * @author Meier Robin, Moser Nadine, Braendli Yves
 *
 */
public class GameWindowController {
	private Game game;

	@FXML
	private Button newGameButton;
	@FXML
	private Button helpButton;
	@FXML
	private TextArea winningQueueInformationTextArea;
	@FXML
	private TextArea gameInformationTextArea;
	@FXML
	private TextArea gamePromptTextArea;
	@FXML
	private GridPane gameAreaGridPane;

	/**
	 * Is called from GameUI to connect the Controller to the game model. And to set
	 * the game view with the basic inserted parameters by the user.
	 * 
	 * @param model The current model of the game class.
	 */
	public void setUpGameView(Game model) {
		game = model;
		setGameInformationText();
		setWinningQueueText();
//		createListenerForPlayerPrompt();
//		createListenerForGameField();
		setupGameField(15, 15); // Add game.getNumberOfRows and game.getNumberOfLines as Parameters
	}

//	private void createListenerForGameField() {
//	game.gameFieldBoundProperty().addListener(new ChangeListener<Color>() {
//
//		@Override
//		public void changed(ObservableValue<? extends Color> observable, Color oldValue, Color newValue) {
//			// TODO Auto-generated method stub
//			
//		}
//	});
//}
	
//	private void createListenerForPlayerPrompt() {
//		game.playerPromptBoundProperty().addListener(new ChangeListener<String>() {
//			@Override
//			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//				writeInPlayerPromptTextField(newValue);
//			}
//		});
//	}

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
			AnchorPane rootPane = loader.load();
			// fill in scene and stage setup
			Scene scene = new Scene(rootPane);
			// new stage for a new window
			Stage inputWindow = new Stage();
			// the controller has to know about the model and the stage
			HelpWindowController helpWindowController = loader.getController();
			helpWindowController.setModel(game);

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
	
	private void setupGameField(int numberOfRows, int numberOfColumns) {
		int gridPaneHeight = (int) gameAreaGridPane.getPrefHeight()/numberOfColumns*numberOfColumns;
		int gridPaneWidth = (int) gameAreaGridPane.getPrefWidth()/numberOfColumns*numberOfColumns;
		gameAreaGridPane.setPrefSize(gridPaneWidth, gridPaneHeight);
		double gridElementHeight = gridPaneHeight/numberOfColumns;
		double gridElementWidth = gridPaneWidth/numberOfRows;
		for (int i = 0; i<numberOfRows; i++) {
			for(int z = 0; z<numberOfColumns; z++) {
				Button newGridElement = new Button("i");
				newGridElement.setMaxSize(gridElementWidth, gridElementHeight);
				newGridElement.setMinSize(gridElementWidth, gridElementHeight);
				newGridElement.setId(String.valueOf(i)+String.valueOf(z));
				newGridElement.setOnAction(new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent event) {
		            	//game.buttonIsPressed(newGridElement.getId())
		            }
		        });
				GridPane.setRowIndex(newGridElement, i);
				GridPane.setColumnIndex(newGridElement, z);
				gameAreaGridPane.getChildren().add(newGridElement);
			}
		}
	}

	private void writeInPlayerPromptTextField(String text) {
		gamePromptTextArea.setText(text);
	}
	

	private void setWinningQueueText() {
		winningQueueInformationTextArea.setText("bla"); // Add game.setWinningQueueText as parameter
	}

	private void setGameInformationText() {
		gameInformationTextArea.setText("bla1"); // Add game.setGameInformationText as parameter
	}

}
