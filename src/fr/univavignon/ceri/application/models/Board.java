/**
 * 
 */
package fr.univavignon.ceri.application.models;

import java.util.ArrayList;
import java.util.List;

import fr.univavignon.ceri.application.config.Settings;
import fr.univavignon.ceri.application.vues.MainController;
import javafx.beans.binding.NumberBinding;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
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
	public boolean checkWinner() {
		
		// TODO: Check if 3 same X/O in a row	
		Pair<Integer, String> rows = this.checkRows();
		
		// If winner
		if (rows != null) {
			
			System.out.println("winner row !");
			
			// Increment the winner score
			Game.incrementWinner(rows.getValue());
			
			// Y Coordinate
			Float y = Float.valueOf(rows.getKey());
			
			// Coordinates
			Point2D from = new Point2D(0,y);
			Point2D to = new Point2D(2,y);
			
			// Draw the line
			Board.drawWinLine(from,to);
			
			// Stop the game
			Game.STATUS.set(false);
			
			return true;			
		}
		
		Pair<Integer, String> cols = this.checkColumn();

		// If no winner
		if (cols != null) {
			
			System.out.println("winner col !");
			
			// Increment the winner score
			Game.incrementWinner(cols.getValue());
			
			// Y Coordinate
			Float x = Float.valueOf(cols.getKey());
			
			// Coordinates
			Point2D from = new Point2D(x,0);
			Point2D to = new Point2D(x,2);
			
			// Draw the line
			Board.drawWinLine(from,to);
			
			// Stop the game
			Game.STATUS.set(false);
			
			return true;			
		}
		
		ArrayList<Object> diags = this.checkDiags();

		// If no winner
		if (diags != null) {
			
			System.out.println("winner diags !");
			
			// Increment the winner score
			Game.incrementWinner((String) diags.get(0));
			
			// X Coordinate
			Float x1 = Float.valueOf((int) diags.get(1));
			// Y Coordinate
			Float y1 = Float.valueOf((int) diags.get(2));
			
			// X Coordinate
			Float x2 = Float.valueOf((int) diags.get(3));
			// Y Coordinate
			Float y2 = Float.valueOf((int) diags.get(4));
			
			// Coordinates
			Point2D from = new Point2D(x1,y1);
			Point2D to = new Point2D(x2,y2);
			
			// Draw the line
			Board.drawWinLine(from,to);
			
			// Stop the game
			Game.STATUS.set(false);
			
			return true;			
		}
		
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
			
			// Coordinates
			Point2D from = new Point2D(0.0,1.0);
			Point2D to = new Point2D(0.0,1.0);
			
			// Draw the line
			Board.drawWinLine(from,to);
			
			// Stop the game
			Game.STATUS.set(false);
		}
		
		return false;
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
		Game.WIN_LINE.setStrokeLineCap(StrokeLineCap.ROUND);
		
		// Thickness
		Game.WIN_LINE.setStrokeWidth(12.5);
		
		// Color of the fill & stroke
		Game.WIN_LINE.setFill(Color.web("#fff"));
		Game.WIN_LINE.setStroke(Color.web("#fff"));
		
		// Offsets
		Double wideOffset = Settings.TILE_WIDTH.divide(2.0).doubleValue();
		Double heightOffset = Settings.TILE_HEIGHT.divide(2.0).doubleValue();
		
		// Start position
		NumberBinding startX = Settings.TILE_WIDTH.multiply(from.getX()).add(wideOffset);
		NumberBinding startY = Settings.TILE_HEIGHT.multiply(from.getY()).add(heightOffset);
		
		// Start position
		Game.WIN_LINE.startXProperty().bind(startX);
		Game.WIN_LINE.startYProperty().bind(startY);
		
		// Start position
		NumberBinding endX = Settings.TILE_WIDTH.multiply(to.getX()).add(wideOffset);
		NumberBinding endY = Settings.TILE_HEIGHT.multiply(to.getY()).add(heightOffset);

		// End position
		Game.WIN_LINE.endXProperty().bind(endX);
		Game.WIN_LINE.endYProperty().bind(endY);
		
		// Set visible
		Game.WIN_LINE.setVisible(true);
		
		// Animate the line
		MainController.fadeOutLeftToRight(Game.WIN_LINE, 10000);
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
	 * Check if we have a winner on the diagonals
	 */
	private ArrayList<Object> checkDiags() {

		ArrayList<Object> res = null;

		/**
		 * First diagonal
		 */
		int diag1 = 0;
		
		// For each Tile on this diag
		for (int i = 0; i < Settings.TILES_NBR_WIDTH; i++) {
			diag1 += Board.matrixAsNumbers().get(i).get(i);
		}
		
		// Check winner
		if (diag1 == 3 || diag1 == -3) {
			
			// Winner shape
			String winner = board.get(0).get(0).currentShape;
			
			// Create the list
			res = new ArrayList<Object>();
			res.add(winner);
			res.add(0);
			res.add(0);
			res.add(Settings.TILES_NBR_WIDTH-1);
			res.add(Settings.TILES_NBR_WIDTH-1);
			
			return res;
		}

		/**
		 * Second diagonal
		 */
		int diag2 = 0;

		// For each Tile on this diag
		int j = 0;
		for (int i = Settings.TILES_NBR_WIDTH-1; i >= 0; i--) {
			diag2 += Board.matrixAsNumbers().get(j).get(i);
			++j;
		}
		
		// Check winner
		if (diag2 == 3 || diag2 == -3) {
			
			// Winner shape
			String winner = board.get(0).get(Settings.TILES_NBR_WIDTH-1).currentShape;
			
			// Create the list
			res = new ArrayList<Object>();
			res.add(winner);
			res.add(Settings.TILES_NBR_WIDTH-1);
			res.add(0);
			res.add(0);
			res.add(Settings.TILES_NBR_WIDTH-1);
			
			return res;
		}

		return res;
		
	}

	/**
	 * Check if we have a winner on the column
	 */
	private Pair<Integer, String> checkColumn() {		

		// Result
		Pair<Integer, String> res = null;
		
		// Current row
		ArrayList<Tile> row;
		
		// For each column
		for (int i = 0; i < board.size(); i++) {
			
			// The sum of the row
			int sum = 0;
		
			/**
			 * Make the sum of the row
			 *  3 => X
			 * -3 => O
			 */
			
			// For each row
			for (int j = 0; j < board.size(); j++) {
				sum += board.get(j).get(i).asInteger();
			}
			
			// If X or O win
			if (sum == 3 || sum == -3) {
				
				// Winner shape
				String winner = board.get(0).get(i).currentShape;
				
				// Create the pair
				res = new Pair<>(i, winner);
				
				// Stop the loop
				break;
			}
		}
		
		return res;		
	}

	/**
	 * Check if we have a winner on the rows
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
