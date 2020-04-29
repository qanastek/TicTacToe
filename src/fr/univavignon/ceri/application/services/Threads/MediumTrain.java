/**
 * 
 */
package fr.univavignon.ceri.application.services.Threads;

import fr.univavignon.ceri.application.vues.configureAi.ConfigureAiController;
import javafx.concurrent.Task;

/**
 * @author Yanis Labrak
 *
 */
public class MediumTrain extends Task<Void> {
	
	/**
	 * Constructor
	 */
	public MediumTrain() {
	}

	@Override
	protected Void call() throws Exception {
		
		int max = Integer.parseInt(ConfigureAiController.CURRENT_EPOCHS_MEDIUM.get());
		
		for (int i = 0; i <= max; i++) {
            updateProgress(i, max);
            updateMessage(i + "/" + max + " Epochs");
			Thread.sleep(0, 1);
		}
		
		return null;
	}

	@Override
	protected void succeeded() {
		System.out.println("Congratulation!");
	}
}
