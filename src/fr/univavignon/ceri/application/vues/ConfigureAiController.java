package fr.univavignon.ceri.application.vues;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

import fr.univavignon.ceri.application.services.Threads.EasyTrain;
import fr.univavignon.ceri.application.services.Threads.HardTrain;
import fr.univavignon.ceri.application.services.Threads.MediumTrain;
import fr.univavignon.ceri.application.services.Threads.RunningThreads;

public class ConfigureAiController implements Initializable {
	
	/**
	 * Easy
	 */
	@FXML
	private VBox progressionEasy;
	
	@FXML
	private ProgressBar progressBarEasy;
	
	@FXML
	private Label progressTextEasy;
	
	/**
	 * Medium
	 */
	@FXML
	private VBox progressionMedium;
	
	@FXML
	private ProgressBar progressBarMedium;
	
	@FXML
	private Label progressTextMedium;
	
	/**
	 * Hard
	 */
	@FXML
	private VBox progressionHard;
	
	@FXML
	private ProgressBar progressBarHard;
	
	@FXML
	private Label progressTextHard;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// Initialize the GUI
		this.initGui();
	}

    /**
	 * Initialize the GUI
	 */
	private void initGui() {
		System.out.println("init gui");
	}

    @FXML
    void trainEasy(ActionEvent event) {
    	
    	System.out.println("trainEasy");
    	
    	// Set visible
    	this.progressionEasy.setVisible(true);
    	
    	// If is running stop It else run It
    	if (RunningThreads.easyTrain != null && RunningThreads.easyTrain.isRunning() == true) {
        	RunningThreads.easyTrain.cancel();			
		} else {
			RunningThreads.easyTrain = new EasyTrain();
		}
    	
    	// Bind progress
    	progressBarEasy.progressProperty().bind(RunningThreads.easyTrain.progressProperty());
    	progressTextEasy.textProperty().bind(RunningThreads.easyTrain.messageProperty());
    	
    	// Run the thread
    	new Thread(RunningThreads.easyTrain).start();
    }
    
    @FXML
    void trainMedium(ActionEvent event) {
    	
    	System.out.println("trainMedium");
    	
    	// Set visible
    	this.progressionMedium.setVisible(true);
    	
    	// If is running stop It else run It
    	if (RunningThreads.mediumTrain != null && RunningThreads.mediumTrain.isRunning() == true) {
    		RunningThreads.mediumTrain.cancel();			
    	} else {
    		RunningThreads.mediumTrain = new MediumTrain();
    	}
    	
    	// Bind progress
    	progressBarMedium.progressProperty().bind(RunningThreads.mediumTrain.progressProperty());
    	progressTextMedium.textProperty().bind(RunningThreads.mediumTrain.messageProperty());
    	
    	// Run the thread
    	new Thread(RunningThreads.mediumTrain).start();
    }
    
    @FXML
    void trainHard(ActionEvent event) {
    	
    	System.out.println("trainHard");
    	
    	// Set visible
    	this.progressionHard.setVisible(true);
    	
    	// If is running stop It else run It
    	if (RunningThreads.hardTrain != null && RunningThreads.hardTrain.isRunning() == true) {
    		RunningThreads.hardTrain.cancel();			
    	} else {
    		RunningThreads.hardTrain = new HardTrain();
    	}
    	
    	// Bind progress
    	progressBarHard.progressProperty().bind(RunningThreads.hardTrain.progressProperty());
    	progressTextHard.textProperty().bind(RunningThreads.hardTrain.messageProperty());
    	
    	// Run the thread
    	new Thread(RunningThreads.hardTrain).start();
    }
	
}
