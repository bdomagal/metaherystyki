package greedy;

import EvolutionAlgorithm.EvaluationFunction;

import java.util.Arrays;
import java.util.logging.Logger;

/**
 * Created by Bartek on 18.10.2017.
 */
public class Greedy implements IGreedy {
    
    private static Greedy instance;

    private final Integer[][] proximityOrder;
    
    protected Greedy(){
        proximityOrder = null;
    }

    private Greedy(double[][] distanceMatrix){
        proximityOrder = new Integer[distanceMatrix.length][distanceMatrix.length];
        for (int i = 0; i < distanceMatrix.length; i++) {
            Integer[] matrix = proximityOrder[i];
            for (int j = 0; j < matrix.length; j++) {
                matrix[j] = j;
            }
            Arrays.sort(matrix, new CitiesDistanceComparator(distanceMatrix[i]));
        }
    }

    public static Greedy getInstance(){
        if(instance == null){
            instance = new Greedy(EvaluationFunction.getInstance().getDistanceMatrix());
        }
        return instance;
    }

    @Override
    public int[] fillMissing(int[] sequence, boolean[] used) {
        for (int i = 1; i < sequence.length; i++) {
            if(sequence[i]==-1) {
                int u = 0;
                while (used[proximityOrder[sequence[i-1]][u]]) {
                    u++;
                }
                used[proximityOrder[sequence[i-1]][u]] = true;
                sequence[i] = proximityOrder[sequence[i-1]][u];
            }
        }
        return sequence;
    }
}
