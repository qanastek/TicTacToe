/**
 * 
 */
package fr.univavignon.ceri.application.services.Threads;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
		
		int max = 200;
		
		for (int i = 0; i <= max; i++) {
            updateProgress(i, max);
            updateMessage(i + "/" + max + " Epochs");
			Thread.sleep(10);
		}
		
		return null;
	}

	@Override
	protected void succeeded() {
		System.out.println("Congratulation!");
	}
}
