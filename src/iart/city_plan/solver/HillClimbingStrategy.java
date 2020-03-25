package iart.city_plan.solver;

import iart.city_plan.model.BuildingProject;
import iart.city_plan.model.City;

import java.util.List;

public class HillClimbingStrategy extends Strategy {
    public HillClimbingStrategy(City city) {
        super(city);
    }

    @Override
    public Solution solve(List<BuildingProject> buildingProjects) {
        return null;
    }
}
