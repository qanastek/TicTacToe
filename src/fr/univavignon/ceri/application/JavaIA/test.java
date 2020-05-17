package fr.univavignon.ceri.application.JavaIA;

import java.io.BufferedWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Obsolete test class that was used to compare the output of the neural network to get its accuracy 
 * @author Vougeot Valentin
 * 
 */

public class test {
	public static void main(String[] args) {
		test.testAccuracy(args);
	}
	
	public static void testAccuracy(String []args) {
		GenerateFromData.main(args);
		
		double[][] X = GenerateFromData.dataInput;
        double[][] Y = GenerateFromData.dataOutput;
        double[][] Y2 = GenerateFromData.dataOutput2;
        
        int m = (int) GenerateFromData.lineCount;
        
        
        NN neuralTest = new NN();
    	
    	neuralTest.train(50000,0.3);
    	
    	int incr = 0;
    	
    	for (int i = 0; i < X.length; i++) {
    		
    		System.out.println(Arrays.toString(X[i]));
    		System.out.println(Arrays.toString(Y[i]));
    		System.out.println(neuralTest.inputToIndex(X[i]));
			if(Y2[i][0]==(neuralTest.inputToIndex(X[i])[0]*3+neuralTest.inputToIndex(X[i])[1])) {
				incr++;
			}
		}
        
    	System.out.println("Accuracy = "+(incr/m*100));
    	
    	System.out.println(Arrays.toString(X[7]));
    	
    	System.out.println(Arrays.toString(Y[7]));
    	
    	System.out.println(neuralTest.inputToIndex(X[7]));
    	
	}
	
	
}
