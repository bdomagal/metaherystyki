package main;

import main.EvaluationFunction.EvaluationFunction;
import main.algorithms.SimulatedAnnealing.SimpleTemperatureFunction;
import main.algorithms.SimulatedAnnealing.SimulatedAnnealing;
import main.greedy.Greedy;
import main.map.World;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class MainSA {


    public static String   file      = "kroA100";
    public static String   filePath      = "tsp_data/"+file +".tsp";
    private static double   maxTemp       = 10000;
    private static int      iterations    = 10000;
    private static int      launches      = 50;
    private static int      nieghbourhood = 1;
    public static double[]  bests;
    public static double[] results;
    static double avg = 0;
    public static void main(String[] args) throws FileNotFoundException{

        //int[] variations = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25};

        String[] variations = {"kroA100", "kroA200", "kroB100", "kroB200","kroC100","kroD100","kroE100"};
        for (String variation : variations) {
            avg=0;
            EvaluationFunction.unSet();
            Greedy.unset();
            file = variation;
            filePath = "tsp_data/" + file + ".tsp";

            results = new double[launches];
            bests = new double[iterations];
            World w = new World(filePath);
            for (int i = 0; i < launches; i++) {
                SimulatedAnnealing sa = new SimulatedAnnealing(new SimpleTemperatureFunction(0.999));
                results[i] = sa.solve(w.getSize(), iterations, maxTemp, nieghbourhood).getValue();
            /*DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator(',');
            DecimalFormat df = new DecimalFormat("########.#######", symbols);
            new DecimalFormat();
            df.setGroupingUsed(false);
            System.out.println(df.format(new BigDecimal(results[i])));*/
                avg += results[i];

            }
            avg /= launches;
            double res = 0;
            for (double result : results) {
                res += Math.pow(result - avg, 2);
            }
            res = Math.sqrt(res / launches);
            System.out.println(String.format("file:%s T:%d IT:%d N:%d", file, (int) maxTemp, iterations, nieghbourhood) + "-------" + avg + "   " + res);
        }
    }


}
