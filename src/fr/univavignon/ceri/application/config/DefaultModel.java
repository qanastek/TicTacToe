/**
 * 
 */
package fr.univavignon.ceri.application.config;

/**
 * @author Yanis Labrak
 *
 */
public class DefaultModel {
	
	/**
	 * Name of the configuration file for the models
	 */
	public static String FILE_NAME = "models_settings.xml";
	
	/**
	 * Epochs
	 */
	public static String LEARNING_RATE = "learning_rate";
	
	/**
	 * Epochs
	 */
	public static String EPOCHS = "epochs";
	
	/**
	 * Epochs
	 */
	public static String BATCH_SIZE = "batch_size";
		
	/**
	 * Default settings for the medium model
	 */
	
	// Learning rate
	public static double LEARNING_RATE_MEDIUM = 0.1;
	
	// Epochs
	public static int EPOCHS_MEDIUM = 10000;
	
	// Batch size
	public static int BATCH_SIZE_MEDIUM = 2500;
	
	/**
	 * Default settings for the hard model
	 */
	
	// Learning rate
	public static double LEARNING_RATE_HARD= 0.001;
	
	// Epochs
	public static int EPOCHS_HARD = 20000;
	
	// Batch size
	public static int BATCH_SIZE_HARD = 500;
	

}
