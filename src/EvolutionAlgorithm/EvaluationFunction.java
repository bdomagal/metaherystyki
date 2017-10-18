package EvolutionAlgorithm;

import greedy.Greedy;
import map.City;
import map.World;

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

    public double[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    protected EvaluationFunction(World world){
        int numberOfCities = world.getWorld().length;
        City[] map = world.getWorld();
        distanceMatrix = new double[numberOfCities][numberOfCities];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                distanceMatrix[i][j] = map[i].distance(map[j]);
            }
        }


    }

    public static EvaluationFunction getInstance(){
        if(instance == null){
            LOGGER.warning("Evaluation function needs to be initialized with cities first.");
        }
        return instance;
    }

    public static EvaluationFunction getInstance(World world){
        if(instance == null){
            instance = new EvaluationFunction(world);
        }
        return instance;
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
