package ch.zhaw.pm2.socialWins;

import javafx.scene.control.TextArea;

import java.io.IOException;



import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
	private Label winningQueueInformationLabel;
	@FXML
	private Label gameInformationLabel;
	@FXML
	private Label gamePromptLabel;
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
		writeInPlayerPromptTextField();
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
				Button newGridElement = new Button("");
				newGridElement.setMaxSize(gridElementWidth, gridElementHeight);
				newGridElement.setMinSize(gridElementWidth, gridElementHeight);
				newGridElement.setId(String.valueOf(i)+String.valueOf(z));
				newGridElement.setOnAction(new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent event) {
		            	int row = Integer.parseInt(newGridElement.getId().substring(0, 1));
		            	int column = Integer.parseInt(newGridElement.getId().substring(1));
//		            	Color player = game.getColorFromCurrentPlayer;
//		            	if(game.nextMove(row, column)){
//		            	writeInPlayerPromptTextField();
//		            }
		            }
		        });
				GridPane.setRowIndex(newGridElement, i);
				GridPane.setColumnIndex(newGridElement, z);
				gameAreaGridPane.getChildren().add(newGridElement);
			}
		}
	}

	private void writeInPlayerPromptTextField() {
		gamePromptLabel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
		gamePromptLabel.setWrapText(true);
		gamePromptLabel.setText("Spieler Lollipoppolizei ist an der Reihe.");
	}
	

	private void setWinningQueueText() {
		winningQueueInformationLabel.setText("Erstelle eine Reihe von 5 Chips aneinander, um das Spiel zu gewinnen."); 
		winningQueueInformationLabel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
		winningQueueInformationLabel.setWrapText(true);
	}

	private void setGameInformationText() {
		gameInformationLabel.setText("Willkommen beim SocialWins, viel Spass beim Spiel. Um Hilfe zu erhalten, den Button links oben clicken."); 
		gameInformationLabel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
		gameInformationLabel.setWrapText(true);
	}

}
