package main.EvolutionAlgorithm;

import main.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tabu {

    private ArrayList<int[]> tabuList;
    private Individual best;
    private double bestTime;
    private int iterations;
    private int tabuListSize;
    private int numberOfCities;
    private int neighbourhoodSize = 100;

    public Tabu(int numberOfCities, int iterations, int tabuListSize, int neighbourhoodSize){
        this.numberOfCities = numberOfCities;
        this.iterations = iterations;
        this.tabuListSize = tabuListSize;
        this.neighbourhoodSize = neighbourhoodSize;
    }

    public Individual tabuSearch() throws FileNotFoundException {
        StringBuilder sb = new StringBuilder("sep=,\n");
        tabuList = new ArrayList<>(tabuListSize);
        best = new Individual(numberOfCities);
        best.setValue();
        tabuList.add(best.getSequence());
        bestTime = best.getValue();
        for (int i = 0; i < iterations; i++) {
            Individual[] neighbourhood =  getNeighbourhood(best);
            Individual bestCandidate = neighbourhood[0];
            for (Individual individual : neighbourhood) {
                if (!isInList(tabuList, individual.getSequence()) && individual.getValue() < bestCandidate.getValue()) {
                    bestCandidate = individual;
                }
            }
            if(bestCandidate.getValue()<bestTime){
                best = bestCandidate;
                bestTime = best.getValue();
            }
            sb.append( String.format("%04d,%d,%d\n", i, (int)bestTime, (int)(bestCandidate.getValue())) );
            tabuList.add(bestCandidate.getSequence());
            if(tabuList.size()>tabuListSize){
                tabuList.remove(0);
            }
        }
        PrintWriter pw = new PrintWriter(new File("0.csv"));
        pw.write(sb.toString());
        pw.flush();
        pw.close();
        return best;

    }

    private Individual[] getNeighbourhood(Individual bestCandidate) {
        Individual[] neighbourhood = new Individual[neighbourhoodSize];
        int i = 0;
        while(i<neighbourhoodSize){
            Individual temp = new Individual(bestCandidate);
            temp.mutate();
            neighbourhood[i] = temp;
            temp.setValue();
            i++;
        }
        return neighbourhood;
    }


    private boolean isInList(final List<int[]> list, final int[] candidate) {
        return list.stream().anyMatch(a -> Arrays.equals(a, candidate));
    }
}
