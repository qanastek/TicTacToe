/**
 * 
 */
package fr.univavignon.ceri.application.models;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yanis Labrak
 *
 */
public class Board {
	
	private static Board INSTANCE;
	
	public List<Tile> tiles;
	
	/**
	 * Constructor
	 */
	private Board() {
		this.tiles = new ArrayList<Tile>();
	}
	
	/**
	 * Get the {@code Board} instance
	 * @return {@code Board}
	 */
	public static Board getInstance() {
		
		// If hasn't been created
		if (Board.INSTANCE == null) {
			return Board.INSTANCE = new Board();
		}
		else {
			return Board.INSTANCE;
		}
	}
	
	/**
	 * Display the current board as a matrix
	 */
	public void displayAsMatrix() {
		
		List<String> boardList = this.getAsList();
		
		int i = 0;

		// Line
		for (int j = 0; j < 3; j++) {
			
			// Column
			for (int k = 0; k < 3; k++) {
				
				System.out.print(boardList.get(i) + " ");
				++i;
			}
			
			// Jump line
			System.out.println("");
		}
	}
	
	/**
	 * Clear the board
	 */
	public void clear() {

		// For each Tiles
		for (Tile tile : tiles) {
			
			// Clear It
			tile.content.setText("");
		}
	}
	
	/**
	 * Return the board as a list of string
	 * @return {@code List<String>}
	 */
	public List<String> getAsList() {
		
		List<String> boardList = new ArrayList<String>();
		
		for (Tile tile : tiles) {
			boardList.add(tile.toString());
		}
		
		return boardList;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return null;
	}

}
