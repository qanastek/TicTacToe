package fr.univavignon.ceri.application.JavaIA;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Decode the data-set for the training
 * @author Vougeot Valentin
 * @author Yanis Labrak
 */

public class GenerateFromData {
	
	
	static Path path = Paths.get("data.txt");
	
	public static double[][] dataInput;
	public static double[][] dataOutput;
	public static long lineCount;
	
	
	public static void GenerateDataset() {
		
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(
					"data.txt"));
			String line = reader.readLine();
			
			int incr=0;
			while (line != null) {
				
				String[] cArray = line.split(",");
				
				for (int i = 0; i < cArray.length-1; i++) {
					
					//System.out.println(" "+cArray[i]);
					switch(cArray[i]) {
					
					case "x":
						dataInput[incr][i]=1;
						break;
						
					case "o":
						dataInput[incr][i]=0;
						break;
						
					case "d":
						dataInput[incr][i]=0.5;
						break;	
					
					}
				}	
				
				incr++;			
				
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		BufferedReader reader2;
		try {
			reader2 = new BufferedReader(new FileReader(
					"dataOut.txt"));
			String line = reader2.readLine();
			
			int incr=0;
			while (line != null) {
				
				String[] cArray = line.split(",");
				
				for (int i = 0; i < cArray.length-1; i++) {
					
					//System.out.println(" "+cArray[i]);
					switch(cArray[i]) {
					
					case "x":
						dataOutput[incr][i]=1;
						break;
						
					case "o":
						dataOutput[incr][i]=0;
						break;
						
					case "d":
						dataOutput[incr][i]=0.5;
						break;	
					
					}
				}	
				
				incr++;			
				
				line = reader2.readLine();
			}
			reader2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		try {
			lineCount = Files.lines(path).count();
			
			dataInput= new double [(int) lineCount][9];
			dataOutput= new double [(int) lineCount][9];

			GenerateDataset();
			
			for (int i = 0; i < dataOutput.length; i++) {
				for (int j = 0; j < dataOutput[i].length; j++) {
					System.out.print(dataOutput[i][j]+" ");
				}
				System.out.println();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
	
	

