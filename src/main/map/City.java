package main.map;

/**
 * Created by Bartek on 13.10.2017.
 */
class City {
    private int x;
    private int y;

    City(int x, int y){
        this.x=x;
        this.y=y;
    }

    private int getX() {
        return x;
    }


    private int getY() {
        return y;
    }


    double distance(City destination){
        return Math.sqrt(Math.pow(x-destination.getX(), 2) + Math.pow(y-destination.getY(), 2));
    }


}
