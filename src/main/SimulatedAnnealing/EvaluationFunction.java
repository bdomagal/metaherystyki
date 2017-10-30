package main.SimulatedAnnealing;

import main.map.World;

import java.util.logging.Logger;

/**
 * Created by Bartek on 13.10.2017.
 */
public class EvaluationFunction implements IEvaluationFunction {

    private static EvaluationFunction instance = null;

    private static final Logger LOGGER = Logger.getLogger(EvaluationFunction.class.getName());

    private final double[][] distanceMatrix;

    protected EvaluationFunction(){
        distanceMatrix = null;
    }

    double[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    private EvaluationFunction(World world){
        int numberOfCities = world.getSize();
        distanceMatrix = new double[numberOfCities][numberOfCities];
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j < numberOfCities; j++) {
                distanceMatrix[i][j] = world.distance(i,j);
            }
        }


    }

    static EvaluationFunction getInstance(){
        if(instance == null){
            LOGGER.warning("Evaluation function needs to be initialized with cities first.");
        }
        return instance;
    }

    public static void createInstance(World world){
        if(instance == null){
            instance = new EvaluationFunction(world);
        }
    }

    @Override
    public double evaluate(Individual individual){
        int[] sequence = individual.getSequence();
        double result = 0;
        for (int i = 0; i < sequence.length-1; i++) {
            result+=distanceMatrix[sequence[i]][sequence[i+1]];
        }
        return result;
    }
}
