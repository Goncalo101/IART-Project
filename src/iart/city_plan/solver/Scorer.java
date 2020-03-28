package iart.city_plan.solver;

import iart.city_plan.model.BuildingProject;
import iart.city_plan.util.structs.Coordinate;
import iart.city_plan.util.structs.Pair;

import java.util.*;

public class Scorer {
    private int maxWalkingDistance;

    public Scorer(int maxWalkingDistance) {
        this.maxWalkingDistance = maxWalkingDistance;
    }

    public int score(Solution solution) {
        int score = 0;
        List<Pair<BuildingProject, List<Coordinate>>> city = solution.getSolution();

        List<Pair<BuildingProject, List<Coordinate>>> residentialBuildings = new LinkedList<>();
        List<Pair<BuildingProject, List<Coordinate>>> utilityBuildings = new LinkedList<>();

        for (Pair<BuildingProject, List<Coordinate>> building : city) {
            BuildingProject project = building.getFirst();
            List<Coordinate> coordinates = building.getSecond();

            if (project.getType().equals("R")) {
                residentialBuildings.add(new Pair<>(project, coordinates));
            } else if (project.getType().equals("U")) {
                utilityBuildings.add(new Pair<>(project, coordinates));
            }
        }

        for (Pair<BuildingProject, List<Coordinate>> residentialBuilding : residentialBuildings) {
            int numUtilities = computeBuildingsInRange(residentialBuilding.getSecond(), utilityBuildings);
            score += numUtilities * residentialBuilding.getFirst().getCapacity();
        }

        return score;
    }

    private int computeBuildingsInRange(List<Coordinate> residentialCoords, List<Pair<BuildingProject, List<Coordinate>>> utilityBuildings) {
        Set<BuildingProject> buildings = new HashSet<>();
        for (Pair<BuildingProject, List<Coordinate>> utilityBuilding : utilityBuildings) {
            int minDistance = Integer.MAX_VALUE;
            for (Coordinate residentialCoord : residentialCoords) {
                for (Coordinate utilityCoord : utilityBuilding.getSecond()) {
                    int distance = computeDistance(utilityCoord, residentialCoord);
                    if (distance <= maxWalkingDistance && distance < minDistance) {
                        minDistance = distance;
                        buildings.add(utilityBuilding.getFirst());
                    }
                }
            }
        }

        return buildings.size();
    }

    private int computeDistance(Coordinate utilityCoord, Coordinate residentialCoord) {
        return Math.abs(utilityCoord.getRow() - residentialCoord.getRow()) + Math.abs(utilityCoord.getCol() - residentialCoord.getCol());
    }
}