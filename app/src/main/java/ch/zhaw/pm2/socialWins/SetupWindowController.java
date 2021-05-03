package ch.zhaw.pm2.socialWins;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * This class is the controller for the setup view.
 * 
 * @author Moser Nadine, Meier Robin and Braendli Yves.
 *
 */
public class SetupWindowController {
	@FXML private ChoiceBox<Integer> playerNumberChoiceBox;
	@FXML private ChoiceBox<Integer> winningRowChoiceBox;
	@FXML private Button startButton;
	@FXML private BorderPane alternateView;
	@FXML private Label errorMessageLabel;

	@FXML private TextField singlePlayerNameTextField;
	@FXML private ToggleGroup aiDifficulty;
	@FXML private AnchorPane playerNameScrollAnchor;
	@FXML private ScrollPane playerNameScrollPane;
	@FXML private VBox playerNameVBox;
	
	ArrayList<TextField> playerNamesTextFields;
	ArrayList<ColorPicker> playerColorPickers;
	
	// TODO export to config
	private static final int DEFAULT_NUMBER_OF_PLAYERS = 1; 
	private static final int MAX_NUMBER_OF_PLAYERS = 8;
	private static final int DEFAULT_WINNINGROW = 4;
	private static final int LOWEST_POSSIBLE_WINNINGROW = 3;
	private static final int HIGHEST_POSSIBLE_WINNINGROW = 6;
	private static final double BOARDSIZE_MULTIPLIKATOR = 1.5;
	private static final String ALLOWED_PLAYERNAME_PATTERN = "^\\w+(-\\w+)*$";
	private static final int PLAYER_INFORMATIONFIELD_HEIGHT = 61;
	
	private double rowSize;
	private double columnSize; 
	private Game game;
	private GameUI gameUI;
	
	public void setGameUI(GameUI gameUI) {
		this.gameUI = gameUI;
	}
	
	/**
	 * Fills the player number choicebox and winning row choicebox 
	 * and loads the initial singleplayer setup view (A).
	 * 
	 * Also implements a listener to switch between 
	 * multiplayer and singleplayer options when a number higher than one is selected in the player number choice box
	 */
	@FXML public void initialize() {
		
		if(playerNumberChoiceBox.getItems().isEmpty()) {
			for(int i = 1; i <= MAX_NUMBER_OF_PLAYERS; i++) {
				playerNumberChoiceBox.getItems().add(i);
			}
			playerNumberChoiceBox.setValue(DEFAULT_NUMBER_OF_PLAYERS);
			
			for(int i = LOWEST_POSSIBLE_WINNINGROW; i <= HIGHEST_POSSIBLE_WINNINGROW; i++) {
				winningRowChoiceBox.getItems().add(i);
			}
			playerNumberChoiceBox.setValue(DEFAULT_NUMBER_OF_PLAYERS);
			winningRowChoiceBox.setValue(DEFAULT_WINNINGROW);

			loadSetupView('A');
		}
		
		playerNumberChoiceBox.setOnAction((event)-> {
				int selectedValue = playerNumberChoiceBox.getSelectionModel().getSelectedItem(); 
				if (selectedValue == 1) {
					loadSetupView('A');
				} else {
					loadSetupView('B');
					generatePlayerDataFields(selectedValue);
				}
		});
	}
	
	private void generatePlayerDataFields(Number new_value) {
		playerNamesTextFields  = new ArrayList<TextField>();
		playerColorPickers = new ArrayList<ColorPicker>();
		int scrollPaneSize = 0;
		for (int i = 0; i < new_value.intValue(); i++) {
			playerNamesTextFields.add(new TextField());
			playerNamesTextFields.get(i).setPromptText("Player " + (i + 1) + " name");
			playerNameVBox.getChildren().add(playerNamesTextFields.get(i));
			playerColorPickers.add(new ColorPicker());
			playerColorPickers.get(i).setMinHeight(24);
			playerNameVBox.getChildren().add(playerColorPickers.get(i));
			scrollPaneSize += PLAYER_INFORMATIONFIELD_HEIGHT;
		}
		playerNameScrollAnchor.setPrefHeight(scrollPaneSize);
	}

	private void loadSetupView(char viewLetter) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("SetupView"+viewLetter+".fxml"));
			loader.setController(this);
			alternateView.setCenter(loader.load());
		}
		catch(IOException e) {
            e.printStackTrace();
		}
	}

	@FXML
	private void startGame() {
		int selectedWinningRowSize = winningRowChoiceBox.getSelectionModel().getSelectedItem();
		int selectedPlayerNumber = playerNumberChoiceBox.getSelectionModel().getSelectedItem();
				
		calculateBoardSize(selectedWinningRowSize, selectedPlayerNumber);
		
		if(selectedPlayerNumber > 1) {
			startMultiplayerGame(selectedPlayerNumber, selectedWinningRowSize);
		}
		else {	
			startSinglePlayerGame(selectedWinningRowSize);
		}
		gameUI.switchToGameWindow(game);
		Stage stage = (Stage) startButton.getScene().getWindow();
		stage.close();
	}

	/**
	 * Calculates the board size depending on number of players and winning row size.
	 * 
	 * The Winningrow size - 2 (default player number) + the selected player number is multiplied by a fixed value. (1.5 by default)
	 * If the resulting number is not an int it gets rounded up to the next higher number. This is the row size.
	 * The columnSize is the rowSize+1
	 * 
	 * If Singleplayer is selected, then selectedplayerNumber gets set to 2
	 * @param selectedwinningRowSize number between 3-6
	 * @param selectedplayerNumber number between 1-8
	 */
	private void calculateBoardSize(int selectedwinningRowSize, int selectedplayerNumber) {
		if(selectedplayerNumber == 1) {
			selectedplayerNumber = 2;
		}
		rowSize = BOARDSIZE_MULTIPLIKATOR * (selectedwinningRowSize-2+selectedplayerNumber);
		if(!(rowSize%1==0)) {
			rowSize=Math.ceil(rowSize);
		}
		columnSize=rowSize+1;
	}

	private void startSinglePlayerGame(int selectedWinningRowSize) {
		String playerName = singlePlayerNameTextField.getText();
		if(!playerName.matches(ALLOWED_PLAYERNAME_PATTERN)) {
			errorMessageLabel.setText("Invalid name");
			return;
		}
		
		RadioButton selectedAiDifficulty = (RadioButton) aiDifficulty.getSelectedToggle();
		int level = Integer.parseInt(selectedAiDifficulty.getText());
		game = new Game(selectedWinningRowSize, playerName, level, (int) columnSize, (int) rowSize);
	}

	private void startMultiplayerGame(int selectedPlayerNumber, int selectedWinningRowSize) {
		HashMap<Color, String> playerData = new HashMap<Color, String>();
		for(int i = 0; i < selectedPlayerNumber; i++) {
			Color playerColor = new Color((float) playerColorPickers.get(i).getValue().getRed(),
		            (float) playerColorPickers.get(i).getValue().getGreen(),
		            (float) playerColorPickers.get(i).getValue().getBlue(),
		            (float) playerColorPickers.get(i).getValue().getOpacity());
			String playerName = playerNamesTextFields.get(i).getText();
			
			if(!playerName.matches(ALLOWED_PLAYERNAME_PATTERN)) {
				errorMessageLabel.setText("Invalid names");
				return;
			}
			for(Color color: playerData.keySet()) {
				if(color.equals(playerColor)) {
					errorMessageLabel.setText("Invalid colors");
					return;
				}
				if(playerData.get(color).equals(playerName)) {
					errorMessageLabel.setText("Invalid names");
					return;
				}
			}
			playerData.put(playerColor, playerName);
		}
		game = new Game(selectedWinningRowSize, playerData, (int) columnSize, (int) rowSize);
	}
}
