package iart.city_plan.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

import iart.city_plan.model.BuildingProject;
import iart.city_plan.model.City;

public class InputProcessor {

    private City city;
    private List<BuildingProject> projs = new LinkedList<>();

    public void openFile() throws Exception {

        // Open File
        File file = new File("input/a_example.in");

        BufferedReader br = new BufferedReader(new FileReader(file));

        // City
        String line = br.readLine();
        String[] aux = line.split(" ");

        this.city = new City(Integer.parseInt(aux[0]), Integer.parseInt(aux[1]), Integer.parseInt(aux[2]));

        int numProjects = Integer.parseInt(aux[3]);
        // Projects
        String type;
        int rows, columns, capacity;

        for (int i = 0; i < numProjects; i++) {

            // Proj info
            line = br.readLine();
            aux = line.split(" ");

            type = aux[0];
            rows = Integer.parseInt(aux[1]);
            columns = Integer.parseInt(aux[2]);
            capacity = Integer.parseInt(aux[3]);

            // Proj plan
            String[] projPlan = new String[rows];

            for (int j = 0; j < rows; j++) {
                projPlan[j] = br.readLine();
                System.out.println(projPlan[j]);
            }
            System.out.println();

            projs.add(new BuildingProject(type, rows, columns, capacity, projPlan));
        }

        br.close();
    }

    public City getCity(){
        return this.city;
    }

    public List<BuildingProject> getProjs(){
        return this.projs;
    }
}
