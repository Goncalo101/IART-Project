package iart.city_plan.model;

public class City {
    private int rows;
    private int columns;
    private int maxWalkingDistance;

    public City(int rows, int columns, int maxWalkingDistance) {

        this.rows = rows;
        this.columns = columns;
        this.maxWalkingDistance = maxWalkingDistance;
    }

    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return this.columns;
    }

    public int getMaxWalkingDistance() {
        return this.maxWalkingDistance;
    }

}
