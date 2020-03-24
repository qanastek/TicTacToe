package fr.univavignon.ceri.application.vues;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import fr.univavignon.ceri.application.models.Game;

public class EndGameController implements Initializable {
		
	@FXML
	private AnchorPane root;
	
	@FXML
	private Button restart;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// Initialize the GUI
		this.initGui();
	}

    /**
	 * Initialize the GUI
	 */
	private void initGui() {
		System.out.println("init gui");
	}
    
    @FXML
    void restart(ActionEvent event) {
    	
    	System.out.println("EndGameController RESTART");
    	
    	// Clear the game
    	Game.getInstance().clear();
    	
    	// Quit the modal
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    	
    }
	
}
