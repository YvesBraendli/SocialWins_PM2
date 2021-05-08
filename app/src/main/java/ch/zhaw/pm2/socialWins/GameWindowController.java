package ch.zhaw.pm2.socialWins;

import java.io.IOException;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
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

	private GameUI gameUI;

	private int numberOfRows;

	private int numberOfColumns;

	/**
	 * This method sets the private datafield for a GameUI-Object.
	 * 
	 * @param gameUI The current GameUI-Object, which should be set as datafield.
	 */
	public void setGameUI(GameUI gameUI) {
		this.gameUI = gameUI;
	}

	/**
	 * Is called from GameUI to connect the Controller to the game model. And to set
	 * the game view with the basic inserted parameters by the user.
	 * 
	 * @param model The current model of the game class.
	 */
	public void setUpGameView(Game model, int rowLength, int columnLength) {
		game = model;
		numberOfColumns = columnLength;
		numberOfRows = rowLength;
		setGameInformationText(Config.WELCOME_TEXT);
		setWinningQueueText();
		writeInPlayerPromptTextField();
		setupGameField();
		//createListenerForComputerMoves();
	}

	@FXML
	private void startNewGame() throws InterruptedException {
		Stage newStage = new Stage();
		gameUI.showSetupWindow(newStage);
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
			inputWindow.setTitle(Config.HELP_WINDOW_TITEL);
			inputWindow.show();

		} catch (IOException e) {
			System.err.println("Failed to load FXML resource: " + e.getMessage());
		}

	}

	private void setupGameField() {
		double gridPaneHeight = gameAreaGridPane.getPrefHeight() / numberOfColumns * numberOfColumns;
		double gridPaneWidth = gameAreaGridPane.getPrefWidth() / numberOfRows * numberOfRows;
		gameAreaGridPane.setPrefSize(gridPaneWidth, gridPaneHeight);
		gameAreaGridPane.setMaxSize(gridPaneWidth, gridPaneHeight);
		gameAreaGridPane.setMinSize(gridPaneWidth, gridPaneHeight);
		double gridElementWidth = gameAreaGridPane.getPrefHeight() / numberOfColumns;
		double gridElementHeight = gameAreaGridPane.getPrefWidth() / numberOfRows;
		for (int i = 0; i < numberOfRows; i++) {
			for (int z = 0; z < numberOfColumns; z++) {
				Button newGridElement = createAndManipulateButton(gridElementHeight, gridElementWidth, z, i);
				GridPane.setRowIndex(newGridElement, i);
				GridPane.setColumnIndex(newGridElement, z);
				gameAreaGridPane.getChildren().add(newGridElement);
			}
		}
	}

	private Button createAndManipulateButton(double buttonHeight, double buttonWidth, int columnIndex, int rowIndex) {
		Button newGridElement = new Button("");
		newGridElement.setMaxSize(buttonWidth, buttonHeight);
		newGridElement.setMinSize(buttonWidth, buttonHeight);
		newGridElement.setPrefSize(buttonWidth, buttonHeight);
		newGridElement.setId(createButtonIndex(columnIndex, rowIndex));
		newGridElement.setBackground(new Background(
				new BackgroundFill(Config.DEFAULT_BACKGROUND_COLOR_OF_GAMEFIELD, CornerRadii.EMPTY, Insets.EMPTY)));
		newGridElement.setBorder(new Border(Config.DEFAULT_BORDERSTROKE));
		newGridElement.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				handleActionForButton(newGridElement);
			}
		});
		return newGridElement;
	}

	private void handleActionForButton(Button buttonToAdd) {
		setGameInformationText(Config.INFORMATION_TEXT);
		ArrayList<Button> buttonsInOneColumn = new ArrayList<>();
		int columnIndexOfCurrentButton = Integer.parseInt(buttonToAdd.getId().substring(0, 2));
		Color colorFromCurrentPlayer = getColorAsPaint();
		if (game.nextMove(columnIndexOfCurrentButton)) { // game.nextMove(columnIndexOfCurrentButton)
			for (Node node : gameAreaGridPane.getChildren()) {
				if (node instanceof Button) {
					Button button = (Button) node;
					int columnIndexOfCheckedButton = Integer.parseInt(button.getId().substring(0, 2));
					if (columnIndexOfCheckedButton == columnIndexOfCurrentButton) {
						buttonsInOneColumn.add(button);
					}
				}
			}
			addColorToLastFreeButtonInColumn(buttonsInOneColumn, colorFromCurrentPlayer);
			writeInPlayerPromptTextField();
		} else {
			setGameInformationText(Config.WRONG_QUEUE_TEXT);
		}
		if (game.getWinner() != null) {
			changeToWinningView();
		}
	}

	private void changeToWinningView() {
		ArrayList<Button> buttonsOnGameField = new ArrayList<>();
		for (Node node : gameAreaGridPane.getChildren()) {
			if (node instanceof Button) {
				Button button = (Button) node;
				buttonsOnGameField.add(button);
			}
		}
		gameUI.switchToWinningView(buttonsOnGameField, numberOfRows, numberOfColumns, game);
		Stage stage = (Stage) newGameButton.getScene().getWindow();
		stage.close();
	}

	private void addColorToLastFreeButtonInColumn(ArrayList<Button> buttonsInColumn, Color playerColor) {
		boolean isFirstElementInRow = true;
		for (int z = 0; z < buttonsInColumn.size(); z++) {
			int previousButtonIndex = z - 1;
			Button currentButton = buttonsInColumn.get(z);
			if (currentButton.getBackground().getFills().get(0)
					.getFill() != Config.DEFAULT_BACKGROUND_COLOR_OF_GAMEFIELD) {
				buttonsInColumn.get(previousButtonIndex).setBackground(
						new Background(new BackgroundFill(playerColor, CornerRadii.EMPTY, Insets.EMPTY)));
				isFirstElementInRow = false;
			}
		}
		if (isFirstElementInRow) {
			int lastElementIndex = buttonsInColumn.size() - 1;
			buttonsInColumn.get(lastElementIndex)
					.setBackground(new Background(new BackgroundFill(playerColor, CornerRadii.EMPTY, Insets.EMPTY)));
		}
	}

	private String createButtonIndex(int column, int row) {
		String columnIndex = String.valueOf(column);
		if (column < Config.INDICATOR_FOR_MULTIPLE_DIGIT_NUMBER) {
			columnIndex = "0" + String.valueOf(column);
		}
		String rowIndex = String.valueOf(row);
		if (row < Config.INDICATOR_FOR_MULTIPLE_DIGIT_NUMBER) {
			rowIndex = "0" + String.valueOf(row);
		}
		String index = columnIndex + rowIndex;
		return index;
	}

	private Color getColorAsPaint() {
		java.awt.Color colorFromCurrentPlayerAsColor = game.getColorFromCurrentPlayer();
		int r = colorFromCurrentPlayerAsColor.getRed();
		int g = colorFromCurrentPlayerAsColor.getGreen();
		int b = colorFromCurrentPlayerAsColor.getBlue();
		int a = colorFromCurrentPlayerAsColor.getAlpha();
		double opacity = a / 255.0;
		Color colorFromCurrentPlayerAsPaint = javafx.scene.paint.Color.rgb(r, g, b, opacity);
		return colorFromCurrentPlayerAsPaint;
	}

	private void writeInPlayerPromptTextField() {
		gamePromptLabel.setBorder(new Border(Config.DEFAULT_BORDERSTROKE));
		gamePromptLabel.setWrapText(true);
		gamePromptLabel.setText(Config.createPlayerPromptText(game.getNameFromCurrentPlayer()));
	}

	private void setWinningQueueText() {
		winningQueueInformationLabel.setText(Config.createWinningQueueText(game.getWinningLineLength()));
		winningQueueInformationLabel.setBorder(new Border(Config.DEFAULT_BORDERSTROKE));
		winningQueueInformationLabel.setWrapText(true);
	}

	private void setGameInformationText(String text) {
		gameInformationLabel.setText(text);
		gameInformationLabel.setBorder(new Border(Config.DEFAULT_BORDERSTROKE));
		gameInformationLabel.setWrapText(true);
	}

	private void colorButtonForComputerMove(int column, int row) {
		Color colorFromCurrentPlayer = getColorAsPaint();
		String index = createButtonIndex(column, row);
		for (Node node : gameAreaGridPane.getChildren()) {
			if (node instanceof Button) {
				Button button = (Button) node;
				if (index.equals(button.getId())) {
					button.setBackground(new Background(
							new BackgroundFill(colorFromCurrentPlayer, CornerRadii.EMPTY, Insets.EMPTY)));
					;
				}
			}
		}
		writeInPlayerPromptTextField();
	}

//	private void createListenerForComputerMoves() {
//		game.NextComputerMoveBoundProperty().addListener( (obs, old, newValue) -> {
//				colorButtonForComputerMove(newValue.intValue(), 0);	        
//	    });
//
//	}

}
