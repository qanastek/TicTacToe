package fr.univavignon.ceri.application.ai;

import java.util.Arrays;

import javafx.concurrent.Task;
import javafx.util.Pair;

/**
 * Neural Network (train and predict)
 * @author Valentin Vougeot
 */
public class NeuralNetwork extends Task<Void> {
  
	// Input data
	static double[][] X;
	
	// Output data
	static double[][] Y;
	
	// Weights
	static double[][] W1;
	static double[][] W2;
	
	// Biais
	static double[][] B1;
	static double[][] B2;

	// Result of the first layer
	static double[][] A1;
	static double[][] A2;
	
	// Back propagation weights
	static double[][] DW1;
	static double[][] DW2;
	
	// Back propagation biais
	static double[][] DB1;
	static double[][] DB2;
	
	// Dataset size
	static int DATASET_SIZE;
	
	// Learning rate
	double LEARNING_RATE = 0.5F;
	
	// Epochs
	int EPOCHS = 50000;
	
	// Nodes
	int HIDDEN_NODES = 10;
	
	// Current epoch
	public static int CURRENT_EPOCH = 0;
    
	/**
	 * Create a neural network from data.txt and dataOut2.txt
	 * @param dataSet {@code Dataset}
	 * @param epochs {@code Integer}
	 * @param learningRate {@code Float}
	 * @param hiddenNodes {@code Integer}
	 */
    public NeuralNetwork(Dataset dataSet, int epochs, double learningRate, int hiddenNodes) {
    	
    	// Get parameters
    	LEARNING_RATE = learningRate;    	
    	EPOCHS = epochs;    	
    	HIDDEN_NODES = hiddenNodes;    	
    	
    	X = dataSet.dataInput;
        Y = dataSet.dataOutput2;

        DATASET_SIZE = (int) dataSet.lineCount;

        X = np.T(X); //421*9
        
        Y = np.T(Y); //421*1

        W1 = np.random(HIDDEN_NODES, 9); //9*9
        B1 = new double[HIDDEN_NODES][DATASET_SIZE]; //421*9

        W2 = np.random(1, HIDDEN_NODES); 
        B2 = new double[1][DATASET_SIZE]; 
    }
    
    /* Function that use the neural network to get an answer for the input 
     * @param input {@code double [][]} the  input matrix to process trough the neural network
     */
    public static void forward(double[][]input) {
    	
        double[][]Z1 = np.add(np.dot(W1, input), B1);
        A1 = np.sigmoid(Z1);
        
        double[][]Z2 = np.add(np.dot(W2, A1), B2);
        A2 = np.sigmoid(Z2);
    }
    
    /* Function that recalculate the weights of the neural network when you train it
     */
    public static void backPropagate() {
        //LAYER 2
        double[][] dZ2 = np.subtract(A2, Y);
        DW2 = np.divide(np.dot(dZ2, np.T(A1)), DATASET_SIZE);
        DB2 = np.divide(dZ2, DATASET_SIZE);

        //LAYER 1
        double[][] dZ1 = np.multiply(np.dot(np.T(W2), dZ2), np.subtract(A1, np.power(A1, 2)));
        DW1 = np.divide(np.dot(dZ1, np.T(X)), DATASET_SIZE);
        DB1 = np.divide(dZ1, DATASET_SIZE);
    }
    
    /*
     * Function use the minimize the cost of the neural network using the learning rate
     * @param d {@code double} value of the learning rate 
     */
    public static void gradientDescent(double d) {
    	
    	W1 = np.subtract(W1, np.multiply(d, DW1));
        B1 = np.subtract(B1, np.multiply(d, DB1));

        W2 = np.subtract(W2, np.multiply(d, DW2));
        B2 = np.subtract(B2, np.multiply(d, DB2));
    }

    /**
     * Training
     */
	@Override
	protected Void call() throws Exception {
		
		// For each epoch
        for (CURRENT_EPOCH = 0; CURRENT_EPOCH <= EPOCHS; CURRENT_EPOCH++) {
            
        	NeuralNetwork.forward(X); 
       
            double cost = np.cross_entropy(DATASET_SIZE, Y, A2);
        	
        	NeuralNetwork.backPropagate();
    		
            updateProgress(CURRENT_EPOCH, EPOCHS);
            updateMessage(CURRENT_EPOCH + "/" + EPOCHS + " Epochs");
        	
        	if (CURRENT_EPOCH % (EPOCHS/10) == 0) {
        		System.out.println("cost : " + cost);
        	}
        	
            NeuralNetwork.gradientDescent(LEARNING_RATE);            
        }
        
		return null;
	}

	@Override
	protected void succeeded() {
		super.succeeded();
		
		System.out.println("Finished!");
        updateMessage("Finished!");
        
        double[] inputTest = {
			 1.0, 0.0, 0.0,
			 0.5, 0.0, 0.5,
			 1.0, 1.0, 0.5
		};
        
        Pair<Integer,Integer> hit = this.prediction(inputTest);
        
        System.out.println("Coordinates: " + hit.getKey() + "," + hit.getValue());
	}
    
    /*
     * Function that train the neural network to get the optimal value of weights
     * @param nbCycles {@code int} the number of cycles (epoch) you want the neural network to do 
     * @param learningRate {@code double} the learning rate of the neural network
     */
    public void train(int nbCycles,double learningRate) {
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
    public Pair<Integer,Integer> prediction(double[]boardInput) {
    	
    	double[][] test =  new double [9][1];
    	 
        for (int j = 0; j < test.length; j++) {
			test[j][0]=boardInput[j];
		}
    	
    	 NeuralNetwork.forward(test);
    	 
    	 System.out.println("A2 length"+A2.length+"*"+A2[0].length);
    	 
    	 System.out.println("A2 "+Arrays.toString(A2[0]));
         
         double[] output = A2[0];
         
         // Get the index
         for (int j = 0; j < output.length; j++) {
 			output[j] =  Math.round(output[j] * 10 * 2) / 2.0;
 		 }
                  
         // Coordinates
         Pair<Integer,Integer> res = new Pair<Integer,Integer>((int) (output[0]/3), (int) (output[0]%3));
         
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
	
    public static void main(String[] args) {
    	
//    	/*
//    	 * Generate and get the data-set
//    	 */
//    	Dataset dataSet = new Dataset();
//    	
//    	NeuralNetwork neuralTest = new NeuralNetwork(dataSet);
//    	
//    	neuralTest.train(EPOCHS,LEARNING_RATE);
//    	
//    	//x,o,o,d,o,d,x,x,d
//    	//[1.0, 0.0, 0.0, 0.5, 0.0, 0.5, 1.0, 1.0, 0.5]
//    	//5
//
//    	//[0.5, 0.0, 0.5, 0.0, 0.0, 1.0, 0.5, 0.5, 0.0]
//    	//[0.5, 0.0, 1.0, 0.0, 0.0, 1.0, 0.5, 0.5, 0.0]
//    	
//    	// Test board
//    	// Return 2
    	
    }
}