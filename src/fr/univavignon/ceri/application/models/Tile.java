/**
 * 
 */
package fr.univavignon.ceri.application.models;

import java.io.InputStream;

import fr.univavignon.ceri.application.config.Colors;
import fr.univavignon.ceri.application.config.Settings;
import fr.univavignon.ceri.application.config.Textures;
import fr.univavignon.ceri.application.vues.MainController;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * A Tile
 * @author Yanis Labrak
 */
public class Tile extends StackPane {
	
	/**
	 * Content of the {@code Tile} :
	 * <br>
	 * null => Blank
	 * <br>
	 * x => Cross
	 * <br>
	 * o => Round
	 */
//	public Text content;
	
	/**
	 * The player piece
	 */
	public ImageView contentImg;
	
	/**
	 * Rectangle
	 */
	public Rectangle shape;
	
	/**
	 * Current state
	 */
	public String currentShape = "b";
	
	/**
	 * Constructor
	 */
	public Tile() {

		this.contentImg = new ImageView();
		
		// Rectangle
		this.shape = new Rectangle();
		this.shape.widthProperty().bind(Settings.TILE_WIDTH);
		this.shape.heightProperty().bind(Settings.TILE_HEIGHT);
		
		// Fill colors and stroke
		this.shape.setFill(Color.web(Colors.TILE_FILL));
		this.shape.setStroke(Color.web(Colors.TILE_STROKE));
		
		// Rounded corners
		this.shape.setArcWidth(Settings.RADIUS);
		this.shape.setArcHeight(Settings.RADIUS);
				
		// Align
		setAlignment(Pos.CENTER);
		
		// Add all the elements to the pane
		getChildren().addAll(this.shape, this.contentImg);
		
		/**
		 * When we click on the Tile
		 */
		this.setOnMouseClicked(event -> {
			
			// If left hand click
			if (event.getButton() == MouseButton.PRIMARY) {
				
				// If it's the end of the game
				if (Game.STATUS.get() == false) {
					
					// New game
					Game.getInstance().clear();
					
					return;
				}
				// If it's not empty
				else if (!this.currentShape.equals("b")) {
					return;
				} else {
					
					// If the are currently playing with the cross
					if (Game.CURRENT_PLAYER.get().equals("x")) {
						
						// Draw the cross
						this.drawCross();
						
						// And change to the circle
						Game.CURRENT_PLAYER.set(Settings.CIRCLE);		
						
					} else {

						// Draw the circle
						this.drawCircle();
						
						// And change to the cross
						Game.CURRENT_PLAYER.set(Settings.CROSS);				
					}
					
					// Increment the hit counter
					Game.HIT.set(Game.HIT.get() + 1);
					
				}
			}
		});
	}

	/**
	 * Draw a cross
	 */
	private void drawCross() {		

		// Insert the image
		this.contentImg.setImage(new Image(getClass().getResourceAsStream(Textures.CROSS)));
		
		// Set he size of it
		this.contentImg.fitWidthProperty().bind(Settings.TILE_WIDTH.divide(1.5));
		this.contentImg.fitHeightProperty().bind(Settings.TILE_HEIGHT.divide(1.5));
		
		// Animation
		MainController.fadeOut(this.contentImg, 250);

		// Change the current shape on the tile
		this.currentShape = Settings.CROSS;
	}

	/**
	 * Draw a cross
	 */
	private void drawCircle() {	

		// Insert the image
		this.contentImg.setImage(new Image(getClass().getResourceAsStream(Textures.CIRCLE)));
		
		// Set he size of it
		this.contentImg.fitWidthProperty().bind(Settings.TILE_WIDTH.divide(1.5));
		this.contentImg.fitHeightProperty().bind(Settings.TILE_HEIGHT.divide(1.5));
		
		// Animation
		MainController.fadeOut(this.contentImg, 250);
		
		// Change the current shape on the tile
		this.currentShape = Settings.CIRCLE;
	}
	
	@Override
	public String toString() {		
		return this.currentShape;
	}

}
