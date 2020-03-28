package iart.city_plan.solver;

import iart.city_plan.graph.Graph;
import iart.city_plan.graph.Vertex;
import iart.city_plan.model.BuildingProject;
import iart.city_plan.model.City;

import java.util.LinkedList;
import java.util.List;

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
        int score = scorer.score(candidateSolution);
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


}
