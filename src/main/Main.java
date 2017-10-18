package main;

import EvolutionAlgorithm.EvaluationFunction;
import EvolutionAlgorithm.IEvaluationFunction;
import EvolutionAlgorithm.Individual;
import greedy.Greedy;
import map.World;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws FileNotFoundException{
	// write your code here
            World w = new World("tsp_data/kroA100.tsp");
            IEvaluationFunction e = EvaluationFunction.getInstance(w);
            Individual i = new Individual(w.getWorld().length);
            int[] seq  = i.getSequence();
            for (int j = 0; j < seq.length; j++) {
                int i1 = seq[j];
                System.out.print(i1+", ");

            }
            System.out.println();
            System.out.println(e.evaluate(i));

    }
}
