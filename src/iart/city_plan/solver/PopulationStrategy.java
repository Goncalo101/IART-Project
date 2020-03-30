package iart.city_plan.solver;

import iart.city_plan.model.BuildingProject;

import java.util.List;

public interface PopulationStrategy {
    List<Solution> generatePopulation(List<BuildingProject> buildingProjects, int populationSize);
    List<Solution> scoreAll(List<Solution> population);
}
