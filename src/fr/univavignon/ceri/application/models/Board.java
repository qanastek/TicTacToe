/**
 * 
 */
package fr.univavignon.ceri.application.models;

import java.util.ArrayList;
import java.util.List;

import fr.univavignon.ceri.application.config.Settings;
import javafx.beans.binding.NumberBinding;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
import javafx.util.Pair;

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
		Pair<Integer, String> rows = this.checkRows();
		
		// If no winner
		if (rows == null) {
			return;
		} else {
			
			System.out.println("winner !");
			
			// Increment the winner score
			Game.incrementWinner(rows.getValue());
			
			// Stop the game
			Game.STATUS.set(false);
			
			// Y Coordinate
			Float y = Float.valueOf(rows.getKey());
			
			// Coordinates
			Point2D from = new Point2D(0,y);
			Point2D to = new Point2D(2,y);
			
			// Draw the line
			Board.drawWinLine(from,to);
		}
		
		this.checkColumn();
		
		this.checkDiags();
		
		// TODO: Make it really work
		boolean res = true;
		
		/**
		 * Par => 0
		 * <br>
		 * P1 => 1
		 * <br>
		 * P2 => 2
		 */
		String winner = "x";		
		
		// TODO: Change this brute force method
		if (res == false) {
			
			// Increment the winner score
			Game.incrementWinner(winner);
			
			// Stop the game
			Game.STATUS.set(false);
			
			// Coordinates
			Point2D from = new Point2D(0.0,1.0);
			Point2D to = new Point2D(0.0,1.0);
			
			// Draw the line
			Board.drawWinLine(from,to);
		}

	}

	/**
	 * Draw the victory line
	 */
	private static void drawWinLine(Point2D from ,Point2D to) {
		
		System.out.println(Game.HIT.get());
		System.out.println(Game.STATUS.get());
		
		if (Game.STATUS.get() == false || Game.HIT.get() < 5) {
			System.out.println("Go out");
			return;
		}
		
		System.out.println("Draw the line!");
		
		// Anti-aliasing
		Game.WIN_LINE.setSmooth(true);
		
		// Thickness
		Game.WIN_LINE.setStrokeWidth(12.5);
		
		// Color of the fill & stroke
		Game.WIN_LINE.setFill(Color.web("#fff"));
		Game.WIN_LINE.setStroke(Color.web("#fff"));
		
		// Offsets
		Double wideOffset = Settings.TILE_WIDTH.divide(2.0).doubleValue();
		Double heightOffset = Settings.TILE_HEIGHT.divide(2.0).doubleValue();
		
		// Start position
		NumberBinding startX = Settings.TILE_WIDTH.multiply(from.getX()).subtract(wideOffset);
		NumberBinding startY = Settings.TILE_HEIGHT.multiply(from.getY()).subtract(heightOffset);
		
		// Start position
		Game.WIN_LINE.startXProperty().bind(startX);
		Game.WIN_LINE.startYProperty().bind(startY);
		
		// Start position
		NumberBinding endX = Settings.TILE_WIDTH.multiply(to.getX()).subtract(wideOffset);
		NumberBinding endY = Settings.TILE_HEIGHT.multiply(to.getY()).subtract(heightOffset);

		// End position
		Game.WIN_LINE.endXProperty().bind(endX);
		Game.WIN_LINE.endYProperty().bind(endY);
		
		// Set visible
		Game.WIN_LINE.setVisible(true);
	}
	
	/**
	 * Return the current matrix as numbers
	 * @return
	 */
	public static List<List<Integer>> matrixAsNumbers() {
		
		List<List<Integer>> matrix = new ArrayList<List<Integer>>();
		
		for (int i = 0; i < board.size(); i++) {
			
			List<Integer> row = new ArrayList<Integer>();
			
			for (int j = 0; j < board.get(i).size(); j++) {
				
				row.add(board.get(i).get(j).asInteger());
			}
			
			matrix.add(row);
		}
		
		return matrix;
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
	 * Check if we have a winner in the rows
	 */
	private Pair<Integer, String> checkRows() {
		
		// Result
		Pair<Integer, String> res = null;
		
		// Current row
		ArrayList<Tile> row;
		
		// For each row
		for (int i = 0; i < board.size(); i++) {
			
			// Collect the row
			row = board.get(i);
			
			// The sum of the row
			int sum = 0;
		
			/**
			 * Make the sum of the row
			 *  3 => X
			 * -3 => O
			 */
			for (Tile tile : row) { sum += tile.asInteger(); }
			
			// If X or O win
			if (sum == 3 || sum == -3) {
				
				// Winner shape
				String winner = row.get(0).currentShape;
				
				// Create the pair
				res = new Pair<>(i, winner);
				
				// Stop the loop
				break;
			}
		}
		
		return res;
	}

}
