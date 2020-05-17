package fr.univavignon.ceri.application.JavaIA;

import java.util.Arrays;

/**
 * Neural Network class with all the function to make the neural network works
 * @author Vougeot Valentin
 * 
 * 
 * In the first finish version the neural network was using an input board state represented by a 1*9 matrix to get an output board state (1*9 matrix) containing one more move 
 * In this version the neural network use an input board state to get the index divised by 10 at witch position it would play
 * 
 * the index is divise by 10 in dataOut2.txt because the index is between 0 and 9 but the neural network use the sigmoid activation function so the output must be contained between 0.0 and 1.0
 */

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
    	
    	/*
    	 * Use The GenerateFromData class to adapt the data from data.txt and dataOut.txt and generate dataOut2.txt
    	 */
    	GenerateFromData.main(args);
    	
    	NN neuralTest = new NN();
    	
    	neuralTest.train(50000,0.3);
    	
    	//x,o,o,d,o,d,x,x,d
    	//[1.0, 0.0, 0.0, 0.5, 0.0, 0.5, 1.0, 1.0, 0.5]
    	//5

    	
    	//[0.5, 0.0, 0.5, 0.0, 0.0, 1.0, 0.5, 0.5, 0.0]
    	//[0.5, 0.0, 1.0, 0.0, 0.0, 1.0, 0.5, 0.5, 0.0]
    	
    	//2
    	 double[] inputTest = {1.0, 0.0, 0.0, 0.5, 0.0, 0.5, 1.0, 1.0, 0.5}; 
         
         System.out.println("index "+Arrays.toString(neuralTest.inputToIndex(inputTest)));
    	
    }
    /*
     * create a neural network from data.txt and dataOut2.txt
     */
    public NN() {
    	X = GenerateFromData.dataInput;
        Y = GenerateFromData.dataOutput2;

        m = (int) GenerateFromData.lineCount;
        int nodes = 3;

        X = np.T(X); //421*9
        
        Y = np.T(Y); //421*1

        W1 = np.random(nodes, 9); //9*9
        b1 = new double[nodes][m]; //421*9

        W2 = np.random(1, nodes); 
        b2 = new double[1][m]; 
    }
    
    /* Function that use the neural network to get an answer for the input 
     * @param input {@code double [][]} the  input matrix to process trough the neural network
     */
    public static void forward(double[][]input) {
    	
        double[][]Z1 = np.add(np.dot(W1, input), b1);
        A1 = np.sigmoid(Z1);
        
        double[][]Z2 = np.add(np.dot(W2, A1), b2);
        A2 = np.sigmoid(Z2);
    }
    
    /* Function that recalculate the weights of the neural network when you train it
     */
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
    
    /*
     * Function use the minimize the cost of the neural network using the learning rate
     * @param d {@code double} value of the learning rate 
     */
    public static void gradientDescent(double d) {
    	
    	W1 = np.subtract(W1, np.multiply(d, dW1));
        b1 = np.subtract(b1, np.multiply(d, db1));

        W2 = np.subtract(W2, np.multiply(d, dW2));
        b2 = np.subtract(b2, np.multiply(d, db2));
    }
    
    /*
     * Function that train the neural network to get the optimal value of weights
     * @param nbCycles {@code int} the number of cycles (epoch) you want the neural network to do 
     * @param learningRate {@code double} the learning rate of the neural network
     */
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
    
    /*
     * Obsolete function that was use to compare the input with the output to get the index at witch the move was played
     * @param input {@code double[]} list of 9 double who represent a board state as input
     * @param output {@code double[]} list of 9 double who represent a board state as output
     * @return int who represent the index of the move 
     */
    public static int compare(double[] input,double[] output) {
    	
    	for (int i = 0; i < input.length; i++) {
			if (input[i]==0.5 && input[i]!=output[i]) {
				return i;
			}
		}
    	return 0;
    }
    
    
    /*
     * Function that use the neural network to get the best move it can do with the input board state 
     * @param boardInput {@code double[]} list of 9 double who represent a board state as input
     * @return res {@code int[]} the position (x,y) of the move from the neural network 
     */
    public int[] inputToIndex(double[]boardInput) {
    	
    	double[][] test =  new double [9][1];
    	 
        for (int j = 0; j < test.length; j++) {
			test[j][0]=boardInput[j];
		}
    	
    	 NN.forward(test);
    	 
    	 System.out.println("A2 length"+A2.length+"*"+A2[0].length);
    	 
    	 System.out.println("A2 "+Arrays.toString(A2[0]));
         
         double[] output = A2[0];
         
         
         for (int j = 0; j < output.length; j++) {
 			output[j] =  Math.round(output[j] *10 * 2) / 2.0;
 		 }
         
         int[] res = new int[2];
         
         res[0]=(int) (output[0]/3);
         res[1]=(int) (output[0]%3);
         System.out.println("Neural Network : ouput "+Arrays.toString(output));
         
//         Obsolete part that was used with an early version of the neural network
//         System.out.println(NN.compare(boardInput,output));
//         
//         int[] res = new int[2];
//         
//         res[0]=compare(boardInput,output)/3;
//         res[1]=compare(boardInput,output)%3;
         
         return res;
    }
}