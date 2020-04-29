/**
 * 
 */
package fr.univavignon.ceri.application.services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Generate the data-set for the training
 * @author Vougeot Valentin
 * @author Yanis Labrak
 */
public class GenerateDataset {
	
	/**
	 * File content
	 */
	public static String FILE_CONTENT = "";
	public static String FILE_CONTENT_OUT = "";
	
	/**
	 * This method generate a data-set of winning games  
	 * @param n {@code Integer} 
	 */
	public static void Generate(int n) {
		
		int winNb = 0;
		
		// Generate n games
		while (winNb < n) {
			
			List<Integer> board = new ArrayList<Integer>();
			
			List<List<Integer>> game = new ArrayList<List<Integer>>();
			
			// Initialize the array
			for (int i = 0; i < 9; i++) { board.add(0);	}
			
			int incr = 0;
			
			int tour = 1;
			
			// If he can win
			while (incr < 5 || hasWinner(board)==0 && incr < 9) {
				
				List<Integer> boardTemp = new ArrayList<Integer>(board);
			
				Random rand = new Random(); 
				int randNumber = rand.nextInt(8 - 0 + 1) + 0;
				
				while (boardTemp.get(randNumber)!=0) {
					
					randNumber = rand.nextInt(8 - 0 + 1) + 0;
					
				}
				
				boardTemp.set(randNumber,tour);
				game.add(boardTemp);
				
				board=boardTemp;
				
				tour=tour*-1;
				incr+=1;
			}
			
			// If the player 
			if (hasWinner(board)==1) {
				
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
	 * Determines if there is a winner in tic-tac-toe board.
	 * @param {@code Board} is the current board state 
	 * @return {@code 0} for draw, {@code 1} for 'X', {@code -1} for 'Y'
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
	 * Change the winner to X and the looser to O
	 * @param game {@code List<List<Integer>>} The board
	 * @param winner {@code Integer} The winner
	 */
	public static void adaptData(List<List<Integer>> game, int winner){
		
		// Foreach row
		for (int i = 0; i < game.size()-1; i++) {
			
			// Foreach column
			for (int j = 0; j < game.get(i).size(); j++) {
			
				/**
				 * Put the winner with X all time
				 */
				if (winner == 1) {
					
					switch (game.get(i).get(j)) {
					
						case 1:
							FILE_CONTENT+="x";
							break;
							
						case -1:
							FILE_CONTENT+="o";
							break;
							
						case 0:	
							FILE_CONTENT+="d";
							break;
					}	
				}
				else if(winner==-1) {
					
					switch (game.get(i).get(j)) {
					
						case -1:
							FILE_CONTENT+="x";
							break;
							
						case 1:
							FILE_CONTENT+="o";
							break;
							
						case 0:	
							FILE_CONTENT+="d";
							break;
					}
				}
				
				if (j < game.get(i).size()-1) {
					FILE_CONTENT += ",";
				}
			}
			
			FILE_CONTENT += "\n";
		}
		
		
		
		for (int i = 1; i < game.size(); i++) {
			
			// Foreach column
			for (int j = 0; j < game.get(i).size(); j++) {
			
				/**
				 * Put the winner with X all time
				 */
				if (winner == 1) {
					
					switch (game.get(i).get(j)) {
					
						case 1:
							FILE_CONTENT_OUT+="x";
							break;
							
						case -1:
							FILE_CONTENT_OUT+="o";
							break;
							
						case 0:	
							FILE_CONTENT_OUT+="d";
							break;
					}	
				}
				else if(winner==-1) {
					
					switch (game.get(i).get(j)) {
					
						case -1:
							FILE_CONTENT_OUT+="x";
							break;
							
						case 1:
							FILE_CONTENT_OUT+="o";
							break;
							
						case 0:	
							FILE_CONTENT_OUT+="d";
							break;
					}
				}
				
				if (j < game.get(i).size()-1) {
					FILE_CONTENT_OUT += ",";
				}
			}
			
			FILE_CONTENT_OUT += "\n";
		}
		
//		FILE_CONTENT += "\n";
	}
	
	public static void sendData() {

		System.out.println("File content: " + FILE_CONTENT);
		
		BufferedWriter writer;
		
		BufferedWriter writer2;
		
		try {
			writer = new BufferedWriter(new FileWriter("data.txt"));
			writer.write(FILE_CONTENT);
		    writer.close();
		    System.out.println("Finished");
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			writer = new BufferedWriter(new FileWriter("dataOut.txt"));
			writer.write(FILE_CONTENT_OUT);
		    writer.close();
		    System.out.println("Finished2");
		    
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

	        // Line
			
			System.out.println("[");
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
	
	public static void main(String[] args) {
		
		List<Integer> board = new ArrayList<Integer>(Arrays.asList(
			 1, 0, 1,
			 0, 0, 0,
			-1,-1,-1 
		));
		System.out.println("Winner: " + GenerateDataset.hasWinner(board));
		
		GenerateDataset.Generate(70);
	}
	
}
