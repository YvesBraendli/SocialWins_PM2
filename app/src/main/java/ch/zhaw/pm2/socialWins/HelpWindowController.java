package ch.zhaw.pm2.socialWins;


import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;


public class HelpWindowController {
	
	private Game game; 
	
	@FXML Label showHelpLabel;

	/**
	 * Is called from GameUI to connect the Controller to the game model.
	 * @param model The current model of the game class.
	 */
	public void setModel(Game model) {
		game = model;
		showHelp();
	}
	
	private void showHelp() {
		showHelpLabel.setWrapText(true);
		showHelpLabel.setBorder(new Border(new BorderStroke(Color.LIGHTGREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
		showHelpLabel.setText(Config.HELP_TEXT); // Add import of help text from game class.
	}

}
