/**
 * 
 */
package fr.univavignon.ceri.application.models;

import fr.univavignon.ceri.application.config.Colors;
import fr.univavignon.ceri.application.config.Settings;
import fr.univavignon.ceri.application.config.Sounds;
import fr.univavignon.ceri.application.config.Textures;
import fr.univavignon.ceri.application.vues.MainController;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
	public String currentShape = Settings.BLANK;
	
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
			
//			System.out.println("Game.CURRENT_PLAYER: " + Game.CURRENT_PLAYER);
//			System.out.println(Game.GAME_MODE);
//			System.out.println(Settings.MULTIPLAYERS);
//			System.out.println(Game.GAME_MODE.equals(Settings.MULTIPLAYERS));
//			System.out.println();
//			System.out.println(
//				((Game.GAME_MODE.equals(Settings.HUMAN_VS_AI) && Game.CURRENT_PLAYER.getValue().equals(Settings.CIRCLE)) ||
//						Game.GAME_MODE.equals(Settings.MULTIPLAYERS))
//			);
		
			// If left hand click
			if (event.getButton() == MouseButton.PRIMARY)
			{
				
				System.out.println("---- a");
				
				// If it's the end of the game
				if (Game.STATUS.get() == false) {

					// New game
					Game.getInstance().clear();
					
					return;
				}
				// If it's not empty
				else if (!this.currentShape.equals(Settings.BLANK)) {
					return;
				}
				else if ((Game.GAME_MODE.equals(Settings.HUMAN_VS_AI) && 
							Game.CURRENT_PLAYER.getValue().equals(Settings.CIRCLE)) ||
							Game.GAME_MODE.equals(Settings.MULTIPLAYERS)
				) {
					
					// If the are currently playing with the cross
					if (Game.CURRENT_PLAYER.get().equals(Settings.CROSS)) {
						
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

					// Check if somebody win the game after this hit
					Game.checkWinner();
				}
			}
		});
	}

	/**
	 * Draw a cross
	 */
	public void drawCross() {		

		// Insert the image
		this.contentImg.setImage(new Image(getClass().getResourceAsStream(Textures.CROSS)));
		// Image anti-aliasing
		this.contentImg.setSmooth(true);

		// Play a sound effect
		AudioClip sound = new AudioClip(this.getClass().getResource(Sounds.CLICK_1).toString());
		sound.play();
		
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
		// Image anti-aliasing
		this.contentImg.setSmooth(true);

		// Play a sound effect
		AudioClip sound = new AudioClip(this.getClass().getResource(Sounds.CLICK_2).toString());
		sound.play();
		
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

	/**
	 * Return the current value as {@code Integer}
	 * @return {@code Integer}
	 */
	public Integer asInteger() {
		
		if (this.currentShape.equals(Settings.CROSS)) {
			return 1;			
		}
		else if (this.currentShape.equals(Settings.CIRCLE)) {
			return -1;			
		}
		else if (this.currentShape.equals(Settings.BLANK)) {
			return 0;			
		}
		
		return 0;
	}

}
