package fr.univavignon.ceri.application;
	
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
	 * The scenes
	 */
	public static Scene scene;
	
	/**
	 * Size of the screen
	 */
	public static Rectangle2D screenBounds;
	
	/**
	 * App width
	 */
	public static SimpleDoubleProperty widthApp = new SimpleDoubleProperty();
	
	/**
	 * App height
	 */
	public static SimpleDoubleProperty heigthApp = new SimpleDoubleProperty();
	
	@Override
	public void start(Stage primaryStage) {
		try {

			primaryStage.setTitle("Projet GUI V1.0");

			// Get the dimensions of the monitor
		    Main.screenBounds = Screen.getPrimary().getBounds();
		    
		    // Load the FXML
		    Parent layout = FXMLLoader.load(getClass().getResource("vues/Main.fxml"));
			
			scene = new Scene(layout,layout.getLayoutY(), layout.getLayoutX());
			scene.getStylesheets().add(getClass().getResource("vues/application.css").toExternalForm());
			
			// Set the width of the window to the half of the monitor width
			Main.widthApp.set(screenBounds.getWidth() * 0.3);
			primaryStage.setWidth(Main.widthApp.get());
//			primaryStage.setMinWidth(Main.widthApp);
//			primaryStage.setMaxWidth(Main.widthApp);

			// Set the height of the window to the half of the monitor height
			Main.heigthApp.set(screenBounds.getHeight() * 0.9);
//			primaryStage.setMinHeight(Main.heigthApp);
//			primaryStage.setMaxHeight(Main.heigthApp);
			primaryStage.setHeight(Main.heigthApp.get());

			primaryStage.setScene(scene);
			primaryStage.show();
			
			/**
			 * Observe width changes
			 */
			Main.scene.widthProperty().addListener(new ChangeListener<Object>() {
				@Override
				public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
					System.out.println("Old width: " + Main.widthApp);
					Main.widthApp.set((double) newValue);
					System.out.println("New width : " + Main.widthApp);
				}			
			});
			
			/**
			 * Observe height changes
			 */
			Main.scene.heightProperty().addListener(new ChangeListener<Object>() {
				@Override
				public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
					System.out.println("Old height : " + Main.heigthApp);
					Main.heigthApp.set((double) newValue);
					System.out.println("New height : " + Main.heigthApp);
				}			
			});
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
