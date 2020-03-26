package iart.city_plan.solver;

import iart.city_plan.model.BuildingProject;
import iart.city_plan.util.Coordinate;

import java.util.*;

public class Solution {
    private Map<BuildingProject, List<Coordinate>> solutions = new HashMap<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(solutions.size() + "\n");

        for (Map.Entry<BuildingProject, List<Coordinate>> buildingEntry : solutions.entrySet()) {
            String line = buildingEntry.getKey() + " " + buildingEntry.getValue() + "\n";
            sb.append(line);
        }

        return sb.toString();
    }

    public void addBuilding(BuildingProject buildingProject, Coordinate coordinate) {
        List<Coordinate> coordinates = new LinkedList<>();

        for (int row = 0; row < buildingProject.getRows(); ++row) {
            for (int col = 0; col < buildingProject.getColumns(); ++col) {
                int x = coordinate.getY() + col;
                int y = coordinate.getX() + row;

                Coordinate candidateCoord = new Coordinate(x, y);
                coordinates.add(candidateCoord);
            }
        }

        solutions.put(buildingProject, coordinates);
    }

    public Map<BuildingProject, List<Coordinate>> getSolutions() {
        return solutions;
    }
}
