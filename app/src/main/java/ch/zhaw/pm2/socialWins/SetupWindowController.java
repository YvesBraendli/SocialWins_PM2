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

public class SetupWindowController {
	@FXML private ChoiceBox<Integer> playerNumberChoiceBox;
	@FXML private ChoiceBox<Integer> winningRowChoiceBox;
	@FXML private Button startButton;
	@FXML private BorderPane alternateView;
	@FXML private Label errorMessageLabel;

	@FXML private TextField singlePlayerNameTextField;
	@FXML private RadioButton computerLevelOneRadioButton;
	@FXML private RadioButton computerLevelTwoTadioButton;
	@FXML private RadioButton computerLevelThreeRadioButton;
	@FXML private ToggleGroup aiDifficulty;
	@FXML private AnchorPane playerNameScrollAnchor;
	@FXML private ScrollPane playerNameScrollPane;
	@FXML private VBox playerNameVBox;
	
	ArrayList<TextField> playerNamesTextFields;
	ArrayList<ColorPicker> playerColorPickers;
		
	private static final int DEFAULT_NUMBER_OF_PLAYERS = 1;
	private static final int MAX_NUMBER_OF_PLAYERS = 8;
	private static final int DEFAULT_WINNINGROW = 4;
	private static final int LOWEST_POSSIBLE_WINNINGROW = 3;
	private static final int HIGHEST_POSSIBLE_WINNINGROW = 6;
	private static final double BOARDSIZE_MULTIPLIKATOR = 1.5;
	private static final String ALLOWED_PLAYERNAME_PATTERN = "^\\w+(-\\w+)*$";
	
	private double rowSize;
	private double columnSize; 
	
	@FXML public void initialize() {
		System.out.println("why does this get loaded so many times...pls help");
		
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
		
		playerNumberChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue ov, Number value, Number new_value) {
				if (new_value.intValue() == 0) {
					loadSetupView('A');
				} else {
					loadSetupView('B');
					generatePlayerDataFields(new_value);
				}
			}

			private void generatePlayerDataFields(Number new_value) {
				playerNamesTextFields  = new ArrayList<TextField>();
				playerColorPickers = new ArrayList<ColorPicker>();
				int scrollPaneSize = 0;
				for (int i = 0; i <= new_value.intValue(); i++) {
					playerNamesTextFields.add(new TextField());
					playerNamesTextFields.get(i).setPromptText("Player " + (i + 1) + " name");
					playerNameVBox.getChildren().add(playerNamesTextFields.get(i));
					playerColorPickers.add(new ColorPicker());
					playerColorPickers.get(i).setMinHeight(24);
					playerNameVBox.getChildren().add(playerColorPickers.get(i));
					scrollPaneSize += 61;
				}
				playerNameScrollAnchor.setPrefHeight(scrollPaneSize);
			}
		});
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
		int selectedwinningRowSize = winningRowChoiceBox.getSelectionModel().getSelectedItem();
		int selectedplayerNumber = playerNumberChoiceBox.getSelectionModel().getSelectedItem();
				
		calculateBoardSize(selectedwinningRowSize, selectedplayerNumber);
		
		if(selectedplayerNumber > 1) {
			startMultiplayerGame(selectedplayerNumber);
		}
		else {	
			startSinglePlayerGame();
		}
	}

	private void calculateBoardSize(int selectedwinningRowSize, int selectedplayerNumber) {
		rowSize = BOARDSIZE_MULTIPLIKATOR * (selectedwinningRowSize-2+selectedplayerNumber);
		if(!(rowSize%1==0)) {
			rowSize=Math.ceil(rowSize);
		}
		columnSize=rowSize+1;
	}

	private void startSinglePlayerGame() {
		String playerName = singlePlayerNameTextField.getText();
		if(!playerName.matches(ALLOWED_PLAYERNAME_PATTERN)) {
			errorMessageLabel.setText("Invalid name");
			return;
		}
		
		RadioButton selectedAiDifficulty = (RadioButton) aiDifficulty.getSelectedToggle();
		int level = Integer.parseInt(selectedAiDifficulty.getText());
		//Game game = new Game(selectedwinningRowSize, playerName, (int) columnSize, (int) rowSize);
	}

	private void startMultiplayerGame(int selectedplayerNumber) {
		HashMap<Color, String> playerData = new HashMap<Color, String>();
		for(int i = 0; i < selectedplayerNumber; i++) {
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
		//Game game = new Game(selectedwinningRowSize, playerData, (int) columnSize, (int) rowSize);
	}
}
