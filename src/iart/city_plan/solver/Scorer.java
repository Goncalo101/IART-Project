package iart.city_plan.solver;

import iart.city_plan.model.BuildingProject;
import iart.city_plan.util.structs.Coordinate;
import iart.city_plan.util.structs.Pair;

import java.util.*;

public class Scorer {
    private static int maxWalkingDistance;

    public Scorer(int maxWalkingDistance) {
        Scorer.maxWalkingDistance = maxWalkingDistance;
    }

    public static int score(Solution solution) {
        int score = 0;
        List<Pair<BuildingProject, List<Coordinate>>> city = solution.getSolutions();

        List<Pair<BuildingProject, List<Coordinate>>> residentialBuildings = new LinkedList<>();
        List<Pair<BuildingProject, List<Coordinate>>> utilityBuildings = new LinkedList<>();

        for (Pair<BuildingProject, List<Coordinate>> building : city) {
            BuildingProject project = building.getT();
            List<Coordinate> coordinates = building.getU();

            if (project.getType().equals("R")) {
                residentialBuildings.add(new Pair<>(project, coordinates));
            } else if (project.getType().equals("U")) {
                utilityBuildings.add(new Pair<>(project, coordinates));
            }
        }

        for (Pair<BuildingProject, List<Coordinate>> residentialBuilding : residentialBuildings) {
            int numUtilities = computeBuildingsInRange(residentialBuilding.getU(), utilityBuildings);
            score += numUtilities * residentialBuilding.getT().getCapacity();
        }

        return score;
    }

    private static int computeBuildingsInRange(List<Coordinate> residentialCoords, List<Pair<BuildingProject, List<Coordinate>>> utilityBuildings) {
        Set<BuildingProject> buildings = new HashSet<>();
        for (Pair<BuildingProject, List<Coordinate>> utilityBuilding : utilityBuildings) {
            int minDistance = Integer.MAX_VALUE;
            for (Coordinate residentialCoord : residentialCoords) {
                for (Coordinate utilityCoord : utilityBuilding.getU()) {
                    int distance = computeDistance(utilityCoord, residentialCoord);
                    if (distance <= maxWalkingDistance && distance < minDistance) {
                        minDistance = distance;
                        buildings.add(utilityBuilding.getT());
                    }
                }
            }
        }

        return buildings.size();
    }

    private static int computeDistance(Coordinate utilityCoord, Coordinate residentialCoord) {
        return Math.abs(utilityCoord.getRow() - residentialCoord.getRow()) + Math.abs(utilityCoord.getCol() - residentialCoord.getCol());
    }
}