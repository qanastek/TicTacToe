package fr.univavignon.ceri.application.ai;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import fr.univavignon.ceri.application.config.Settings;

/**
 * Decode the data-set for the training
 * @author Vougeot Valentin
 */
public class Dataset {

	// Input file
	public File FILE = new File(Settings.INPUT_FILE);
	
	public Path path = Paths.get(Settings.INPUT_FILE);
	
	public double[][] dataInput;
	public double[][] dataOutput;
	public double[][] dataOutput2;
	
	// Line counter
	public long lineCount;
	
	/**
	 * Constructor
	 */
	public Dataset() {
		
		try {
			
			lineCount = Files.lines(path).count();
			
			dataInput= new double [(int) lineCount][9];
			dataOutput= new double [(int) lineCount][9];
			dataOutput2= new double [(int) lineCount][1];
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// Check if the data-set isn't already generated
		if (!FILE.exists()) {

			// Generate the data-set
			GenerateDataset.generate(70);
		}
		
		/**
		 * Load the inputs
		 */
		BufferedReader bufferInput;
		try {
			
			// Open the stream
			bufferInput = new BufferedReader(new FileReader(Settings.INPUT_FILE));
			
			// Read the first line
			String line = bufferInput.readLine();
			
			// Number of boards
			int boardCounter = 0;
			
			// For each boards
			while (line != null) {
				
				// Convert the board
				String[] board = line.split(",");
				
				// For each tiles
				for (int i = 0; i < board.length-1; i++) {
					
					/**
					 * Convert the tiles
					 * 1: Winner
					 * 0: Looser
					 * 0.5: Draw
					 */
					switch(board[i]) {
					
						case "x":
							dataInput[boardCounter][i] = 1;
							break;
							
						case "o":
							dataInput[boardCounter][i] = 0;
							break;
							
						case "b":
							dataInput[boardCounter][i] = 0.5;
							break;	
					
					}
				}	
				
				boardCounter++;			
				
				// Read the next board
				line = bufferInput.readLine();
			}
			
			// Close the stream
			bufferInput.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		/**
		 * Load the output
		 */
		BufferedReader bufferOutput;
		try {

			// Open the stream
			bufferOutput = new BufferedReader(new FileReader(Settings.OUTPUT_FILE));

			// Read the first line
			String line = bufferOutput.readLine();

			// Number of boards
			int boardCounter = 0;

			// For each boards
			while (line != null) {

				// Convert the board
				String[] board = line.split(",");

				// For each tiles
				for (int i = 0; i < board.length-1; i++) {

					/**
					 * Convert the tiles
					 * 1: Winner
					 * 0: Looser
					 * 0.5: Draw
					 */
					switch(board[i]) {
						
						case "x":
							dataOutput[boardCounter][i] = 1;
							break;
							
						case "o":
							dataOutput[boardCounter][i] = 0;
							break;
							
						case "b":
							dataOutput[boardCounter][i] = 0.5;
							break;						
					}
				}	
				
				boardCounter++;			
				
				// Read the next board
				line = bufferOutput.readLine();
			}
			
			// Close the stream
			bufferOutput.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		float temp = 0;
        
        int[] playedHitIndex = new int[(int)lineCount];
        
        /*
         * Find the played hit between both boards
         */
        // For each board
        for (int i = 0; i < dataInput.length; i++) {
        	
        	// For each hit
			for (int j = 0; j < dataInput[i].length; j++) {
				
				// If different
				if(dataInput[i][j] != dataOutput[i][j]) {
					
					// Keep the index of the played hit for this board 
					playedHitIndex[i] = j;
				}
			}
		}
        
        /**
         * Normalize values between 0 and 1
         */
        for (int i = 0; i < playedHitIndex.length; i++) {
        	
        	// Normalize
        	temp = (float) playedHitIndex[i]/10;
        	
        	// Add it
			dataOutput2[i][0] = temp;
		}
        
        System.out.println("Size: " + dataOutput2.length);
	}
}
	
	

