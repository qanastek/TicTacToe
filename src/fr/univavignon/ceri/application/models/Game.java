/**
 * 
 */
package fr.univavignon.ceri.application.models;

import java.util.ArrayList;
import java.util.List;

import fr.univavignon.ceri.application.config.Settings;
import javafx.geometry.Insets;

/**
 * @author Yanis Labrak
 *
 */
public class Game {
	
	private static Game instance;
	
	public Board board;
	
	public static String CURRENT_PLAYER = "x";
	
	public static int HIT = 0;
	
	/**
	 * Constructor
	 */
	private Game() {
		
		this.board = Board.getInstance();

		// Initialize the board
		for (int i = 0; i < Settings.TILES_NBR_WIDTH; i++) {
			
			for (int j = 0; j < Settings.TILES_NBR_WIDTH; j++) {
				
				Tile tile = new Tile();
				
				tile.setTranslateY(i * Settings.getSize());
				tile.setTranslateX(j * Settings.getSize());
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
		if (Game.instance == null) {			
			return Game.instance = new Game();
		}
		else {
			return Game.instance;			
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
		
		// If the minimal number of hit as be done
		if (Game.HIT >= 5) {
			// TODO: Check if 3 same X/O in a row
			// TODO: If yes draw a line
		}
	}
	
	

}
