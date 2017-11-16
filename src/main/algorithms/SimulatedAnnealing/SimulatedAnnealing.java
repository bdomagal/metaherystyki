package main.algorithms.SimulatedAnnealing;

import main.MainSA;
import main.map.Individual;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class SimulatedAnnealing {

    private double best = Double.MAX_VALUE;
    private final TemperatureFunction temperatureFunction;
    private Random random;
    public SimulatedAnnealing(TemperatureFunction simpleTemperatureFunction) {
        this.temperatureFunction = simpleTemperatureFunction;
        random = new Random();
    }

    public Individual solve(int size, int kMax, double maxTemp, int neigh){
        StringBuilder sb = new StringBuilder("sep=,\n");
        Individual point = new Individual(size);
        point.setValue();
        
        double temp = maxTemp;
        int k = 0;
        while(k<kMax){
            Individual neighbour = getBestNeighbour(neigh, point);
            if(neighbour.getValue()<point.getValue()){
                point = neighbour;
            }
            else {
                if (random.nextDouble() < Math.exp(-(neighbour.getValue() - point.getValue()) / temp)) {

                    point = neighbour;
                }
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

    private Individual getBestNeighbour(int neigh, Individual point) {

        Individual best = null;
        double b = Double.MAX_VALUE;
        for (int i = 0; i < neigh; i++) {
            Individual neighbour = new Individual(point);
            neighbour.mutate();
            neighbour.setValue();
            if(neighbour.getValue()<b){
                b= neighbour.getValue();
                best = neighbour;
            }
        }
        return best;

    }

}
