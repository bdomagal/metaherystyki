package main;

import main.EvolutionAlgorithm.Individual;
import main.EvolutionAlgorithm.Tabu;
import main.map.World;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static double mutationChance;
    private static double crossChance;
    private static String filePath;
    private static int populationSize;
    private static int generations;
    private static int iterations;
    private static int tournament;

    public static double[] bests;
    private static double[] avgs;
    private static double[] worsts;
    public static double[] results;
    static double avg = 0;
    public static void main(String[] args) throws FileNotFoundException{
	// write your code here
        loadParameters(args);
        bests = new double[generations];
        avgs = new double[generations];
        worsts = new double[generations];
        results = new double[iterations];
        World w = new World(filePath);
        int threads = 10;
        while(threads>0) {
            Tabu tabuSearch = new Tabu(w.getSize(), 1000, 100, 300);
            double max = Double.MAX_VALUE;
            for (int i = 0; i < 1; i++) {
                Individual ind = tabuSearch.tabuSearch();
                max = Math.min(ind.getValue(), max);
            }
            System.out.println(String.format("%f",max));
            Main.add(max);
            threads--;

        }
        System.out.println("-------"+avg/10);
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
        System.out.print("Populatin = ");
        populationSize = sc.nextInt();
        System.out.print("Generations = ");
        generations = sc.nextInt();
        System.out.print("Iterations = ");
        iterations = sc.nextInt();
        System.out.print("Tournament Size = ");
        tournament = sc.nextInt();
    }
    public synchronized static void add(double val){
        avg+=val;
    }
}
