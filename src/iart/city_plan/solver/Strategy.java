package iart.city_plan.solver;

import iart.city_plan.model.BuildingProject;
import iart.city_plan.model.City;
import iart.city_plan.util.structs.Coordinate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class Strategy {
    protected final int rows;
    protected final int columns;
    protected Map<Coordinate, Integer> city = new LinkedHashMap<>();

    public Strategy(City city) {
        this.rows = city.getRows();
        this.columns = city.getColumns();

        fillCity(rows, columns);
    }

    private void fillCity(int rows, int columns) {
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < columns; ++col) {
                Coordinate coordinate = new Coordinate(row, col);
                this.city.put(coordinate, -1);
            }
        }
    }

    public abstract Solution solve(List<BuildingProject> buildingProjects);
}
