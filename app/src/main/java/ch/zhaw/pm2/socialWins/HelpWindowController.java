package ch.zhaw.pm2.socialWins;


import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;


public class HelpWindowController {
	
	private Game game; 
	
	@FXML TextArea showHelpTextArea;

	/**
	 * Is called from GameUI to connect the Controller to the game model.
	 * @param model The current model of the game class.
	 */
	public void setModel(Game model) {
		game = model;
		showHelp();
	}
	
	private void showHelp() {
		showHelpTextArea.setText("Endlich funktioniert dieser Kack!"); // Add import of help text from game class.
	}

}
