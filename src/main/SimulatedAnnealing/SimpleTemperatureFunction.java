package main.SimulatedAnnealing;

public class SimpleTemperatureFunction implements TemperatureFunction {
    private double step;
    private double curr = 0;
    @Override
    public double getEps() {
        return 0;
    }

    @Override
    public double cool(double maxTemp, int k) {
        if (curr == 0){curr = maxTemp;}
        curr = step * curr;
        return maxTemp/(1+Math.log(1+k));
    }

    public SimpleTemperatureFunction(double v) {
        step = v;
    }
}
