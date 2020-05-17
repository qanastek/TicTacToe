/**
 * 
 */
package fr.univavignon.ceri.application.services.Threads;

import fr.univavignon.ceri.application.ai.NeuralNetwork;

/**
 * @author Yanis Labrak
 *
 */
public class RunningThreads {
	
	/**
	 * Train of the medium model
	 */
	public static NeuralNetwork MEDIUM_TRAIN;
	
	/**
	 * Train of the hard model
	 */
	public static NeuralNetwork HARD_TRAIN;

	/**
	 * The thread for the medium training
	 */
	public static Thread THREAD_MEDIUM;
	
	/**
	 * The thread for the hard training
	 */
	public static Thread THREAD_HARD;
	
}
