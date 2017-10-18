package greedy;

import java.util.Comparator;

/**
 * Created by Bartek on 18.10.2017.
 */
public class CitiesDistanceComparator implements Comparator<Integer> {

    private final double[] distances;

    public CitiesDistanceComparator(double[] distances) {
        this.distances = distances;
    }


    @Override
    public int compare(Integer o1, Integer o2) {
        return (int)((distances[o1]-distances[o2])*1000);
    }
}
