package iart.city_plan.solver;

import iart.city_plan.model.BuildingProject;
import iart.city_plan.model.City;
import iart.city_plan.util.structs.Coordinate;
import iart.city_plan.util.structs.Pair;

import java.util.*;

public class GeneticStrategy extends Strategy {
    private final Scorer scorer;
    private List<Solution> probabilities = new LinkedList<>();

    public GeneticStrategy(City city) {
        super(city);
        this.scorer = new Scorer(city.getMaxWalkingDistance());
    }

    @Override
    public Solution solve(List<BuildingProject> buildingProjects) {
        int populationSize = 20;
        List<Solution> population = generatePopulation(buildingProjects, populationSize);

        return geneticAlgorithm(population);
    }

    private Solution geneticAlgorithm(List<Solution> population) {
        Random random = new Random();
        long startTime = System.currentTimeMillis();
        int iter = 0;

        do {
            System.out.println("Iteration: " + iter++);
            List<Solution> newPopulation = new LinkedList<>();
            for (Solution solution : population) {
                solution.sort();
            }

            for (int i = 0; i < population.size(); ++i) {
                Solution parent1 = randomSelection(random);
                Solution parent2 = randomSelection(random);
                Solution child = reproduce(parent1, parent2, random);

                // if (mutation condition) mutate(child)
                newPopulation.add(child);
            }

            population = newPopulation;
            scoreAll(population);

        } while ((System.currentTimeMillis() - startTime) < 1800000);

        return bestSolution(population);
    }

    private Solution bestSolution(List<Solution> population) {
        int score = 0;
        Solution best = null;

        for (Solution solution : population) {
            int solutionScore = solution.getScore();
            if (solutionScore > score) {
                score = solutionScore;
                best = solution;
            }
        }

        return best;
    }

    private Solution randomSelection(Random random) {
        return probabilities.get(random.nextInt(probabilities.size()));
    }

    private List<Solution> scoreAll(List<Solution> population) {
        List<Solution> scores = new LinkedList<>();

        int scoreSum = 0;
        for (Solution solution : population) {
            int score = scorer.score(solution);
            scoreSum += score;
            solution.setScore(score);

            scores.add(solution);
        }

        if (scoreSum == 0) {
            System.out.println("Something went really wrong (score sum was 0).");
            System.exit(1);
        }

        probabilities.clear();
        for (Solution solution : scores) {
            int solutionScore = solution.getScore();
            int probability = (solutionScore * 100) / scoreSum;
            solution.setProbability(probability);

            for (int i = 0; i < probability; ++i) {
                probabilities.add(solution);
            }
        }

        return scores;
    }

    private Solution reproduce(Solution parent1, Solution parent2, Random random) {
        resetCity();
        Coordinate cutCoordinate = getCoordinate(random);

        Solution child = new Solution();

        List<Pair<BuildingProject, List<Coordinate>>> projects1 = parent1.getSolution();
        List<Pair<BuildingProject, List<Coordinate>>> projects2 = parent2.getSolution();

        int numProjects1 = projects1.size();
        int numProjects2 = projects2.size();

        if (numProjects1 > numProjects2) {
            List<Pair<BuildingProject, List<Coordinate>>> temp = projects1;
            projects1 = projects2;
            projects2 = temp;
        }

        for (Pair<BuildingProject, List<Coordinate>> projects : projects1) {
            Coordinate current = projects.getSecond().get(0);
            if ((current.getRow() == cutCoordinate.getRow()) && (current.getCol() > cutCoordinate.getCol()) || (current.getRow() > cutCoordinate.getRow())) {
                break;
            } else {
                placeBuilding(projects.getSecond().get(0), projects.getFirst(), child);
            }
        }

        for (Pair<BuildingProject, List<Coordinate>> projects : projects2) {
            Coordinate current = projects.getSecond().get(0);
            if ((current.getRow() == cutCoordinate.getRow()) && (current.getCol() > cutCoordinate.getCol()) || (current.getRow() > cutCoordinate.getRow())) {
                placeBuilding(projects.getSecond().get(0), projects.getFirst(), child);
            }
        }

        return child;
    }

    private List<Solution> generatePopulation(List<BuildingProject> buildingProjects, int populationSize) {
        List<Solution> population = new LinkedList<>();
        while (populationSize-- > 0) {
            Solution candidateSolution = generateRandomSolution(buildingProjects);
            resetCity();
            population.add(candidateSolution);
        }

        scoreAll(population);

        return population;
    }
}
