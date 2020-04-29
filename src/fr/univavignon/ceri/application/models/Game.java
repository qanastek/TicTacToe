/**
 * 
 */
package fr.univavignon.ceri.application.models;

import fr.univavignon.ceri.application.config.Settings;
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
	 * Current game mode
	 */
	public static String GAME_MODE = Settings.HUMAN_VS_AI;
	
	/**
	 * Constructor
	 */
	private Game() {
		
		// The board
		this.board = Board.getInstance();
		
		// The current player
		Game.CURRENT_PLAYER = new SimpleStringProperty(Settings.FIRST_PLAYER);
		
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
	public static boolean checkWinner() {
		
		// Check if as winner
		boolean win = Game.getInstance().board.checkWinner();
		
		/**
		 * If no winner was found and the game is ended
		 */
		if (win == false && Game.HIT.get() >= Math.pow(Settings.TILES_NBR_WIDTH, 2)) {
			
			// Stop the game
			Game.STATUS.set(false);	
			
		}
		/**
		 * If a winner was found
		 */
		else if (win == true) {
			
			// Stop the game
			Game.STATUS.set(false);			
		}
		
		return win;		
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
		
		// Reset current player to x
    	Game.CURRENT_PLAYER.set(Settings.BLANK);
    	Game.CURRENT_PLAYER.set(Settings.FIRST_PLAYER);
		
		// Hide the winning line
		Game.WIN_LINE.setVisible(false);
		
		/**
		 * Clear the matrix
		 */
    	Board.getInstance().clear(); 
		
		// Clear hit
    	Game.HIT.set(0);
    	
    	// Allow the players to play
		Game.STATUS.set(true);
	}
	
	/**
	 * Increment the score of the winner
	 * @param {@code Integer} winner
	 */
	public static void incrementWinner(String winner) {
		
		// If P1 win
		if (winner.equals(Settings.CROSS)) {			
			Game.SCORE_P1.set(Game.SCORE_P1.get() + 1);
		}
		// If P2 win
		else if (winner.equals(Settings.CIRCLE)) {
			Game.SCORE_P2.set(Game.SCORE_P2.get() + 1);
		}
	}
}
