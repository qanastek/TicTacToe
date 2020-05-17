/**
 * 
 */
package fr.univavignon.ceri.application.ai;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import fr.univavignon.ceri.application.config.Settings;

/**
 * Generate the data-set for the training
 * @author Valentin Vougeot
 */
public class GenerateDataset {
	
	/**
	 * Input data
	 */
	public static String INPUT_DATA = "";
	
	/**
	 * Output
	 */
	public static String OUTPUT_DATA = "";
	
	/**
	 * Number of tiles
	 */
	public static int NBR_TILES = 9;
	
	/**
	 * Max index
	 */
	public static int MAX_INDEX = NBR_TILES-1;
	
	/**
	 * Empty constructor
	 */
	public GenerateDataset() {
		super();
	}
	
	/**
	 * This method generate a data-set of winning games  
	 * @param n {@code Integer} Number of winning games to generates
	 */
	public static void generate(int n) {
		
		// Current wins founded
		int winNb = 0;
		
		// Generate until we have n winning games
		while (winNb < n) {
			
			/**
			 * Chronological hits history
			 * 0: Not used
			 * 1: Used
			 */
			List<Integer> board = new ArrayList<Integer>();
			
			/**
			 * Chronological board history for each hit of the game
			 */
			List<List<Integer>> game = new ArrayList<List<Integer>>();
			
			// Initialize the current board
			for (int i = 0; i < NBR_TILES; i++) { board.add(0);	}
			
			// Current number of hit
			int hitsCounter = 0;
			
			// Current player
			int currentPlayer = 1;
			
			// If somebody can win
			while (hitsCounter < 5 || hasWinner(board) == 0 && hitsCounter < NBR_TILES) {
			
				// Get the random instance
				Random rand = new Random();

				// Generate the random position
				int indexHit = rand.nextInt(MAX_INDEX - 0 + 1) + 0;
				
				// Generate a position until the tile wasn't already used
				while (board.get(indexHit) != 0) {
					
					// Generate a new random number between 0 and 8
					indexHit = rand.nextInt(MAX_INDEX - 0 + 1) + 0;					
				}
				
				// Set the turn of the current played tile
				board.set(indexHit,currentPlayer);
				
				// Save the board
				game.add(board);
				
				// Change the current player
				currentPlayer *= -1;
				
				// Increase the number of hits
				hitsCounter++;
			}
			
			// Check if the X win
			if (hasWinner(board) == 1) {
				
				adaptData(game,1);
				winNb+=1;
			}
			else if (hasWinner(board)==-1) {
				
				adaptData(game,-1);
				winNb+=1;
			}
			
			//display(game, board);
			
		}
		
		sendData();
	}

	/**
	 * Check if there is a winner in this tic-tac-toe board.
	 * @param {@code Board} The current board state 
	 * @return
	 * 0:  Draw
	 * <br>
	 * 1:  X Win
	 * <br>
	 * -1: Y Win
	 */
	public static int hasWinner(List<Integer> board) {
		
		final int[] START = new int[] { 0, 3, 6, 0, 1, 2, 0, 2 };
		final int[] INCR  = new int[] { 1, 1, 1, 3, 3, 3, 4, 2 };
		
		final int SIZE = 3;
		
		// For each row
	    for (int i = 0; i < START.length; i++) {
	    	
	        int sum = 0;

			// For each column
	        for (int j = 0; j < SIZE; j++) {
	            sum += board.get(START[i] + j * INCR[i]);
	        }
	        
	        if (Math.abs(sum) == SIZE) {
	            return sum / SIZE;
	        }
	    }
	    
	    return 0;
	}
	
	/**
	 * Convert the winner to X, the looser to O and empty tile to D
	 * @param game {@code List<List<Integer>>} The board
	 * @param winner {@code Integer} The winner
	 */
	public static void adaptData(List<List<Integer>> game, int winner){
		
		/**
		 * For each board of the game add the hit to the input file
		 */
		for (int i = 0; i < game.size()-1; i++) {

			// For each tiles			
			for (int j = 0; j < game.get(i).size(); j++) {
				
				// The current tile
				int tile = game.get(i).get(j);
			
				/**
				 * Set the winner to X
				 */
				if (winner == 1) {
					
					switch (tile) {
					
						case 1:
							INPUT_DATA += Settings.CROSS;
							break;
							
						case -1:
							INPUT_DATA += Settings.CIRCLE;
							break;
							
						case 0:	
							INPUT_DATA += Settings.BLANK;
							break;
					}	
				}
				else if(winner==-1) {
					
					switch (tile) {
					
						case -1:
							INPUT_DATA += Settings.CROSS;
							break;
							
						case 1:
							INPUT_DATA += Settings.CIRCLE;
							break;
							
						case 0:	
							INPUT_DATA += Settings.BLANK;
							break;
					}
				}
				
				// Add a comma between hits
				if (j < game.get(i).size()-1) {
					INPUT_DATA += ",";
				}
			}
			
			// Jump line between boards
			INPUT_DATA += "\n";
		}
		
		/**
		 * For each board of the game add the hit to the output file
		 */
		for (int i = 1; i < game.size(); i++) {

			// For each tiles
			for (int j = 0; j < game.get(i).size(); j++) {

				// The current tile
				int tile = game.get(i).get(j);

				/**
				 * Set the winner to X
				 */
				if (winner == 1) {
					
					switch (tile) {
					
						case 1:
							OUTPUT_DATA += Settings.CROSS;
							break;
							
						case -1:
							OUTPUT_DATA += Settings.CIRCLE;
							break;
							
						case 0:	
							OUTPUT_DATA += Settings.BLANK;
							break;
					}	
				}
				else if(winner==-1) {
					
					switch (tile) {
					
						case -1:
							OUTPUT_DATA += Settings.CROSS;
							break;
							
						case 1:
							OUTPUT_DATA += Settings.CIRCLE;
							break;
							
						case 0:	
							OUTPUT_DATA += Settings.BLANK;
							break;
					}
				}

				// Add a comma between hits
				if (j < game.get(i).size()-1) {
					OUTPUT_DATA += ",";
				}
			}

			// Jump line between boards
			OUTPUT_DATA += "\n";
		}
	}
	
	/**
	 * Write the data in both files
	 */
	public static void sendData() {

		System.out.println("File content: " + INPUT_DATA);
		
		// Create a buffer
		BufferedWriter writer;
		
		/**
		 * Write the input data into the file
		 */
		try {
			writer = new BufferedWriter(new FileWriter(Settings.INPUT_FILE));
			writer.write(INPUT_DATA);
		    writer.close();
		    System.out.println("Finish the generation of the input!");
		    
		} catch (IOException e) {
			e.printStackTrace();
		}

		/**
		 * Write the output data into the file
		 */
		try {
			writer = new BufferedWriter(new FileWriter(Settings.OUTPUT_FILE));
			writer.write(OUTPUT_DATA);
		    writer.close();
		    System.out.println("Finish the generation of the output!");
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Display the board matrix
	 * @param game {@code List<List<Integer>>}
	 */
	public static void display(List<List<Integer>> game, List<Integer> board) {
		
		for (int i = 0; i < game.size(); i++) {
			
			int l = 0;
			
			System.out.println("[");

	        // Line
	        for (int j = 0; j < 3; j++) {

	            // Column
	            for (int k = 0; k < 3; k++) {

	                System.out.print(game.get(i).get(l) + "  ");
	                ++l;
	            }

	            // Jump line
	            System.out.println("");
	        }
	        
	        System.out.println("]");
	        
	        System.out.println("--------------------------------");
			
		} 
		System.out.println(hasWinner(board));
		
	}	
}
