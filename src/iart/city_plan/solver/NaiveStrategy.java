package iart.city_plan.solver;

import iart.city_plan.model.BuildingProject;
import iart.city_plan.model.City;
import iart.city_plan.util.structs.Coordinate;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class NaiveStrategy extends Strategy {

    private Solution solution = new Solution();

    public NaiveStrategy(City city) {
        super(city);
    }

    @Override
    public Solution solve(List<BuildingProject> buildingProjects) {
        for (BuildingProject buildingProject : buildingProjects) {
            placeBuilding(buildingProject);
        }

        return solution;
    }

    private void placeBuilding(BuildingProject buildingProject) {
        Coordinate firstAvailable = new Coordinate(0, 0);
        List<Coordinate> coordsToFill = new LinkedList<>();
        String[] plan = buildingProject.getPlan();

        for (Map.Entry<Coordinate, Integer> position : super.city.entrySet())
            if (position.getValue() == 0) {
                firstAvailable = position.getKey();
                break;
            }

        for (int row = 0; row < buildingProject.getRows(); ++row) {
            for (int col = 0; col < buildingProject.getColumns(); ++col) {
                int x = firstAvailable.getCol() + col;
                int y = firstAvailable.getRow() + row;

                Coordinate candidateCoord = new Coordinate(x, y);
                if (!super.city.get(candidateCoord).equals(0) || x >= super.rows || y >= super.columns)
                    return;

                if (plan[row].charAt(col) != '.') {
                    coordsToFill.add(candidateCoord);
                }
            }
        }

        this.solution.addBuilding(buildingProject, coordsToFill.get(0));
        for (Coordinate coordinate : coordsToFill) {
            super.city.put(coordinate, buildingProject.getID());
        }
    }
}
