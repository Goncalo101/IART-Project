package iart.city_plan.model;

import iart.city_plan.util.Coordinate;

import java.util.Arrays;
import java.util.Objects;

public class BuildingProject {
    private String type;
    private Coordinate firstCoordinate;
    private int rows;
    private int columns;
    private int capacity;
    private String[] plan;
    private Integer id;
    private Integer placedID;
    private static Integer next = 0;
    private static Integer nextPlaced = 0;

    public BuildingProject(BuildingProject buildingProject) {
        this.type = buildingProject.type;
        this.firstCoordinate = buildingProject.firstCoordinate;
        this.rows = buildingProject.rows;
        this.columns = buildingProject.columns;
        this.capacity = buildingProject.capacity;
        this.plan = buildingProject.plan;
        this.id = buildingProject.id;
        this.placedID = buildingProject.placedID;
    }

    public BuildingProject(String type, int rows, int columns, int capacity, String[] plan) {
        this.type = type;
        this.rows = rows;
        this.columns = columns;
        this.capacity = capacity;
        this.plan = plan;
        this.id = next++;
        this.firstCoordinate = new Coordinate(0, 0);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuildingProject that = (BuildingProject) o;
        return rows == that.rows &&
                columns == that.columns &&
                capacity == that.capacity &&
                Objects.equals(type, that.type) &&
                firstCoordinate.equals(that.firstCoordinate) &&
                Arrays.equals(plan, that.plan) &&
                Objects.equals(placedID, that.placedID);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(type, firstCoordinate, rows, columns, capacity, id);
        result = 31 * result + Arrays.hashCode(plan);
        return result + placedID;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.firstCoordinate = coordinate;
    }

    public void place() {
        this.placedID = nextPlaced++;
    }
}