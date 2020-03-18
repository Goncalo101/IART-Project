package iart.city_plan.model;

public class BuildingProject {
    private String type;
    private int rows;
    private int columns;
    private int capacity;
    private String[] plan;

    public BuildingProject(String type, int rows, int columns, int capacity, String[] plan) {

        this.type = type;
        this.rows = rows;
        this.columns = columns;
        this.capacity = capacity;
        this.plan = plan;
    }

    public String getType() {
        return this.type;
    }

    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return this.columns;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public String[] getPlan() {
        return this.plan;
    }

}