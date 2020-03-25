/**
 * 
 */
package fr.univavignon.ceri.application.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
	 * Score player 1
	 */
	public static IntegerProperty SCORE_P1;
	
	/**
	 * Score player 2
	 */
	public static IntegerProperty SCORE_P2;
	
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
		
		// Players scores
		Game.SCORE_P1 = new SimpleIntegerProperty(0);
		Game.SCORE_P2 = new SimpleIntegerProperty(0);
		
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
	 * Basic clear
	 */
	public static void basicClear() {
    	
    	// Clear player 1 score
    	Game.SCORE_P1.set(0);
    	
    	// Clear player 2 score
    	Game.SCORE_P2.set(0);
    	
    	// Clear the game
    	Game.getInstance().clear();
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
	
	/**
	 * Increment the score of the winner
	 * @param {@code Integer} winner
	 */
	public static void incrementWinner(int winner) {
		
		// If P1 win
		if (winner == 1) {
			
			Game.SCORE_P1.set(Game.SCORE_P1.get() + 1);
			// TODO: Add vfx
		}
		// If P2 win
		else if (winner == 2) {
			
			Game.SCORE_P2.add(1);	
			// TODO: Add vfx	
		}
		// If par
		else {

			System.out.println("Par nobody win!");
			// TODO: Add sfx
			// TODO: Add vfx
		}
	}
}
