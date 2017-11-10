package main;

import main.algorithms.SimulatedAnnealing.SimpleTemperatureFunction;
import main.algorithms.SimulatedAnnealing.SimulatedAnnealing;
import main.map.World;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class MainSA {


    public static String   file      = "kroA100";
    public static String   filePath      = "tsp_data/"+file +".tsp";
    private static double   maxTemp       = 100000;
    private static int      iterations    = 1000000;
    private static int      launches      = 10;
    public static double[]  bests;
    public static double[] results;
    static double avg = 0;
    public static void main(String[] args) throws FileNotFoundException{
        bests = new double[iterations];
        results = new double[launches];
        World w = new World(filePath);
        SimulatedAnnealing sa = new SimulatedAnnealing(new SimpleTemperatureFunction(0.9999));
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
        avg/=10;
        System.out.println("-------"+avg);
        double res = 0;
        for (double result : results) {
            res += Math.sqrt(Math.pow(result-avg, 2));
        }
        res /=10;
        System.out.println(res);
    }


}
