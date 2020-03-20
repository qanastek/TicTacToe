package fr.univavignon.ceri.application.vues;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.ToggleSwitch;

import fr.univavignon.ceri.application.Main;
import fr.univavignon.ceri.application.models.Game;

public class MainController implements Initializable {

	@FXML
	private VBox vboxPane;
	
    @FXML
    private Pane gameScene;

    @FXML
    private ImageView currentPlayerIcon;

    @FXML
    private Button restart;

    @FXML
    private Button rival;
    
    @FXML
    private VBox player1Box;
    
    @FXML
    private VBox player2Box;
    
    @FXML
    private Label player1Turn;
    
    @FXML
    private Label player2Turn;
    
    /**
     * The {@code Game} instance
     */
    public static Game GAME = Game.getInstance();
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.initGui();
	}

    /**
	 * Initialize the graphical user interface as we want
	 */
	private void initGui() {

		System.out.println("init gui");
		
		/**
		 * When the player change
		 */
		Game.CURRENT_PLAYER.addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				
				System.out.println("run");

				/**
				 * Labels
				 */
				getLabelPlayer(oldValue).setVisible(false);
				getLabelPlayer(newValue).setVisible(true);
				
				/**
				 * Vbox
				 */
				getVboxPlayer(oldValue).getStyleClass().remove("playerActive");
				getVboxPlayer(newValue).getStyleClass().add("playerActive");
			}
		});
				
		/**
		 * Add the Tile  on the Pane
		 */;
		this.gameScene.getChildren().addAll(MainController.GAME.board.tiles);
	}
	
	/**
	 * Return the {@code Vbox} of the current player
	 * @param form {@code String}
	 * @return {@code Label} The {@code Vbox}
	 */
	VBox getVboxPlayer(String form) {
		
		if (form.equals("x")) {
			return player1Box;
		}
		
		return player2Box;
	}
	
	/**
	 * Return the "Your Turn" {@code Label} of the current player
	 * @param form {@code String}
	 * @return {@code Label} The "Your Turn" label
	 */
	Label getLabelPlayer(String form) {
		
		if (form.equals("x")) {
			return player1Turn;
		}
		
		return player2Turn;
	}

	@FXML
    void switchRival(ActionEvent event) {
		System.out.println("Play with firends !");
    }

    @FXML
    void restart(ActionEvent event) {
    	System.out.println("restart");
    }
}
