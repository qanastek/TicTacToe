package fr.univavignon.ceri.application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Tic Tac Toe
 * @author Yanis Labrak
 * @author Valentin Vougeot
 */
public class Main extends Application {
	
	/**
	 * Size of the screen
	 */
	public static Rectangle2D screenBounds;
	
	public static Double widthApp;
	
	public static Double heigthApp;
	
	@Override
	public void start(Stage primaryStage) {
		try {

			primaryStage.setTitle("Projet GUI V1.0");

			// Get the dimensions of the monitor
		    Main.screenBounds = Screen.getPrimary().getBounds();
		    
		    // Load the FXML
		    Parent layout = FXMLLoader.load(getClass().getResource("vues/Main.fxml"));
			
			Scene scene = new Scene(layout,layout.getLayoutY(), layout.getLayoutX());
			scene.getStylesheets().add(getClass().getResource("vues/application.css").toExternalForm());
			
			// Set the width of the window to the half of the monitor width
			Main.widthApp = screenBounds.getWidth() * 0.3;
//			primaryStage.setMinWidth(Main.widthApp);
//			primaryStage.setMaxWidth(Main.widthApp);
			primaryStage.setWidth(Main.widthApp);

			// Set the height of the window to the half of the monitor height
			Main.heigthApp = screenBounds.getHeight() * 0.9;
//			primaryStage.setMinHeight(Main.heigthApp);
//			primaryStage.setMaxHeight(Main.heigthApp);
			primaryStage.setHeight(Main.heigthApp);

			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
