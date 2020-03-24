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
    	if (RunningThreads.EASY_TRAIN != null && RunningThreads.EASY_TRAIN.isRunning() == true) {
        	RunningThreads.EASY_TRAIN.cancel();			
		} else {
			RunningThreads.EASY_TRAIN = new EasyTrain();
		}
    	
    	// Bind progress
    	progressBarEasy.progressProperty().bind(RunningThreads.EASY_TRAIN.progressProperty());
    	progressTextEasy.textProperty().bind(RunningThreads.EASY_TRAIN.messageProperty());
    	
    	// Run the thread
    	new Thread(RunningThreads.EASY_TRAIN).start();
    }
    
    @FXML
    void trainMedium(ActionEvent event) {
    	
    	System.out.println("trainMedium");
    	
    	// Set visible
    	this.progressionMedium.setVisible(true);
    	
    	// If is running stop It else run It
    	if (RunningThreads.MEDIUM_TRAIN != null && RunningThreads.MEDIUM_TRAIN.isRunning() == true) {
    		RunningThreads.MEDIUM_TRAIN.cancel();			
    	} else {
    		RunningThreads.MEDIUM_TRAIN = new MediumTrain();
    	}
    	
    	// Bind progress
    	progressBarMedium.progressProperty().bind(RunningThreads.MEDIUM_TRAIN.progressProperty());
    	progressTextMedium.textProperty().bind(RunningThreads.MEDIUM_TRAIN.messageProperty());
    	
    	// Run the thread
    	new Thread(RunningThreads.MEDIUM_TRAIN).start();
    }
    
    @FXML
    void trainHard(ActionEvent event) {
    	
    	System.out.println("trainHard");
    	
    	// Set visible
    	this.progressionHard.setVisible(true);
    	
    	// If is running stop It else run It
    	if (RunningThreads.HARD_TRAIN != null && RunningThreads.HARD_TRAIN.isRunning() == true) {
    		RunningThreads.HARD_TRAIN.cancel();			
    	} else {
    		RunningThreads.HARD_TRAIN = new HardTrain();
    	}
    	
    	// Bind progress
    	progressBarHard.progressProperty().bind(RunningThreads.HARD_TRAIN.progressProperty());
    	progressTextHard.textProperty().bind(RunningThreads.HARD_TRAIN.messageProperty());
    	
    	// Run the thread
    	new Thread(RunningThreads.HARD_TRAIN).start();
    }
	
}
