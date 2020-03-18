package iart.city_plan.model;

public class City {
    private int rows;
    private int columns;
    private int maxWalkingDistance;

    City(int rows, int columns, int maxWalkingDistance) {

        this.rows = rows;
        this.columns = columns;
        this.maxWalkingDistance = maxWalkingDistance;
    }
}
