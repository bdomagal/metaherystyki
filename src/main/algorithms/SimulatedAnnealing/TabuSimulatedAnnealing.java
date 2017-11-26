package main.algorithms.SimulatedAnnealing;

import main.MainSA;
import main.map.Individual;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TabuSimulatedAnnealing  extends SimulatedAnnealing{

    private double best = Double.MAX_VALUE;
    private final TemperatureFunction temperatureFunction;
    private Random random;
    private List<int[]> tabuList;
    public TabuSimulatedAnnealing(TemperatureFunction simpleTemperatureFunction) {
        super(simpleTemperatureFunction);
        this.temperatureFunction = simpleTemperatureFunction;
        random = new Random();
    }

    public Individual solve(int size, int kMax, double maxTemp, int neigh, int tabuListSize){
        StringBuilder sb = new StringBuilder("sep=,\n");
        Individual point = new Individual(size);
        point.setValue();

        tabuList = new ArrayList<>(tabuListSize);
        double temp = maxTemp;
        int k = 0;
        while(k<kMax){
            Individual neighbour = getBestNeighbour(neigh, point);
            boolean tabu = !isInList(tabuList, neighbour.getSequence());
            if(tabu && neighbour.getValue()<point.getValue()){
                point = neighbour;
            }
            else {
                if (tabu && random.nextDouble() < Math.exp(-(neighbour.getValue() - point.getValue()) / temp)) {

                    point = neighbour;
                }
            }
            tabuList.add(point.getSequence());
            if(tabuList.size()>tabuListSize){
                tabuList.remove(0);
            }
            if(point.getValue() < best){best = point.getValue();}
            if(k%100 == 0) {
                sb.append((int)(point.getValue()) +"," +(int)(best)+"\n");
            }
            k++;
            temp = temperatureFunction.cool(maxTemp, k);
        }
        File f = new File(MainSA.file + ".csv");
        try (PrintWriter pw = new PrintWriter(f)) {
            pw.write(sb.toString());
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return point;
    }


    private boolean isInList(final List<int[]> list, final int[] candidate) {
        return list.stream().anyMatch(a -> Arrays.equals(a, candidate));
    }

}
