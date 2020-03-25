package iart.city_plan.solver;

import iart.city_plan.model.BuildingProject;
import iart.city_plan.util.Coordinate;

import java.util.HashMap;
import java.util.Map;

public class Solution {
    private Map<BuildingProject, Coordinate> solutions = new HashMap<>();

    @Override
    public String toString() {
        System.out.println(solutions.size());
        for (Map.Entry<BuildingProject, Coordinate> buildingEntry : solutions.entrySet()) {
            System.out.println(buildingEntry.getKey() + " " + buildingEntry.getValue());
        }

        return "program end\n";
    }

    public void addBuilding(BuildingProject buildingProject, Coordinate coordinate) {
        solutions.put(buildingProject, coordinate);
    }
}
