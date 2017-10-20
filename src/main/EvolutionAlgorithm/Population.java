package main.EvolutionAlgorithm;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Bartek on 18.10.2017.
 */
public class Population {
    private Individual[] population;
    private int numberOfCities;
    private Individual best;
    private double bestVal;
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

    public void nextGeneration(int tournamentSize, double crossChance, double mutationChance){
        Individual[] next = new Individual[population.length];
        bestVal=Double.MAX_VALUE;
        best=null;
        for (int i = 0; i < population.length; i++) {
            Individual father = select(tournamentSize);
            Individual mother = select(tournamentSize);
            next[i] = getChild(father, mother, crossChance, mutationChance);
            next[i].setValue();
            if(next[i].getValue()<bestVal){
                best = next[i];
                bestVal = next[i].getValue();
            }
        }
        population = next;
        System.out.println(bestVal);
//        write(best, worst, avarage);
    }

    private Individual getChild(Individual father, Individual mother, double crossChance, double mutationChance) {
        if(random.nextDouble()<crossChance){
            father.cross(mother);
        }
        if(random.nextDouble()<mutationChance){
            father.mutate();
        }
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
