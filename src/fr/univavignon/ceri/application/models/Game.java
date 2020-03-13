/**
 * 
 */
package fr.univavignon.ceri.application.models;

import java.util.ArrayList;
import java.util.List;

import fr.univavignon.ceri.application.config.Settings;

/**
 * @author Yanis Labrak
 *
 */
public class Game {
	
	private static Game instance;
	
	public Board board;
	
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
	
	

}
