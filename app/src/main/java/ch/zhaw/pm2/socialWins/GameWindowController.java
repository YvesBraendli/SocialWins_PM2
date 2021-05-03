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

	/**
	 * Is called from GameUI to connect the Controller to the game model. And to set
	 * the game view with the basic inserted parameters by the user.
	 * 
	 * @param model The current model of the game class.
	 */
	public void setUpGameView(Game model, int rowLength, int columnLength) {
		game = model;
		setGameInformationText();
		setWinningQueueText();
		writeInPlayerPromptTextField();
		setupGameField(rowLength, columnLength);
	}

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
		int gridPaneHeight = (int) gameAreaGridPane.getPrefHeight() / numberOfColumns * numberOfColumns;
		int gridPaneWidth = (int) gameAreaGridPane.getPrefWidth() / numberOfRows * numberOfRows;
		gameAreaGridPane.setPrefSize(gridPaneWidth, gridPaneHeight);
		double gridElementWidth = gridPaneHeight / numberOfColumns;
		double gridElementHeight = gridPaneWidth / numberOfRows;
		for (int i = 0; i < numberOfRows; i++) {
			for (int z = 0; z < numberOfColumns; z++) {
				Button newGridElement = new Button("");
				newGridElement.setMaxSize(gridElementWidth, gridElementHeight);
				newGridElement.setMinSize(gridElementWidth, gridElementHeight);
				newGridElement.setId(String.valueOf(z) + String.valueOf(i));
				newGridElement.setBackground(
						new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
				newGridElement.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
						CornerRadii.EMPTY, new BorderWidths(1))));
				newGridElement.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						ArrayList<Button> buttonsInOneColumn = new ArrayList<>();
						int columnIndexOfCurrentButton = Integer.parseInt(newGridElement.getId().substring(0, 1));
						Color colorFromCurrentPlayer = getColor();
						if (true) { // add game.nextMove(columnIndexOfCurrentButton)
							for (Node node : gameAreaGridPane.getChildren()) {
								if (node instanceof Button) {
									Button button = (Button) node;
									int columnIndexOfCheckedButton = Integer.parseInt(button.getId().substring(0, 1));
									if (columnIndexOfCheckedButton == columnIndexOfCurrentButton) {
										buttonsInOneColumn.add(button);
									}
								}
							}
							boolean isFirstElementInRow = true;
							for (int z = 0; z < buttonsInOneColumn.size(); z++) {
								int previousButtonIndex = z - 1;
								Button currentButton = buttonsInOneColumn.get(z);
								if ((Color) currentButton.getBackground().getFills().get(0).getFill() != Color.WHITE) {
									buttonsInOneColumn.get(previousButtonIndex).setBackground(new Background(
											new BackgroundFill(colorFromCurrentPlayer, CornerRadii.EMPTY, Insets.EMPTY)));
									isFirstElementInRow = false;
								}
							}
							if (isFirstElementInRow) {
								int lastElementIndex = buttonsInOneColumn.size() - 1;
								buttonsInOneColumn.get(lastElementIndex).setBackground(new Background(
										new BackgroundFill(colorFromCurrentPlayer, CornerRadii.EMPTY, Insets.EMPTY)));
							}
							writeInPlayerPromptTextField();
						}
					}
				});
				GridPane.setRowIndex(newGridElement, i);
				GridPane.setColumnIndex(newGridElement, z);
				gameAreaGridPane.getChildren().add(newGridElement);
			}
		}
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
		winningQueueInformationLabel.setText("Erstelle eine Reihe von 5 Chips aneinander, um das Spiel zu gewinnen.");
		winningQueueInformationLabel.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
		winningQueueInformationLabel.setWrapText(true);
	}

	private void setGameInformationText() {
		gameInformationLabel.setText(
				"Willkommen beim SocialWins, viel Spass beim Spiel. Um Hilfe zu erhalten, den Button links oben clicken.");
		gameInformationLabel.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
		gameInformationLabel.setWrapText(true);
	}

}
