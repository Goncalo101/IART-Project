package iart.city_plan;

import iart.city_plan.solver.HillClimbingStrategy;
import iart.city_plan.solver.Solution;
import iart.city_plan.solver.Strategy;
import iart.city_plan.util.io.InputProcessor;
import iart.city_plan.util.io.OutputProcessor;

import java.io.IOException;

public class CityPlan {
    public static void main(String[] args) throws IOException {
        String inputFileName = args[0];
        InputProcessor ip = new InputProcessor(inputFileName);

        Strategy strategy = new HillClimbingStrategy(ip.getCity());
        Solution solution = strategy.solve(ip.getBuildingProjects());

        System.out.println(solution);

        String outputFileName = args[1];
        OutputProcessor op = new OutputProcessor(outputFileName, solution);
        op.writeSolution(solution);
    }
}
