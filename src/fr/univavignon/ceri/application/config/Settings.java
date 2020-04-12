/**
 * 
 */
package fr.univavignon.ceri.application.config;

import javafx.beans.binding.NumberBinding;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * @author Yanis Labrak
 *
 */
public class Settings {
	
	/**
	 * The cross tile
	 */
	public static String CROSS = "x";
	
	/**
	 * The circle tile
	 */
	public static String CIRCLE = "o";
	
	/**
	 * The blank tile
	 */
	public static String BLANK = "b";

	/**
	 * The bot piece
	 */
	public static String BOT_PIECE = Settings.CROSS;
	
	/**
	 * The player piece
	 */
	public static String PLAYER_PIECE = Settings.CIRCLE;
	
	/**
	 * Who play first
	 */
	public static final String FIRST_PLAYER = PLAYER_PIECE;
	
	/**
	 * Game mode human versus robot
	 */
	public static String HUMAN_VS_AI = "ai";
	
	/**
	 * Game mode multiplayers
	 */
	public static String MULTIPLAYERS = "multi";
	
	/**
	 * Number of {@code Tile}s on the board width
	 */
	public static int TILES_NBR_WIDTH = 3;
	
	/**
	 * Radius of the {@code Tile}s
	 */
	public static final double RADIUS = 50.0;

	/**
	 * Size of the {@code Text} inside the {@code Tile}s
	 */
	public static final double SIZE_TEXT_TILE = 60;
	
	/**
	 * App width
	 */
	public static SimpleDoubleProperty WIDTH_BOARD = new SimpleDoubleProperty();
	
	/**
	 * App height
	 */
	public static SimpleDoubleProperty HEIGHT_BOARD = new SimpleDoubleProperty();
	
	/**
	 * Tile width
	 */
	public static final NumberBinding TILE_WIDTH = WIDTH_BOARD.divide(3);
	
	/**
	 * Tile height
	 */
	public static final NumberBinding TILE_HEIGHT = HEIGHT_BOARD.divide(3);
	
}
