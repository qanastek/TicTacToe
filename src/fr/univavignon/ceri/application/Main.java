package fr.univavignon.ceri.application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {

			primaryStage.setTitle("Projet GUI V1.0");

//			BorderPane root = new BorderPane();

		    Parent layout = FXMLLoader.load(getClass().getResource("vues/Main.fxml"));
			
			Scene scene = new Scene(layout,600,920);
			scene.getStylesheets().add(getClass().getResource("vues/application.css").toExternalForm());

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
