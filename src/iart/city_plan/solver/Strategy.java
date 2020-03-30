package iart.city_plan.solver;

import iart.city_plan.model.BuildingProject;
import iart.city_plan.model.City;
import iart.city_plan.util.structs.Coordinate;

import java.util.*;

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

    public void resetCity() {
        fillCity(rows, columns);
    }

    protected Solution generateRandomSolution(List<BuildingProject> buildingProjects) {
        Solution candidateSolution = new Solution();

        Random random = new Random();
        int attempts = 0;

            if (random.nextInt() % 2 == 0) {
                while (attempts++ < 250) {
                    for (BuildingProject buildingProject : buildingProjects) {
                        Coordinate coordinate = getCoordinate(random);
                        int counter = 0;

                        while (placeBuilding(coordinate, buildingProject, candidateSolution) == null && counter++ < 10) {
                            coordinate = getCoordinate(random);
                        }
                    }
                }
            } else {
                while (attempts++ < 250) {
                    for (Coordinate coordinate : city.keySet()) {
                        int counter = 0;

                        BuildingProject buildingProject = buildingProjects.get(random.nextInt(buildingProjects.size()));
                        while (placeBuilding(coordinate, buildingProject, candidateSolution) == null && counter++ < 10) {
                            coordinate = getCoordinate(random);
                        }
                    }
                }
            }

        return candidateSolution;
    }

    protected Coordinate getCoordinate(Random random) {
        int row = random.nextInt(rows);
        int col = random.nextInt(columns);
        return new Coordinate(row, col);
    }

    protected Solution placeBuilding(Coordinate coordinate, BuildingProject buildingProject, Solution solution) {
        List<Coordinate> coordsToFill = new LinkedList<>();
//        System.out.println("Attempting to place building with id " + buildingProject.getID() + " in coordinate " + coordinate);

        for (int row = 0; row < buildingProject.getRows(); ++row) {
            for (int col = 0; col < buildingProject.getColumns(); ++col) {
                int newCol = coordinate.getCol() + col;
                int newRow = coordinate.getRow() + row;

                Coordinate candidateCoord = new Coordinate(newRow, newCol);
//                System.out.println("\tTrying cell " + candidateCoord);
                if (newRow >= rows || newCol >= columns || (!city.get(candidateCoord).equals(-1) && buildingProject.getPlan()[row].charAt(col) == '#'))
                    return null;

                if (buildingProject.getPlan()[row].charAt(col) != '.') {
                    coordsToFill.add(candidateCoord);
                }
            }
        }

        Coordinate coordinate1 = coordsToFill.get(0);
        BuildingProject bp = new BuildingProject(buildingProject);
        bp.setCoordinate(coordinate1);
        bp.place();

        solution.addBuilding(bp, coordinate);
        for (Coordinate coordToFill : coordsToFill) {
//            System.out.println("\tPlaced on cell " + coordToFill);
            city.put(coordToFill, buildingProject.getID());
        }

        return solution;
    }

    public abstract Solution solve(List<BuildingProject> buildingProjects);
}
