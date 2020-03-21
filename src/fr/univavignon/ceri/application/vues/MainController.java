package fr.univavignon.ceri.application.vues;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.ToggleSwitch;

import fr.univavignon.ceri.application.Main;
import fr.univavignon.ceri.application.config.Settings;
import fr.univavignon.ceri.application.config.Strings;
import fr.univavignon.ceri.application.config.Textures;
import fr.univavignon.ceri.application.models.Board;
import fr.univavignon.ceri.application.models.Game;

public class MainController implements Initializable {

	@FXML
	private VBox vboxPane;
	
    @FXML
    private Pane gameScene;
    
    @FXML
    private ImageView player1Img;
    
    @FXML
    private ImageView player2Img;

    @FXML
    private Button restart;

    @FXML
    private Button rival;
    
    @FXML
    private Button easy;
    
    @FXML
    private Button medium;
    
    @FXML
    private Button hard;
    
    @FXML
    private VBox player1Box;
    
    @FXML
    private VBox player2Box;
    
    @FXML
    private Label player1Turn;
    
    @FXML
    private Label player2Turn;
    
    @FXML
    private Label player1Name;
    
    @FXML
    private Label player2Name;
    
    @FXML
    private ImageView playWithImg;
    
    @FXML
    private HBox difficulties;
    
    /**
     * The {@code Game} instance
     */
    public static Game GAME = Game.getInstance();
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.initGui();
	}

    /**
	 * Initialize the GUI
	 */
	private void initGui() {

		System.out.println("init gui");
		
		// Board width
		this.observeBoardWidth();

		// Board height
		this.observeBoardHeight();

		// Current player
		this.observeCurrentPlayer();
		
		// Count hit
		this.observeHit();
						
		/**
		 * Add the Tile  on the Pane
		 */;
		this.gameScene.getChildren().addAll(MainController.GAME.board.tiles);
	}
	
	/**
	 * Observe the player hits
	 */
	private void observeHit() {

		Game.HIT.addListener(new ChangeListener<Object>() {
			
	      @Override
	      public void changed(ObservableValue<?> observableValue, Object oldValue, Object newValue) {
	    	  
    	  	int newHit = (int) newValue;
	        
    	  	// If it's possible to win
			if (newHit >= 5) {
							
				// Display the board
				System.out.println("Board");
				Board.getInstance().displayAsMatrix();	

				// Check if we have a winner
				Game.checkWinner();
			}			
	      }	      
	    });
	}

	/**
	 * When the player change
	 */
	private void observeCurrentPlayer() {

		Game.CURRENT_PLAYER.addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				
				System.out.println("Player changed!");

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
	}

	/**
	 * Observe height changes of the board
	 */
	private void observeBoardHeight() {

		this.gameScene.heightProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				Settings.HEIGHT_BOARD.set((double) newValue);
			}			
		});
	}

	/**
	 * Observe width changes of the board
	 */
	private void observeBoardWidth() {
		
		this.gameScene.widthProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				Settings.WIDTH_BOARD.set((double) newValue);
			}			
		});
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
		
		switch (this.rival.getText()) {
		
			// Want to play with a friend
			case "Play with a friend":
				
				// Change text
				this.rival.setText("Play with a Bot");

				// Hide difficulties
				this.difficulties.setVisible(false);
				
				// Set bot name
				this.player1Name.setText("Robot " + Strings.EASY);
				
				// Change image
				this.playWithImg.setImage(new Image(getClass().getResourceAsStream(Textures.EASY)));
				break;

			// Want to play with a bot
			case "Play with a Bot":

				// Change text
				this.rival.setText("Play with a friend");
				
				// Show difficulties
				this.difficulties.setVisible(true);
				
				// Change image
				this.playWithImg.setImage(new Image(getClass().getResourceAsStream(Textures.KID)));
				
				break;
		}
    }

    @FXML
    void restart(ActionEvent event) {
    	
    	// Clear the game
    	Game.getInstance().clear();    	
    }
    
    @FXML
    void easy(ActionEvent event) {
    	
    	System.out.println("easy");
    	
		// Set bot name
		this.player1Name.setText("Robot " + Strings.EASY);
    	
		// Switch robot image
		this.player1Img.setImage(new Image(getClass().getResourceAsStream(Textures.EASY)));
		
    	// Clear the game
    	Game.getInstance().clear();   
    }
    
    @FXML
    void medium(ActionEvent event) {
    	
    	System.out.println("medium");
    	
		// Switch robot image
		this.player1Img.setImage(new Image(getClass().getResourceAsStream(Textures.MEDIUM)));
    	
		// Set bot name
		this.player1Name.setText("Robot " + Strings.MEDIUM);

    	// Clear the game
    	Game.getInstance().clear();   
    }
    
    @FXML
    void hard(ActionEvent event) {
    	
    	System.out.println("hard");
    	
		// Switch robot image
		this.player1Img.setImage(new Image(getClass().getResourceAsStream(Textures.HARD)));
    	
		// Set bot name
		this.player1Name.setText("Robot " + Strings.HARD);

    	// Clear the game
    	Game.getInstance().clear();   
    }
}
