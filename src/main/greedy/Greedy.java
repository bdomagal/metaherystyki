package main.greedy;

import main.EvolutionAlgorithm.EvaluationFunction;

import java.util.Arrays;
import java.util.Comparator;
import java.util.logging.Logger;

/**
 * Created by Bartek on 18.10.2017.
 */
public class Greedy implements IGreedy {
    
    private static Greedy instance;
    private static final Logger LOGGER = Logger.getLogger(Greedy.class.getName());
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
            LOGGER.warning("Greedy needs to be initialized with distances first.");
        }
        return instance;
    }
    public static Greedy getInstance(double[][] distanceMatrix){
        if(instance == null){
            instance = new Greedy(distanceMatrix);
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

    /**
     * Created by Bartek on 18.10.2017.
     */
    public static class CitiesDistanceComparator implements Comparator<Integer> {

        private final double[] distances;

        CitiesDistanceComparator(double[] distances) {
            this.distances = distances;
        }


        @Override
        public int compare(Integer o1, Integer o2) {
            return (int)((distances[o1]-distances[o2])*1000);
        }
    }
}
