package ch.zhaw.pm2.socialWins;



import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;

public class setupWindowController {
	@FXML private ChoiceBox<Integer> playerNumberChoiceBox;
	@FXML private TextField playerOneNameTextfield;
	@FXML private RadioButton computerLevelOneRadioButton;
	@FXML private RadioButton computerLevelTwoTadioButton;
	@FXML private RadioButton computerLevelThreeRadioButton;
	@FXML private ToggleGroup aiDifficulty;
	@FXML private ChoiceBox<Integer> winningRowChoiceBox;
	@FXML private Button startButton;
	@FXML private BorderPane alternateView;
		
	private static final int DEFAULT_NUMBER_OF_PLAYERS = 1;
	private static final int MAX_NUMBER_OF_PLAYERS = 8;
	private static final int DEFAULT_WINNINGROW = 4;
	private static final int LOWEST_POSSIBLE_WINNINGROW = 3;
	private static final int HIGHEST_POSSIBLE_WINNINGROW = 6;
	
	@FXML public void initialize() {
		for(int i = 1; i <= MAX_NUMBER_OF_PLAYERS; i++) {
			playerNumberChoiceBox.getItems().add(i);
		}
		playerNumberChoiceBox.setValue(1);
		for(int i = LOWEST_POSSIBLE_WINNINGROW; i <= HIGHEST_POSSIBLE_WINNINGROW; i++) {
			winningRowChoiceBox.getItems().add(i);
		}
		playerNumberChoiceBox.setValue(DEFAULT_NUMBER_OF_PLAYERS);
		winningRowChoiceBox.setValue(DEFAULT_WINNINGROW);
		
		playerNumberChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue ov, Number value, Number new_value) {
				try {
					if(new_value.intValue() == 0) {
						FXMLLoader loader = new FXMLLoader(getClass().getResource("setupViewA.fxml"));
						loader.setController(this);
						alternateView.setCenter(loader.load());
					}
					else {
						FXMLLoader loader = new FXMLLoader(getClass().getResource("setupViewB.fxml"));
						loader.setController(this);
						alternateView.setCenter(loader.load());
					}
				}
				catch(IOException e) {
		            e.printStackTrace();
				}
			}
		});
	}


	@FXML
	private void startGame() {
		
	}
}
