/**
 * 
 */
package fr.univavignon.ceri.application.config;

import fr.univavignon.ceri.application.Main;

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
	 * This is the size of the {@code Tile}s
	 */
	public static Double getSize() {
		
		return 200.0;
		
//		if (Main.widthApp != null) {
//			return Main.widthApp / 3;			
//		} else {
//			return 0.0;
//		}
	}

}
