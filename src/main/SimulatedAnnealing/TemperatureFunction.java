package main.SimulatedAnnealing;

public interface TemperatureFunction {
    double getEps();

    double cool(double maxTemp, int k);
}
