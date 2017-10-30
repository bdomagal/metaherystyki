package main.SimulatedAnnealing;

import java.util.Random;

public class SimulatedAnnealing {

    private final TemperatureFunction temperatureFunction;
    private Random random;
    public SimulatedAnnealing(TemperatureFunction simpleTemperatureFunction) {
        this.temperatureFunction = simpleTemperatureFunction;
        random = new Random();
    }

    public Individual solve(int size, int kMax, double maxTemp){
        Individual point = new Individual(size);
        point.setValue();
        
        double temp = maxTemp;
        int k = 0;
        while(temp>temperatureFunction.getEps() && k<kMax){
            Individual neighbour = new Individual(point);
            neighbour.mutate();
            neighbour.setValue();
            if(neighbour.getValue()<point.getValue()){
                point = neighbour;
            }
            else {
                if (random.nextDouble() < Math.exp(-(neighbour.getValue() - point.getValue()) / temp)) {

                    point = neighbour;
                }
            }
            k++;
            temp = temperatureFunction.cool(maxTemp, k);
        }
        return point;
    }

}
