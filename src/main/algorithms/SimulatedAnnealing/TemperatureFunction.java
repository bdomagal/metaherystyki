package main.algorithms.SimulatedAnnealing;

public interface TemperatureFunction {
    double getEps();

    double cool(double maxTemp, int k);
}
