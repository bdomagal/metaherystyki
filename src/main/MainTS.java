package main;

import main.EvaluationFunction.EvaluationFunction;
import main.algorithms.Tabu;
import main.greedy.Greedy;
import main.map.Individual;
import main.map.World;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class MainTS {

    public static String    file            = "kroA100";
    public static String    filePath        = "tsp_data/"+file +".tsp";
    private static int      iterations      = 100000;
    private static int      launches        = 50;
    private static int      tabuListSize    = 10;
    private static int      neigbourhoodSize=50;

    public static double[] bests;
    public static double[] results;
    static double avg = 0;
    public static void main(String[] args) throws FileNotFoundException{
	// write your code here
        System.out.println("this is TS:");
        String[] variations = {"kroA100", "kroA200", "kroB100", "kroB200","kroC100","kroD100","kroE100"};
        long startTime = System.currentTimeMillis();
        for (String variation : variations) {
            avg=0;
            EvaluationFunction.unSet();
            Greedy.unset();
            file = variation;
            filePath = "tsp_data/" + file + ".tsp";
            bests = new double[launches];
            results = new double[launches];
            World w = new World(filePath);
            //StringBuilder sb = new StringBuilder();
            for (int i = 0; i < launches; i++) {
                Tabu tabuSearch = new Tabu(w.getSize(), iterations, tabuListSize, neigbourhoodSize);
                double max = Double.MAX_VALUE;
                for (int k = 0; k < 1; k++) {
                    Individual ind = tabuSearch.tabuSearch();
                    max = Math.min(ind.getValue(), max);
                }
                //System.out.println(String.format("%d : %f", i, max));
                //sb.append(String.format("%f\n", max));
                results[i] = max;

            }/*
            File f = new File("wykresik.csv");
            PrintWriter pw = new PrintWriter(f);
            pw.write(sb.toString());
            pw.flush();
            pw.close();*/
            calculateVariance();
            long endTime   = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            System.out.println(totalTime/1000);
        }
    }

    private static void calculateVariance() {
        double avg = 0;
        double var = 0;
        for (double result1 : results) {
            avg += result1;

        }
        avg = avg/launches;

        for (double result : results) {
            var += Math.pow((result - avg), 2);

        }
        var = Math.sqrt(var/launches);
        System.out.println(file + "------ " + avg + "   " + var);
    }


}
