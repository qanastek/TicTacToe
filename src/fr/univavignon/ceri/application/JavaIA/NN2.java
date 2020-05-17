package fr.univavignon.ceri.application.JavaIA;

import java.util.Arrays;

/**
 * Obsolete neural network class that was used in an early version before getting completely reworked into NN.java
 * @author Vougeot Valentin
 * 
 */
public class NN2 {
	
	public static void main(String[] args) {
		

	    double[][] W1;
	    double[][] b1;

	    double[][] W2;
	    double[][] b2;
	    
	    double[][] Z1;
	    double[][] A1;
	    
	    double[][] Z2;
	    double[][] A2;
		  
		GenerateFromData.main(args);
    	
		double[][] X = GenerateFromData.dataInput;
		double[][] Y = GenerateFromData.dataOutput2;

        int m = (int) GenerateFromData.lineCount;
        int nodes = 9;

        X = np.T(X);
             
        Y = np.T(Y);

        W1 = np.random(nodes, 9);
        b1 = new double[nodes][m];

        W2 = np.random(1, nodes);
        b2 = new double[1][m];

        for (int i = 0; i < 50000; i++) {
            // LAYER 1
            Z1 = np.add(np.dot(W1, X), b1);
            A1 = np.sigmoid(Z1);

            //LAYER 2
            Z2 = np.add(np.dot(W2, A1), b2);
            A2 = np.sigmoid(Z2);

            double cost = np.cross_entropy(m, Y, A2);
            //costs.getData().add(new XYChart.Data(i, cost));
         
            // Back Prop
            //LAYER 2
            double[][] dZ2 = np.subtract(A2, Y);
            double[][] dW2 = np.divide(np.dot(dZ2, np.T(A1)), m);
            double[][] db2 = np.divide(dZ2, m);

            //LAYER 1
            double[][] dZ1 = np.multiply(np.dot(np.T(W2), dZ2), np.subtract(A1, np.power(A1, 2)));

            // double[][] dZ1 = np.multiply(np.dot(np.T(W2), dZ2), np.subtract(1.0, np.power(A1, 2)));
            double[][] dW1 = np.divide(np.dot(dZ1, np.T(X)), m);
            double[][] db1 = np.divide(dZ1, m);

            // G.D
            W1 = np.subtract(W1, np.multiply(0.1, dW1));
            b1 = np.subtract(b1, np.multiply(0.1, db1));

            W2 = np.subtract(W2, np.multiply(0.1, dW2));
            b2 = np.subtract(b2, np.multiply(0.1, db2));

            if (i % 4000 == 0) {
                System.out.println("==============");
                System.out.println("Cost = " + cost);
                System.out.println("Predictions = " + Arrays.deepToString(A2));
            }
             
        }
        double[]boardInput = {0.5, 0.0, 0.5, 0.0, 0.0, 1.0, 0.5, 0.5, 0.0};
        
        double[][] test =  new double [9][9];
   	 
        for (int j = 0; j < test.length; j++) {
			test[j]=boardInput;
		}
        System.out.println("Predictions = " + Arrays.deepToString(test));
        
        Z1 = np.add(np.dot(W1, test), b1);
        A1 = np.sigmoid(Z1);

        //LAYER 2
        Z2 = np.add(np.dot(W2, A1), b2);
        A2 = np.sigmoid(Z2);
        System.out.println("Predictions = " + Arrays.deepToString(A2));
     } 
        

}
