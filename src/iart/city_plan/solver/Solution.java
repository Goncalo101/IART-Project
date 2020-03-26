package iart.city_plan.solver;

import iart.city_plan.model.BuildingProject;
import iart.city_plan.util.structs.Coordinate;
import iart.city_plan.util.structs.Pair;

import java.util.*;

public class Solution {
    private List<Pair<BuildingProject, List<Coordinate>>> solutions = new LinkedList<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(solutions.size() + "\n");

        for (Pair<BuildingProject, List<Coordinate>>  buildingEntry : solutions) {
            String line = buildingEntry.getT() + " " + buildingEntry.getU() + "\n";
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

        solutions.add(new Pair<>(buildingProject, coordinates));
    }

    public List<Pair<BuildingProject, List<Coordinate>>> getSolutions() {
        return solutions;
    }
}
