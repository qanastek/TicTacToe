package fr.univavignon.ceri.application.JavaIA;

import java.util.Arrays;

public class NN {
  
	static double[][] X;
	static double[][] Y;
	static double[][] W1;
	static double[][] W2;
	static double[][] b1;
	static double[][] b2;

	static double[][] A1;
	static double[][] A2;
	
	static double[][] dW1;
	static double[][] db1;
	static double[][] dW2;
	static double[][] db2;
	
	static int m;
	
    public static void main(String[] args) {
    	
    	GenerateFromData.main(args);
    	
    	NN neuralTest = new NN();
    	
    	neuralTest.train(50000,0.7);

    	
    	//[0.5, 0.0, 0.5, 0.0, 0.0, 1.0, 0.5, 0.5, 0.0]
    	//[0.5, 0.0, 1.0, 0.0, 0.0, 1.0, 0.5, 0.5, 0.0]
    	
    	//2
    	 double[] inputTest = {1.0, 1.0, 0.5, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0}; 
         
         System.out.println("index "+Arrays.toString(neuralTest.inputToIndex(inputTest)));
    	
    }
    
    public NN() {
    	X = GenerateFromData.dataInput;
        Y = GenerateFromData.dataOutput;

        m = (int) GenerateFromData.lineCount;
        int nodes = 9;

        X = np.T(X);
        
        Y = np.T(Y);

        W1 = np.random(nodes, 9);
        b1 = new double[nodes][m];

        W2 = np.random(1, nodes);
        b2 = new double[1][m];
    }
    
    public static void forward(double[][]input) {
    	
        double[][]Z1 = np.add(np.dot(W1, input), b1);
        A1 = np.sigmoid(Z1);

        double[][]Z2 = np.add(np.dot(W2, A1), b2);
        A2 = np.sigmoid(Z2);
    }
    
    public static void backPropagate() {
        //LAYER 2
        double[][] dZ2 = np.subtract(A2, Y);
        dW2 = np.divide(np.dot(dZ2, np.T(A1)), m);
        db2 = np.divide(dZ2, m);

        //LAYER 1
        double[][] dZ1 = np.multiply(np.dot(np.T(W2), dZ2), np.subtract(A1, np.power(A1, 2)));
        dW1 = np.divide(np.dot(dZ1, np.T(X)), m);
        db1 = np.divide(dZ1, m);
    }
    
    public static void gradientDescent(double d) {
    	
    	W1 = np.subtract(W1, np.multiply(d, dW1));
        b1 = np.subtract(b1, np.multiply(d, db1));

        W2 = np.subtract(W2, np.multiply(d, dW2));
        b2 = np.subtract(b2, np.multiply(d, db2));
    }
    
    public void train(int nbCycles,double learningRate) {
        for (int i = 0; i < nbCycles; i++) {
            
        	NN.forward(X); 
       
            double cost = np.cross_entropy(m, Y, A2);
        	
        	NN.backPropagate();
        	
        	if (i % 5000 ==0) {
        		System.out.println("cost : "+cost);
        	}
        	
            NN.gradientDescent(learningRate);
            
        }
    }
    
    public static int compare(double[] input,double[] output) {
    	
    	for (int i = 0; i < input.length; i++) {
			if (input[i]==0.5 && input[i]!=output[i]) {
				return i;
			}
		}
    	return 0;
    }
    
    public int[] inputToIndex(double[]boardInput) {
    	
    	double[][] test =  new double [9][9];
    	 
        for (int j = 0; j < test.length; j++) {
			test[j]=boardInput;
		}
    	
    	 NN.forward(test);
    	 
    	 System.out.println("A2 "+Arrays.toString(A2[0]));
         
         double [] output = A2[0];
         
         for (int j = 0; j < output.length; j++) {
 			output[j] =  Math.round(output[j] * 2) / 2.0;
 		 }
         
         System.out.println("Neural Network : ouput "+Arrays.toString(output));
         
         System.out.println(NN.compare(boardInput,output));
         
         int[] res = new int[2];
         
         res[0]=compare(boardInput,output)/3;
         res[1]=compare(boardInput,output)%3;
         
         return res;
    }
}