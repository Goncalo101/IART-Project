package iart.city_plan.solver;

import iart.city_plan.model.BuildingProject;
import iart.city_plan.model.City;
import iart.city_plan.util.structs.Coordinate;
import iart.city_plan.util.structs.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class HillClimbingStrategy extends Strategy {
    private Scorer scorer;

    public HillClimbingStrategy(City city) {
        super(city);
        this.scorer = new Scorer(city.getMaxWalkingDistance());
    }

    @Override
    public Solution solve(List<BuildingProject> buildingProjects) {

        Solution current = generateSolution(buildingProjects);
        scorer.score(current);

        int iter = 0;
        while (iter < 500) {
            System.out.println("Iteration: " + iter++);
            List<Solution> solutionList = generateNeighbourSolutions(current, buildingProjects);
            scoreAll(solutionList);
            Solution neighbor = bestSolution(solutionList);

            Pair<BuildingProject, List<Coordinate>> lastAdded = neighbor.getLastAdded();
            addBuilding(lastAdded.getSecond().get(0), lastAdded.getFirst());

            if (neighbor.getScore() <= current.getScore()) return current;
            current = neighbor;
        }

        return current;
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
        return placeBuilding(new Coordinate(0, 0), buildingProjects.get(random.nextInt(buildingProjects.size())), solution);
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

        System.out.println("Max score was: " + maxScore);

        return scores;
    }
}
