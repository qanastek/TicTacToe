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
public class HardTrain extends Task<Void> {
	
	/**
	 * Constructor
	 */
	public HardTrain() {
	}

	@Override
	protected Void call() throws Exception {
		
		int max = 300;
		
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
