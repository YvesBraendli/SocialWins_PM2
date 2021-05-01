package ch.zhaw.pm2.socialWins;



import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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

	@FXML private TextField singlePlayerNameTextField;
	@FXML private RadioButton computerLevelOneRadioButton;
	@FXML private RadioButton computerLevelTwoTadioButton;
	@FXML private RadioButton computerLevelThreeRadioButton;
	@FXML private ToggleGroup aiDifficulty;
	@FXML private AnchorPane playerNameScrollAnchor;
	@FXML private ScrollPane playerNameScrollPane;
	@FXML private VBox playerNameVBox;
	private SetupWindowController setupWindowController = this;
	
	ArrayList<TextField> playerNames;
		
	private static final int DEFAULT_NUMBER_OF_PLAYERS = 1;
	private static final int MAX_NUMBER_OF_PLAYERS = 8;
	private static final int DEFAULT_WINNINGROW = 4;
	private static final int LOWEST_POSSIBLE_WINNINGROW = 3;
	private static final int HIGHEST_POSSIBLE_WINNINGROW = 6;
	
	@FXML public void initialize() {
		System.out.println("why does this get loaded so many times...pls help");
		
		if(playerNumberChoiceBox.getItems().isEmpty()) {
			for(int i = 1; i <= MAX_NUMBER_OF_PLAYERS; i++) {
				playerNumberChoiceBox.getItems().add(i);
			}
			playerNumberChoiceBox.setValue(1);
			
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
					playerNames  = new ArrayList<TextField>();
					int scrollPaneSize = 0;
					for (int i = 0; i <= new_value.intValue(); i++) {
						playerNames.add(new TextField());
						playerNames.get(i).setPromptText("Player " + (i + 1) + " name");
						scrollPaneSize += 31;
					}
					playerNameVBox.getChildren().addAll(playerNames);
					playerNameScrollAnchor.setPrefHeight(scrollPaneSize);
				}
			}
		});
	}

	private void loadSetupView(char viewLetter) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("SetupView"+viewLetter+".fxml"));
			loader.setController(setupWindowController);
			alternateView.setCenter(loader.load());
		}
		catch(IOException e) {
            e.printStackTrace();
		}
	}


	@FXML
	private void startGame() {
		
	}
}
