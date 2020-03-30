package iart.city_plan.solver;

import iart.city_plan.model.BuildingProject;
import iart.city_plan.model.City;

import java.util.LinkedList;
import java.util.List;
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
        generatePopulation(buildingProjects, 150);

        Solution current = selectRandomSolution();

        int iter = 0;
        long maxIter = 100;
        double temperature = 100;
        while (iter < maxIter) {
            temperature -= 1;

            resetCity();
            Solution next = selectRandomSolution();
            int currentScore = scorer.score(current);
            int nextScore = scorer.score(next);
            System.out.println("Iteration: " + iter++ + " Temperature: " + temperature + " current score: " + currentScore + " next score: " + nextScore);
            if (temperature == 0.0) return current;


            int delta = nextScore - currentScore;
            double factor = delta / temperature;
            double prob = Math.exp(factor);

            if (delta > 0) current = next;
            else if (prob >= Math.random()) {
                current = next;
            }
        }

        return current;
    }

    private Solution selectRandomSolution() {
        Random random = new Random();
        return scoredPopulation.get(random.nextInt(scoredPopulation.size()));
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
        for (Solution solution : population) {
            int score = scorer.score(solution);
            System.out.println("Score: " + score);
            scoreSum += score;
            solution.setScore(score);

            scores.add(solution);
        }

        if (scoreSum == 0) {
            System.out.println("Something went really wrong (score sum was 0).");
            System.exit(1);
        }

        return scores;
    }
}
