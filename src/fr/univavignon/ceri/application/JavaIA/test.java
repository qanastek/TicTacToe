package fr.univavignon.ceri.application.JavaIA;

import java.util.Arrays;
import java.util.StringTokenizer;

public class test {
	public static void main(String[] args) {
		test.testAccuracy(args);
	}
	
	public static void testAccuracy(String []args) {
		GenerateFromData.main(args);
		
		double[][] X = GenerateFromData.dataInput;
        double[][] Y = GenerateFromData.dataOutput;
        
        int m = (int) GenerateFromData.lineCount;
        
        int[] indexList = new int[m];
        
        for (int i = 0; i < X.length; i++) {
			for (int j = 0; j < X[i].length; j++) {
				if(X[i][j]!=Y[i][j]) {
					indexList[i]=j;
				}
			}
		}
        
        System.out.println("test "+Arrays.toString(indexList));
        
        NN neuralTest = new NN();
    	
    	neuralTest.train(50000,0.3);
    	
    	int incr = 0;
    	
    	for (int i = 0; i < X.length; i++) {
    		
    		System.out.println(Arrays.toString(X[i]));
    		System.out.println(Arrays.toString(Y[i]));
    		System.out.println(neuralTest.inputToIndex(X[i]));
			if(indexList[i]==neuralTest.inputToIndex(X[i])) {
				incr++;
			}
		}
        
    	System.out.println("Accuracy = "+(incr/m*100));
    	
    	System.out.println(Arrays.toString(X[7]));
    	
    	System.out.println(Arrays.toString(Y[7]));
    	
    	System.out.println(neuralTest.inputToIndex(X[7]));
    	
	}
	
	
}
