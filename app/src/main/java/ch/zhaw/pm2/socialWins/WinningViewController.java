package ch.zhaw.pm2.socialWins;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class WinningViewController {
	private Game game;
	private GameUI gameUI;
	@FXML
	Button newGameButton;
	@FXML
	Button closeButton;
	@FXML
	GridPane showGameFieldGridPane;
	@FXML
	Label winningTextLabel;

	/**
	 * Sets up the winning view with the current parameters.
	 * 
	 * @param model The game class with the current game-status.
	 */
	public void setUpWinningView(Game model, int numberOfRows, int numberOfColumns, ArrayList<Button> buttons,
			GameUI gameUI) {
		game = model;
		this.gameUI = gameUI;
		showGameField(numberOfColumns, numberOfRows, buttons);
		showWinningText();
	}

	@FXML
	private void startNewGame() {
		Stage newStage = new Stage();
		gameUI.showSetupWindow(newStage);
		Stage stage = (Stage) newGameButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void quitGame() {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}

	private void showGameField(int numberOfColumns, int numberOfRows, ArrayList<Button> buttons) {
		double gridPaneHeight = showGameFieldGridPane.getPrefHeight() / numberOfColumns * numberOfColumns;
		double gridPaneWidth = showGameFieldGridPane.getPrefWidth() / numberOfRows * numberOfRows;
		showGameFieldGridPane.setPrefSize(gridPaneWidth, gridPaneHeight);
		showGameFieldGridPane.setMaxSize(gridPaneWidth, gridPaneHeight);
		showGameFieldGridPane.setMinSize(gridPaneWidth, gridPaneHeight);
		double gridElementWidth = showGameFieldGridPane.getPrefHeight() / numberOfColumns;
		double gridElementHeight = showGameFieldGridPane.getPrefWidth() / numberOfRows;
		int counterForButtonArrayList = 0;
		for (int i = 0; i < numberOfRows; i++) {
			for (int z = 0; z < numberOfColumns; z++) {
				Paint colorForCurrentLabel = buttons.get(counterForButtonArrayList).getBackground().getFills().get(0)
						.getFill();
				Label newGridElement = new Label("");
				newGridElement.setMaxSize(gridElementWidth, gridElementHeight);
				newGridElement.setMinSize(gridElementWidth, gridElementHeight);
				newGridElement.setBackground(new Background(new BackgroundFill(colorForCurrentLabel,
						new CornerRadii(0.0), new javafx.geometry.Insets(0.0))));
				newGridElement.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
						CornerRadii.EMPTY, new BorderWidths(1))));
				GridPane.setRowIndex(newGridElement, i);
				GridPane.setColumnIndex(newGridElement, z);
				showGameFieldGridPane.getChildren().add(newGridElement);
				counterForButtonArrayList++;
			}
		}
	}

	private void showWinningText() {
		winningTextLabel.setWrapText(true);
		winningTextLabel.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
		winningTextLabel.setText(Config.WINNING_TEXT_FRONT + game.getWinner().getName() + Config.WINNING_TEXT_BACK);
	}

}
