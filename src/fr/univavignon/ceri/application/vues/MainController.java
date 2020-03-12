package fr.univavignon.ceri.application.vues;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.ToggleSwitch;

import fr.univavignon.ceri.application.Main;

public class MainController implements Initializable {

	@FXML
	private VBox vboxPane;
	
    @FXML
    private Pane gameScene;

    @FXML
    private Button help;

    @FXML
    private ImageView currentPlayerIcon;

    @FXML
    private Button refresh;

    @FXML
    private ToggleSwitch rival;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("initialize ----------");
		this.initGui();
	}

    /**
	 * Initialize the graphical user interface as we want
	 */
	private void initGui() {

		if (Main.widthApp != null && Main.widthApp > 0) {
			
			gameScene.setPrefWidth(Main.widthApp);
			gameScene.setPrefHeight(Main.widthApp);	
			
			vboxPane.setPrefHeight(Main.widthApp);	
			vboxPane.setPrefHeight(Main.widthApp);	
		}
	}

	@FXML
    void switchRival(ActionEvent event) {

    }

    @FXML
    void help(ActionEvent event) {

    }

    @FXML
    void refresh(ActionEvent event) {

    }
}
