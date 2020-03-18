package iart.city_plan.model;

public class BuildingProject {
    private String type;
    private int rows;
    private int columns;
    private int capacity;
    private String[] plan;

    BuildingProject(String type, int rows, int columns, int capacity, String[] plan) {

        this.type = type;
        this.rows = rows;
        this.columns = columns;
        this.capacity = capacity;
        this.plan = plan;
    }
}
