package main;

import main.EvaluationFunction.EvaluationFunction;
import main.algorithms.Population;
import main.greedy.Greedy;
import main.map.World;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MainGA {
    private static double mutationChance = 0.005;
    private static double crossChance = 0.3;
    private static String fileName = "kroA100";
    private static String filePath = "tsp_data/" + fileName + ".tsp";
    private static int populationSize = 3000;
    private static int generations = 300;
    private static int iterations = 10;
    private static int tournament = 18;

    private static double[] bests;
    private static double[] avgs;
    private static double[] worsts;
    private static double[] results;
    public static void main(String[] args) throws FileNotFoundException{
	// write your code here
        System.out.println("this is GA:");
        World w =null;
        String[] mutations = {"kroA100", "kroA200", "kroB100", "kroB200","kroC100","kroD100","kroE100"};
        for (String mutation : mutations) {
            EvaluationFunction.unSet();
            Greedy.unset();
            fileName = mutation;
            filePath = "tsp_data/" + fileName + ".tsp";
            w = new World(filePath);
            bests = new double[generations];
            avgs = new double[generations];
            worsts = new double[generations];
            results = new double[iterations];
            for (int g = 0; g < iterations; g++) {
                Population population = new Population(populationSize, w.getSize());
                for (int i = 0; i < generations; i++) {
                    population.nextGeneration(tournament, crossChance, mutationChance);
                    bests[i] = population.getBestVal();
                    worsts[i] = population.getWorstVal();
                    avgs[i] = population.getAvg();
                }
                //System.out.println(bests[generations - 1] + "   " + avgs[generations - 1] + "   " + worsts[generations - 1]);
                /*File f = new File(g + ".csv");
                PrintWriter pw = new PrintWriter(f);
                StringBuilder sb = new StringBuilder("sep=,\n");
                for (int i = 0; i < bests.length; i++) {
                    sb.append(i + "," + (int) bests[i] + "," + (int) avgs[i] + "," + (int) worsts[i] + "\n");

                }
                pw.write(sb.toString());
                pw.flush();
                pw.close();*/
                results[g] = bests[generations - 1];
            }
            calculateVariance();
        }
    }

    private static void calculateVariance() {
        double avg = 0;
        double var = 0;
        for (int i = 0; i < results.length; i++) {
            avg += results[i];

        }
        avg = avg/iterations;

        for (int i = 0; i < results.length; i++) {
            double result = results[i];
            var += Math.pow((result - avg), 2);

        }
        var = Math.sqrt(var/ iterations);
        System.out.println(fileName + "------ " + avg + "   " + var);
    }

    private static void loadParameters(String[] args){
        try {
            tournament = Integer.parseInt(args[6]);
            mutationChance = Double.parseDouble(args[0]);
            crossChance = Double.parseDouble(args[1]);
            filePath = args[2];
            populationSize = Integer.parseInt(args[3]);
            generations = Integer.parseInt(args[4]);
            iterations = Integer.parseInt(args[5]);
        }catch (NumberFormatException nfe) {
            System.out.println("Parameters could not be read please check their format");
            System.exit(-1);
        }catch (ArrayIndexOutOfBoundsException a){
            readParameters();
        }
    }

    private  static void readParameters(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Mutation Chance = ");
        mutationChance = sc.nextDouble();
        System.out.print("Cross Chance = ");
        crossChance = sc.nextDouble();
        System.out.print("File Path = ");
        filePath = sc.next();
        System.out.print("Population = ");
        populationSize = sc.nextInt();
        System.out.print("Generations = ");
        generations = sc.nextInt();
        System.out.print("Iterations = ");
        iterations = sc.nextInt();
        System.out.print("Tournament Size = ");
        tournament = sc.nextInt();
    }
}
