/**
 * 
 */
package fr.univavignon.ceri.application.models;

import java.util.ArrayList;
import java.util.List;

import fr.univavignon.ceri.application.config.Settings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

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
	public static StringProperty CURRENT_PLAYER;
	
	/**
	 * Total number of hits
	 */
	public static IntegerProperty HIT;
	
	/**
	 * The winning line
	 */
	public static Line WIN_LINE;
	
	/**
	 * Status of the game
	 * <br>
	 * True => Running
	 * <br>
	 * False => Stopped
	 */
	public static BooleanProperty STATUS = new SimpleBooleanProperty(true);
	
	/**
	 * Constructor
	 */
	private Game() {
		
		// The board
		this.board = Board.getInstance();
		
		// The current player
		Game.CURRENT_PLAYER = new SimpleStringProperty("x");
		
		// Total number of hits
		Game.HIT = new SimpleIntegerProperty(0);
		
		// The winning line
		Game.WIN_LINE = new Line();
		Game.WIN_LINE.setVisible(false);
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
		
		System.out.println("Clear game");
		
		// Clear hit
    	Game.HIT.set(0);
		
		// Reset current player to x
    	Game.CURRENT_PLAYER.set("x");
		
		// Hide the winning line
		Game.WIN_LINE.setVisible(false);
    	
    	// Allow the players to play
		Game.STATUS.set(true);
		
		/**
		 * Clear the matrix
		 */
    	Board.getInstance().clear(); 
	}
}
