package ch.zhaw.pm2.socialWins;


import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;


public class HelpWindowController {
	
	private Game game; 
	private Parent parentSceneGraph;
	
	@FXML TextArea showHelpTextArea;

	/**
	 * Is called from GameUI to connect the Controller to the game model.
	 * @param model The current model of the game class.
	 */
	public void setModel(Game model) {
		game = model;
	}
	
	public void setParentSceneGraph(Parent parentSceneGraph) {
		this.parentSceneGraph = parentSceneGraph;
	}

}
