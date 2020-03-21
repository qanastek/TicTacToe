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
