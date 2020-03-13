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
	
	private static Board instance;
	
	public List<Tile> tiles;
	
	/**
	 * Constructor
	 */
	private Board() {
		this.tiles = new ArrayList<Tile>();
	}
	
	public static Board getInstance() {
		
		// If hasn't been created
		if (Board.instance == null) {
			return Board.instance = new Board();
		}
		else {
			return Board.instance;			
		}		
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return null;
	}

}
