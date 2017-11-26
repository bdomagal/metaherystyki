package main.map;


import main.EvaluationFunction.EvaluationFunction;
import main.algorithms.Tabu;
import main.greedy.Greedy;
import main.greedy.IGreedy;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Bartek on 13.10.2017.
 */
public class Individual {
    private int[] sequence;

    private static Random random = new Random();

    private final IGreedy greedy;

    private double value;

    public static int  x = 0;
    public Individual(int numberOfCities, boolean b) {
        sequence = new int[numberOfCities];
        init(numberOfCities, b);
        greedy = Greedy.getInstance(EvaluationFunction.getInstance().getDistanceMatrix());
        x++;
    }

    public double getValue() {
        return value;
    }

    public void setValue() {
        this.value = EvaluationFunction.getInstance().evaluate(this);
    }

    public Individual(int numberOfCities){
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

    public int[] getSequence() {
        return sequence;
    }

    private Individual(int[] sequence){
        this.sequence = sequence;
        greedy = Greedy.getInstance();
    }

    public Individual(Individual individual){
        greedy=individual.greedy;
        sequence = individual.sequence.clone();
    }

    public void mutate(){
        int i1 = random.nextInt(sequence.length);
        int i2 = random.nextInt(sequence.length);
        swap(i1, i2);
    }

    private void swap(int i1, int i2) {
        if(i1<i2){
            int t = i1;
            i1 = i2;
            i2 = t;
        }
        int[] temp = new int[i1-i2];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = sequence[i2+i];
        }
        for (int i = 0; i < temp.length; i++) {
            sequence[i + i2] = temp[temp.length - i -1];
        }
    }

    public void mutateMultiple(double mutationChance){
        for (int i1 = 0; i1 < sequence.length; i1++) {
            if(random.nextDouble()<mutationChance) {
                int i2 = random.nextInt(sequence.length);
                swap(i1, i2);
            }
        }
    }

    public void cross2(Individual other){
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

    public void cross(Individual other){
        int start = random.nextInt(sequence.length);
        int end = random.nextInt(sequence.length);
        if(start==end){return;}
        if(end<start){
            int t = end;
            end = start;
            start= t;
        }
        int[] child = new int[sequence.length];
        int[] p2 = other.getSequence();
        for (int i = 0; i < p2.length; i++) {
            if(i<start||i>end){
                child[i] = -1;
            }
            else{
                child[i] = sequence[i];
            }
        }
        for (int i = start; i <= end; i++) {
            if(!contains(child, p2[i])) {
                int swap = determinePos(sequence, p2, start, end, i);
                child[swap] = p2[i];
            }
        }
        for (int i = 0; i < p2.length; i++) {
            if(child[i]<0){
                child[i]=p2[i];
            }

        }
        sequence=child;
    }

    private boolean contains(int[] child, int i) {
        for (int j = 0; j < child.length; j++) {
            int i1 = child[j];
            if(i==i1){
                return true;
            }
        }
        return false;
    }

    private int determinePos(int[] sequence, int[] p2, int start, int end, int i) {
        int pos = i;
        pos = find(sequence[i], p2);
        if(pos<=end && pos>=start){
            return determinePos(sequence, p2, start, end, pos);
        }
        return pos;
    }

    private int find(int i, int[] p2) {
        for (int j = 0; j < p2.length; j++) {
            if(p2[j]==i){
                return j;}
        }
        return -1;
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
    private void init(int numberOfCities, boolean b) {
        Tabu t = new Tabu(numberOfCities, 1000, 5 ,10);
        int[] se = null;
        try {
            se = t.tabuSearch().sequence;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        sequence = se;
    }

    public Individual localBest(){
        Individual best =null;
        double  b = Double.MAX_VALUE;
        for (int i = 0; i < sequence.length; i++) {
            for (int j = i+1; j < sequence.length; j++) {
                Individual ind = new Individual(this);
                ind.swap(i,j);
                ind.setValue();
                if(ind.getValue()<b){
                    best = ind;
                    b= ind.getValue();
                }
            }
        }
        return best;
    }

}
