package ch.zhaw.pm2.socialWins;

import java.awt.color.*;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
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

	private GameUI gameUI;
	
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
		setGameInformationText(Config.WELCOME_TEXT);
		setWinningQueueText();
		writeInPlayerPromptTextField();
		setupGameField(rowLength, columnLength);
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
			inputWindow.setTitle("Help Window");
			inputWindow.show();

		} catch (IOException e) {
			System.err.println("Failed to load FXML resource: " + e.getMessage());
		}

	}

	private void setupGameField(int numberOfRows, int numberOfColumns) {
		double gridPaneHeight = gameAreaGridPane.getPrefHeight() / numberOfColumns * numberOfColumns;
		double gridPaneWidth = gameAreaGridPane.getPrefWidth() / numberOfRows * numberOfRows;
		gameAreaGridPane.setPrefSize(gridPaneWidth, gridPaneHeight);
		gameAreaGridPane.setMaxSize(gridPaneWidth, gridPaneHeight);
		gameAreaGridPane.setMinSize(gridPaneWidth, gridPaneHeight);
		double gridElementWidth = gameAreaGridPane.getPrefHeight() / numberOfColumns;
		double gridElementHeight = gameAreaGridPane.getPrefWidth() / numberOfRows;
		for (int i = 0; i < numberOfRows; i++) {
			for (int z = 0; z < numberOfColumns; z++) {
				Button newGridElement = new Button("");
				newGridElement.setMaxSize(gridElementWidth, gridElementHeight);
				newGridElement.setMinSize(gridElementWidth, gridElementHeight);
				newGridElement.setPrefSize(gridElementWidth, gridElementHeight);
				newGridElement.setId(createIndex(z, i));
				newGridElement.setBackground(
						new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
				newGridElement.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
						CornerRadii.EMPTY, new BorderWidths(1))));
				newGridElement.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						setGameInformationText(Config.INFORMATION_TEXT);
						ArrayList<Button> buttonsInOneColumn = new ArrayList<>();
						int columnIndexOfCurrentButton = Integer.parseInt(newGridElement.getId().substring(0, 2));
						Color colorFromCurrentPlayer = getColor();
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
					}
				});
				GridPane.setRowIndex(newGridElement, i);
				GridPane.setColumnIndex(newGridElement, z);
				gameAreaGridPane.getChildren().add(newGridElement);
			}
		}
	}

	private void addColorToLastFreeButtonInColumn(ArrayList<Button> buttonsInColumn, Color playerColor) {
		boolean isFirstElementInRow = true;
		for (int z = 0; z < buttonsInColumn.size(); z++) {
			int previousButtonIndex = z - 1;
			Button currentButton = buttonsInColumn.get(z);
			if (currentButton.getBackground().getFills().get(0).getFill() != Color.WHITE) {
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

	private String createIndex(int z, int i) {
		int indicatorForMultiDigitNumber = 10;
		String columnIndex = String.valueOf(z);
		if (z < indicatorForMultiDigitNumber) {
			columnIndex = "0" + String.valueOf(z);
		}
		String rowIndex = String.valueOf(i);
		if (i < indicatorForMultiDigitNumber) {
			rowIndex = "0" + String.valueOf(i);
		}
		String index = columnIndex + rowIndex;
		return index;
	}

	private Color getColor() {
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
		gamePromptLabel.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
		gamePromptLabel.setWrapText(true);
		gamePromptLabel.setText("Spieler " + game.getNameFromCurrentPlayer() + " ist an der Reihe.");
	}

	private void setWinningQueueText() {
		winningQueueInformationLabel.setText("Erstelle eine Reihe von " + game.getWinningLineLength()
				+ " Chips aneinander, um das Spiel zu gewinnen.");
		winningQueueInformationLabel.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
		winningQueueInformationLabel.setWrapText(true);
	}

	private void setGameInformationText(String text) {
		gameInformationLabel.setText(text);
		gameInformationLabel.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
		gameInformationLabel.setWrapText(true);
	}

}
