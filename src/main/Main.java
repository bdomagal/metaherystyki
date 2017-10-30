package main;

import main.SimulatedAnnealing.SimpleTemperatureFunction;
import main.SimulatedAnnealing.SimulatedAnnealing;
import main.map.World;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Main {


    private static String   filePath      = "tsp_data/kroA100.tsp";
    private static double   maxTemp       = 350;
    private static int      iterations    = 10000000;
    private static int      launches      = 20;
    public static double[]  bests;
    public static double[] results;
    static double avg = 0;
    public static void main(String[] args) throws FileNotFoundException{
        bests = new double[iterations];
        results = new double[launches];
        World w = new World(filePath);
        SimulatedAnnealing sa = new SimulatedAnnealing(new SimpleTemperatureFunction(0.9999999));
        while(launches>0) {
            results[launches-1] = sa.solve(w.getSize(), iterations, maxTemp).getValue();
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator(',');
            DecimalFormat df = new DecimalFormat("########.#######", symbols);
            new DecimalFormat();
            df.setGroupingUsed(false);
            System.out.println(df.format(new BigDecimal(results[launches-1])));
            avg+=results[launches-1];
            launches--;

        }
        avg/=20;
        System.out.println("-------"+avg);
        double res = 0;
        for (double result : results) {
            res += Math.sqrt(Math.pow(result-avg, 2));
        }
        res /=20;
        System.out.println(res);
    }


}
