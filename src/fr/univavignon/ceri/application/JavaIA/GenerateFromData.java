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

import javax.rmi.CORBA.Stub;

/**
 * Decode the data-set for the training
 * @author Vougeot Valentin
 */

public class GenerateFromData {
	
	
	static Path path = Paths.get("data.txt");
	
	public static double[][] dataInput;
	public static double[][] dataOutput;
	public static double[][] dataOutput2;
	public static long lineCount;
	
	/**
	 * Main class who read the data text file
	 */
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
		
		String FILE_CONTENT = "";
		
		float temp = 0;
        
        int[] indexList = new int[(int)lineCount];
        
        /*
         * look at the difference between input and output data to extract the index of the move and write it in dataOut2.txt
         */
        for (int i = 0; i < dataInput.length; i++) {
			for (int j = 0; j < dataInput[i].length; j++) {
				if(dataInput[i][j]!=dataOutput[i][j]) {
					indexList[i]=j;
				}
			}
		}
        
        System.out.println("test "+Arrays.toString(indexList)+" "+indexList.length);
        
        for (int i = 0; i < indexList.length; i++) {
        	temp=(float)indexList[i]/10;
        	FILE_CONTENT+= temp;
			FILE_CONTENT+="\n";
		}
        
        System.out.println(FILE_CONTENT.length());
        BufferedWriter writer;
       
		
		try {
			writer = new BufferedWriter(new FileWriter("dataOut2.txt"));
			writer.write(FILE_CONTENT);
		    writer.close();
		    System.out.println("Finished");
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		BufferedReader reader3;
		try {
			reader3 = new BufferedReader(new FileReader(
					"dataOut2.txt"));
			String line = reader3.readLine();
			
			int incr=0;
			while (line != null && incr<lineCount) {
				//System.out.println(incr);
				dataOutput2[incr][0]=new Double(line);
				incr++;
			}
			reader3.readLine();
			reader3.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public static void main(String[] args) {
		
		try {
			lineCount = Files.lines(path).count();
			
			
			dataInput= new double [(int) lineCount][9];
			dataOutput= new double [(int) lineCount][9];
			dataOutput2= new double [(int) lineCount][1];

			GenerateDataset();
			
//			for (int i = 0; i < dataOutput.length; i++) {
//				for (int j = 0; j < dataOutput[i].length; j++) {
//					System.out.print(dataOutput[i][j]+" ");
//				}
//				System.out.println();
//			}
			
//			System.out.println(dataOutput.length);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
	
	

