package EvolutionAlgorithm;


import greedy.Greedy;

import java.util.*;

/**
 * Created by Bartek on 13.10.2017.
 */
public class Individual {
    private int[] sequence;

    private static Random random = new Random();

    private final Greedy greedy;

    public Individual(int numberOfCities){
        sequence = new int[numberOfCities];
        init(numberOfCities);
        greedy = Greedy.getInstance();
    }

    public int[] getSequence() {
        return sequence;
    }

    private Individual(int[] sequence){
        this.sequence = sequence;
        greedy = Greedy.getInstance();
    }

    public void mutate(double mutationChance){
        if(random.nextDouble() <= mutationChance){
            int i1 = random.nextInt(sequence.length);
            int i2 = random.nextInt(sequence.length);
            int temp = sequence[i1];
            sequence[i1] = sequence[i2];
            sequence[i2] = temp;
        }
    }

    public Individual cross(double crossChance, Individual other){
        if(random.nextDouble() <= crossChance){
            int[] child = new int[sequence.length];
            boolean[] used = new boolean[sequence.length];
            for (int i = 0; i < child.length/2; i++) {
                child[i] = sequence[i];
                used[sequence[i]] = true;
            }
            for (int i = child.length-1; i >= child.length/2; i--) {
                if(!used[other.sequence[i]]){
                    child[i] = other.sequence[i];
                    used[other.sequence[i]] = true;
                }
                else{
                    child[i] = -1;
                }
            }
            child = greedy.fillMissing(child, used);
            return new Individual(child);
        }
        return null;
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
