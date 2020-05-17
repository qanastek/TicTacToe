/**
 * 
 */
package fr.univavignon.ceri.application.qLearning;

import java.util.Random;

/**
 * @author Yanis Labrak
 */
public class qLearning {
//	
//    private final double alpha = 0.1; // Learning rate
//    private final double gamma = 0.9; // Eagerness - 0 looks in the near future, 1 looks in the distant future
//
//    private final int mazeWidth = 3;
//    private final int mazeHeight = 3;
//    private final int statesCount = mazeHeight * mazeWidth;
//
//    private final int reward = 100;
//    private final int penalty = -10;
//
//	/**
//	 * 
//	 */
//	public qLearning() {
//		// TODO Auto-generated constructor stub
//	}
//	
//	void printPolicy() {
//        System.out.println("\nPrint policy");
//        for (int i = 0; i < statesCount; i++) {
//            System.out.println("From state " + i + " goto state " + getPolicyFromState(i));
//        }
//	}
//	
//	int getPolicyFromState(int state) {
//		
//        int[] actionsFromState = possibleActionsFromState(state);
//
//        double maxValue = Double.MIN_VALUE;
//        int policyGotoState = state;
//
//        // Pick to move to the state that has the maximum Q value
//        for (int nextState : actionsFromState) {
//            double value = Q[state][nextState];
//
//            if (value > maxValue) {
//                maxValue = value;
//                policyGotoState = nextState;
//            }
//        }
//        
//        return policyGotoState;
//	}
//	
//	void calculateQ() {
//		
//        Random rand = new Random();
//
//        for (int i = 0; i < 1000; i++) { // Train cycles
//            // Select a random initial state
//            int crtState = rand.nextInt(statesCount);
//
//            while (!isFinalState(crtState)) {
//            	
//                int[] actionsFromCurrentState = possibleActionsFromState(crtState);
//
//                // Pick a random action from the ones possible
//                int index = rand.nextInt(actionsFromCurrentState.length);
//                int nextState = actionsFromCurrentState[index];
//
//                // Q(state,action)= Q(state,action) + alpha * (R(state,action) + gamma * Max(next state, all actions) - Q(state,action))
//                double q = Q[crtState][nextState];
//                double maxQ = maxQ(nextState);
//                int r = R[crtState][nextState];
//
//                double value = q + alpha * (r + gamma * maxQ - q);
//                Q[crtState][nextState] = value;
//
//                crtState = nextState;
//            }
//        }
//    }
}
