package main;

import main.algorithms.Tabu;
import main.map.Individual;
import main.map.World;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class MainTS {

    public static String    file            = "kroA100";
    public static String    filePath        = "tsp_data/"+file +".tsp";
    private static int      iterations      = 50000;
    private static int      launches        = 10;
    private static int      tabuListSize    = 10;
    private static int      neigbourhoodSize=15;

    public static double[] bests;
    public static double[] results;
    static double avg = 0;
    public static void main(String[] args) throws FileNotFoundException{
	// write your code here
        System.out.println("this is TS:");
        bests = new double[iterations];
        results = new double[iterations];
        World w = new World(filePath);
        StringBuilder sb = new StringBuilder();
        for(int i =0;  i<launches; i++) {
            Tabu tabuSearch = new Tabu(w.getSize(), iterations, tabuListSize, neigbourhoodSize);
            double max = Double.MAX_VALUE;
            for (int k = 0; k < 1; k++) {
                Individual ind = tabuSearch.tabuSearch();
                max = Math.min(ind.getValue(), max);
            }
            System.out.println(String.format("%d : %f",i,max));
            sb.append(String.format("%f\n",max));
            MainTS.add(max);

        }
        File f = new File("wykresik.csv");
        PrintWriter pw = new PrintWriter(f);
        pw.write(sb.toString());
        pw.flush();
        pw.close();
        System.out.println("-------"+avg/launches);
    }

    private static void calculateVariance() {
        double avg = 0;
        double var = 0;
        for (double result1 : results) {
            avg += result1;

        }
        avg = avg/iterations;

        for (double result : results) {
            var += Math.pow((result - avg), 2);

        }
        var = Math.sqrt(var/ iterations);
        System.out.println("------ " + avg + "   " + var);
    }



    public synchronized static void add(double val){
        avg+=val;
    }
}
