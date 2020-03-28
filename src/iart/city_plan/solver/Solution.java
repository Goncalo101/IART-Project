package iart.city_plan.solver;

import iart.city_plan.model.BuildingProject;
import iart.city_plan.util.structs.Coordinate;
import iart.city_plan.util.structs.Pair;

import java.util.*;

public class Solution {
    private List<Pair<BuildingProject, List<Coordinate>>> solution = new LinkedList<>();
    private int score = 0;
    private int probability = 0;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(solution.size() + "\n");

        for (Pair<BuildingProject, List<Coordinate>>  buildingEntry : solution) {
            String line = buildingEntry.getFirst() + " " + buildingEntry.getSecond().get(0) + "\n";
            sb.append(line);
        }

        return sb.toString();
    }

    public void addBuilding(BuildingProject buildingProject, Coordinate coordinate) {
        List<Coordinate> coordinates = new LinkedList<>();

        for (int row = 0; row < buildingProject.getRows(); ++row) {
            for (int col = 0; col < buildingProject.getColumns(); ++col) {
                int newCol = coordinate.getCol() + col;
                int newRow = coordinate.getRow() + row;

                Coordinate candidateCoord = new Coordinate(newRow, newCol);
                coordinates.add(candidateCoord);
            }
        }

        solution.add(new Pair<>(buildingProject, coordinates));
    }

    public List<Pair<BuildingProject, List<Coordinate>>> getSolution() {
        return solution;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getProbability() {
        return probability;
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }

    public void sort() {
        solution.sort(new Comparator<Pair<BuildingProject, List<Coordinate>>>() {

            @Override
            public int compare(Pair<BuildingProject, List<Coordinate>> buildingProjectListPair, Pair<BuildingProject, List<Coordinate>> t1) {
                if (buildingProjectListPair.getSecond().get(0).getRow() == t1.getSecond().get(0).getRow()) {
                    return buildingProjectListPair.getSecond().get(0).getCol() - t1.getSecond().get(0).getCol();
                } else {
                    return buildingProjectListPair.getSecond().get(0).getRow() - t1.getSecond().get(0).getRow();
                }
            }
        });
    }
}
