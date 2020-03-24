/**
 * 
 */
package fr.univavignon.ceri.application.models;

import java.util.ArrayList;
import java.util.List;

import fr.univavignon.ceri.application.config.Settings;
import javafx.scene.paint.ImagePattern;

/**
 * @author Yanis Labrak
 *
 */
public class Board {
	
	private static Board INSTANCE;
	
	private static List<ArrayList<Tile>> board;
	
	/**
	 * Constructor
	 */
	private Board() {
		
		Board.setBoard(new ArrayList<ArrayList<Tile>>());

		// Initialize the board
		for (int i = 0; i < Settings.TILES_NBR_WIDTH; i++) {
			
			// The row
			ArrayList<Tile> row = new ArrayList<Tile>();
			
			for (int j = 0; j < Settings.TILES_NBR_WIDTH; j++) {
				
				Tile tile = new Tile();
				
				tile.translateYProperty().bind(Settings.TILE_HEIGHT.multiply(i));
				tile.translateXProperty().bind(Settings.TILE_WIDTH.multiply(j));
				
//				tile.setPadding(new Insets(10, 10, 10, 10));
				
				row.add(tile);
			}
			
			Board.board.add(row);
		}
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
	 * Getter for the board
	 * @return the board
	 */
	public static List<ArrayList<Tile>> getBoard() {
		return board;
	}

	/**
	 * Setter for the board
	 * @param board the board to set
	 */
	public static void setBoard(List<ArrayList<Tile>> board) {
		Board.board = board;
	}
	
	/**
	 * Display the current board as a matrix
	 */
	public void displayAsMatrix() {
		
		// For each row
		for (ArrayList<Tile> row : getBoard()) {
			
			// For each Tiles
			for (Tile tile : row) {

				// Display the tile content
				System.out.print(tile.toString());
			}
			
			// Jump line
			System.out.println("");
		}
	}
	
	/**
	 * Return the content of the matrix as a list of Tile
	 * @return {@code ArrayList<Tile>} The list of {@code Tile}s
	 */
	public static ArrayList<Tile> getTiles() {
		
		// The list of Tiles
		ArrayList<Tile> list = new ArrayList<Tile>();
		
		// For each row
		for (ArrayList<Tile> row : Board.getBoard()) {
			
			// For each Tiles
			for (Tile tile : row) {

				// Add in the list
				list.add(tile);
			}
		}
		
		return list;
	}
	
	/**
	 * Clear the board
	 */
	public void clear() {

		// For each row
		for (ArrayList<Tile> row : getBoard()) {
			
			// For each Tiles
			for (Tile tile : row) {
				
				// Clear the image
				tile.contentImg.setImage(null);
				
				// Clear the current piece
				tile.currentShape = Settings.BLANK;
			}
		}
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return null;
	}

	/**
	 * Check if somebody won the game
	 */
	public void checkWinner() {
		// TODO: Check if 3 same X/O in a row
		// TODO: If yes draw a line		
	}

}
