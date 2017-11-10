package main;

import main.algorithms.Population;
import main.map.World;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MainGA {

    private static double mutationChance;
    private static double crossChance;
    private static String filePath;
    private static int populationSize;
    private static int generations;
    private static int iterations;
    private static int tournament;

    private static double[] bests;
    private static double[] avgs;
    private static double[] worsts;
    private static double[] results;
    public static void main(String[] args) throws FileNotFoundException{
	// write your code here
        System.out.println("this is GA:");
        loadParameters(args);
        bests = new double[generations];
        avgs = new double[generations];
        worsts = new double[generations];
        results = new double[iterations];
        World w = new World(filePath);
        for(int g=0; g<iterations; g++) {
            Population population = new Population(populationSize, w.getSize());
            for (int i = 0; i < generations; i++) {
                population.nextGeneration(tournament, crossChance, mutationChance);
                bests[i] = population.getBestVal();
                worsts[i] = population.getWorstVal();
                avgs[i] = population.getAvg();
            }
            System.out.println(bests[generations - 1] + "   " + avgs[generations - 1] + "   " + worsts[generations - 1]);
            File f = new File(g+".csv");
            PrintWriter pw = new PrintWriter(f);
            StringBuilder sb = new StringBuilder("sep=,\n");
            for (int i = 0; i < bests.length; i++) {
                sb.append(i + "," + (int)bests[i] + "," + (int)avgs[i] + "," + (int)worsts[i] + "\n");

            }
            pw.write(sb.toString());
            pw.flush();
            pw.close();
            results[g] = bests[generations-1];
        }
        calculateVariance();
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
        System.out.println("------ " + avg + "   " + var);
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
