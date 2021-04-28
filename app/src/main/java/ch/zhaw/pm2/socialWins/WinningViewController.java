package ch.zhaw.pm2.socialWins;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class WinningViewController {
	private Game game;
	private Parent parentSceneGraph;
	@FXML Button newGameButton;
	@FXML Button closeButton;
	@FXML TextArea showGameFieldTextArea;
	@FXML TextArea winningTextTextArea;
	
	public void setUpWinningView(Game model) {
		game = model;
		showGameField();
		showWinningText();
	}
	
	public void setParentSceneGraph(Parent parentSceneGraph) {
		this.parentSceneGraph = parentSceneGraph;
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
	
	private void showGameField() {
		showGameFieldTextArea.setText("bla");
	}
	
	private void showWinningText() {
		winningTextTextArea.setText("bla2");
	}

}
