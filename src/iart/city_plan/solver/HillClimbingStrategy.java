package iart.city_plan.solver;

import iart.city_plan.graph.Graph;
import iart.city_plan.graph.Vertex;
import iart.city_plan.model.BuildingProject;
import iart.city_plan.model.City;
import iart.city_plan.util.structs.Coordinate;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class HillClimbingStrategy extends Strategy {
    private Solution solution = new Solution();
    private Graph<Solution> graph = new Graph<>();
    private Scorer scorer;

    public HillClimbingStrategy(City city) {
        super(city);
        this.scorer = new Scorer(city.getMaxWalkingDistance());
    }

    @Override
    public Solution solve(List<BuildingProject> buildingProjects) {
        // generate random solution
        Solution candidateSolution = generateRandomSolution(buildingProjects);

        // generate neighbour solutions
        // List<Solution> solutionList = generateNeighbourSolutions(candidateSolution);

        // score neighbour solutions (check constraints)
        int score = Scorer.score(candidateSolution);
        System.out.println("Score: " + score);

        // for (Solution _solution : solutionList) {
        // }

        // use solution with highest score
        // back to top until no highest scored solution

        return candidateSolution;
    }

    private List<Solution> generateNeighbourSolutions(Solution candidateSolution) {
        List<Solution> solutionList = new LinkedList<>();
        Vertex<Solution> candidate = new Vertex<>(candidateSolution);

        graph.addVertex(candidateSolution);

//        for (Operator operator : operators) {
//            Solution neighbour = operator.apply(candidateSolution);
//            solutionList.add(neighbour);
//        }

        for (Solution solution : solutionList) {
            Vertex<Solution> added = graph.addVertex(solution);
            if (added != null)
                graph.addEdge(candidate, added, 0);
        }

        return solutionList;
    }

    private Solution generateRandomSolution(List<BuildingProject> buildingProjects) {
        Solution candidateSolution = new Solution();

        Random random = new Random();
        int attempts = 0;
        while (attempts++ < 100)
            for (BuildingProject buildingProject : buildingProjects) {
                Coordinate coordinate = getCoordinate(random);
                int counter = 0;

                while (placeBuilding(coordinate, buildingProject, candidateSolution) == null && counter++ < 10) {
                    coordinate = getCoordinate(random);
                }
            }

        return candidateSolution;
    }

    private Coordinate getCoordinate(Random random) {
        int row = random.nextInt(super.rows);
        int col = random.nextInt(super.columns);
        Coordinate coordinate = new Coordinate(row, col);
        System.out.println("getCoordinate: " + coordinate);
        return coordinate;
    }

    private Solution placeBuilding(Coordinate coordinate, BuildingProject buildingProject, Solution solution) {
        List<Coordinate> coordsToFill = new LinkedList<>();
        System.out.println("Attempting to place building with id " + buildingProject.getID() + " in coordinate " + coordinate);

        for (int row = 0; row < buildingProject.getRows(); ++row) {
            for (int col = 0; col < buildingProject.getColumns(); ++col) {
                int newCol = coordinate.getCol() + col;
                int newRow = coordinate.getRow() + row;

                Coordinate candidateCoord = new Coordinate(newRow, newCol);
                System.out.println("\tTrying cell " + candidateCoord);
                if (newRow >= rows || newCol >= columns || !super.city.get(candidateCoord).equals(-1))
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
            System.out.println("\tPlaced on cell " + coordToFill);
            city.put(coordToFill, buildingProject.getID());
        }


        return solution;
    }
}
