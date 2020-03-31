package iart.city_plan.solver;

import iart.city_plan.model.BuildingProject;
import iart.city_plan.model.City;
import iart.city_plan.util.structs.Coordinate;
import iart.city_plan.util.structs.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SimulatedAnnealingStrategy extends Strategy implements PopulationStrategy {
    private final Scorer scorer;
    private List<Solution> scoredPopulation;

    public SimulatedAnnealingStrategy(City city) {
        super(city);
        this.scorer = new Scorer(city.getMaxWalkingDistance());
    }

    @Override
    public Solution solve(List<BuildingProject> buildingProjects) {
        Random random = new Random();
        Solution current = generateSolution(buildingProjects);

        int iter = 0;
        long maxIter = 100;
        double temperature = 350;
        while (true) {
            temperature -= 0.5;
            Solution next = selectRandomSolution(current, buildingProjects, random);
            Pair<BuildingProject, List<Coordinate>> lastAdded = next.getLastAdded();
            addBuilding(lastAdded.getSecond().get(0), lastAdded.getFirst());

            int currentScore = scorer.score(current);
            int nextScore = scorer.score(next);
            System.out.println("Iteration: " + iter++ + " Temperature: " + temperature + " current score: " + currentScore + " next score: " + nextScore);
            if (temperature == 0.0) return current;


            int delta = nextScore - currentScore;
            double factor = delta / temperature;
            double prob = Math.exp(-factor);

            if (delta > 0) current = next;
            else if (prob >= Math.random()) {
                current = next;
            }
        }
    }

    private void addBuilding(Coordinate coordinate, BuildingProject buildingProject) {
        for (int row = 0; row < buildingProject.getRows(); ++row) {
            for (int col = 0; col < buildingProject.getColumns(); ++col) {
                int newCol = coordinate.getCol() + col;
                int newRow = coordinate.getRow() + row;

                Coordinate coord = new Coordinate(newRow, newCol);
                city.put(coord, buildingProject.getID());
            }
        }
    }

    private Solution generateSolution(List<BuildingProject> buildingProjects) {
        Random random = new Random();
        Solution solution = new Solution();

        List<BuildingProject> utilityProjects = new LinkedList<>();
        for (BuildingProject buildingProject : buildingProjects) {
            if (buildingProject.getType().equals("U")) {
                utilityProjects.add(buildingProject);
            }
        }

        return placeBuilding(new Coordinate(0, 0), utilityProjects.get(random.nextInt(utilityProjects.size())), solution);
    }

    private Solution selectRandomSolution(Solution current, List<BuildingProject> buildingProjects, Random random) {
        List<Solution> solutionList = generateNeighbourSolutions(current, buildingProjects);
        scoreAll(solutionList);

        return solutionList.get(random.nextInt(solutionList.size()));
    }

    private List<Solution> generateNeighbourSolutions(Solution current, List<BuildingProject> buildingProjects) {
        List<Solution> solutionList = new LinkedList<>();

        for (Map.Entry<Coordinate, Integer> cell : city.entrySet()) {
            if (cell.getValue() == -1) {
                for (BuildingProject buildingProject : buildingProjects) {
                    Solution solution = new Solution(current);
                    Solution temp = placeBuilding(cell.getKey(), buildingProject, solution);
                    if (temp != null) {
                        solutionList.add(temp);
                        removeBuilding(buildingProject, cell.getKey());
                    }
                }
                break;
            }
        }

        return solutionList;
    }

    private void removeBuilding(BuildingProject buildingProject, Coordinate coordinate) {
        for (int row = 0; row < buildingProject.getRows(); ++row) {
            for (int col = 0; col < buildingProject.getColumns(); ++col) {
                if (buildingProject.getPlan()[row].charAt(col) == '#') {
                    int newCol = coordinate.getCol() + col;
                    int newRow = coordinate.getRow() + row;

                    Coordinate newCoordinate = new Coordinate(newRow, newCol);
                    city.put(newCoordinate, -1);
                }
            }
        }
    }

    private double temperature(long l) {
        return Math.log(l);
    }

    @Override
    public List<Solution> generatePopulation(List<BuildingProject> buildingProjects, int populationSize) {
        List<Solution> population = new LinkedList<>();
        while (populationSize-- > 0) {
            System.out.println("Individuals left: " + populationSize);
            Solution candidateSolution = generateRandomSolution(buildingProjects);
            resetCity();
            population.add(candidateSolution);
        }

        scoredPopulation = scoreAll(population);

        return population;
    }

    @Override
    public List<Solution> scoreAll(List<Solution> population) {
        List<Solution> scores = new LinkedList<>();

        int scoreSum = 0;
        int maxScore = 0;
        for (Solution solution : population) {
            int score = scorer.score(solution);
            if (score > maxScore) maxScore = score;

            scoreSum += score;
            solution.setScore(score);

            scores.add(solution);
        }

        return scores;
    }
}
