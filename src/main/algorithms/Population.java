package main.algorithms;

import main.map.Individual;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Bartek on 18.10.2017.
 */
public class Population {
    public Individual[] population;
    private int numberOfCities;
    private Individual best;
    private double bestVal;
    private double worstVal = 0;
    private double avg = 0;
    private static Random random = new Random();

    public Population(int size, int cities){
        population = new Individual[size];
        numberOfCities = cities;
        init();
    }

    public Population(Population population){
        this.population = new Individual[population.population.length];
        numberOfCities = population.numberOfCities;
    }
    private void init() {
        for (int i = 0; i < population.length; i++) {
            population[i] = new Individual(numberOfCities);
            population[i].setValue();
        }

    }

    public Individual getBest() {
        return best;
    }

    public double getBestVal() {
        return bestVal;
    }

    public double getWorstVal() {
        return worstVal;
    }

    public double getAvg() {
        return avg;
    }

    public void nextGeneration(int tournamentSize, double crossChance, double mutationChance){
        Individual[] next = new Individual[population.length];
        bestVal=Double.MAX_VALUE;
        worstVal = 0;
        avg = 0;
        best=null;
        for (int i = 0; i < population.length; i++) {
            Individual father = select(tournamentSize);
            Individual mother = select(tournamentSize);
            next[i] = getChild(father, mother, crossChance, mutationChance);
            next[i].setValue();
            double temp = next[i].getValue();
            if(next[i].getValue()<bestVal){
                best = next[i];
                bestVal = temp;
            }
            worstVal = Math.max(temp, worstVal);
            avg += temp;

        }
        avg = avg/population.length;
        population = next;
    }

    public Individual getChild(Individual father, Individual mother, double crossChance, double mutationChance) {
        if(random.nextDouble()<crossChance){
            father.cross(mother);
        }
            father.mutateMultiple(mutationChance);

        return father;
    }

    private Individual select(int tournamentSize){

        double best = Double.MAX_VALUE;
        Individual winner = null;
        ArrayList<Individual> participants = new ArrayList<>();
        while(tournamentSize>0){
            Individual temp = population[random.nextInt(population.length)];
            if(!participants.contains(temp)){
                participants.add(temp);
                tournamentSize--;
                if(temp.getValue()<best){
                    best=temp.getValue();
                    winner = temp;
                }
            }
        }

        return new Individual(winner);
    }
}
