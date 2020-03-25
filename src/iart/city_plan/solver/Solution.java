package iart.city_plan.solver;

import iart.city_plan.model.BuildingProject;
import iart.city_plan.util.Coordinate;

import java.util.HashMap;
import java.util.Map;

public class Solution {
    private Map<BuildingProject, Coordinate> solutions = new HashMap<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(solutions.size() + "\n");

        for (Map.Entry<BuildingProject, Coordinate> buildingEntry : solutions.entrySet()) {
            String line = buildingEntry.getKey() + " " + buildingEntry.getValue() + "\n";
            sb.append(line);
        }

        return sb.toString();
    }

    public void addBuilding(BuildingProject buildingProject, Coordinate coordinate) {
        solutions.put(buildingProject, coordinate);
    }
}
