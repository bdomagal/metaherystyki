package main.EvolutionAlgorithm;


import main.greedy.Greedy;
import main.greedy.IGreedy;

import java.util.*;

/**
 * Created by Bartek on 13.10.2017.
 */
public class Individual {
    private int[] sequence;

    private static Random random = new Random();

    private final IGreedy greedy;

    private double value;

    public double getValue() {
        return value;
    }

    void setValue() {
        this.value = EvaluationFunction.getInstance().evaluate(this);
    }

    Individual(int numberOfCities){
        sequence = new int[numberOfCities];
        init(numberOfCities);
        greedy = Greedy.getInstance(EvaluationFunction.getInstance().getDistanceMatrix());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i : sequence) {
            sb.append(String.format("%03d, ", i));
        }
        sb.append("]");
        return sb.toString();
    }

    int[] getSequence() {
        return sequence;
    }

    private Individual(int[] sequence){
        this.sequence = sequence;
        greedy = Greedy.getInstance();
    }

    Individual(Individual individual){
        greedy=individual.greedy;
        sequence = individual.sequence.clone();
    }

    void mutate(){
        int i1 = random.nextInt(sequence.length);
        int i2 = random.nextInt(sequence.length);
        int temp = sequence[i1];
        sequence[i1] = sequence[i2];
        sequence[i2] = temp;
    }

    void cross(Individual other){
        boolean[] used = new boolean[sequence.length];
        for (int i = 0; i < sequence.length/2; i++) {
            used[sequence[i]] = true;
        }
        for (int i = sequence.length-1; i >= sequence.length/2; i--) {
            if(!used[other.sequence[i]]){
                sequence[i] = other.sequence[i];
                used[other.sequence[i]] = true;
            }
            else{
                sequence[i] = -1;
            }
        }
        sequence = greedy.fillMissing(sequence, used);

    }


    /**
     * Initialize individual with random order of cities
     * @param numberOfCities - number of cities to visit
     */
    private void init(int numberOfCities) {
        ArrayList<Integer> temp = new ArrayList<>();
        for (int i = 0; i < numberOfCities; i++) {
            temp.add(i);
        }
        Collections.shuffle(temp);
        for (int i = 0; i < numberOfCities; i++) {
            sequence[i] = temp.get(i);
        }
    }

}
