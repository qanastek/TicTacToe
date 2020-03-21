/**
 * 
 */
package fr.univavignon.ceri.application.models;

import fr.univavignon.ceri.application.config.Settings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Yanis Labrak
 */
public class Game {
	
	/**
	 * The game instance
	 */
	private static Game INSTANCE;
	
	/**
	 * The board
	 */
	public Board board;
	
	/**
	 * The current player
	 */
	public static StringProperty CURRENT_PLAYER = new SimpleStringProperty("x");
	
	/**
	 * Total number of hits
	 */
	public static IntegerProperty HIT = new SimpleIntegerProperty(0);
	
	/**
	 * Constructor
	 */
	private Game() {
		
		this.board = Board.getInstance();

		// Initialize the board
		for (int i = 0; i < Settings.TILES_NBR_WIDTH; i++) {
			
			for (int j = 0; j < Settings.TILES_NBR_WIDTH; j++) {
				
				Tile tile = new Tile();
				
				tile.translateYProperty().bind(Settings.TILE_HEIGHT.multiply(i));
				tile.translateXProperty().bind(Settings.TILE_WIDTH.multiply(j));
				
//				tile.setPadding(new Insets(10, 10, 10, 10));
				
				this.board.tiles.add(tile);
			}
		}
	}
	
	/**
	 * Get the instance
	 * @return {@code Game}
	 */
	public static Game getInstance() {
		
		// If hasn't been created
		if (Game.INSTANCE == null) {			
			return Game.INSTANCE = new Game();
		}
		else {
			return Game.INSTANCE;			
		}		
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return null;
	}

	/**
	 * Check if we have a winner
	 */
	public static void checkWinner() {
		Game.getInstance().board.checkWinner();		
	}
	
	/**
	 * Clear the current game
	 */
	public void clear() {
		
    	// Clear the matrix
    	Board.getInstance().clear(); 
		
		// Clear hit
    	Game.HIT.set(0);
		
		// Reset current player to x
    	Game.CURRENT_PLAYER.set("x");
	}
}
