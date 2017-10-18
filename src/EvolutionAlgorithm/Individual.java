package EvolutionAlgorithm;


import java.util.*;

/**
 * Created by Bartek on 13.10.2017.
 */
public class Individual {
    private int[] sequence;

    private static Random random = new Random();


    public Individual(int numberOfCities){
        sequence = new int[numberOfCities];
        init(numberOfCities);
    }

    public int[] getSequence() {
        return sequence;
    }

    private Individual(int[] sequence){
        this.sequence = sequence;
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

/*    public void cross(double crossChance, Individual other){
        if(random.nextDouble() <= crossChance){
            int[] child = new int[sequence.length];
            boolean[] assignedCities = new boolean[sequence.length];
            for (int i = 0; i < child.length/2; i++) {
                child[i] = sequence[i];
                used.add(sequence[i]);
            }
            for (int i = child.length-1; i >= child.length/2; i--) {
                if(!used.contains(other.sequence[i])){
                    child.
                }

            }
        }
    }*/


    /**
     * Initialize individual with random order of cities
     * @param numberOfCities - number of cities to visit
     */
    private void init(int numberOfCities) {
        ArrayList<Integer> temp = new ArrayList<Integer>();
        for (int i = 0; i < numberOfCities; i++) {
            temp.add(i);
        }
        Collections.shuffle(temp);
        for (int i = 0; i < numberOfCities; i++) {
            sequence[i] = temp.get(i);
        }
    }

}
