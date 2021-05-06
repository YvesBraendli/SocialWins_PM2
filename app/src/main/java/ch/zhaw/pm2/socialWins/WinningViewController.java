package ch.zhaw.pm2.socialWins;


import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class WinningViewController {
	private Game game;
	private static final int GRID_PANE_HEIGHT = 380;
	private static final int GRID_PANE_WIDTH = 380;
	@FXML Button newGameButton;
	@FXML Button closeButton;
	@FXML GridPane showGameFieldGridPane;
	@FXML Label winningTextLabel;
	
	/**
	 * Sets up the winning view with the current parameters.
	 * @param model The game class with the current game-status.
	 */
	public void setUpWinningView(Game model, int numberOfRows, int numberOfColumns) {
		game = model;
		showGameField(numberOfColumns,numberOfRows);
		showWinningText();
	}
	
	@FXML
	private void startNewGame() {
		Stage stage = (Stage) newGameButton.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	private void quitGame() {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}
	
	
//	double gridPaneHeight = gameAreaGridPane.getPrefHeight() / numberOfColumns * numberOfColumns;
//	double gridPaneWidth = gameAreaGridPane.getPrefWidth() / numberOfRows * numberOfRows;
//	gameAreaGridPane.setPrefSize(gridPaneWidth, gridPaneHeight);
//	gameAreaGridPane.setMaxSize(gridPaneWidth, gridPaneHeight);
//	gameAreaGridPane.setMinSize(gridPaneWidth, gridPaneHeight);
//	double gridElementWidth = gameAreaGridPane.getPrefHeight() / numberOfColumns;
//	double gridElementHeight = gameAreaGridPane.getPrefWidth() / numberOfRows;
	
	private void showGameField(int numberOfColumns, int numberOfRows) {
		double gridPaneHeight = showGameFieldGridPane.getPrefHeight()/numberOfColumns*numberOfColumns;
		double gridPaneWidth = showGameFieldGridPane.getPrefWidth()/numberOfRows*numberOfRows;
		showGameFieldGridPane.setPrefSize(gridPaneWidth, gridPaneHeight);
		showGameFieldGridPane.setMaxSize(gridPaneWidth, gridPaneHeight);
		showGameFieldGridPane.setMinSize(gridPaneWidth, gridPaneHeight);
		double gridElementWidth = showGameFieldGridPane.getPrefHeight()/numberOfColumns;
		double gridElementHeight = showGameFieldGridPane.getPrefWidth()/numberOfRows;
		for (int i = 0; i<numberOfRows; i++) {
			for(int z = 0; z<numberOfColumns; z++) {
				Label newGridElement = new Label("");
				newGridElement.setMaxSize(gridElementWidth, gridElementHeight);
				newGridElement.setMinSize(gridElementWidth, gridElementHeight);
				newGridElement.setBackground(new Background(new BackgroundFill(Color.DEEPPINK, new CornerRadii(0.0), new javafx.geometry.Insets(0.0)))); // Get Color from game
				newGridElement.setBorder(new Border(
	                    new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
				GridPane.setRowIndex(newGridElement, i);
				GridPane.setColumnIndex(newGridElement, z);
				showGameFieldGridPane.getChildren().add(newGridElement);
			}
		}
	}
	
	private void showWinningText() {
		winningTextLabel.setWrapText(true);
		winningTextLabel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
		winningTextLabel.setText("Spieler KarottenNase hat das Spiel gewonnen. Herzliche Gratulation!");
	}

}
