/**
 * 
 */
package fr.univavignon.ceri.application.models;

import java.util.ArrayList;
import java.util.List;

import fr.univavignon.ceri.application.config.Settings;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;

/**
 * @author Yanis Labrak
 *
 */
public class Board {
	
	/**
	 * The instance of the class
	 */
	private static Board INSTANCE;
	
	/**
	 * The board which contains the {@code Tile}s
	 */
	private static List<ArrayList<Tile>> board;
	
	/**
	 * Constructor
	 */
	private Board() {
		
		// Instantiate the board
		Board.board = new ArrayList<ArrayList<Tile>>();

		// Initialize the board
		for (int i = 0; i < Settings.TILES_NBR_WIDTH; i++) {
			
			// The row
			ArrayList<Tile> row = new ArrayList<Tile>();
			
			// For each column
			for (int j = 0; j < Settings.TILES_NBR_WIDTH; j++) {
				
				// Create the Tile
				Tile tile = new Tile();
				
				// Change it's position
				tile.translateYProperty().bind(Settings.TILE_HEIGHT.multiply(i));
				tile.translateXProperty().bind(Settings.TILE_WIDTH.multiply(j));
				
//				tile.setPadding(new Insets(10, 10, 10, 10));
				
				// And add it to the row
				row.add(tile);
			}
			
			// Add the row to the board
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
		this.checkRows();
		this.checkColumn();
		this.checkDiags();
		
		// TODO: Make it really work
		boolean res = true;
		
		if (res == true) {
			
			System.out.println("check winner board");
			
			Game.STATUS.set(false);
			
			// TODO: If yes draw a line	
			Board.drawWinLine();
		}

	}

	/**
	 * Draw the victory line
	 */
	private static void drawWinLine() {
		
		if (Game.STATUS.get() == false || Game.HIT.get() < 5) {
			return;
		}
		
		// Anti-aliasing
		Game.WIN_LINE.setSmooth(true);
		
		// Thickness
		Game.WIN_LINE.setStrokeWidth(12.5);
		
		// Color of the fill & stroke
		Game.WIN_LINE.setFill(Color.web("#fff"));
		Game.WIN_LINE.setStroke(Color.web("#fff"));
		
		// Start position
		Game.WIN_LINE.startXProperty().bind(Settings.TILE_WIDTH.divide(2.0));
		Game.WIN_LINE.startYProperty().bind(Settings.TILE_HEIGHT.divide(2.0));

		// End position
		Game.WIN_LINE.endXProperty().bind(Settings.TILE_WIDTH.multiply(2.5));
		Game.WIN_LINE.endYProperty().bind(Settings.TILE_HEIGHT.multiply(2.5));
		
		// Set visible
		Game.WIN_LINE.setVisible(true);
	}

	/**
	 * 
	 */
	private void checkDiags() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 */
	private void checkColumn() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 */
	private void checkRows() {
		// TODO Auto-generated method stub
		
	}

}
