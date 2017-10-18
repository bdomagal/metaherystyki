package map;

/**
 * Created by Bartek on 13.10.2017.
 */
public class City {
    private int x;
    private int y;

    public City(int x, int y){
        this.x=x;
        this.y=y;
    };

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double distance(City destination){
        return Math.sqrt(Math.pow(x-destination.getX(), 2) + Math.pow(y-destination.getY(), 2));
    }


}
