package iart.city_plan.solver;

import iart.city_plan.model.BuildingProject;
import iart.city_plan.util.Coordinate;

import java.util.*;


public class Scorer {
    private static int maxWalkingDistance;

    public Scorer(int maxWalkingDistance) {
        Scorer.maxWalkingDistance = maxWalkingDistance;
    }

    public static int score(Solution solution) {
        int score = 0;
        Map<BuildingProject, List<Coordinate>> city = solution.getSolutions();

        Map<BuildingProject, List<Coordinate>> residentialBuildings = new HashMap<>();
        Map<BuildingProject, List<Coordinate>> utilityBuildings = new HashMap<>();

        for (Map.Entry<BuildingProject, List<Coordinate>> building : city.entrySet()) {
            BuildingProject project = building.getKey();
            List<Coordinate> coordinates = building.getValue();

            if (project.getType().equals("R")) {
                residentialBuildings.put(project, coordinates);
            } else if (project.getType().equals("U")) {
                utilityBuildings.put(project, coordinates);
            }
        }

        for (Map.Entry<BuildingProject, List<Coordinate>> residentialBuilding : residentialBuildings.entrySet()) {
            int numUtilities = computeBuildingsInRange(residentialBuilding.getValue(), utilityBuildings);
            score += numUtilities * residentialBuilding.getKey().getCapacity();
        }

        return score;
    }

    private static int computeBuildingsInRange(List<Coordinate> residentialCoords, Map<BuildingProject, List<Coordinate>> utilityBuildings) {
        Set<BuildingProject> buildings = new HashSet<>();
        for (Map.Entry<BuildingProject, List<Coordinate>> utilityBuilding : utilityBuildings.entrySet()) {
            int minDistance = Integer.MAX_VALUE;
            for (Coordinate residentialCoord : residentialCoords) {
                for (Coordinate utilityCoord : utilityBuilding.getValue()) {
                    int distance = computeDistance(utilityCoord, residentialCoord);
                    if (distance <= maxWalkingDistance && distance < minDistance) {
                        minDistance = distance;
                        buildings.add(utilityBuilding.getKey());
                    }
                }
            }
        }

        return buildings.size();
    }

    private static int computeDistance(Coordinate utilityCoord, Coordinate residentialCoord) {
        return Math.abs(utilityCoord.getX() - residentialCoord.getX()) + Math.abs(utilityCoord.getY() - residentialCoord.getY());
    }
}