package main.map;

import main.EvolutionAlgorithm.EvaluationFunction;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Bartek on 13.10.2017.
 */
public class World {

    private City[] world;

    public City[] getWorld() {
        return world;
    }

    public int getSize(){
        return world.length;
    }

    public World(String path) throws FileNotFoundException {

        Scanner sc = new Scanner(new File(path));
        sc.nextLine();
        sc.nextLine();
        sc.nextLine();
        sc.next();
        int dimension = sc.nextInt();
        world = new City[dimension];
        sc.nextLine();
        sc.nextLine();
        for(int i=0; i<dimension; i++){
            sc.nextLine();
            sc.nextInt();
            int x = sc.nextInt();
            int y = sc.nextInt();
            world[i] = new City(x, y);
        }
        EvaluationFunction.createInstance(this);
    }

    public double distance(int i, int j) {
        return world[i].distance(world[j]);
    }
}
