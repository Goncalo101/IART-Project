package iart.city_plan.util.io;

import iart.city_plan.model.BuildingProject;
import iart.city_plan.model.City;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class InputProcessor {

    private City city;
    private List<BuildingProject> buildingProjects = new LinkedList<>();
    private int numProjects;

    public InputProcessor(String inputFileName) throws IOException {
        File file = new File(inputFileName);
        BufferedReader br = new BufferedReader(new FileReader(file));
        loadCity(br);
        loadProjects(br);

        showInfo();

        br.close();
    }

    private void showInfo() {
        System.out.println(city);
        for (BuildingProject project : this.buildingProjects)
            System.out.println(project);
    }

    private void loadProjects(BufferedReader br) throws IOException {
        for (int i = 0; i < this.numProjects; i++) {
            // Extract project info
            String line = br.readLine();
            String[] splitInput = line.split(" ");

            String type = splitInput[0];
            int rows = Integer.parseInt(splitInput[1]);
            int columns = Integer.parseInt(splitInput[2]);
            int capacity = Integer.parseInt(splitInput[3]);

            // Extract project plan
            String[] projPlan = new String[rows];

            for (int j = 0; j < rows; j++) {
                projPlan[j] = br.readLine();
            }

            BuildingProject project = new BuildingProject(type, rows, columns, capacity, projPlan);
            this.buildingProjects.add(project);
        }
    }

    private void loadCity(BufferedReader br) throws IOException {
        String[] splitInput = br.readLine().split(" ");

        int rows = Integer.parseInt(splitInput[0]);
        int columns = Integer.parseInt(splitInput[1]);
        int maxWalkingDistance = Integer.parseInt(splitInput[2]);
        int numProjects = Integer.parseInt(splitInput[3]);

        this.city = new City(rows, columns, maxWalkingDistance);
        this.numProjects = numProjects;
    }

    public City getCity() {
        return this.city;
    }

    public List<BuildingProject> getBuildingProjects() {
        return this.buildingProjects;
    }
}
