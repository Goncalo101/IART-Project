package iart.city_plan.model;

public class BuildingProject {
    private String type;
    private int rows;
    private int columns;
    private int capacity;
    private String[] plan;
    private Integer id;
    private static Integer next = 0;

    public BuildingProject(BuildingProject buildingProject) {
        this.type = buildingProject.type;
        this.rows = buildingProject.rows;
        this.columns = buildingProject.columns;
        this.capacity = buildingProject.capacity;
        this.plan = buildingProject.plan;
        this.id = buildingProject.id;
    }

    public BuildingProject(String type, int rows, int columns, int capacity, String[] plan) {
        this.type = type;
        this.rows = rows;
        this.columns = columns;
        this.capacity = capacity;
        this.plan = plan;
        this.id = next++;
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

    public Integer getID() {
        return this.id;
    }

    @Override
    public String toString() {
        return this.id.toString();
    }
}