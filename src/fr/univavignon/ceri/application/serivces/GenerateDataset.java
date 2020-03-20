/**
 * 
 */
package fr.univavignon.ceri.application.serivces;

import fr.univavignon.ceri.application.config.Settings;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
/**
 * @author Yanis Labrak
 *
 */
public class GenerateDataset {
	
	public static String FILE_CONTENT="";
	
	/**
	 * This method generate a dataset of winning games  
	 */
	public static void Generate(int n) {
		
		int winNb = 0;
		
		while (winNb<n) {
			List<Integer> board = new ArrayList<Integer>();
			
			List<List<Integer>> game = new ArrayList<List<Integer>>();
			
			for (int i = 0; i < 9; i++) {
				
				board.add(0);	
			}
			
			int incr = 0;
			
			int tour = 1;
			
			while (incr<5 || hasWinner(board)==0 && incr < 9) {
				
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
			
			if (hasWinner(board)==1) {
				
				adaptData(game,1);
				winNb+=1;
			}
			
			else if (hasWinner(board)==-1) {
				
				adaptData(game,-1);
				winNb+=1;
			}
			
		}
		
		sendData();
			
////	Debug		
//		for (int i = 0; i <game.size(); i++) {
//			
//			int l = 0;
//
//	        // Line
//			
//			System.out.println("[");
//	        for (int j = 0; j < 3; j++) {
//
//	            // Column
//	            for (int k = 0; k < 3; k++) {
//
//	                System.out.print(game.get(i).get(l) + "  ");
//	                ++l;
//	            }
//
//	            // Jump line
//	            System.out.println("");
//	        }
//	        System.out.println("]");
//	        System.out.println("--------------------------------");
//			
//		} 
//		System.out.println(hasWinner(board));
		
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
		
	    for (int i = 0; i < START.length; i++) {
	        int sum = 0;
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
	 * 
	 * @param game
	 * @param winner
	 */
	public static void adaptData(List<List<Integer>> game,int winner){
		
		for (int i = 0; i < game.size(); i++) {
			
			for (int j = 0; j < game.get(i).size(); j++) {
			
				if (winner==1) {
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
				FILE_CONTENT+=";";
			}
			
			FILE_CONTENT+="\n";
			
		}
		
		FILE_CONTENT+="\n";
		
		
	
	}
	
	public static void sendData() {

		System.out.println(FILE_CONTENT);
		
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter("src\\fr\\univavignon\\ceri\\application\\serivces\\data.txt"));
			writer.write(FILE_CONTENT);
		    writer.close();
		    System.out.println("Finished");
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		
		
		List<Integer> board=new ArrayList<Integer>(Arrays.asList(
			 1, 0, 1,
			 0, 0, 0,
			-1,-1,-1 
		));
	
		GenerateDataset d= new GenerateDataset();
		
		System.out.println(GenerateDataset.hasWinner(board));
		
		GenerateDataset.Generate(1000);
		
	}
	
}
