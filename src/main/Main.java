package main;

import main.EvolutionAlgorithm.EvaluationFunction;
import main.EvolutionAlgorithm.IEvaluationFunction;
import main.EvolutionAlgorithm.Individual;
import main.EvolutionAlgorithm.Population;
import main.map.World;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    private static double mutationChance;
    private static double crossChance;
    private static String filePath;
    private static int populationSize;
    private static int generations;
    private static int iterations;
    private static int tournament;

    public static void main(String[] args) throws FileNotFoundException{
	// write your code here
        loadParameters(args);
        System.out.println(" "+mutationChance+crossChance+filePath+populationSize+generations+iterations+tournament);
        World w = new World(filePath);
        Population population = new Population(populationSize, w.getSize());
        for(int i =0; i<generations; i++){
            System.out.print(i+"  ");
            population.nextGeneration(tournament, crossChance, mutationChance);
        }

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
