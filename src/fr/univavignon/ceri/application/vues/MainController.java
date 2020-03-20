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
    private Button refresh;

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
				
				if (newValue.equals("x")) {
					System.out.println("---- X");
				}
				else if (newValue.equals("o")) {
					System.out.println("---- O");
				}
			}
		});
		
//		if (Main.widthApp != null && Main.widthApp.get() > 0) {
//			
//			System.out.println(Main.widthApp.get());
//			System.out.println(Main.widthApp.get() *0.5);
//			
//			gameScene.setPrefWidth(Main.widthApp.get());
//			gameScene.setPrefHeight(Main.widthApp.get());	
//			
//			vboxPane.setPrefHeight(Main.widthApp.get());	
//			vboxPane.setPrefHeight(Main.widthApp.get());	
//		}
		
		/**
		 * Add the Tile  on the Pane
		 */;
		this.gameScene.getChildren().addAll(MainController.GAME.board.tiles);
	}

	@FXML
    void switchRival(ActionEvent event) {
		System.out.println("Play with firends !");
    }

    @FXML
    void refresh(ActionEvent event) {

    }
}
