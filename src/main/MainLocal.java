package main;

import main.EvaluationFunction.EvaluationFunction;
import main.algorithms.Population;
import main.greedy.Greedy;
import main.map.Individual;
import main.map.World;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainLocal {
    private static double mutationChance = 0.025;
    private static double crossChance = 0.3;
    private static String fileName = "kroA100";
    private static String filePath = "tsp_data/" + fileName + ".tsp";
    private static int populationSize = 4000;
    private static int generations = 1000;
    private static int iterations = 20;
    private static int tournament = 35;


    private static double[] results;
    public static void main(String[] args) throws FileNotFoundException{
	// write your code here
        System.out.println("this is GA:");
        World w =null;
        String[] mutations = {"kroA100"/*, "kroA200", "kroB100", "kroB200","kroC100","kroD100","kroE100"*/};

        for (String mutation : mutations) {
            EvaluationFunction.unSet();
            Greedy.unset();
            fileName = mutation;
            filePath = "tsp_data/" + fileName + ".tsp";
            w = new World(filePath);
            results = new double[iterations];
            double best = Double.MAX_VALUE;
            for(int i = 0; i<iterations; i++) {
                Individual ind = new Individual(100);
                ind.setValue();
                Individual temp = ind.localBest();
                temp.setValue();
                int idleCtr = 0;
                while (ind.getValue() >= temp.getValue() && idleCtr < 5) {
                    if (temp.getValue() == ind.getValue()) {
                        idleCtr++;
                        ind = temp;
                    }
                    if (temp.getValue() < ind.getValue()) {
                        idleCtr = 0;
                        ind = temp;
                    }
                    temp = ind.localBest();
                    temp.setValue();
                }
                System.out.println(ind.getValue());
                results[i] = ind.getValue();
                if(best>ind.getValue()){
                    best=ind.getValue();
                }
            }
            calculateVariance();
            System.out.println(best);

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
