package iart.city_plan;

import iart.city_plan.solver.*;
import iart.city_plan.util.io.InputProcessor;
import iart.city_plan.util.io.OutputProcessor;

import java.io.IOException;

public class CityPlan {
    public static void main(String[] args) throws IOException {
        String inputFileName = args[1];
        InputProcessor ip = new InputProcessor(inputFileName);

        Strategy strategy = null;

        if(args[0].equals("hillClimbing")){
            strategy = new HillClimbingStrategy(ip.getCity());
        } else if(args[0].equals("simulatedAnnealing")){
            strategy = new SimulatedAnnealingStrategy(ip.getCity());
        } else if(args[0].equals("genetic")){
            strategy = new GeneticStrategy(ip.getCity());
        } else{
            System.out.println("Invalid strategy. Options: hillClimbing, simulatedAnnealing or genetic");
            System.exit(-1);
        }

        Solution solution = strategy.solve(ip.getBuildingProjects());

        System.out.println(solution);

        String outputFileName = args[2];
        OutputProcessor op = new OutputProcessor(outputFileName, solution);
        op.writeSolution(solution);
    }
}
